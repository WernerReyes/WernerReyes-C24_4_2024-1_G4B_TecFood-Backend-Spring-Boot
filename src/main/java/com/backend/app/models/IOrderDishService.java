package com.backend.app.models;

import com.backend.app.models.dtos.orderDish.FindOrderDishesByUserDto;
import com.backend.app.models.dtos.orderDish.UpdateOrderDishStatusDto;
import com.backend.app.models.responses.orderDish.CreateOrderDishResponse;
import com.backend.app.models.responses.orderDish.FindOrderDishesByUserResponse;
import com.backend.app.models.responses.orderDish.UpdateOrderDishStatusResponse;

public interface IOrderDishService {
    public CreateOrderDishResponse createOrderDish() throws Exception;
    public UpdateOrderDishStatusResponse updateOrderDishStatus(UpdateOrderDishStatusDto updateOrderDishStatusDto) throws Exception;
    public FindOrderDishesByUserResponse findOrderDishesByUser(
            FindOrderDishesByUserDto findOrderDishesByUserDto
    ) throws Exception;
}
