package com.backend.app.models.dtos.responses.dishCategory;

import com.backend.app.persistence.entities.DishCategoryEntity;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"dishCategories", "total"})
public record CreateManyDishCategoriesResponse(
        List<DishCategoryEntity> dishCategories,
        Integer total
) {
}
