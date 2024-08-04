package com.backend.app.models.dtos.requests.auth;

import com.backend.app.models.dtos.annotations.NotNullAndNotEmpty;
import com.backend.app.utilities.RegularExpUtility;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthRequest {
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
