package com.backend.app.models.dtos.orderDish;

import com.backend.app.persistence.enums.EOrderDishStatus;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderDishStatusDto {
    @NotNull(message = "Order dish ID is required")
    private Long orderDishId;

    @NotNull(message = "Status is required")
    private EOrderDishStatus status;

}
