package com.backend.app.models;

import com.backend.app.models.dtos.paypal.CreatePaymentDto;
import com.backend.app.models.responses.paypal.CompletePaymentResponse;
import com.backend.app.models.responses.paypal.CreatePaymentResponse;

public interface IPaypalService {
    CreatePaymentResponse createPayment(CreatePaymentDto createPaymentDto);
    CompletePaymentResponse completePayment(String orderId);
}
