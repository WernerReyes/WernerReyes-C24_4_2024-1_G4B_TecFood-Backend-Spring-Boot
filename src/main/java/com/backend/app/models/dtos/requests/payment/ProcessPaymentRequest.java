package com.backend.app.models.dtos.requests.payment;

import com.backend.app.persistence.enums.payment.EPaymentMethod;
import com.backend.app.utilities.RequestValidatorUtility;
import lombok.*;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
    public class ProcessPaymentRequest {

    @NotNull(message = "Order dish ID is required")
    private Long orderDishId;

    @NotNull(message = "Payment method is required")
    private EPaymentMethod paymentMethod;

    public ProcessPaymentRequest(Long orderDishId, EPaymentMethod paymentMethod) {
        this.orderDishId = orderDishId;
        this.paymentMethod = paymentMethod;

        RequestValidatorUtility.validate(this);
    }
}
