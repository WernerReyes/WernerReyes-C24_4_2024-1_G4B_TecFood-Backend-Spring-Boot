package com.backend.app.models;

import com.backend.app.models.dtos.requests.orderDish.FindOrderDishesByUserRequest;
import com.backend.app.models.dtos.requests.orderDish.UpdateOrderDishStatusRequest;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.models.dtos.responses.common.PagedResponse;
import com.backend.app.models.dtos.responses.orderDish.FindOrderDishesByUserResponse;
import com.backend.app.persistence.entities.OrderDishEntity;
import com.backend.app.persistence.enums.EOrderDishStatus;

public interface IOrderDishService {
    ApiResponse<OrderDishEntity> createOrderDish();
    ApiResponse<EOrderDishStatus> updateOrderDishStatus(UpdateOrderDishStatusRequest updateOrderDishStatusDto);
    ApiResponse<PagedResponse<FindOrderDishesByUserResponse>> findOrderDishesByUser(
            FindOrderDishesByUserRequest findOrderDishesByUserDto
    );
    ApiResponse<OrderDishEntity> findById(Long id);
}
