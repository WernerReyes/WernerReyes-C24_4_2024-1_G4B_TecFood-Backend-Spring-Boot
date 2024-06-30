package com.backend.app.models.dtos.dish;

import com.backend.app.models.dtos.common.PaginationDto;
import lombok.*;
import javax.validation.constraints.*;
import java.util.List;

@Getter
@AllArgsConstructor
public class FindDishesDto extends PaginationDto {

    private List<
            @Min(value = 1, message = "Category ID must be greater than 0")
            @Digits(integer = 10, fraction = 0, message = "Category ID must be a number and integer")
            Long
            > idCategory;

    @Min(value = 3, message = "Page must be greater than 3")
    private String search;

    @Min(value = 0, message = "Min value must be greater than 0")
    @Max(value = 1000, message = "Min value must be less than 1000")
    private Integer min;

    @Min(value = 0, message = "Max value must be greater than 0")
    @Max(value = 1000, message = "Max value must be less than 1000")
    private Integer max;

    public FindDishesDto(Integer page, Integer limit, List<Long> idCategory, String search, Integer min, Integer max) {
        super(page, limit);
        this.idCategory = idCategory;
        this.search = search;
        this.min = min;
        this.max = max;
    }
}

