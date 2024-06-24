package com.backend.app.models;

import com.backend.app.models.dtos.card.AddToCartDto;
import com.backend.app.models.responses.cartDish.*;

public interface ICartDishService {
    AddToCartResponse addOneDishToCart(Long dishId);
    AddToCartResponse addManyDishesToCart(AddToCartDto addToCartDto);
    DeleteToCardResponse deleteOneDishFromCart(Long dishId);
    DeleteToCardResponse deleteAllDishesFromCart(Long dishId);
    FindDishesToCartResponse findDishesCartByUser();
    FindTotalDishesToCartResponse findTotalDishesCartByUser();
    FindDishToCartResponse findDishToCartByDishId(Long dishId);
}
