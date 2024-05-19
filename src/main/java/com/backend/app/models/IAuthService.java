package com.backend.app.models;

import com.backend.app.models.dtos.auth.LoginGoogleUserDto;
import com.backend.app.models.dtos.auth.LoginUserDto;
import com.backend.app.models.dtos.auth.RegisterUserDto;
import com.backend.app.models.responses.auth.LoginUserResponse;
import com.backend.app.models.responses.auth.RegisterUserResponse;

public interface IAuthService {
    public LoginUserResponse login(LoginUserDto loginUserDto) throws Exception;
    public LoginUserResponse loginGoogle(LoginGoogleUserDto loginGoogleUserDto) throws Exception;
    public RegisterUserResponse register(RegisterUserDto registerUserDto) throws Exception;
    public LoginUserResponse revalidateToken() throws Exception;
}
