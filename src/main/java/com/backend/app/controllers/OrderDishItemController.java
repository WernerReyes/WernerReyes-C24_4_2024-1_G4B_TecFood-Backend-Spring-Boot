package com.backend.app.controllers;

import com.backend.app.models.responses.orderDishItem.FindOrderDishItemByOrderResponse;
import com.backend.app.services.OrderDishItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order-dish-item")
public class OrderDishItemController {

    @Autowired
    private OrderDishItemServiceImpl orderDishItemServiceImpl;

    @GetMapping("/order/{orderDishId}")
    public ResponseEntity<FindOrderDishItemByOrderResponse> findOrderDishItemByOrder(
            @PathVariable Long orderDishId
    ) {
        return new ResponseEntity<>(orderDishItemServiceImpl.findOrderDishItemByOrder(orderDishId), HttpStatus.OK);
    }

}
