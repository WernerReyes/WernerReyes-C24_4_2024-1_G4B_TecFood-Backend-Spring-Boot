package com.backend.app.models;

import com.backend.app.models.dtos.dish.FindDishesDto;
import com.backend.app.models.dtos.dish.FindDishesWithoutSelectedDishDto;
import com.backend.app.models.responses.dish.FindDishResponse;
import com.backend.app.models.responses.dish.FindDishesResponse;
import com.backend.app.models.responses.dish.FindDishesToSearchResponse;
import com.backend.app.models.responses.dish.FindDishesWithoutSelectedDishResponse;

public interface IDishService {
    FindDishesResponse findAll(FindDishesDto findDishesDto);
    FindDishesToSearchResponse findAllToSearch();
    FindDishesWithoutSelectedDishResponse findAllWithoutSelectedDish(FindDishesWithoutSelectedDishDto findDishesWithoutSelectedDishDto);
    FindDishResponse findById(Long id);
    FindDishResponse findByName(String name);
}
