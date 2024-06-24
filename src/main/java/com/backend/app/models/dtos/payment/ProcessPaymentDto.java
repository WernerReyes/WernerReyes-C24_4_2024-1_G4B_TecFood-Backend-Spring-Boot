package com.backend.app.models.dtos.payment;

import com.backend.app.models.dtos.NotNullAndNotEmpty;
import com.backend.app.persistence.enums.payment.EPaymentMethod;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
    public class ProcessPaymentDto {

    @NotNullAndNotEmpty(message = "Order dish ID is required")
    private Long orderDishId;

    @NotNullAndNotEmpty(message = "Payment method is required")
    private EPaymentMethod paymentMethod;
}
