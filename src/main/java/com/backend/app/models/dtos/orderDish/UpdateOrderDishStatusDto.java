package com.backend.app.models.dtos.orderDish;

import com.backend.app.models.dtos.NotNullAndNotEmpty;
import com.backend.app.persistence.enums.EOrderDishStatus;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderDishStatusDto {
    @NotNullAndNotEmpty(message = "Order dish ID is required")
    private Long orderDishId;

    @NotNullAndNotEmpty(message = "Status is required")
    private EOrderDishStatus status;

}
