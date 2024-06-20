package com.backend.app.models.dtos.payment;

import com.backend.app.exceptions.DtoException;
import com.backend.app.persistence.enums.payment.EPaymentMethod;
import com.backend.app.utilities.ValidationsUtility;
import lombok.*;

import java.util.Arrays;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
    public class ProcessPaymentDto {
    private Long orderDishId;
    private EPaymentMethod paymentMethod;

    public static DtoException<ProcessPaymentDto> create(
            ProcessPaymentDto body
    ) throws IllegalAccessException {
        if(ValidationsUtility.hasNullField(body)) return new DtoException<>("One or more fields are empty", null);
        EPaymentMethod paymentMethod = Arrays.stream(EPaymentMethod.values())
                .filter(e -> e.equals(body.paymentMethod))
                .findFirst()
                .orElse(null);
        if(paymentMethod  == null) return new DtoException<>("Invalid payment method", null);
        return new DtoException<>(null, body);
    }
}
