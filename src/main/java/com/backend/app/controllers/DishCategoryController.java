package com.backend.app.controllers;

import com.backend.app.models.responses.dishCategory.GetDishCategoriesResponse;
import com.backend.app.models.responses.dishCategory.GetDishCategoryResponse;
import com.backend.app.services.DishCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dish-category")
public class DishCategoryController {

    @Autowired
    private DishCategoryServiceImpl dishCategoryServiceImpl;

    @GetMapping("")
    public ResponseEntity<GetDishCategoriesResponse> findAll() {
        return new ResponseEntity<>(dishCategoryServiceImpl.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetDishCategoryResponse> findById(@PathVariable Long id) {
        return new ResponseEntity<>(dishCategoryServiceImpl.findById(id), HttpStatus.OK);
    }
}