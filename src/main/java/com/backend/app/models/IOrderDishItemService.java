package com.backend.app.models;

import com.backend.app.models.responses.orderDishItem.FindOrderDishItemByOrderResponse;

public interface IOrderDishItemService {
    public FindOrderDishItemByOrderResponse findOrderDishItemByOrder(Long orderDishId);
}
