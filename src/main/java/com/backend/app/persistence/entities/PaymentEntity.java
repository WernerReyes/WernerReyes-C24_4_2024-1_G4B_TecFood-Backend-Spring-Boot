package com.backend.app.persistence.entities;

import com.backend.app.persistence.enums.payment.EPaymentMethod;
import com.backend.app.persistence.enums.payment.EPaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="payment")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="payment_id")
    private Long id;

    @Column(name="amount")
    private Double amount;

    @Column(name="payment_method")
    @Enumerated(EnumType.STRING)
    private EPaymentMethod paymentMethod;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private EPaymentStatus status;

    @CreatedDate
    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_dish_id", nullable = false, updatable = false)
    private OrderDishEntity orderDish;

}
