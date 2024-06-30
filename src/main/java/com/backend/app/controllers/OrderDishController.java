package com.backend.app.controllers;

import com.backend.app.exceptions.CustomException;
import com.backend.app.exceptions.DtoException;
import com.backend.app.models.dtos.orderDish.FindOrderDishesByUserDto;
import com.backend.app.models.dtos.orderDish.UpdateOrderDishStatusDto;
import com.backend.app.models.responses.orderDish.CreateOrderDishResponse;
import com.backend.app.models.responses.orderDish.FindOrderDishesByUserResponse;
import com.backend.app.models.responses.orderDish.UpdateOrderDishStatusResponse;
import com.backend.app.persistence.entities.OrderDishEntity;
import com.backend.app.persistence.enums.EOrderDishStatus;
import com.backend.app.services.OrderDishServiceImpl;
import com.backend.app.utilities.DtoValidatorUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order-dish")
public class OrderDishController {

    @Autowired
    private OrderDishServiceImpl orderDishServiceImpl;

    @PostMapping("")
    public ResponseEntity<CreateOrderDishResponse> createOrderDish() {
        return new ResponseEntity<>(orderDishServiceImpl.createOrderDish(), HttpStatus.OK);
    }

    @PutMapping("/{orderDishId}/status")
    public ResponseEntity<UpdateOrderDishStatusResponse> updateOrderDishStatus(
            @PathVariable Long orderDishId,
            @RequestBody Map<String, String> body
            )  {
        EOrderDishStatus status = EOrderDishStatus.valueOf(body.get("status"));
        UpdateOrderDishStatusDto updateOrderDishStatusDto = new UpdateOrderDishStatusDto(orderDishId, status);
        DtoException<UpdateOrderDishStatusDto> updateOrderDishStatusDtoException = DtoValidatorUtility.validate(updateOrderDishStatusDto);
        if(updateOrderDishStatusDtoException.getError() != null) throw CustomException.badRequest(updateOrderDishStatusDtoException.getError());
        return new ResponseEntity<>(orderDishServiceImpl.updateOrderDishStatus(
                updateOrderDishStatusDtoException.getData()
        ), HttpStatus.OK);
    }

    @GetMapping("/{orderDishId}")
    public ResponseEntity<OrderDishEntity> findOrderDishById(
            @PathVariable Long orderDishId
    )  {
        return new ResponseEntity<>(orderDishServiceImpl.findById(orderDishId), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<FindOrderDishesByUserResponse> findOrderDishesByUser(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "COMPLETED") List<EOrderDishStatus> status
    )  {
        FindOrderDishesByUserDto findOrderDishesByUserDto = new FindOrderDishesByUserDto(status, page, limit);
        DtoException<FindOrderDishesByUserDto> findOrderDishesByUserDtoException = DtoValidatorUtility.validate(findOrderDishesByUserDto);
        if(findOrderDishesByUserDtoException.getError() != null) throw CustomException.badRequest(findOrderDishesByUserDtoException.getError());
        return new ResponseEntity<>(orderDishServiceImpl.findOrderDishesByUser(
                findOrderDishesByUserDtoException.getData()
        ), HttpStatus.OK);

    }

}
