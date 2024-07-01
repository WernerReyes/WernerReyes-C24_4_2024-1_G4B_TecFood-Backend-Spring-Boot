package com.backend.app.models.dtos.requests.user;

import com.backend.app.models.dtos.annotations.NotNullAndNotEmpty;
import com.backend.app.utilities.RequestValidatorUtility;
import com.backend.app.utilities.RegularExpUtility;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

    @NotNullAndNotEmpty(message = "First name is required")
    @Size(min = 3, max = 50, message = "First name must have at least 3 characters")
    private String firstName;

    @NotNullAndNotEmpty(message = "Last name is required")
    @Size(min = 3, max = 50, message = "First name must have at least 3 characters")
    private String lastName;

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

    public UpdateUserRequest(String firstName, String lastName, String phoneNumber, String dni) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dni = dni;

        RequestValidatorUtility.validate(this);
    }
}
