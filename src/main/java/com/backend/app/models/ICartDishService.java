package com.backend.app.models;

import com.backend.app.models.dtos.requests.cartDish.AddToCartRequest;
import com.backend.app.models.dtos.responses.cartDish.*;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.persistence.entities.CartDishEntity;

public interface ICartDishService {
    ApiResponse<CartDishEntity> addOneDishToCart(Long dishId);
    ApiResponse<CartDishEntity> addManyDishesToCart(AddToCartRequest addToCartDto);
    ApiResponse<Integer> deleteOneDishFromCart(Long dishId);
    ApiResponse<Integer> deleteAllDishesFromCart(Long dishId);
    ApiResponse<FindDishesToCartByUserResponse> findDishesCartByUser();
    ApiResponse<Integer> findTotalDishesCartByUser();
    ApiResponse<CartDishEntity> findDishToCartByDishId(Long dishId);
}
