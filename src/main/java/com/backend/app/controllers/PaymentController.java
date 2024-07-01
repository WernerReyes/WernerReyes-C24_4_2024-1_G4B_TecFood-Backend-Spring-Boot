package com.backend.app.controllers;

import com.backend.app.models.IPaymentService;
import com.backend.app.models.dtos.requests.payment.FindPaymentByUserRequest;
import com.backend.app.models.dtos.requests.payment.ProcessPaymentRequest;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.models.dtos.responses.common.PagedResponse;
import com.backend.app.persistence.entities.PaymentEntity;
import com.backend.app.persistence.enums.payment.EPaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final IPaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<ApiResponse<PaymentEntity>> processPayment(
            @RequestBody ProcessPaymentRequest processPaymentRequest
    ) {
        return new ResponseEntity<>(paymentService.processPayment(processPaymentRequest), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<PagedResponse<List<PaymentEntity>>>> findPaymentByUser(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "COMPLETED") List<EPaymentStatus> status
    ) {
        FindPaymentByUserRequest findPaymentByUserRequest = new FindPaymentByUserRequest(status, page, limit);
        return new ResponseEntity<>(paymentService.findPaymentByUser(findPaymentByUserRequest), HttpStatus.OK);
    }
}
