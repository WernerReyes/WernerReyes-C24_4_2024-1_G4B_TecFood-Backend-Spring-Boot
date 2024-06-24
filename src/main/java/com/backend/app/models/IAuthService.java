package com.backend.app.models;

import com.backend.app.models.dtos.auth.LoginGoogleUserDto;
import com.backend.app.models.dtos.auth.LoginUserDto;
import com.backend.app.models.dtos.auth.RegisterUserDto;
import com.backend.app.models.responses.auth.LoginUserResponse;
import com.backend.app.models.responses.auth.RegisterUserResponse;

public interface IAuthService {
    LoginUserResponse login(LoginUserDto loginUserDto) throws Exception;
    LoginUserResponse loginGoogle(LoginGoogleUserDto loginGoogleUserDto) throws Exception;
    RegisterUserResponse register(RegisterUserDto registerUserDto);
    LoginUserResponse renovateToken(String expiredToken) throws Exception;
    LoginUserResponse revalidateToken() throws Exception;
}
