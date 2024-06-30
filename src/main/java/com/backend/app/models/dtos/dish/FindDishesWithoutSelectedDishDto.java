package com.backend.app.models.dtos.dish;

import com.backend.app.models.dtos.common.PaginationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FindDishesWithoutSelectedDishDto extends PaginationDto {
    @NotNull(message = "Selected dish ID is required")
    private Long selectedDishId;

    public FindDishesWithoutSelectedDishDto(Integer limit, Long selectedDishId) {
        super(1, limit);
        this.selectedDishId = selectedDishId;
    }
}
