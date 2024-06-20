package com.backend.app.controllers;

import com.backend.app.exceptions.CustomException;
import com.backend.app.exceptions.DtoException;
import com.backend.app.models.dtos.card.AddToCartDto;
import com.backend.app.models.responses.cartDish.*;
import com.backend.app.services.CartDishServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart-dish")
public class CartDishController {

    @Autowired
    private CartDishServiceImpl cartDishServiceImpl;

    @PostMapping("")
    public ResponseEntity<AddToCartResponse> addOneDishToCart(@RequestBody AddToCartDto addToCartDto) throws Exception {
        DtoException<AddToCartDto> addToCartDtoException = AddToCartDto.create(addToCartDto);
        if(addToCartDtoException.getError() != null) throw CustomException.badRequest(addToCartDtoException.getError());
        return new ResponseEntity<>(cartDishServiceImpl.addOneDishToCart(addToCartDto.getDishId()), HttpStatus.OK);
    }

    @PostMapping("/many")
    public ResponseEntity<AddToCartResponse> addManyDishesToCart(@RequestBody AddToCartDto addToCartDto) throws Exception {
        DtoException<AddToCartDto> addManyDishesToCartDtoException = AddToCartDto.create(addToCartDto);
        if(addManyDishesToCartDtoException.getError() != null) throw CustomException.badRequest(addManyDishesToCartDtoException.getError());
        return new ResponseEntity<>(cartDishServiceImpl.addManyDishesToCart(addManyDishesToCartDtoException.getData()), HttpStatus.OK);
    }

    @DeleteMapping("/{dishId}")
    public ResponseEntity<DeleteToCardResponse> deleteOneDishFromCart(@PathVariable Long dishId) throws Exception {
        return new ResponseEntity<>(cartDishServiceImpl.deleteOneDishFromCart(dishId), HttpStatus.OK);
    }

    @DeleteMapping("/all/{dishId}")
    public ResponseEntity<DeleteToCardResponse> deleteAllDishesFromCart(@PathVariable Long dishId) throws Exception {
        return new ResponseEntity<>(cartDishServiceImpl.deleteAllDishesFromCart(dishId), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<FindDishesToCartResponse> findDishesCartByUser() throws Exception {
        return new ResponseEntity<>(cartDishServiceImpl.findDishesCartByUser(), HttpStatus.OK);
    }

    @GetMapping("/total")
    public ResponseEntity<FindTotalDishesToCartResponse> findTotalDishesCartByUser() throws Exception {
        return new ResponseEntity<>(cartDishServiceImpl.findTotalDishesCartByUser(), HttpStatus.OK);
    }

    @GetMapping("/dish/{dishId}")
    public ResponseEntity<FindDishToCartResponse> findDishToCartByDishId(@PathVariable Long dishId) throws Exception {
        return new ResponseEntity<>(cartDishServiceImpl.findDishToCartByDishId(dishId), HttpStatus.OK);
    }

}
