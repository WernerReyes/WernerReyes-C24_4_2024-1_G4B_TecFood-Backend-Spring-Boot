package com.backend.app.models.dtos.user;

import com.backend.app.utilities.RegularExpUtility;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {

    @Min(value = 3, message = "First name must have at least 3 characters")
    @Max(value = 50, message = "First name must have at most 50 characters")
    private String firstName;

    @Min(value = 3, message = "Last name must have at least 3 characters")
    @Max(value = 50, message = "Last name must have at most 50 characters")
    private String lastName;

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
}
