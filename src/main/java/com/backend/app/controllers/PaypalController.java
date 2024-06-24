package com.backend.app.controllers;

import com.backend.app.exceptions.CustomException;
import com.backend.app.exceptions.DtoException;
import com.backend.app.models.dtos.paypal.CreatePaymentDto;
import com.backend.app.models.responses.paypal.CompletePaymentResponse;
import com.backend.app.models.responses.paypal.CreatePaymentResponse;
import com.backend.app.services.PaypalServiceImpl;
import com.backend.app.utilities.DtoValidatorUtility;
import com.backend.app.utilities.URLUtility;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/paypal")
public class PaypalController {

    private final String CAPTURE_URL = "/capture";
    private final String CANCEL_URL = "/cancel";

    @Autowired
    private PaypalServiceImpl paypalServiceImpl;

    @PostMapping("/create-payment")
    public ResponseEntity<CreatePaymentResponse> createPayment(
            @RequestBody Map<String, Object> body,
            HttpServletRequest request
            ) {
        String urlCapture = URLUtility.getBaseURl(request) + "/api/paypal" +  CAPTURE_URL;
        String urlCancel = URLUtility.getBaseURl(request) + "/api/paypal" + CANCEL_URL;
        Long orderDishId = Long.parseLong(body.get("orderDishId").toString());
        CreatePaymentDto createPaymentDto = new CreatePaymentDto(orderDishId, urlCapture, urlCancel);
        DtoException<CreatePaymentDto> createPaymentDtoException = DtoValidatorUtility.validate(createPaymentDto);
        if (createPaymentDtoException.getError() != null) throw  CustomException.badRequest(createPaymentDtoException.getError());
        return new ResponseEntity<>(paypalServiceImpl.createPayment(
                createPaymentDtoException.getData()
        ), HttpStatus.OK);
    }

    @PostMapping(CAPTURE_URL)
    public ResponseEntity<CompletePaymentResponse> completePayment(
            @RequestBody Map<String, Object> body
    ) {
        String orderId = body.get("orderId").toString();
        return new ResponseEntity<>(paypalServiceImpl.completePayment(
                orderId
        ), HttpStatus.OK);
    }

    @PostMapping(CANCEL_URL)
    public ResponseEntity<String> cancelPayment() {
        return new ResponseEntity<>("Payment cancelled", HttpStatus.OK);
    }

}
