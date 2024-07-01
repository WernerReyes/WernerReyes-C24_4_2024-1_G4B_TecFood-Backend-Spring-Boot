package com.backend.app.models.dtos.requests.orderDish;

import com.backend.app.persistence.enums.EOrderDishStatus;
import com.backend.app.utilities.RequestValidatorUtility;
import lombok.Getter;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderDishStatusRequest {
    @NotNull(message = "Order dish ID is required")
    private Long orderDishId;

    @NotNull(message = "Status is required")
    private EOrderDishStatus status;

    public UpdateOrderDishStatusRequest(Long orderDishId, EOrderDishStatus status) {
        this.orderDishId = orderDishId;
        this.status = status;

        RequestValidatorUtility.validate(this);
    }

}
