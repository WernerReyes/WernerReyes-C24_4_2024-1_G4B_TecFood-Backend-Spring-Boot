package com.backend.app.models.responses.dishCategory;

import com.backend.app.persistence.entities.DishCategoryEntity;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"message","dishCategory"})
public record GetDishCategoryResponse(
        String message,
        DishCategoryEntity dishCategory
) { }
