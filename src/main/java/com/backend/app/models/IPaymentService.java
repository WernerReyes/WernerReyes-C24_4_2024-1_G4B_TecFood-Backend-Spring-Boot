package com.backend.app.models;

import com.backend.app.models.dtos.payment.FindPaymentByUserDto;
import com.backend.app.models.dtos.payment.ProcessPaymentDto;
import com.backend.app.models.responses.payment.FindPaymentByUserResponse;
import com.backend.app.models.responses.payment.ProcessPaymentResponse;

public interface IPaymentService {
    ProcessPaymentResponse processPayment(ProcessPaymentDto processPaymentDto);
    FindPaymentByUserResponse findPaymentByUser(FindPaymentByUserDto findPaymentByUserDto);
}
