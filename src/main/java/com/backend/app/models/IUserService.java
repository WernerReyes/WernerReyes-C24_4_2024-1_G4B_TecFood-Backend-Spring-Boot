package com.backend.app.models;

import com.backend.app.models.dtos.requests.common.UploadImagesRequest;
import com.backend.app.models.dtos.requests.user.UpdateUserRequest;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.persistence.entities.UserEntity;
import java.util.List;

public interface IUserService {
    ApiResponse<List<UserEntity>> findAll();
    ApiResponse<UserEntity> findById(Long id);
    ApiResponse<UserEntity> updateUser (UpdateUserRequest updateUserRequest) throws Exception;
    ApiResponse<String>  uploadProfile (UploadImagesRequest uploadImagesDto) throws Exception;
}
