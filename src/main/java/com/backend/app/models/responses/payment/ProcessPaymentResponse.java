package com.backend.app.models.responses.payment;

import com.backend.app.persistence.entities.PaymentEntity;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "message",
        "payment",
        "invoiceReportUrl"
})
public record ProcessPaymentResponse(
        String message,
        PaymentEntity payment,
        String invoiceReportUrl
) {
}
