package com.backend.app.models;

import com.backend.app.models.dtos.card.AddToCartDto;
import com.backend.app.models.responses.cart.AddToCartResponse;
import com.backend.app.models.responses.cart.DeleteToCardResponse;
import com.backend.app.models.responses.cart.GetDishToCartResponse;
import com.backend.app.models.responses.cart.GetDishesCartResponse;

public interface ICartService {
    AddToCartResponse addOneDishToCart(Long dishId) throws Exception;
    AddToCartResponse addManyDishesToCart(AddToCartDto addToCartDto) throws Exception;
    DeleteToCardResponse deleteOneDishFromCart(Long dishId) throws Exception;
    DeleteToCardResponse deleteAllDishesFromCart(Long dishId) throws Exception;
    GetDishesCartResponse getDishesCartByUser() throws Exception;
    GetDishToCartResponse getDishToCartByDishId(Long dishId) throws Exception;
}
