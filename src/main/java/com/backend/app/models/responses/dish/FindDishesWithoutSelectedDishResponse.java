package com.backend.app.models.responses.dish;

import com.backend.app.persistence.entities.DishEntity;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"message","dishes"})
public record FindDishesWithoutSelectedDishResponse(
        String message,
        List<DishEntity>dishes
) { }
