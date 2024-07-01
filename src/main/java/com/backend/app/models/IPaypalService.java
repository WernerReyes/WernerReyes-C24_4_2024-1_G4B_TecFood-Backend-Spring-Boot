package com.backend.app.models;

import com.backend.app.models.dtos.requests.paypal.CreatePaymentRequest;
import com.backend.app.models.dtos.responses.common.ApiResponse;

public interface IPaypalService {
    ApiResponse<String> createPayment(CreatePaymentRequest createPaymentRequest);
    ApiResponse<String> completePayment(String orderId);
}
