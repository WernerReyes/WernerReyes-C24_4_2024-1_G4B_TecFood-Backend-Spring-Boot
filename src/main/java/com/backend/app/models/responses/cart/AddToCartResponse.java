package com.backend.app.models.responses.cart;

import com.backend.app.persistence.entities.CartEntity;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"message", "cartItem"})
public record AddToCartResponse(String message, CartEntity cartItem) { }