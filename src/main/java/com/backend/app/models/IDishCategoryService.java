package com.backend.app.models;

import com.backend.app.models.dtos.requests.common.UploadImagesRequest;
import com.backend.app.models.dtos.requests.dishCategory.CreateDishCategoryRequest;
import com.backend.app.models.dtos.requests.dishCategory.UpdateDishCategoryRequest;
import com.backend.app.models.dtos.requests.dishCategory.UpdateDishCategoryImageRequest;
import com.backend.app.models.dtos.requests.common.UpdateStatusRequest;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.persistence.entities.DishCategoryEntity;

import java.util.List;

public interface IDishCategoryService {
    ApiResponse<DishCategoryEntity> create(
            UploadImagesRequest uploadImagesRequest,
            CreateDishCategoryRequest createDishCategoryRequest
    );
    ApiResponse<DishCategoryEntity> update(UpdateDishCategoryRequest updateDishCategoryRequest);
    ApiResponse<DishCategoryEntity> updateImage(UpdateDishCategoryImageRequest updateDishCategoryImageRequest);
    ApiResponse<DishCategoryEntity> updateStatus(UpdateStatusRequest updateStatusRequest);
    ApiResponse<Void> delete(Long dishCategoryId);
    ApiResponse<Void> deleteMany(List<Long> dishCategoryIds);
    ApiResponse<List<DishCategoryEntity>> findAll();
    ApiResponse<List<DishCategoryEntity>> findAllPublished();
    ApiResponse<DishCategoryEntity> findById(Long dishCategoryId);
}
