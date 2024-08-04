package com.backend.app.models.dtos.requests.dishCategory;

import com.backend.app.utilities.RequestValidatorUtility;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateDishCategoryRequest extends DishCategoryRequest {

    @JsonCreator
    public CreateDishCategoryRequest(String name) {
        super(name);

        RequestValidatorUtility.validate(this);
    }
}
