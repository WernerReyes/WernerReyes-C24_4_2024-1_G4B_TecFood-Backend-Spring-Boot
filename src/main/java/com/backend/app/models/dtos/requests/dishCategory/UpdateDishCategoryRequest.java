package com.backend.app.models.dtos.requests.dishCategory;

import com.backend.app.persistence.enums.EStatus;
import com.backend.app.utilities.RequestValidatorUtility;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UpdateDishCategoryRequest extends DishCategoryRequest {

    @NotNull(message = "Id is required")
    @Min(value = 1, message = "Id must be greater than 0")
    private Long id;

    public UpdateDishCategoryRequest(Long id, String name) {
        super(name);
        this.id = id;

        RequestValidatorUtility.validate(this);
    }
}
