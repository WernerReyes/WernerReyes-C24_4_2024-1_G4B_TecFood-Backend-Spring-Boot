package com.backend.app.models.responses.orderDishItem;

import com.backend.app.persistence.entities.OrderDishItemEntity;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"message","orderDishItem"})
public record FindOrderDishItemByOrderResponse(
        String message,
        List<OrderDishItemEntity> orderDishItem
) {
}
