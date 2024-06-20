package com.backend.app.models.dtos.dish;
import com.backend.app.exceptions.DtoException;
import com.backend.app.models.dtos.common.PaginationDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetDishesDto extends PaginationDto {
    private List<Long> idCategory;
    private String search;
    private Integer min;
    private Integer max;

    public static DtoException<GetDishesDto> create(GetDishesDto body) {
        DtoException<PaginationDto> paginationDto = PaginationDto.create(body.getPage(), body.getLimit());
        if (paginationDto.getError() != null) return new DtoException<>(paginationDto.getError(), null);
        if(body.getIdCategory() != null) {
            if (body.getIdCategory().stream().mapToDouble(Long::doubleValue).anyMatch(id -> id <= 0)) return new DtoException<>("One or more fields are empty", null);
        }
        if(body.min != null && body.min < 0) return new DtoException<>("Min value must be greater than 0", null);
        if(body.max != null && body.max < 0) return new DtoException<>("Max value must be greater than 0", null);
        return new DtoException<>(null, body);
    }
}

