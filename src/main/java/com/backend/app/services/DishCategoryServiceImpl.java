package com.backend.app.services;

import com.backend.app.exceptions.CustomException;
import com.backend.app.models.IDishCategoryService;
import com.backend.app.models.IUploadFileService;
import com.backend.app.models.dtos.requests.common.UploadImagesRequest;
import com.backend.app.models.dtos.requests.dishCategory.CreateDishCategoryRequest;
import com.backend.app.models.dtos.requests.dishCategory.UpdateDishCategoryRequest;
import com.backend.app.models.dtos.requests.dishCategory.UpdateDishCategoryImageRequest;
import com.backend.app.models.dtos.requests.common.UpdateStatusRequest;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.persistence.entities.DishCategoryEntity;
import com.backend.app.persistence.enums.EFileType;
import com.backend.app.persistence.enums.EResponseStatus;
import com.backend.app.persistence.enums.EStatus;
import com.backend.app.persistence.repositories.DishCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DishCategoryServiceImpl implements IDishCategoryService {

    private final DishCategoryRepository dishCategoryRepository;
    private final IUploadFileService uploadFileService;

    @Override
    public ApiResponse<DishCategoryEntity> create(
            UploadImagesRequest uploadImagesRequest,
            CreateDishCategoryRequest createDishCategoryRequest) {
        boolean existName = dishCategoryRepository.findByNameIgnoreCase(createDishCategoryRequest.getName().trim()) != null;
        if (existName) throw CustomException.badRequest("Category already exists");

        try {
            //* Upload image
            String imageUrl = uploadFileService.uploadFile(
                uploadImagesRequest.getFiles().get(0),
                uploadImagesRequest.getTypeFile()
            );

            DishCategoryEntity dishCategory = DishCategoryEntity
                .builder()
                .name(createDishCategoryRequest.getName())
                    .status(EStatus.PRIVATE)
                    .isUsed(false)
                .imageUrl(imageUrl)
                .build();

            dishCategoryRepository.save(dishCategory);

            return new ApiResponse<>(
                    EResponseStatus.SUCCESS,
                "Category created",
                dishCategory
            );

        } catch (Exception e) {
            throw CustomException.badRequest("Error uploading image");
        }
    }

    @Override
    public ApiResponse<DishCategoryEntity> update(UpdateDishCategoryRequest updateDishCategoryRequest) {
        DishCategoryEntity dishCategory = dishCategoryRepository.findById(updateDishCategoryRequest.getId()).orElse(null);
        if (dishCategory == null) throw CustomException.badRequest("Category not found");

        if (isTheSameData(dishCategory, updateDishCategoryRequest)) throw  CustomException.badRequest("No changes detected");

        boolean existName = dishCategoryRepository.findByNameIgnoreCaseAndIdNot(updateDishCategoryRequest.getName().trim(), updateDishCategoryRequest.getId()) != null;
        if (existName) throw CustomException.badRequest("Category already exists");

        dishCategory.setName(updateDishCategoryRequest.getName());
        dishCategoryRepository.save(dishCategory);

        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Category updated",
                dishCategory
        );
    }

    @Override
    public ApiResponse<DishCategoryEntity> updateImage(UpdateDishCategoryImageRequest updateDishCategoryImageRequest) {
        DishCategoryEntity dishCategory = dishCategoryRepository.findById(updateDishCategoryImageRequest.getDishCategoryId()).orElse(null);
        if (dishCategory == null) throw CustomException.badRequest("Category not found");

        try {
            //* Delete old image
            uploadFileService.deleteFile(dishCategory.getImageUrl(), updateDishCategoryImageRequest.getTypeFile());

            //* Upload image
            String imageUrl = uploadFileService.uploadFile(
                    updateDishCategoryImageRequest.getFiles().get(0),
                    updateDishCategoryImageRequest.getTypeFile()
            );
            
            dishCategory.setImageUrl(imageUrl);
            dishCategoryRepository.save(dishCategory);

            return new ApiResponse<>(
                    EResponseStatus.SUCCESS,
                    "Category image updated",
                    dishCategory
            );
        } catch (Exception e) {
            throw CustomException.badRequest("Error uploading image");
        }
    }

    @Override
    public ApiResponse<DishCategoryEntity> updateStatus(UpdateStatusRequest updateStatusRequest) {
        DishCategoryEntity dishCategory = dishCategoryRepository.findById(updateStatusRequest.getId()).orElse(null);
        if (dishCategory == null) throw CustomException.badRequest("Category not found");

        if (dishCategory.getStatus().equals(updateStatusRequest.getStatus()))
            throw CustomException.badRequest("Category already has the status: " + updateStatusRequest.getStatus());

        dishCategory.setStatus(updateStatusRequest.getStatus());
        dishCategoryRepository.save(dishCategory);

        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Category status updated",
                dishCategory
        );
    }

    @Override
    public ApiResponse<Void> delete(Long dishCategoryId) {
        DishCategoryEntity dishCategory = dishCategoryRepository.findById(dishCategoryId).orElse(null);
        if (dishCategory == null) throw CustomException.badRequest("Category not found");

        if(dishCategory.getIsUsed()) throw CustomException.badRequest("Category: " + dishCategory.getName() + " has dishes, can't be deleted");

        try {
            //* Delete image
            uploadFileService.deleteFile(dishCategory.getImageUrl(), EFileType.IMAGE);

            //* Delete category
            dishCategoryRepository.delete(dishCategory);

            return new ApiResponse<>(
                    EResponseStatus.SUCCESS,
                    "Category: " + dishCategory.getName() + " was deleted successfully",
                    null
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw CustomException.badRequest("Error deleting category");
        }
    }

    @Override
    public ApiResponse<Void> deleteMany(List<Long> dishCategoryIds) {
        List<DishCategoryEntity> dishCategories = dishCategoryRepository.findAllById(dishCategoryIds);
        if (dishCategories.isEmpty()) throw CustomException.badRequest("Categories not found");
        if (dishCategories.size() != dishCategoryIds.size()) throw CustomException.badRequest("Some categories not found");

        try {
            int count = 0;
            for (DishCategoryEntity dishCategory : dishCategories) {
                this.delete(dishCategory.getId());
                count++;
             }

            return new ApiResponse<>(
                    EResponseStatus.SUCCESS,
                    count + " categories were deleted successfully",
                    null
            );
        } catch (Exception e) {
            throw CustomException.badRequest(e.getMessage());
        }

    }

    @Override
    public ApiResponse<List<DishCategoryEntity>> findAll() {
        List<DishCategoryEntity> dishCategories = dishCategoryRepository.findAll();
        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Categories found",
                dishCategories
        );
    }

    @Override
    public ApiResponse<List<DishCategoryEntity>> findAllPublished() {
        List<DishCategoryEntity> dishCategories = dishCategoryRepository.findAllByStatus(EStatus.PUBLISHED);
        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Categories found",
                dishCategories
        );
    }

    @Override
    public ApiResponse<DishCategoryEntity> findById(Long dishCategoryId) {
        DishCategoryEntity dishCategory = dishCategoryRepository.findById(dishCategoryId).orElse(null);
        if (dishCategory == null) throw CustomException.badRequest("Category not found");

        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Category found",
                dishCategory
        );
    }

    private boolean isTheSameData(DishCategoryEntity dishCategory, UpdateDishCategoryRequest updateDishCategoryRequest) {
        return  dishCategory.getName().equalsIgnoreCase(updateDishCategoryRequest.getName().trim());
    }

}
