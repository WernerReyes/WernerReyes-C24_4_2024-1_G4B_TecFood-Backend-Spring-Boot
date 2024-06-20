package com.backend.app.models.responses.cartDish;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"message", "quantity"})
public record DeleteToCardResponse(String message, int quantity) {
}
