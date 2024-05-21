package com.backend.app.models.responses.cart;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"message"})
public record AddToCartResponse(String message) { }