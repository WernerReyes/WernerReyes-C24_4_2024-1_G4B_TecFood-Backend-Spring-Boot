package com.backend.app.controllers;

import com.backend.app.exceptions.CustomException;
import com.backend.app.exceptions.DtoException;
import com.backend.app.models.dtos.auth.LoginGoogleUserDto;
import com.backend.app.models.dtos.auth.LoginUserDto;
import com.backend.app.models.dtos.auth.RegisterUserDto;
import com.backend.app.models.responses.auth.LoginUserResponse;
import com.backend.app.models.responses.auth.RegisterUserResponse;
import com.backend.app.services.AuthServiceImpl;
import com.backend.app.utilities.DtoValidatorUtility;
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
    public ResponseEntity<RegisterUserResponse> register(@RequestBody RegisterUserDto registerUserDto) {
        DtoException<RegisterUserDto> registerUserDtoException = DtoValidatorUtility.validate(registerUserDto);
        if(registerUserDtoException.getError() != null) throw CustomException.badRequest(registerUserDtoException.getError());
        return new ResponseEntity<>(authServiceImpl.register(registerUserDtoException.getData()), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> login(@RequestBody  LoginUserDto loginUserDto ) throws Exception {
        DtoException<LoginUserDto> loginUserDtoException = DtoValidatorUtility.validate(loginUserDto);
        if(loginUserDtoException.getError() != null) throw CustomException.badRequest(loginUserDtoException.getError());
        return new ResponseEntity<>(authServiceImpl.login(loginUserDtoException.getData()), HttpStatus.OK);
    }

    @PostMapping("/login-google")
    public ResponseEntity<LoginUserResponse> loginGoogle(@RequestBody LoginGoogleUserDto loginGoogleUserDto) throws Exception {
        DtoException<LoginGoogleUserDto> loginGoogleUserDtoException = DtoValidatorUtility.validate(loginGoogleUserDto);
        if(loginGoogleUserDtoException.getError() != null) throw CustomException.badRequest(loginGoogleUserDtoException.getError());
        return new ResponseEntity<>(authServiceImpl.loginGoogle(loginGoogleUserDtoException.getData()), HttpStatus.OK);
    }

    @PostMapping("/renovate-token")
    public ResponseEntity<LoginUserResponse> renovateToken(@RequestParam String expiredToken) throws Exception {
        return new ResponseEntity<>(authServiceImpl.renovateToken(expiredToken), HttpStatus.OK);
    }

    @GetMapping("/revalidate-token")
    public ResponseEntity<LoginUserResponse> revalidateToken() throws Exception {
        return new ResponseEntity<>(authServiceImpl.revalidateToken(), HttpStatus.OK);
    }
}
