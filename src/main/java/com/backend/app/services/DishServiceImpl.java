package com.backend.app.services;

import com.backend.app.exceptions.CustomException;
import com.backend.app.models.IDishService;
import com.backend.app.models.IUploadFileService;
import com.backend.app.models.dtos.requests.common.UpdateStatusRequest;
import com.backend.app.models.dtos.requests.common.UploadImagesRequest;
import com.backend.app.models.dtos.requests.dish.*;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.models.dtos.responses.common.PagedResponse;
import com.backend.app.persistence.entities.DishCategoryEntity;
import com.backend.app.persistence.entities.DishEntity;
import com.backend.app.persistence.entities.DishImageEntity;
import com.backend.app.persistence.enums.EFileType;
import com.backend.app.persistence.enums.EResponseStatus;
import com.backend.app.persistence.enums.EStatus;
import com.backend.app.persistence.repositories.DishCategoryRepository;
import com.backend.app.persistence.repositories.DishImageRepository;
import com.backend.app.persistence.repositories.DishRepository;
import com.backend.app.persistence.specifications.DishSpecification;
import com.backend.app.utilities.UsageStatusUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DishServiceImpl implements IDishService {

    private final DishRepository dishRepository;
    private final DishCategoryRepository dishCategoryRepository;
    private final DishImageRepository dishImageRepository;

    private final IUploadFileService uploadFileService;

    private final UsageStatusUtility usageStatusUtility;

    @Scheduled(cron = "0 0 0 * * ?") //* Every day at midnight
    @Override
    public void deactivateExpiredOffers() {
        List<DishEntity> expiredDishes = dishRepository.findExpiredOffers(LocalDateTime.now());
        for (DishEntity dish : expiredDishes) {
            dish.setDiscountPercentage(null);
            dish.setDiscountPrice(null);
            dish.setSaleStartDate(null);
            dish.setSaleEndDate(null);
            dishRepository.save(dish);
        }
    }

    @Override
    public ApiResponse<DishEntity> create(CreateDishRequest createDishRequest, UploadImagesRequest uploadImagesRequest) {
        DishEntity dish = dishRepository.findByNameIgnoreCase(createDishRequest.getName().trim());
        if (dish != null) throw CustomException.badRequest("Product already exists");

        List<DishCategoryEntity> dishCategories = dishCategoryRepository.findAllByIdInAndStatus(
                createDishRequest.getCategoriesId(),
                EStatus.PUBLISHED
        );
        if (dishCategories.isEmpty()) throw CustomException.badRequest("Categories must be published");
        if (dishCategories.size() != createDishRequest.getCategoriesId().size()) throw CustomException.badRequest("Some categories not found");

        dish = DishEntity.builder()
                .name(createDishRequest.getName())
                .price(createDishRequest.getPrice())
                .categories(dishCategories)
                .stock(createDishRequest.getStock())
                .description(createDishRequest.getDescription())
                .status(EStatus.PRIVATE)
                .isUsed(false)
                .build();
        dishRepository.save(dish);

        //* Update categories usage status
        for (DishCategoryEntity dishCategory : dishCategories) {
            usageStatusUtility.updateDishCategoryUsageStatus(dishCategory);
        }

        //* Upload image
        List<DishImageEntity> dishImages = uploadDishImage(dish, uploadImagesRequest.getFiles());
        dish.setImages(dishImages);

        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Dish: " + dish.getName() + " created successfully",
                dish
        );
    }

    @Override
    public ApiResponse<DishEntity> putOffer(PutDishOfferRequest putDishOfferRequest) {
        DishEntity dish = dishRepository.findById(putDishOfferRequest.getDishId()).orElse(null);
        if (dish == null) throw CustomException.badRequest("Dish not found");

        dish.setDiscountPercentage(putDishOfferRequest.getDiscountPercentage());
        dish.setSaleStartDate(putDishOfferRequest.getSaleStartDate());
        dish.setSaleEndDate(putDishOfferRequest.getSaleEndDate());

        //* Calculate discount price rounded to 2 decimal places
        double discountPrice = dish.getPrice() - (dish.getPrice() * (dish.getDiscountPercentage() / 100));
        dish.setDiscountPrice(Math.round(discountPrice * 100.0) / 100.0);

        dishRepository.save(dish);

        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Dish offer created successfully",
                dish
        );
    }

    @Override
    public ApiResponse<DishEntity> deleteOffer(Long dishId) {
        DishEntity dish = dishRepository.findById(dishId).orElse(null);
        if (dish == null) throw CustomException.badRequest("Dish not found");

        dish.setDiscountPercentage(null);
        dish.setDiscountPrice(null);
        dish.setSaleStartDate(null);
        dish.setSaleEndDate(null);
        dishRepository.save(dish);

        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Dish offer deleted successfully",
                dish
        );
    }

    @Override
    public ApiResponse<List<DishEntity>> deleteManyOffers(List<Long> dishesId) {
        try {
            List<DishEntity> dishes = new ArrayList<>();
            for (Long dishId : dishesId) {
                DishEntity dish = this.deleteOffer(dishId).data();
                dishes.add(dish);
            }
            return new ApiResponse<>(
                    EResponseStatus.SUCCESS,
                    dishes.size() + " offers were deleted successfully",
                    dishes
            );
        } catch (Exception e) {
            throw CustomException.badRequest(e.getMessage());
        }
    }


    @Override
    public ApiResponse<DishEntity> update(UpdateDishRequest updateDishRequest) {
        DishEntity dish = dishRepository.findById(updateDishRequest.getDishId()).orElse(null);
        if (dish == null) throw CustomException.badRequest("Product not found");

        //* Check if the data is the same
        if (isTheSameData(dish, updateDishRequest)) throw CustomException.badRequest("You must change at least one field");

        //* Check if the name already exists
        DishEntity dishByName = dishRepository.findByNameIgnoreCase(updateDishRequest.getName().trim());
        if (dishByName != null && !dishByName.getId().equals(dish.getId())) throw CustomException.badRequest("Product already exists");

        List<DishCategoryEntity> dishCategories = dishCategoryRepository.findAllByIdInAndStatus(
                updateDishRequest.getCategoriesId(),
                EStatus.PUBLISHED
        );
        if (dishCategories.isEmpty()) throw CustomException.badRequest("Categories must be published");


        dish.setName(updateDishRequest.getName().trim());
        dish.setPrice(updateDishRequest.getPrice());
        dish.setCategories(dishCategories);
        dish.setStock(updateDishRequest.getStock());
        dish.setDescription(updateDishRequest.getDescription());
        dishRepository.save(dish);

        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Dish updated successfully",
                dish
        );
    }

    @Override
    public ApiResponse<List<DishImageEntity>> updateImage(UpdateDishImageRequest updateDishImageRequest) {
        DishEntity dish = dishRepository.findById(updateDishImageRequest.getDishId()).orElse(null);
        if (dish == null) throw CustomException.badRequest("Product not found");

        //* Delete images depending on the quantity of images in the request
        List<DishImageEntity> dishImages;
        if (updateDishImageRequest.getImageIdToUpdate() != null) {
            dishImages = updateDishImage(updateDishImageRequest);
        } else {
            dishImages = uploadDishImage(dish, updateDishImageRequest.getFiles());
        }

        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Dish image updated successfully",
                dishImages
        );
    }

    @Override
    public ApiResponse<DishEntity> updateStatus(UpdateStatusRequest updateStatusRequest) {
        DishEntity dish = dishRepository.findById(updateStatusRequest.getId()).orElse(null);
        if (dish == null) throw CustomException.badRequest("Dish not found");

        if (dish.getStatus().equals(updateStatusRequest.getStatus()))
            throw CustomException.badRequest("Dish already has the status: " + updateStatusRequest.getStatus());

        dish.setStatus(updateStatusRequest.getStatus());
        dishRepository.save(dish);

        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Dish status updated successfully",
                dish
        );
    }

    @Override
    public ApiResponse<Void> delete(Long dishId) {
        DishEntity dish = dishRepository.findById(dishId).orElse(null);
        if (dish == null) throw CustomException.badRequest("Dish not found");

        if (dish.getIsUsed()) throw CustomException.badRequest("Dish is used in orders or carts, can't be deleted");

        List<DishCategoryEntity> dishCategories = new ArrayList<>(dish.getCategories());

        try {
            for (DishImageEntity image : dish.getImages()) {
                uploadFileService.deleteFile(image.getUrl(), EFileType.IMAGE);
            }

            dishRepository.delete(dish);

            //* Update categories usage status
            for (DishCategoryEntity dishCategory : dishCategories) {
                usageStatusUtility.updateDishCategoryUsageStatus(dishCategory);
            }

            return new ApiResponse<>(
                    EResponseStatus.SUCCESS,
                    "Dish deleted successfully",
                    null
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw CustomException.badRequest("Error deleting dish: " + dish.getName());
        }
    }

    @Override
    public ApiResponse<Void> deleteMany(List<Long> dishesId) {
        List<DishEntity> dishes = dishRepository.findAllById(dishesId);
        if (dishes.isEmpty()) throw CustomException.badRequest("Dishes not found");
        if (dishes.size() != dishesId.size()) throw CustomException.badRequest("Some dishes not found");

        try {
            int count = 0;
            for (DishEntity dish : dishes) {
                this.delete(dish.getId());
                count++;
            }

            return new ApiResponse<>(
                    EResponseStatus.SUCCESS,
                    count + " dishes were deleted successfully",
                    null
            );
        } catch (Exception e) {
            throw CustomException.badRequest(e.getMessage());
        }
    }

    @Override
    public ApiResponse<PagedResponse<List<DishEntity>>> findAllPaginated(FindDishesRequest findDishesRequest) {
        Pageable pageable = PageRequest.of(findDishesRequest.getPage() - 1, findDishesRequest.getLimit());
        Page<DishEntity> dishes = applySpecsAndPagination(pageable, findDishesRequest);
        int total = (int) dishes.getTotalElements();

        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "All dishes paginated",
                new PagedResponse<>(
                        dishes.getContent(),
                        findDishesRequest.getPage(),
                        dishes.getTotalPages(),
                        findDishesRequest.getLimit(),
                        total,
                        dishes.hasNext() ? "/api/dish?page=" + (findDishesRequest.getPage() + 1) + "&limit=" + findDishesRequest.getLimit() : null,
                        dishes.hasPrevious() ? "/api/dish?page=" + (findDishesRequest.getPage() - 1) + "&limit=" + findDishesRequest.getLimit() : null
                )
        );
    }

    @Override
    public ApiResponse<List<DishEntity>> findAll() {
        List<DishEntity> dishes = dishRepository.findAll();
        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "All dishes",
                dishes
        );
    }

    @Override
    public ApiResponse<List<DishEntity>> findAllPublished() {
        List<DishEntity> dishes = dishRepository.findAllByStatus(EStatus.PUBLISHED);
        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "All published dishes",
                dishes
        );
    }

    @Override
    public ApiResponse<List<DishEntity>> findAllWithoutSelectedDish(FindDishesWithoutSelectedDishRequest findDishesWithoutSelectedDishRequest) {
        Specification<DishEntity> spec = Specification.where(
                DishSpecification.idNotEqual(findDishesWithoutSelectedDishRequest.getSelectedDishId())
        );

        Pageable pageable = PageRequest.of(findDishesWithoutSelectedDishRequest.getPage() - 1, findDishesWithoutSelectedDishRequest.getLimit());
        Page<DishEntity> dishes = dishRepository.findAll(spec, pageable);

        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "All dishes without selected dish",
                dishes.getContent()
        );
    }

    @Override
    public ApiResponse<DishEntity> findById(Long id) {
        DishEntity dish = dishRepository.findById(id).orElse(null);
        if (dish  == null) throw CustomException.badRequest("Dish not found");

        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Dish found",
                dish
        );
    }

    @Override
    public ApiResponse<DishEntity> findByName(String name) {
        DishEntity dish = dishRepository.findByName(name);
        if (dish  == null) throw CustomException.badRequest("Dish not found");

        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Dish found",
                dish
        );
    }


    private Page<DishEntity> applySpecsAndPagination(Pageable pageable, FindDishesRequest findDishesRequest) {
        Specification<DishEntity> spec = Specification.where(
                DishSpecification.statusEqual(EStatus.PUBLISHED)
        ).and(
                DishSpecification.idDishCategoryIn(findDishesRequest.getIdCategory())
        ).and(
                DishSpecification.priceBetween(findDishesRequest.getMin(), findDishesRequest.getMax())
        ).and(
                DishSpecification.nameContaining(findDishesRequest.getSearch())
        );
        return dishRepository.findAll(spec, pageable);
    }

    private List<DishImageEntity> uploadDishImage(DishEntity dish, List<MultipartFile> files) {
        List<String> images = uploadFileService.uploadFiles(files, EFileType.IMAGE);
        for (String image : images) {
            DishImageEntity dishImage = DishImageEntity.builder()
                    .url(image)
                    .dish(dish)
                    .build();
            dishImageRepository.save(dishImage);
        }

        return dishImageRepository.findAllByDish(dish);
    }

    private List<DishImageEntity> updateDishImage(UpdateDishImageRequest updateDishImageRequest) {
        DishImageEntity image = dishImageRepository.findById(updateDishImageRequest.getImageIdToUpdate()).orElse(null);
        if (image == null) throw CustomException.badRequest("Image not found");

        //* Delete image from cloudinary
        uploadFileService.deleteFile(image.getUrl(), EFileType.IMAGE);

        //* Upload new image
        String url = uploadFileService.uploadFile(updateDishImageRequest.getFiles().get(0), EFileType.IMAGE);
        image.setUrl(url);
        dishImageRepository.save(image);

        return dishImageRepository.findAllByDish(image.getDish());
    }


    private boolean isTheSameData(DishEntity dish, UpdateDishRequest updateDishRequest) {
        return (dish.getName().equals(updateDishRequest.getName())) &&
                (dish.getPrice().equals(updateDishRequest.getPrice())) &&
                (dish.getStock().equals(updateDishRequest.getStock())) &&
                (dish.getCategories().stream().map(DishCategoryEntity::getId).toList().equals(updateDishRequest.getCategoriesId())) &&
                (dish.getDescription().equals(updateDishRequest.getDescription()));
    }



}
