package com.backend.app.controllers;

import com.backend.app.exceptions.CustomException;
import com.backend.app.exceptions.DtoException;
import com.backend.app.models.dtos.orderDish.FindOrderDishesByUserDto;
import com.backend.app.models.dtos.orderDish.UpdateOrderDishStatusDto;
import com.backend.app.models.responses.orderDish.CreateOrderDishResponse;
import com.backend.app.models.responses.orderDish.FindOrderDishesByUserResponse;
import com.backend.app.models.responses.orderDish.UpdateOrderDishStatusResponse;
import com.backend.app.persistence.enums.EOrderDishStatus;
import com.backend.app.services.OrderDishServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @RequestBody UpdateOrderDishStatusDto updateOrderDishStatusDto
            ) throws Exception {
        updateOrderDishStatusDto.setOrderDishId(orderDishId);
        DtoException<UpdateOrderDishStatusDto> updateOrderDishStatusDtoException = UpdateOrderDishStatusDto.create(updateOrderDishStatusDto);
        if(updateOrderDishStatusDtoException.getError() != null) throw CustomException.badRequest(updateOrderDishStatusDtoException.getError());
        return new ResponseEntity<>(orderDishServiceImpl.updateOrderDishStatus(
                updateOrderDishStatusDtoException.getData()
        ), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<FindOrderDishesByUserResponse> findOrderDishesByUser(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "COMPLETED") List<EOrderDishStatus> status
    )  {
        FindOrderDishesByUserDto findOrderDishesByUserDto = new FindOrderDishesByUserDto();
        findOrderDishesByUserDto.setPage(page);
        findOrderDishesByUserDto.setLimit(limit);
        findOrderDishesByUserDto.setStatus(status);
        DtoException<FindOrderDishesByUserDto> findOrderDishesByUserDtoException = FindOrderDishesByUserDto.create(findOrderDishesByUserDto);
        if(findOrderDishesByUserDtoException.getError() != null) throw CustomException.badRequest(findOrderDishesByUserDtoException.getError());
        return new ResponseEntity<>(orderDishServiceImpl.findOrderDishesByUser(
                findOrderDishesByUserDtoException.getData()
        ), HttpStatus.OK);

    }

}
