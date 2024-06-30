package com.backend.app.models.dtos.user;

import com.backend.app.utilities.RegularExpUtility;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {

    @Size(min = 3, max = 50, message = "First name must have at least 3 characters")
    private String firstName;

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
}
