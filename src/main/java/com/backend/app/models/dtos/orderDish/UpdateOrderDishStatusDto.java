package com.backend.app.models.dtos.orderDish;

import com.backend.app.exceptions.DtoException;
import com.backend.app.persistence.enums.EOrderDishStatus;
import com.backend.app.utilities.ValidationsUtility;
import lombok.*;

import java.util.Arrays;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateOrderDishStatusDto {

    private Long orderDishId;
    private EOrderDishStatus status;

    public static DtoException<UpdateOrderDishStatusDto> create(UpdateOrderDishStatusDto body) throws IllegalAccessException {
        if(ValidationsUtility.hasNullField(body)) return new DtoException<>("One or more fields are empty", null);
        EOrderDishStatus eOrderDishStatus = Arrays.stream(EOrderDishStatus.values())
                .filter(e -> e.equals(body.status))
                .findFirst()
                .orElse(null);
        if(eOrderDishStatus == null) return new DtoException<>("Invalid status", null);
        return new DtoException<>(null, body);
    }

}
