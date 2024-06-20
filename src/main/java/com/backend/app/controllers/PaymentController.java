package com.backend.app.controllers;

import com.backend.app.exceptions.CustomException;
import com.backend.app.exceptions.DtoException;
import com.backend.app.models.dtos.payment.ProcessPaymentDto;
import com.backend.app.models.responses.payment.ProcessPaymentResponse;
import com.backend.app.services.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    PaymentServiceImpl paymentServiceImpl;

    @PostMapping("/process")
    public ResponseEntity<ProcessPaymentResponse> processPayment(
            @RequestBody ProcessPaymentDto processPaymentDto
    ) throws IllegalAccessException {
        DtoException<ProcessPaymentDto> processPaymentDtoException = ProcessPaymentDto.create(processPaymentDto);
        if (processPaymentDtoException.getError() != null)
            throw CustomException.badRequest(processPaymentDtoException.getError());
        return new ResponseEntity<>(paymentServiceImpl.processPayment(processPaymentDtoException.getData()), HttpStatus.OK);
    }
}
