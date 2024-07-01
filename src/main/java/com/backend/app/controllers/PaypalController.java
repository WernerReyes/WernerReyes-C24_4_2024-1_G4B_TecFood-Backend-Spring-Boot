package com.backend.app.controllers;

import com.backend.app.models.IPaypalService;
import com.backend.app.models.dtos.requests.paypal.CreatePaymentRequest;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.utilities.URLUtility;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/paypal")
public class PaypalController {

    private final IPaypalService paypalServiceImpl;
    private final String CAPTURE_URL = "/capture";
    private final String CANCEL_URL = "/cancel";

    @PostMapping("/create-payment")
    public ResponseEntity<ApiResponse<String>> createPayment(
            @RequestBody Map<String, Object> body,
            HttpServletRequest request
            ) {
        String urlCapture = URLUtility.getBaseURl(request) + "/api/paypal" +  CAPTURE_URL;
        String urlCancel = URLUtility.getBaseURl(request) + "/api/paypal" + CANCEL_URL;
        Long orderDishId = Long.parseLong(body.get("orderDishId").toString());
        CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest(orderDishId, urlCapture, urlCancel);
        return new ResponseEntity<>(paypalServiceImpl.createPayment(createPaymentRequest), HttpStatus.OK);
    }

    @PostMapping(CAPTURE_URL)
    public ResponseEntity<ApiResponse<String>> completePayment(
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
