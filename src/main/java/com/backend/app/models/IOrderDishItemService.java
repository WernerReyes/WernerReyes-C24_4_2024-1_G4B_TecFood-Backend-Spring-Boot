package com.backend.app.models;

import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.persistence.entities.OrderDishItemEntity;

import java.util.List;

public interface IOrderDishItemService {
    ApiResponse<List<OrderDishItemEntity>> findOrderDishItemByOrderId(Long orderDishId);
}
