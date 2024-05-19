package com.backend.app.controllers;

import com.backend.app.exception.CustomException;
import com.backend.app.exception.DtoException;
import com.backend.app.models.dtos.auth.LoginGoogleUserDto;
import com.backend.app.models.dtos.auth.LoginUserDto;
import com.backend.app.models.dtos.auth.RegisterUserDto;
import com.backend.app.models.responses.auth.LoginUserResponse;
import com.backend.app.models.responses.auth.RegisterUserResponse;
import com.backend.app.services.AuthServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(@RequestBody @Valid RegisterUserDto body) throws Exception {
        DtoException<RegisterUserDto> registerUserDto = RegisterUserDto.create(body);
        if(registerUserDto.getError() != null) throw CustomException.badRequest(registerUserDto.getError());
        return new ResponseEntity<>(authServiceImpl.register(registerUserDto.getData()), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> login(@Valid @RequestBody LoginUserDto body) throws Exception {
        DtoException<LoginUserDto> loginUserDto = LoginUserDto.create(body);
        if(loginUserDto.getError() != null) throw CustomException.badRequest(loginUserDto.getError());
        return new ResponseEntity<>(authServiceImpl.login(loginUserDto.getData()), HttpStatus.OK);
    }

    @PostMapping("/login-google")
    public ResponseEntity<LoginUserResponse> loginGoogle(@RequestBody @Valid LoginGoogleUserDto body) throws Exception {
        DtoException<LoginGoogleUserDto> loginGoogleUserDto = LoginGoogleUserDto.create(body);
        if(loginGoogleUserDto.getError() != null) throw CustomException.badRequest(loginGoogleUserDto.getError());
        return new ResponseEntity<>(authServiceImpl.loginGoogle(loginGoogleUserDto.getData()), HttpStatus.OK);
    }

    @GetMapping("/revalidate-token")
    public ResponseEntity<LoginUserResponse> revalidateToken() throws Exception {
        return new ResponseEntity<>(authServiceImpl.revalidateToken(), HttpStatus.OK);
    }
}
