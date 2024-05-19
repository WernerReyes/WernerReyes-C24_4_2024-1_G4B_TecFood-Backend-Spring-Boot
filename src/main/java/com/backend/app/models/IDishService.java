package com.backend.app.models;

import com.backend.app.models.dtos.dish.GetDishesDto;
import com.backend.app.models.responses.dish.GetDishResponse;
import com.backend.app.models.responses.dish.GetDishesResponse;

public interface IDishService {
    public GetDishesResponse findAll(GetDishesDto getDishesDto);
    public GetDishResponse findById(Long id);
}
