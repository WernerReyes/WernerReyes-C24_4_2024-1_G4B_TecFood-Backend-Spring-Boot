package com.backend.app.controllers;

import com.backend.app.exception.CustomException;
import com.backend.app.exception.DtoException;
import com.backend.app.models.dtos.card.AddToCartDto;
import com.backend.app.models.responses.cart.AddToCartResponse;
import com.backend.app.models.responses.cart.DeleteToCardResponse;
import com.backend.app.models.responses.cart.GetDishesCartResponse;
import com.backend.app.services.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartServiceImpl cartServiceImpl;

    @PostMapping("")
    public ResponseEntity<AddToCartResponse> addOneDishToCart(@RequestBody AddToCartDto addToCartDto) throws Exception {
        return new ResponseEntity<>(cartServiceImpl.addOneDishToCart(addToCartDto.getDishId()), HttpStatus.OK);
    }

    @PostMapping("/many")
    public ResponseEntity<AddToCartResponse> addManyDishesToCart(@RequestBody AddToCartDto addToCartDto) throws Exception {
        DtoException<AddToCartDto> addManyDishesToCartDto = AddToCartDto.create(addToCartDto);
        if(addManyDishesToCartDto.getError() != null) throw CustomException.badRequest(addManyDishesToCartDto.getError());
        return new ResponseEntity<>(cartServiceImpl.addManyDishesToCart(addManyDishesToCartDto.getData()), HttpStatus.OK);
    }

    @DeleteMapping("/{dishId}")
    public ResponseEntity<DeleteToCardResponse> deleteOneDishFromCart(@PathVariable Long dishId) throws Exception {
        return new ResponseEntity<>(cartServiceImpl.deleteOneDishFromCart(dishId), HttpStatus.OK);
    }

    @DeleteMapping("/all/{dishId}")
    public ResponseEntity<DeleteToCardResponse> deleteAllDishesFromCart(@PathVariable Long dishId) throws Exception {
        return new ResponseEntity<>(cartServiceImpl.deleteAllDishesFromCart(dishId), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<GetDishesCartResponse> getDishesCart() throws Exception {
        return new ResponseEntity<>(cartServiceImpl.getDishesCartByUser(), HttpStatus.OK);
    }

}
