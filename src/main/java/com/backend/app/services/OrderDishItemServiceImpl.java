package com.backend.app.services;

import com.backend.app.exceptions.CustomException;
import com.backend.app.models.IOrderDishItemService;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.persistence.entities.OrderDishEntity;
import com.backend.app.persistence.entities.OrderDishItemEntity;
import com.backend.app.persistence.enums.EResponseStatus;
import com.backend.app.persistence.repositories.OrderDishItemRepository;
import com.backend.app.persistence.repositories.OrderDishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderDishItemServiceImpl implements IOrderDishItemService {

    private final OrderDishRepository orderDishRepository;
    private final OrderDishItemRepository orderDishItemRepository;

    @Override
    public ApiResponse<List<OrderDishItemEntity>> findOrderDishItemByOrderId(Long orderDishId) {
        OrderDishEntity orderDish = orderDishRepository.findById(orderDishId).orElse(null);
        if (orderDish == null) throw CustomException.badRequest("Order not found");

        List<OrderDishItemEntity> orderDishItem = orderDishItemRepository.findByOrderDish(orderDish);

        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Order dish item found",
                orderDishItem
        );
    }
}
