package com.backend.app.models.responses.dish;

import com.backend.app.persistence.entities.DishEntity;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"message","dishes"})
public record GetDishResponse(
        String message,
        DishEntity dish
) { }
