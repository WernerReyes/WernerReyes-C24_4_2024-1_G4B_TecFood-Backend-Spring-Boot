package com.backend.app.models;

import com.backend.app.models.dtos.payment.ProcessPaymentDto;
import com.backend.app.models.responses.payment.ProcessPaymentResponse;

public interface IPaymentService {
    public ProcessPaymentResponse processPayment(ProcessPaymentDto processPaymentDto);
}
