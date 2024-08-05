package com.backend.app.models.dtos.requests.dish;

import com.backend.app.utilities.RequestValidatorUtility;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateDishRequest extends DishRequest {

    public CreateDishRequest(String name, String description, Double price, List<Long> categoriesId, Integer stock) {
        super(name, description, price, categoriesId, stock);

        RequestValidatorUtility.validate(this);
    }
}
