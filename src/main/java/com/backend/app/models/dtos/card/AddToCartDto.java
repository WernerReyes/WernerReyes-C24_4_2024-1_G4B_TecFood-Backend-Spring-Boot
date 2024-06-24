package com.backend.app.models.dtos.card;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddToCartDto {

    @NotNull(message = "Dish ID is required")
    @Min(value = 1, message = "Dish ID must be greater than 0")
    private Long dishId;

    @Min(value = 1, message = "Quantity must be greater than 1")
    @Max(value = 5, message = "Quantity must be less than 5")
    private Integer quantity;
}
