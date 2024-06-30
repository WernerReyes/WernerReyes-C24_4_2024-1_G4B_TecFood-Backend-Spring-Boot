package com.backend.app.models;

import com.backend.app.models.dtos.orderDish.FindOrderDishesByUserDto;
import com.backend.app.models.dtos.orderDish.UpdateOrderDishStatusDto;
import com.backend.app.models.responses.orderDish.CreateOrderDishResponse;
import com.backend.app.models.responses.orderDish.FindOrderDishesByUserResponse;
import com.backend.app.models.responses.orderDish.UpdateOrderDishStatusResponse;
import com.backend.app.persistence.entities.OrderDishEntity;

public interface IOrderDishService {
    CreateOrderDishResponse createOrderDish() throws Exception;
    UpdateOrderDishStatusResponse updateOrderDishStatus(UpdateOrderDishStatusDto updateOrderDishStatusDto) throws Exception;
    FindOrderDishesByUserResponse findOrderDishesByUser(
            FindOrderDishesByUserDto findOrderDishesByUserDto
    ) throws Exception;
    OrderDishEntity findById(Long id);
}
