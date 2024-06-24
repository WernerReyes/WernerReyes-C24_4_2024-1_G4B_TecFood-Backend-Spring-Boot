package com.backend.app.models.responses.paypal;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.paypal.http.HttpResponse;
import com.paypal.orders.Order;

@JsonPropertyOrder({
        "message",
        "order"
})
public record CompletePaymentResponse(
        String message,
        Order order
) {
}
