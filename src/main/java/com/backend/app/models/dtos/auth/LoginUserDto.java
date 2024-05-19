package com.backend.app.models.dtos.auth;

import com.backend.app.exception.DtoException;
import com.backend.app.utilities.ValidationsUtility;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginUserDto {

    private String email;
    private String password;

    public static DtoException<LoginUserDto> create(LoginUserDto body) throws IllegalAccessException {
        if(ValidationsUtility.hasNullField(body)) return new DtoException<>("One or more fields are empty", null);

        if (ValidationsUtility.isFieldEmpty(body.email))  return new DtoException<>("Email is required", null);
        if (!ValidationsUtility.isEmailValid(body.email)) return new DtoException<>("Email must be a valid Tecsup email", null);

        if (ValidationsUtility.isFieldEmpty(body.password)) return new DtoException<>("Password is required", null);
        if (!ValidationsUtility.isPasswordValid(body.password)) {
            return new DtoException<>("Password must have at least 8 characters, 1 uppercase letter, 1 lowercase letter, 1 number and 1 special character", null);
        }
        return new DtoException<>(null,body);
    }
}

