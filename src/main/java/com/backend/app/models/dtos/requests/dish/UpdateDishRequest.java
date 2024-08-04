package com.backend.app.models.dtos.requests.dish;

import com.backend.app.utilities.RequestValidatorUtility;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UpdateDishRequest extends DishRequest {
    @NotNull(message = "Dish ID is required")
    @Min(value = 1, message = "Dish ID must be greater than 0")
    private Long dishId;

    public UpdateDishRequest(Long dishId, String name, String description, Double price, List<Long> categoriesId, Integer stock) {
        super(name, description, price, categoriesId, stock);
        this.dishId = dishId;

        RequestValidatorUtility.validate(this);
    }


}
