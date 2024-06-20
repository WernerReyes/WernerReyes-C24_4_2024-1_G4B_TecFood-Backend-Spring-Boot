package com.backend.app.models.responses.orderDish;

import com.backend.app.persistence.enums.EOrderDishStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"message","status"})
public record UpdateOrderDishStatusResponse(
        String message,
        EOrderDishStatus status
) {
}
