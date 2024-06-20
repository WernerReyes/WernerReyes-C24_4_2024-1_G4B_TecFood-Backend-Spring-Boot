package com.backend.app.services;

import com.backend.app.exceptions.CustomException;
import com.backend.app.models.IPaymentService;
import com.backend.app.models.dtos.payment.ProcessPaymentDto;
import com.backend.app.models.responses.payment.ProcessPaymentResponse;
import com.backend.app.persistence.entities.OrderDishEntity;
import com.backend.app.persistence.entities.PaymentEntity;
import com.backend.app.persistence.entities.UserEntity;
import com.backend.app.persistence.enums.EOrderDishStatus;
import com.backend.app.persistence.enums.payment.EPaymentStatus;
import com.backend.app.persistence.repositories.OrderDishRepository;
import com.backend.app.persistence.repositories.PaymentRepository;
import com.backend.app.utilities.CloudinaryUtility;
import com.backend.app.utilities.InvoiceReportUtility;
import com.backend.app.utilities.UserAuthenticationUtility;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements IPaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderDishRepository orderDishRepository;

    @Autowired
    private UserAuthenticationUtility userAuthenticationUtility;

    @Autowired
    private InvoiceReportUtility invoiceReportUtility;

    @Autowired
    private CloudinaryUtility cloudinaryUtility;

    @Override
    public ProcessPaymentResponse processPayment(ProcessPaymentDto processPaymentDto) {
        OrderDishEntity orderDish = orderDishRepository.findById(processPaymentDto.getOrderDishId()).orElse(null);
        if (orderDish == null) throw CustomException.badRequest("Order not found");
        if (orderDish.getStatus() == EOrderDishStatus.CANCELLED) throw CustomException.badRequest("Order was cancelled");
        // if (orderDish.getStatus() != EOrderDishStatus.COMPLETED) throw CustomException.badRequest("Order not completed yet");

        PaymentEntity paymentExists = paymentRepository.findByOrderDishId(orderDish.getId());
        if (paymentExists != null) throw CustomException.badRequest("Payment already processed");

        UserEntity user = userAuthenticationUtility.find();

        PaymentEntity payment = PaymentEntity
                .builder()
                .orderDish(orderDish)
                .amount(orderDish.getTotal())
                .paymentMethod(processPaymentDto.getPaymentMethod())
                .status(EPaymentStatus.PENDING)
                .build();

        try {
            payment.setStatus(EPaymentStatus.COMPLETED);

            JasperPrint jasperPrint = invoiceReportUtility.generateReport(user, orderDish, payment);
            String url = cloudinaryUtility.uploadInvoice(
                    jasperPrint,
                    user.getId().intValue()
            );

            orderDish.setInvoiceReportUrl(url);
            orderDishRepository.save(orderDish);

            paymentRepository.save(payment);
        } catch (Exception e) {
            payment.setStatus(EPaymentStatus.FAILED);
            System.out.println(e.getMessage());
            throw CustomException.internalServerError("Payment failed, please try again later");
        }

        paymentRepository.save(payment);


        return new ProcessPaymentResponse(
                "Payment processed successfully",
                payment,
                orderDish.getInvoiceReportUrl()
        );
    }
}
