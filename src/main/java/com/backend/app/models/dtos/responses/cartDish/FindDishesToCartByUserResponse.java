package com.backend.app.models.dtos.responses.cartDish;

import com.backend.app.persistence.entities.CartDishEntity;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
@JsonPropertyOrder({"cart", "totalQuantity", "totalPayment"})
public record FindDishesToCartByUserResponse(List<CartDishEntity> cart, int totalQuantity, Double totalPayment) {
}
