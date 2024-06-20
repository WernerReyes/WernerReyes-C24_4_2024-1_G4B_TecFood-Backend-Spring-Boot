package com.backend.app.models.dtos.orderDish;

import com.backend.app.exceptions.DtoException;
import com.backend.app.models.dtos.common.PaginationDto;
import com.backend.app.persistence.enums.EOrderDishStatus;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FindOrderDishesByUserDto extends PaginationDto {
    private List<EOrderDishStatus> status;
    public static DtoException<FindOrderDishesByUserDto> create(FindOrderDishesByUserDto body) {
        DtoException<PaginationDto> paginationDto = PaginationDto.create(body.getPage(), body.getLimit());
        if(paginationDto.getError() != null) return new DtoException<>(paginationDto.getError(), null);
        boolean allStatusExist = body.getStatus().stream()
                .allMatch(Arrays.asList(EOrderDishStatus.values())::contains);
        if(!allStatusExist) return new DtoException<>("Invalid status", null);
        return new DtoException<>(null, body);
    }
}
