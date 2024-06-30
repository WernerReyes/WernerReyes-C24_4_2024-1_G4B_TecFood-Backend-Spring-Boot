package com.backend.app.models.dtos.payment;

import com.backend.app.persistence.enums.payment.EPaymentMethod;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
    public class ProcessPaymentDto {

    @NotNull(message = "Order dish ID is required")
    private Long orderDishId;

    @NotNull(message = "Payment method is required")
    private EPaymentMethod paymentMethod;
}
