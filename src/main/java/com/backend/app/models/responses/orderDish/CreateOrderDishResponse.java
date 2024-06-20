package com.backend.app.models.responses.orderDish;

import com.backend.app.persistence.entities.OrderDishEntity;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"message", "orderDish"})
public record CreateOrderDishResponse(
        String message,
        OrderDishEntity orderDish
) {
}
