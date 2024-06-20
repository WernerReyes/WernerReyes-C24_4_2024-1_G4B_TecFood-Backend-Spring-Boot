package com.backend.app.models.responses.orderDish;

import com.backend.app.persistence.entities.OrderDishEntity;
import com.backend.app.persistence.enums.EOrderDishStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"message","orderDishes", "status", "currentPage", "total", "limit", "next", "previous"})
public record FindOrderDishesByUserResponse(
        String message,
        List<OrderDishEntity> orderDishes,
        List<EOrderDishStatus> status,
        int currentPage,
        int total,
        int limit,
        String next,
        String previous
) {
}
