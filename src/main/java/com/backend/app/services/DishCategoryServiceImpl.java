package com.backend.app.services;

import com.backend.app.exception.CustomException;
import com.backend.app.models.IDishCategoryService;
import com.backend.app.models.responses.dishCategory.GetDishCategoriesResponse;
import com.backend.app.models.responses.dishCategory.GetDishCategoryResponse;
import com.backend.app.persistence.entities.DishCategoryEntity;
import com.backend.app.persistence.repositories.DishCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DishCategoryServiceImpl implements IDishCategoryService {

    @Autowired
    private DishCategoryRepository dishCategoryRepository;

    @Override
    public GetDishCategoriesResponse findAll() {
        return new GetDishCategoriesResponse(
                "All categories",
                dishCategoryRepository.findAll()
        );
    }

    @Override
    public GetDishCategoryResponse findById(Long id) {
        DishCategoryEntity dishCategory = dishCategoryRepository.findById(id).orElse(null);
        if (dishCategory == null) throw CustomException.badRequest("Category not found");

        return new GetDishCategoryResponse(
                "Category found",
                dishCategory
        );
    }
}
