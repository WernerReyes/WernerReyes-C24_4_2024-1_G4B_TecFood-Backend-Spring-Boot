package com.backend.app.persistence.repositories;

import com.backend.app.persistence.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    PaymentEntity findByOrderDishId(Long orderDishId);
}