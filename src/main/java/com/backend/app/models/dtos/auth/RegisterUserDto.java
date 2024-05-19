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
public class RegisterUserDto {

    private String email;
    private String password;
    private String phoneNumber;
    private String dni;
    private String firstName;
    private String lastName;

    public static DtoException<RegisterUserDto> create(RegisterUserDto body) throws IllegalAccessException {
        if(ValidationsUtility.hasNullField(body)) return new DtoException<>("One or more fields are empty", null);

        if (ValidationsUtility.isFieldEmpty(body.email))  return new DtoException<>("Email is required", null);
        if (!ValidationsUtility.isEmailValid(body.email)) return new DtoException<>("Email must be a valid Tecsup email", null);

        if (ValidationsUtility.isFieldEmpty(body.password)) return new DtoException<>("Password is required", null);
        if (!ValidationsUtility.isPasswordValid(body.password)) {
            return new DtoException<>("Password must have at least 8 characters, 1 uppercase letter, 1 lowercase letter, 1 number and 1 special character", null);
        }

        if(body.phoneNumber != null) {
            if (ValidationsUtility.isFieldEmpty(body.phoneNumber)) return new DtoException<>("Phone number is required", null);
            if (!ValidationsUtility.isPhoneNumberValid(body.phoneNumber)) return new DtoException<>("Phone number must have at least 9 characters", null);
        }
        if(body.dni != null) {
            if (ValidationsUtility.isFieldEmpty(body.dni)) return new DtoException<>("DNI is required", null);
            if (!ValidationsUtility.isDniValid(body.dni)) return new DtoException<>("DNI must have at least 8 characters", null);
        }

        if (!ValidationsUtility.isNameValid(body.firstName)) return new DtoException<>("First name must have at least 3 characters", null);
        if (!ValidationsUtility.isNameValid(body.lastName)) return new DtoException<>("Last name must have at least 3 characters", null);

        return new DtoException<>(null,body);
    }
}

