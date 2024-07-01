package com.backend.app.models;

import com.backend.app.models.dtos.requests.auth.LoginGoogleRequest;
import com.backend.app.models.dtos.requests.auth.LoginRequest;
import com.backend.app.models.dtos.requests.auth.RegisterRequest;
import com.backend.app.models.dtos.responses.auth.*;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.persistence.entities.UserEntity;

public interface IAuthService {
    ApiResponse<LoginResponse> login(LoginRequest loginUserDto) throws Exception;
    ApiResponse<LoginResponse> loginGoogle(LoginGoogleRequest loginGoogleDto) throws Exception;
    ApiResponse<Void> register(RegisterRequest registerDto);
    ApiResponse<String> renovateToken(String expiredToken) throws Exception;
    ApiResponse<UserEntity> revalidateToken();
}
