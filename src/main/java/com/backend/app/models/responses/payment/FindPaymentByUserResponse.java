package com.backend.app.models.responses.payment;

import com.backend.app.persistence.entities.PaymentEntity;
import com.backend.app.persistence.enums.payment.EPaymentStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"message","payments", "currentPage", "total", "limit"})
public record FindPaymentByUserResponse(
        String message,
        List<PaymentEntity> payments,
        int currentPage,
        int total,
        int limit
) {
}
