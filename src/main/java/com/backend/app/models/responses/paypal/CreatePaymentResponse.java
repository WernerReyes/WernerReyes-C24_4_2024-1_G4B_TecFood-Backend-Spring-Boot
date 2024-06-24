package com.backend.app.models.responses.paypal;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "message",
        "id"
})
public record CreatePaymentResponse(
        String message,
        String id
) { }
