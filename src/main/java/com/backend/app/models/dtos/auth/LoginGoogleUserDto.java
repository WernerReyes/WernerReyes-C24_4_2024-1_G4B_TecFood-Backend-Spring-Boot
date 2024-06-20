package com.backend.app.models.dtos.auth;

import com.backend.app.exceptions.DtoException;
import com.backend.app.utilities.ValidationsUtility;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginGoogleUserDto {

    private String email;
    private String imgUrl;
    private Boolean isEmailVerified;
    private Boolean isGoogleAccount;
    private String firstName;
    private String lastName;

    public static DtoException<LoginGoogleUserDto> create(LoginGoogleUserDto body) throws IllegalAccessException {
        if(ValidationsUtility.hasNullField(body)) return new DtoException<>("One or more fields are empty", null);

        if (ValidationsUtility.isFieldEmpty(body.email))  return new DtoException<>("Email is required", null);
        if (!ValidationsUtility.isEmailValid(body.email)) return new DtoException<>("Email must be a valid Tecsup email", null);

        if (!ValidationsUtility.isNameValid(body.firstName)) return new DtoException<>("First name must have at least 3 characters", null);

        if (!ValidationsUtility.isNameValid(body.lastName)) return new DtoException<>("Last name must have at least 3 characters", null);

        if(!body.isGoogleAccount || !body.isEmailVerified) return new DtoException<>("Email must be verified", null);

        if(ValidationsUtility.isFieldEmpty(body.imgUrl)) return new DtoException<>("Image URL is required", null);
        if(!ValidationsUtility.isUrlValid(body.imgUrl)) return new DtoException<>("Image URL is not valid", null);

        return new DtoException<>(null,body);
    }
}


