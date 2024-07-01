package com.backend.app.models.dtos.requests.orderDish;

import com.backend.app.models.dtos.annotations.UniqueElements;
import com.backend.app.models.dtos.requests.common.PaginationRequest;
import com.backend.app.persistence.enums.EOrderDishStatus;
import com.backend.app.utilities.RequestValidatorUtility;
import lombok.*;


import java.util.List;

@Getter
@Setter
public class FindOrderDishesByUserRequest extends PaginationRequest {

    @UniqueElements(message = "The status must be unique")
    private List<
            EOrderDishStatus
            > status;

    public FindOrderDishesByUserRequest(List<EOrderDishStatus> status, Integer page, Integer limit) {
        super(page, limit);
        this.status = status;

        RequestValidatorUtility.validate(this);
    }
}
