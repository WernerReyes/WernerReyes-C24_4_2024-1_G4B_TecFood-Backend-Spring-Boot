package com.backend.app.models;

import com.backend.app.models.dtos.card.AddToCartDto;
import com.backend.app.models.responses.cartDish.*;

public interface ICartDishService {
    AddToCartResponse addOneDishToCart(Long dishId) throws Exception;
    AddToCartResponse addManyDishesToCart(AddToCartDto addToCartDto) throws Exception;
    DeleteToCardResponse deleteOneDishFromCart(Long dishId) throws Exception;
    DeleteToCardResponse deleteAllDishesFromCart(Long dishId) throws Exception;
    FindDishesToCartResponse findDishesCartByUser() throws Exception;
    FindTotalDishesToCartResponse findTotalDishesCartByUser() throws Exception;
    FindDishToCartResponse findDishToCartByDishId(Long dishId) throws Exception;
}
