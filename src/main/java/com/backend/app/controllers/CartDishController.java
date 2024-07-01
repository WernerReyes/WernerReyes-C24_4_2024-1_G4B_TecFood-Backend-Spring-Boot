package com.backend.app.controllers;

import com.backend.app.models.ICartDishService;
import com.backend.app.models.dtos.requests.cartDish.AddToCartRequest;
import com.backend.app.models.dtos.responses.cartDish.*;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.persistence.entities.CartDishEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart-dish")
public class CartDishController {

    private final ICartDishService cartDishService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<CartDishEntity>> addOneDishToCart(@RequestBody AddToCartRequest addToCartRequest) {
        return new ResponseEntity<>(cartDishService.addOneDishToCart(addToCartRequest.getDishId()), HttpStatus.OK);
    }

    @PostMapping("/many")
    public ResponseEntity<ApiResponse<CartDishEntity>> addManyDishesToCart(@RequestBody AddToCartRequest addToCartRequest) {
        return new ResponseEntity<>(cartDishService.addManyDishesToCart(addToCartRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{dishId}")
    public ResponseEntity<ApiResponse<Integer>> deleteOneDishFromCart(@PathVariable Long dishId) {
        return new ResponseEntity<>(cartDishService.deleteOneDishFromCart(dishId), HttpStatus.OK);
    }

    @DeleteMapping("/all/{dishId}")
    public ResponseEntity<ApiResponse<Integer>> deleteAllDishesFromCart(@PathVariable Long dishId) {
        return new ResponseEntity<>(cartDishService.deleteAllDishesFromCart(dishId), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<FindDishesToCartByUserResponse>> findDishesCartByUser() {
        return new ResponseEntity<>(cartDishService.findDishesCartByUser(), HttpStatus.OK);
    }

    @GetMapping("/total")
    public ResponseEntity<ApiResponse<Integer>> findTotalDishesCartByUser() {
        return new ResponseEntity<>(cartDishService.findTotalDishesCartByUser(), HttpStatus.OK);
    }

    @GetMapping("/dish/{dishId}")
    public ResponseEntity<ApiResponse<CartDishEntity>> findDishToCartByDishId(@PathVariable Long dishId) {
        return new ResponseEntity<>(cartDishService.findDishToCartByDishId(dishId), HttpStatus.OK);
    }

}
