package com.backend.app.models;

import com.backend.app.models.responses.dishCategory.GetDishCategoriesResponse;
import com.backend.app.models.responses.dishCategory.GetDishCategoryResponse;

public interface IDishCategoryService {
    public GetDishCategoriesResponse findAll();
    public GetDishCategoryResponse findById(Long id);
}
