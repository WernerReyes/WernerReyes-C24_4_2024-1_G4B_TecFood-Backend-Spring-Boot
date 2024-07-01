package com.backend.app.models.dtos.responses.orderDish;

import com.backend.app.persistence.entities.OrderDishEntity;
import com.backend.app.persistence.enums.EOrderDishStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"orderDishes", "status"})
public record FindOrderDishesByUserResponse(
        List<OrderDishEntity> orderDishes,
        List<EOrderDishStatus> status
) {
}
