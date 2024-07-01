package com.backend.app.models.dtos.requests.cartDish;

import com.backend.app.utilities.RequestValidatorUtility;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AddToCartRequest {
    @NotNull(message = "Dish ID is required")
    @Min(value = 1, message = "Dish ID must be greater than 0")
    private Long dishId;

    @Min(value = 1, message = "Quantity must be greater than 1")
    @Max(value = 5, message = "Quantity must be less than 5")
    private Integer quantity;

    public AddToCartRequest(Long dishId, Integer quantity) {
        this.dishId = dishId;
        this.quantity = quantity;

        RequestValidatorUtility.validate(this);
    }

}
