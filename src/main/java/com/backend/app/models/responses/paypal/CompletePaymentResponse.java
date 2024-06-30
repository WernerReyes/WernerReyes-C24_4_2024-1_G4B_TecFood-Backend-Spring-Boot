package com.backend.app.models.responses.paypal;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.paypal.http.HttpResponse;
import com.paypal.orders.Order;

@JsonPropertyOrder({
        "message",
        "id"
})
public record CompletePaymentResponse(
        String message,
        String id
) {
}
