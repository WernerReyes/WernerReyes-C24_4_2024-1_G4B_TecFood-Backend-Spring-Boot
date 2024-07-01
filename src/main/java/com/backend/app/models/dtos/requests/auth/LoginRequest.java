package com.backend.app.models.dtos.requests.auth;
import com.backend.app.utilities.RequestValidatorUtility;

public class LoginRequest extends AuthRequest {
    public LoginRequest(String email, String password) {
        super(email, password);

        RequestValidatorUtility.validate(this);
    }
}

