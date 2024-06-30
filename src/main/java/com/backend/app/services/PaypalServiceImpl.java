package com.backend.app.services;

import com.backend.app.exceptions.CustomException;
import com.backend.app.models.IPaypalService;
import com.backend.app.models.dtos.paypal.CreatePaymentDto;
import com.backend.app.models.responses.paypal.CompletePaymentResponse;
import com.backend.app.models.responses.paypal.CreatePaymentResponse;
import com.backend.app.persistence.entities.OrderDishEntity;
import com.backend.app.persistence.repositories.OrderDishItemRepository;
import com.backend.app.persistence.repositories.OrderDishRepository;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaypalServiceImpl implements IPaypalService {

    @Autowired
    private PayPalHttpClient payPalHttpClient;

    @Autowired
    private OrderDishRepository orderDishRepository;

    @Autowired
    private OrderDishItemRepository orderDishItemRepository;

    public CreatePaymentResponse createPayment(CreatePaymentDto createPaymentDto) {
        System.out.println("createPaymentDto = " + createPaymentDto.getOrderDishId());
        OrderDishEntity orderDish = orderDishRepository.findById(createPaymentDto.getOrderDishId()).orElseThrow(() -> CustomException.notFound("Order not found"));
        List<Item> items = new ArrayList<>(orderDishItemRepository.findByOrderDish(orderDish).stream().map(orderDishItem -> new Item().name(orderDishItem.getDish().getName()).unitAmount(new Money().currencyCode("USD").value(String.valueOf(fromSolesToDollars(orderDishItem.getDish().getPrice())))).quantity(String.valueOf(orderDishItem.getQuantity()))).toList());

        double totalAmount = items.stream().mapToDouble(item -> Double.parseDouble(item.unitAmount().value()) * Integer.parseInt(item.quantity())).sum();
        BigDecimal totalAmountBD = BigDecimal.valueOf(totalAmount);
        totalAmountBD = totalAmountBD.setScale(2, RoundingMode.HALF_UP);
        totalAmount = totalAmountBD.doubleValue();

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");
        AmountWithBreakdown amountBreakdown = new AmountWithBreakdown()
                .currencyCode("USD").value(String.valueOf(totalAmount))
                .amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode("USD").value(String.valueOf(totalAmount))));
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .amountWithBreakdown(amountBreakdown).items(items).description("Payment for items");
        orderRequest.purchaseUnits(List.of(purchaseUnitRequest));
        ApplicationContext applicationContext = new ApplicationContext()
                .returnUrl(createPaymentDto.getUrlCapture())
                .cancelUrl(createPaymentDto.getUrlCancel());
        orderRequest.applicationContext(applicationContext);
        OrdersCreateRequest ordersCreateRequest = new OrdersCreateRequest().requestBody(orderRequest);

        try {
            HttpResponse<Order> orderHttpResponse = payPalHttpClient.execute(ordersCreateRequest);

            String id = orderHttpResponse.result().id();

            return new CreatePaymentResponse("Payment created", id);
        } catch (IOException e) {
            System.out.println("e = " + e.getMessage());
            throw CustomException.internalServerError("Error creating payment");
        }
    }

    public CompletePaymentResponse completePayment(
            String orderId
    ) {
        OrdersCaptureRequest ordersCaptureRequest = new OrdersCaptureRequest(orderId);
        ordersCaptureRequest.requestBody(new OrderActionRequest());
        try {

            HttpResponse<Order> httpResponse = payPalHttpClient.execute(ordersCaptureRequest);
            if (httpResponse.result().status() != null) {
                return new CompletePaymentResponse("Payment completed", httpResponse.result().id());
            }
        } catch (IOException e) {
            throw CustomException.internalServerError("Error completing payment");
        }

        throw CustomException.internalServerError("Error completing payment");
    }


    private double fromSolesToDollars(double soles) {
        return Math.round((soles / 3.8) * 100.0) / 100.0;
    }
}
