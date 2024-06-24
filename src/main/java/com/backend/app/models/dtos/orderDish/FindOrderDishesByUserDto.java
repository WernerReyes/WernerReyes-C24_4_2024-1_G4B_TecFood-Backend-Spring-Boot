package com.backend.app.models.dtos.orderDish;

import com.backend.app.models.dtos.common.PaginationDto;
import com.backend.app.persistence.enums.EOrderDishStatus;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FindOrderDishesByUserDto extends PaginationDto {

    private List<
            EOrderDishStatus
            > status;

    public FindOrderDishesByUserDto(List<EOrderDishStatus> status, Integer page, Integer limit) {
        super(page, limit);
        this.status = status;
    }
}
