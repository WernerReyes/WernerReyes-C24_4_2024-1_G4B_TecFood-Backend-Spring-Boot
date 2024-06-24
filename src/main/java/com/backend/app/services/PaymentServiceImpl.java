package com.backend.app.services;

import com.backend.app.exceptions.CustomException;
import com.backend.app.models.IPaymentService;
import com.backend.app.models.dtos.payment.FindPaymentByUserDto;
import com.backend.app.models.dtos.payment.ProcessPaymentDto;
import com.backend.app.models.responses.payment.FindPaymentByUserResponse;
import com.backend.app.models.responses.payment.ProcessPaymentResponse;
import com.backend.app.persistence.entities.OrderDishEntity;
import com.backend.app.persistence.entities.PaymentEntity;
import com.backend.app.persistence.entities.UserEntity;
import com.backend.app.persistence.enums.EOrderDishStatus;
import com.backend.app.persistence.enums.payment.EPaymentStatus;
import com.backend.app.persistence.repositories.OrderDishRepository;
import com.backend.app.persistence.repositories.PaymentRepository;
import com.backend.app.persistence.specifications.PaymentSpecification;
import com.backend.app.utilities.CloudinaryUtility;
import com.backend.app.utilities.EmailUtility;
import com.backend.app.utilities.InvoiceReportUtility;
import com.backend.app.utilities.UserAuthenticationUtility;
import jakarta.transaction.Transactional;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Locale;

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

    @Autowired
    private EmailUtility emailUtility;

    @Override
    @Transactional
    public ProcessPaymentResponse processPayment(ProcessPaymentDto processPaymentDto) {
        OrderDishEntity orderDish = orderDishRepository.findById(processPaymentDto.getOrderDishId()).orElse(null);
        if (orderDish == null) throw CustomException.badRequest("Order not found");
        if (orderDish.getStatus() == EOrderDishStatus.CANCELLED) throw CustomException.badRequest("Order was cancelled");
        if (orderDish.getStatus() == EOrderDishStatus.PENDING) throw CustomException.badRequest("Order is pending");

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

            String subject = ("Invoice for " + user.getFirstName() + " " + user.getLastName()).toUpperCase(Locale.ROOT);

            String text = "Dear " + user.getFirstName() +  ", Attached is the invoice for your recent purchase. Please review the details and contact us if you need any clarification. Thank you for your purchase!";

            emailUtility.sendEmailWithAttachment(
                user.getEmail(),
                subject,
                text,
                url
            );
            paymentRepository.save(payment);
        } catch (Exception e) {
            payment.setStatus(EPaymentStatus.FAILED);
            System.out.println(e.getMessage());
            throw CustomException.internalServerError("Payment failed, please try again later");
        }

        paymentRepository.save(payment);


        return new ProcessPaymentResponse(
                "Payment processed successfully",
                payment
        );
    }

    public FindPaymentByUserResponse findPaymentByUser(FindPaymentByUserDto findPaymentByUserDto) {
        Pageable pageable = PageRequest.of(findPaymentByUserDto.getPage() - 1, findPaymentByUserDto.getLimit());
        Page<PaymentEntity> payments = filters(pageable, findPaymentByUserDto);
        return new FindPaymentByUserResponse(
                "Payments found",
                payments.getContent(),
                findPaymentByUserDto.getPage(),
                (int) payments.getTotalElements(),
                findPaymentByUserDto.getLimit()
        );
    }

    private Page<PaymentEntity> filters(Pageable pageable, FindPaymentByUserDto findPaymentByUserDto) {
        Specification<PaymentEntity> spec = Specification.where(
                PaymentSpecification.userEquals(userAuthenticationUtility.find())
        );
        return paymentRepository.findAll(spec, pageable);
    }

}
