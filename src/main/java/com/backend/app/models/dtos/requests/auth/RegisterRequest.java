package com.backend.app.models.dtos.requests.auth;

import com.backend.app.models.dtos.annotations.NotNullAndNotEmpty;
import com.backend.app.utilities.RequestValidatorUtility;
import com.backend.app.utilities.RegularExpUtility;
import lombok.Getter;
import jakarta.validation.constraints.*;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest extends AuthRequest {
    @Pattern(
            regexp = RegularExpUtility.PHONE_NUMBER_OPTIONAL_REGEX,
            message = "Phone number must have at least 9 characters"
    )
    private String phoneNumber;

    @Pattern(
            regexp = RegularExpUtility.DNI_OPTIONAL_REGEX,
            message = "DNI must have at least 8 characters"
    )
    private String dni;

    @NotNullAndNotEmpty(message = "First name is required")
    @Size(min = 3, message = "First name must have at least 3 characters")
    private String firstName;

    @NotNullAndNotEmpty(message = "Last name is required")
    @Size(min = 3, message = "Last name must have at least 3 characters")
    private String lastName;

    public RegisterRequest(String email, String password, String phoneNumber, String dni, String firstName, String lastName) {
        super(email, password);
        this.phoneNumber = phoneNumber;
        this.dni = dni;
        this.firstName = firstName;
        this.lastName = lastName;

        RequestValidatorUtility.validate(this);
    }


}
