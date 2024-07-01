package com.backend.app.services;

import com.backend.app.exceptions.CustomException;
import com.backend.app.models.IEmailService;
import com.backend.app.models.IPaymentService;
import com.backend.app.models.IUploadFileService;
import com.backend.app.models.dtos.requests.payment.FindPaymentByUserRequest;
import com.backend.app.models.dtos.requests.payment.ProcessPaymentRequest;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.models.dtos.responses.common.PagedResponse;
import com.backend.app.persistence.entities.OrderDishEntity;
import com.backend.app.persistence.entities.PaymentEntity;
import com.backend.app.persistence.entities.UserEntity;
import com.backend.app.persistence.enums.EOrderDishStatus;
import com.backend.app.persistence.enums.EResponseStatus;
import com.backend.app.persistence.enums.payment.EPaymentStatus;
import com.backend.app.persistence.repositories.OrderDishRepository;
import com.backend.app.persistence.repositories.PaymentRepository;
import com.backend.app.persistence.specifications.PaymentSpecification;
import com.backend.app.utilities.InvoiceReportUtility;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements IPaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderDishRepository orderDishRepository;

    private final UserAuthenticationService userAuthenticationService;
    private final IEmailService emailService;
    private final IUploadFileService uploadFileService;

    private final InvoiceReportUtility invoiceReportUtility;


    @Override
    @Transactional
    public ApiResponse<PaymentEntity> processPayment(ProcessPaymentRequest processPaymentRequest) {
        OrderDishEntity orderDish = orderDishRepository.findById(processPaymentRequest.getOrderDishId()).orElse(null);
        if (orderDish == null) throw CustomException.badRequest("Order not found");
        if (orderDish.getStatus() == EOrderDishStatus.CANCELLED) throw CustomException.badRequest("Order was cancelled");
        if (orderDish.getStatus() == EOrderDishStatus.PENDING) throw CustomException.badRequest("Order is pending");

        PaymentEntity paymentExists = paymentRepository.findByOrderDishId(orderDish.getId());
        if (paymentExists != null) throw CustomException.badRequest("Payment already processed");

        UserEntity user = userAuthenticationService.find();

        PaymentEntity payment = PaymentEntity
                .builder()
                .orderDish(orderDish)
                .amount(orderDish.getTotal())
                .paymentMethod(processPaymentRequest.getPaymentMethod())
                .status(EPaymentStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        try {
            payment.setStatus(EPaymentStatus.COMPLETED);

            JasperPrint jasperPrint = invoiceReportUtility.generateReport(user, orderDish, payment);
            String url = uploadFileService.uploadInvoice(jasperPrint);

            orderDish.setInvoiceReportUrl(url);
            orderDishRepository.save(orderDish);

            String subject = ("Invoice for " + user.getFirstName() + " " + user.getLastName()).toUpperCase(Locale.ROOT);

            String text = "Dear " + user.getFirstName() +  ", Attached is the invoice for your recent purchase. Please " +
                        "review the details and contact us if you need any clarification. Thank you for your purchase!";

            emailService.sendEmailWithAttachment(
                "invoice-" + orderDish.getId(),
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

        return new ApiResponse<>(EResponseStatus.SUCCESS, "Payment processed successfully", payment);
    }

    public ApiResponse<PagedResponse<List<PaymentEntity>>> findPaymentByUser(FindPaymentByUserRequest findPaymentByUserRequest) {
        Pageable pageable = PageRequest.of(findPaymentByUserRequest.getPage() - 1, findPaymentByUserRequest.getLimit());
        Page<PaymentEntity> payments = applySpecsAndPagination(pageable);
        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "All dishes paginated",
                new PagedResponse<>(
                        payments.getContent(),
                        findPaymentByUserRequest.getPage(),
                        payments.getTotalPages(),
                        findPaymentByUserRequest.getLimit(),
                        (int) payments.getTotalElements(),
                        payments.hasNext() ? "/api/dish?page=" + (findPaymentByUserRequest.getPage() + 1) + "&limit=" + findPaymentByUserRequest.getLimit() : null,
                        payments.hasPrevious() ? "/api/dish?page=" + (findPaymentByUserRequest.getPage() - 1) + "&limit=" + findPaymentByUserRequest.getLimit() : null
                )
        );
    }

    private Page<PaymentEntity> applySpecsAndPagination(Pageable pageable) {
        Specification<PaymentEntity> spec = Specification.where(
                PaymentSpecification.userEquals(userAuthenticationService.find())
        );
        return paymentRepository.findAll(spec, pageable);
    }

}
