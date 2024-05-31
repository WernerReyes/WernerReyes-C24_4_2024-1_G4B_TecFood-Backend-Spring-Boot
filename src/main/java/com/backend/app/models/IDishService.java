package com.backend.app.models;

import com.backend.app.models.dtos.dish.GetDishesDto;
import com.backend.app.models.responses.dish.GetDishResponse;
import com.backend.app.models.responses.dish.GetDishesResponse;
import com.backend.app.models.responses.dish.GetDishesToSearchResponse;

public interface IDishService {
    public GetDishesResponse findAll(GetDishesDto getDishesDto);
    public GetDishesToSearchResponse findAllToSearch();
    public GetDishResponse findById(Long id);
    public GetDishResponse findByName(String name);
}
