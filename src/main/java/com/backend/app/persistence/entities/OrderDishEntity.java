package com.backend.app.persistence.entities;

import com.backend.app.persistence.enums.EOrderDishStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="order_dish")
public class OrderDishEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Long id;

    @Column(name="invoice_report_url")
    private String invoiceReportUrl;

    @Column(name="order_date")
    @LastModifiedDate
    private LocalDateTime orderDate;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private EOrderDishStatus status;

    @Column(name="total")
    private Double total;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
