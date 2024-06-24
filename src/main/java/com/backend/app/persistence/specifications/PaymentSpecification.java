package com.backend.app.persistence.specifications;

import com.backend.app.exceptions.CustomException;
import com.backend.app.persistence.entities.PaymentEntity;
import com.backend.app.persistence.entities.UserEntity;
import org.springframework.data.jpa.domain.Specification;

public class PaymentSpecification {

    public static Specification<PaymentEntity> userEquals(UserEntity user) {
        return (root, query, criteriaBuilder) -> {
            if (user == null) throw CustomException.badRequest("User is required");
            return criteriaBuilder.equal(root.get("orderDish").get("user"), user);
        };
    }
}
