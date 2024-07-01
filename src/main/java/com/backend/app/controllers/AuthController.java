package com.backend.app.controllers;

import com.backend.app.models.IAuthService;
import com.backend.app.models.dtos.requests.auth.LoginGoogleRequest;
import com.backend.app.models.dtos.requests.auth.LoginRequest;
import com.backend.app.models.dtos.requests.auth.RegisterRequest;
import com.backend.app.models.dtos.responses.auth.*;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(authService.register(registerRequest), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) throws Exception {
        return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/login-google")
    public ResponseEntity<ApiResponse<LoginResponse>> loginGoogle(@RequestBody LoginGoogleRequest loginGoogleRequest) throws Exception {
        return new ResponseEntity<>(authService.loginGoogle(loginGoogleRequest), HttpStatus.OK);
    }

    @PostMapping("/renovate-token")
    public ResponseEntity<ApiResponse<String>> renovateToken(@RequestParam String expiredToken) throws Exception {
        return new ResponseEntity<>(authService.renovateToken(expiredToken), HttpStatus.OK);
    }

    @GetMapping("/revalidate-token")
    public ResponseEntity<ApiResponse<UserEntity>> revalidateToken() {
        return new ResponseEntity<>(authService.revalidateToken(), HttpStatus.OK);
    }
}
