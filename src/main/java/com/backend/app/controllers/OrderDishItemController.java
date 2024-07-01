package com.backend.app.controllers;

import com.backend.app.models.IOrderDishItemService;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.persistence.entities.OrderDishItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order-dish-item")
public class OrderDishItemController {

    private final IOrderDishItemService orderDishItemService;

    @GetMapping("/order/{orderDishId}")
    public ResponseEntity<ApiResponse<List<OrderDishItemEntity>>> findOrderDishItemByOrder(
            @PathVariable Long orderDishId
    ) {
        return new ResponseEntity<>(orderDishItemService.findOrderDishItemByOrderId(orderDishId), HttpStatus.OK);
    }

}
