package com.backend.app.models.dtos.requests.dish;

import com.backend.app.models.dtos.annotations.NotNullAndNotEmpty;
import com.backend.app.models.dtos.annotations.UniqueElements;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DishRequest {
    @NotNullAndNotEmpty(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @NotNullAndNotEmpty(message = "Description is required")
    @Size(min = 3, max = 255, message = "Description must be between 3 and 255 characters")
    private String description;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be greater than 0")
    @DecimalMax(value = "999999.99", message = "Price must be less than 999999.99")
    private double price;

    @Size(min = 1, max = 5, message = "At least one category is required and at most 5")
    @UniqueElements(message = "The categories must be unique")
    private List<
            @NotNull(message = "Category is required")
            @Min(value = 1, message = "Category ID must be greater than 0")
                    Long> categoriesId;


    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock must be greater than 0")
    private Integer stock;


}
