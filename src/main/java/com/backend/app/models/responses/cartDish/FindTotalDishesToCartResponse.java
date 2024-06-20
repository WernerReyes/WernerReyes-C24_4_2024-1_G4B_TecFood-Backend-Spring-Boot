package com.backend.app.models.responses.cartDish;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"message","totalQuantity"})
public record FindTotalDishesToCartResponse(
        String message,
        Integer totalQuantity
) {
}
