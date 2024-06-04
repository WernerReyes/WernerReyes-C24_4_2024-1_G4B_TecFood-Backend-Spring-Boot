package com.backend.app.models.responses.dish;

import com.backend.app.persistence.entities.DishEntity;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"message","dish"})
public record GetDishResponse(
        String message,
        DishEntity dish
) { }
