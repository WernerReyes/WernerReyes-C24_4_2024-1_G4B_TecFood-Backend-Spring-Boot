package com.backend.app.controllers;


import com.backend.app.models.IOrderDishService;
import com.backend.app.models.dtos.requests.orderDish.FindOrderDishesByUserRequest;
import com.backend.app.models.dtos.requests.orderDish.UpdateOrderDishStatusRequest;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.models.dtos.responses.common.PagedResponse;
import com.backend.app.models.dtos.responses.orderDish.FindOrderDishesByUserResponse;
import com.backend.app.persistence.entities.OrderDishEntity;
import com.backend.app.persistence.enums.EOrderDishStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order-dish")
public class OrderDishController {

    private final IOrderDishService orderDishService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<OrderDishEntity>> createOrderDish() {
        return new ResponseEntity<>(orderDishService.createOrderDish(), HttpStatus.OK);
    }

    @PutMapping("/{orderDishId}/status")
    public ResponseEntity<ApiResponse<EOrderDishStatus>> updateOrderDishStatus(
            @PathVariable Long orderDishId,
            @RequestBody Map<String, String> body
            ){
        EOrderDishStatus status = EOrderDishStatus.valueOf(body.get("status"));
        UpdateOrderDishStatusRequest updateOrderDishStatusRequest = new UpdateOrderDishStatusRequest(orderDishId, status);
        return new ResponseEntity<>(orderDishService.updateOrderDishStatus(updateOrderDishStatusRequest), HttpStatus.OK);
    }

    @GetMapping("/{orderDishId}")
    public ResponseEntity<ApiResponse<OrderDishEntity>> findOrderDishById(
            @PathVariable Long orderDishId
    )  {
        return new ResponseEntity<>(orderDishService.findById(orderDishId), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<PagedResponse<FindOrderDishesByUserResponse>>> findOrderDishesByUser(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "COMPLETED") List<EOrderDishStatus> status
    ) {
        FindOrderDishesByUserRequest findOrderDishesByUserRequest = new FindOrderDishesByUserRequest(status, page, limit);
        return new ResponseEntity<>(orderDishService.findOrderDishesByUser(findOrderDishesByUserRequest), HttpStatus.OK);

    }

}
