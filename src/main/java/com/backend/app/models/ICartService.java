package com.backend.app.models;

import com.backend.app.models.dtos.card.AddToCartDto;
import com.backend.app.models.responses.cart.AddToCartResponse;
import com.backend.app.models.responses.cart.DeleteToCardResponse;
import com.backend.app.models.responses.cart.GetDishesCartResponse;

public interface ICartService {
    AddToCartResponse addOneDishToCart(Long dishId) throws Exception;
    AddToCartResponse addManyDishesToCart(AddToCartDto addToCartDto) throws Exception;
    DeleteToCardResponse deleteOneDishFromCart(Long dishId) throws Exception;
    DeleteToCardResponse deleteItemFromCart(Long cartId) throws Exception;
    GetDishesCartResponse getDishesCart() throws Exception;
}
