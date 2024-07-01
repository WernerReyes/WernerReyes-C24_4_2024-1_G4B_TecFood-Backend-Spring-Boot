package com.backend.app.models.dtos.requests.dish;

import com.backend.app.models.dtos.requests.common.PaginationRequest;
import com.backend.app.utilities.RequestValidatorUtility;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FindDishesWithoutSelectedDishRequest extends PaginationRequest {
    @NotNull(message = "Selected dish ID is required")
    private Long selectedDishId;

    public FindDishesWithoutSelectedDishRequest(Integer limit, Long selectedDishId) {
        super(1, limit);
        this.selectedDishId = selectedDishId;

        RequestValidatorUtility.validate(this);
    }

}
