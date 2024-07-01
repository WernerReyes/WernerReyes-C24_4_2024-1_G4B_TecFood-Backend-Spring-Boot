package com.backend.app.models.dtos.requests.payment;


import com.backend.app.models.dtos.requests.common.PaginationRequest;
import com.backend.app.persistence.enums.payment.EPaymentStatus;
import com.backend.app.utilities.RequestValidatorUtility;
import lombok.Getter;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FindPaymentByUserRequest extends PaginationRequest {
    @NotNull(message = "Status is required")
    private List<EPaymentStatus> status;

    public FindPaymentByUserRequest(List<EPaymentStatus> status, Integer page, Integer limit) {
        super(page, limit);
        this.status = status;

        RequestValidatorUtility.validate(this);
    }

}
