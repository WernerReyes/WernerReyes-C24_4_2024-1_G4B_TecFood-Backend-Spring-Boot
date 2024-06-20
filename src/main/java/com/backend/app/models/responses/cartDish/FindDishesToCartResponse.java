package com.backend.app.models.responses.cartDish;

import com.backend.app.persistence.entities.CartDishEntity;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
@JsonPropertyOrder({"message", "cart", "totalQuantity", "totalPayment"})
public record FindDishesToCartResponse(String message, List<CartDishEntity> cart, int totalQuantity, Double totalPayment) {
}
