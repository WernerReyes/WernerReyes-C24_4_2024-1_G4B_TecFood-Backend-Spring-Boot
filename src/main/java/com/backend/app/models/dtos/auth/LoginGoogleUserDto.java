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
public class LoginGoogleUserDto {

    @NotNullAndNotEmpty(message = "Email is required")
    @Email(message = "Email must be a valid Tecsup email", regexp = RegularExpUtility.EMAIL_REGEX)
    private String email;

    @NotNullAndNotEmpty(message = "Image URL is required")
    @Pattern(message = "Image URL is not valid" ,regexp = RegularExpUtility.URL_REGEX)
    private String imgUrl;

    @NotNull(message = "Email must be verified")
    @AssertTrue(message = "Email must be verified")
    private Boolean isEmailVerified;

    @NotNull(message = "Google account is required")
    @AssertTrue(message = "Google account is required")
    private Boolean isGoogleAccount;

    @NotNullAndNotEmpty(message = "First name is required")
    @Size(min = 3, message = "First name must have at least 3 characters")
    private String firstName;

    @NotNullAndNotEmpty(message = "Last name is required")
    @Size(min = 3, message = "Last name must have at least 3 characters")
    private String lastName;

}


