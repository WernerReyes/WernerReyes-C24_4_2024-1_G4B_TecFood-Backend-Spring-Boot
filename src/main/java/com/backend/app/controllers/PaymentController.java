package com.backend.app.controllers;

import com.backend.app.exceptions.CustomException;
import com.backend.app.exceptions.DtoException;
import com.backend.app.models.dtos.payment.FindPaymentByUserDto;
import com.backend.app.models.dtos.payment.ProcessPaymentDto;
import com.backend.app.models.responses.payment.FindPaymentByUserResponse;
import com.backend.app.models.responses.payment.ProcessPaymentResponse;
import com.backend.app.persistence.enums.payment.EPaymentStatus;
import com.backend.app.services.PaymentServiceImpl;
import com.backend.app.utilities.DtoValidatorUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    PaymentServiceImpl paymentServiceImpl;

    @PostMapping("/process")
    public ResponseEntity<ProcessPaymentResponse> processPayment(
            @RequestBody ProcessPaymentDto processPaymentDto
    ) {
        DtoException<ProcessPaymentDto> processPaymentDtoException = DtoValidatorUtility.validate(processPaymentDto);
        if (processPaymentDtoException.getError() != null)
            throw CustomException.badRequest(processPaymentDtoException.getError());
        return new ResponseEntity<>(paymentServiceImpl.processPayment(processPaymentDtoException.getData()), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<FindPaymentByUserResponse> findPaymentByUser(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "COMPLETED") List<EPaymentStatus> status
    ) {
        FindPaymentByUserDto findPaymentByUserDto = new FindPaymentByUserDto(status, page, limit);
        DtoException<FindPaymentByUserDto> findPaymentByUserDtoException = DtoValidatorUtility.validate(findPaymentByUserDto);
        if (findPaymentByUserDtoException.getError() != null)
            throw CustomException.badRequest(findPaymentByUserDtoException.getError());
        return new ResponseEntity<>(paymentServiceImpl.findPaymentByUser(findPaymentByUserDtoException.getData()), HttpStatus.OK);
    }
}
