package com.backend.app.models.responses.dishCategory;

import com.backend.app.persistence.entities.DishCategoryEntity;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"message","dishCategories"})
public record GetDishCategoriesResponse(
        String message,
        List<DishCategoryEntity> dishCategories
) { }
