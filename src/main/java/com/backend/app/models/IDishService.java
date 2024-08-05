package com.backend.app.models;

import com.backend.app.models.dtos.requests.common.UpdateStatusRequest;
import com.backend.app.models.dtos.requests.common.UploadImagesRequest;
import com.backend.app.models.dtos.requests.dish.*;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.models.dtos.responses.common.PagedResponse;
import com.backend.app.persistence.entities.DishEntity;
import com.backend.app.persistence.entities.DishImageEntity;

import java.util.List;

public interface IDishService {
    ApiResponse<DishEntity> create(CreateDishRequest createDishDto, UploadImagesRequest uploadImagesRequest);
    ApiResponse<DishEntity> putOffer(PutDishOfferRequest putDishOfferRequest);
    ApiResponse<DishEntity> deleteOffer(Long dishId);
    ApiResponse<List<DishEntity>> deleteManyOffers(List<Long> dishesId);
    ApiResponse<DishEntity> update(UpdateDishRequest updateDishDto);
    ApiResponse<List<DishImageEntity>> updateImage(UpdateDishImageRequest updateDishImageRequest);
    ApiResponse<DishEntity> updateStatus(UpdateStatusRequest updateStatusRequest);
    ApiResponse<Void> delete(Long dishId);
    ApiResponse<Void> deleteMany(List<Long> dishesId);
    ApiResponse<PagedResponse<List<DishEntity>>> findAllPaginated(FindDishesRequest findDishesRequest);
    ApiResponse<List<DishEntity>> findAll();
    ApiResponse<List<DishEntity>> findAllPublished();
    ApiResponse<List<DishEntity>> findAllWithoutSelectedDish(FindDishesWithoutSelectedDishRequest findDishesWithoutSelectedDishDto);
    ApiResponse<DishEntity> findById(Long id);
    ApiResponse<DishEntity> findByName(String name);

    //* Methods to use only in services
    void deactivateExpiredOffers();
}
