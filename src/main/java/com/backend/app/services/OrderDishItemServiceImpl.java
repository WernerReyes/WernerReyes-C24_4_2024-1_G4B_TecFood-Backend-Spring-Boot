package com.backend.app.services;

import com.backend.app.exceptions.CustomException;
import com.backend.app.models.IOrderDishItemService;
import com.backend.app.models.responses.orderDishItem.FindOrderDishItemByOrderResponse;
import com.backend.app.persistence.entities.OrderDishEntity;
import com.backend.app.persistence.entities.OrderDishItemEntity;
import com.backend.app.persistence.repositories.OrderDishItemRepository;
import com.backend.app.persistence.repositories.OrderDishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDishItemServiceImpl implements IOrderDishItemService {

    @Autowired
    private OrderDishRepository orderDishRepository;

    @Autowired
    private OrderDishItemRepository orderDishItemRepository;

    @Override
    public FindOrderDishItemByOrderResponse findOrderDishItemByOrder(Long orderDishId) {
        OrderDishEntity orderDish = orderDishRepository.findById(orderDishId).orElse(null);
        if (orderDish == null) throw CustomException.badRequest("Order not found");

        List<OrderDishItemEntity> orderDishItem = orderDishItemRepository.findByOrderDish(orderDish);

        return new FindOrderDishItemByOrderResponse(
                "Order items",
                orderDishItem
        );
    }
}
