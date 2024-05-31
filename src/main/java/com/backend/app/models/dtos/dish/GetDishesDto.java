package com.backend.app.models.dtos.dish;
import com.backend.app.exception.DtoException;
import com.backend.app.models.dtos.common.PaginationDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetDishesDto extends PaginationDto {
    private List<Long> idCategory;
    private String search;
    private Integer min;
    private Integer max;

    private GetDishesDto(Integer page, Integer limit, List<Long> idCategory, Integer min, Integer max, String search) {
        super(page, limit);
        this.idCategory = idCategory;
        this.min = min;
        this.max = max;
        this.search = search;
    }

    public static DtoException<GetDishesDto> create(Integer page, Integer limit, List<Long> idCategory, Integer min, Integer max, String search) {
        DtoException<PaginationDto> paginationDto = PaginationDto.create(page, limit);
        if (paginationDto.getError() != null) return new DtoException<>(paginationDto.getError(), null);
        if(idCategory != null) {
            if (idCategory.stream().mapToDouble(Long::doubleValue).anyMatch(id -> id <= 0)) return new DtoException<>("One or more fields are empty", null);
        }
        if(min != null && min < 0) return new DtoException<>("Min value must be greater than 0", null);
        if(max != null && max < 0) return new DtoException<>("Max value must be greater than 0", null);
        return new DtoException<>(null, new GetDishesDto(page, limit, idCategory, min, max, search));
    }
}

