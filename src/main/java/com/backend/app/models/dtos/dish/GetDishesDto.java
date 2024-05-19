package com.backend.app.models.dtos.dish;
import com.backend.app.exception.DtoException;
import com.backend.app.models.dtos.common.PaginationDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetDishesDto extends PaginationDto {
    private Long idCategory;
    private String search;

    private GetDishesDto(int page, int limit, Long idCategory, String search) {
        super(page, limit);
        this.idCategory = idCategory;
        this.search = search;
    }

    public static DtoException<GetDishesDto> create(int page, int limit, Long idCategory, String search) {
        DtoException<PaginationDto> paginationDto = PaginationDto.create(page, limit);
        if (paginationDto.getError() != null) return new DtoException<>(paginationDto.getError(), null);
        if(idCategory != null) {
            if (idCategory < 1) return new DtoException<>("Invalid category id", null);
        }
        return new DtoException<>(null, new GetDishesDto(page, limit, idCategory, search));
    }
}

