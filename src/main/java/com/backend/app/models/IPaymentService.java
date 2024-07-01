package com.backend.app.models;

import com.backend.app.models.dtos.requests.payment.FindPaymentByUserRequest;
import com.backend.app.models.dtos.requests.payment.ProcessPaymentRequest;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.models.dtos.responses.common.PagedResponse;
import com.backend.app.persistence.entities.PaymentEntity;

import java.util.List;

public interface IPaymentService {
    ApiResponse<PaymentEntity> processPayment(ProcessPaymentRequest processPaymentRequest);
    ApiResponse<PagedResponse<List<PaymentEntity>>> findPaymentByUser(FindPaymentByUserRequest findPaymentByUserRequest);
}
