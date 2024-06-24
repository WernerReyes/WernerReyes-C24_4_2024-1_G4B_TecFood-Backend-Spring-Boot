package com.backend.app.controllers;

import com.backend.app.exceptions.CustomException;
import com.backend.app.exceptions.DtoException;
import com.backend.app.models.dtos.card.AddToCartDto;
import com.backend.app.models.responses.cartDish.*;
import com.backend.app.services.CartDishServiceImpl;
import com.backend.app.utilities.DtoValidatorUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cart-dish")
public class CartDishController {

    @Autowired
    private CartDishServiceImpl cartDishServiceImpl;

    @PostMapping("")
    public ResponseEntity<AddToCartResponse> addOneDishToCart(@RequestBody AddToCartDto addToCartDto) {
        DtoException<AddToCartDto> addToCartDtoException = DtoValidatorUtility.validate(addToCartDto);
        if(addToCartDtoException.getError() != null) throw CustomException.badRequest(addToCartDtoException.getError());
        return new ResponseEntity<>(cartDishServiceImpl.addOneDishToCart(addToCartDtoException.getData().getDishId()), HttpStatus.OK);
    }

    @PostMapping("/many")
    public ResponseEntity<AddToCartResponse> addManyDishesToCart(@RequestBody AddToCartDto addToCartDto) {
        DtoException<AddToCartDto> addManyDishesToCartDtoException = DtoValidatorUtility.validate(addToCartDto);
        if(addManyDishesToCartDtoException.getError() != null) throw CustomException.badRequest(addManyDishesToCartDtoException.getError());
        return new ResponseEntity<>(cartDishServiceImpl.addManyDishesToCart(addManyDishesToCartDtoException.getData()), HttpStatus.OK);
    }

    @DeleteMapping("/{dishId}")
    public ResponseEntity<DeleteToCardResponse> deleteOneDishFromCart(@PathVariable Long dishId) {
        return new ResponseEntity<>(cartDishServiceImpl.deleteOneDishFromCart(dishId), HttpStatus.OK);
    }

    @DeleteMapping("/all/{dishId}")
    public ResponseEntity<DeleteToCardResponse> deleteAllDishesFromCart(@PathVariable Long dishId) {
        return new ResponseEntity<>(cartDishServiceImpl.deleteAllDishesFromCart(dishId), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<FindDishesToCartResponse> findDishesCartByUser() {
        return new ResponseEntity<>(cartDishServiceImpl.findDishesCartByUser(), HttpStatus.OK);
    }

    @GetMapping("/total")
    public ResponseEntity<FindTotalDishesToCartResponse> findTotalDishesCartByUser() {
        return new ResponseEntity<>(cartDishServiceImpl.findTotalDishesCartByUser(), HttpStatus.OK);
    }

    @GetMapping("/dish/{dishId}")
    public ResponseEntity<FindDishToCartResponse> findDishToCartByDishId(@PathVariable Long dishId) {
        return new ResponseEntity<>(cartDishServiceImpl.findDishToCartByDishId(dishId), HttpStatus.OK);
    }

}
