package com.backend.app.models.responses.cart;

import com.backend.app.persistence.entities.CartEntity;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
@JsonPropertyOrder({"message", "cart", "totalQuantity", "totalPayment"})
public record GetDishesCartResponse(String message, List<CartEntity> cart, int totalQuantity, Double totalPayment) {
}
