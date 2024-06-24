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
public class LoginUserDto {

    @NotNullAndNotEmpty(message = "Email is required")
    @Email(message = "Email must be a valid Tecsup email", regexp = RegularExpUtility.EMAIL_REGEX)
    private String email;

    @NotNullAndNotEmpty(message = "Password is required")
    @Pattern(
            regexp = RegularExpUtility.PASSWORD_REGEX,
            message = "Password must have at least 8 characters, 1 uppercase letter, 1 lowercase letter, 1 number and 1 special character"
    )
    private String password;

}

