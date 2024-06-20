package com.backend.app.models.dtos.user;

import com.backend.app.exceptions.DtoException;
import com.backend.app.utilities.ValidationsUtility;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class UpdateUserDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String dni;

    public static DtoException<UpdateUserDto> create(
            UpdateUserDto body
            )  {

        if(!ValidationsUtility.isNameValid(body.firstName)) return new DtoException<>("Invalid first name", null);

        if (!ValidationsUtility.isNameValid(body.lastName)) return new DtoException<>("Invalid last name", null);

        if(!ValidationsUtility.isFieldEmpty(body.phoneNumber)) {
            if (!ValidationsUtility.isPhoneNumberValid(body.phoneNumber)) return new DtoException<>("Phone number must have at least 9 characters", null);
        }
        if(!ValidationsUtility.isFieldEmpty(body.dni)) {
            if (!ValidationsUtility.isDniValid(body.dni)) return new DtoException<>("DNI must have at least 8 characters", null);
        }

        return new DtoException<>(null, body);
    }

}
