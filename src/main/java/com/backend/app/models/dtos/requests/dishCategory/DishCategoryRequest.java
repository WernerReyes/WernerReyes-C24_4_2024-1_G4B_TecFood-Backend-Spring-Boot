package com.backend.app.models.dtos.requests.dishCategory;

import com.backend.app.models.dtos.annotations.NotNullAndNotEmpty;
import com.backend.app.persistence.enums.EStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishCategoryRequest {
    @NotNullAndNotEmpty(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;
}