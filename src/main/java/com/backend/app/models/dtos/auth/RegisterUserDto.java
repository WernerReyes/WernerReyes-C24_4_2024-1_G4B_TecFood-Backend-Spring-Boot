package com.backend.app.models.dtos.auth;

import com.backend.app.models.dtos.NotNullAndNotEmpty;
import com.backend.app.utilities.RegularExpUtility;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterUserDto {

    @NotNullAndNotEmpty(message = "Email is required")
    @Email(message = "Email must be a valid Tecsup email", regexp = RegularExpUtility.EMAIL_REGEX)
    private String email;

    @NotNullAndNotEmpty(message = "Password is required")
    @Pattern(
            regexp = RegularExpUtility.PASSWORD_REGEX,
            message = "Password must have at least 8 characters, 1 uppercase letter, 1 lowercase letter, 1 number and 1 special character"
    )
    private String password;

    @Pattern(
            regexp = RegularExpUtility.PHONE_NUMBER_REGEX,
            message = "Phone number must have at least 9 characters"
    )
    private String phoneNumber;


    @Pattern(
            regexp = RegularExpUtility.DNI_REGEX,
            message = "DNI must have at least 8 characters"
    )
    private String dni;

    @NotNullAndNotEmpty(message = "First name is required")
    @Size(min = 3, message = "First name must have at least 3 characters")
    private String firstName;

    @NotNullAndNotEmpty(message = "Last name is required")
    @Size(min = 3, message = "Last name must have at least 3 characters")
    private String lastName;
}
