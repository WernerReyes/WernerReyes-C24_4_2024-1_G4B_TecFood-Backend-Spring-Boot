package com.backend.app.persistence.specifications;

import com.backend.app.exceptions.CustomException;
import com.backend.app.persistence.entities.OrderDishEntity;
import com.backend.app.persistence.entities.UserEntity;
import com.backend.app.persistence.enums.EOrderDishStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class OrderDishSpecification {

    public static Specification<OrderDishEntity> userEquals(UserEntity user) {
        return (root, query, criteriaBuilder) -> {
            if (user == null) throw CustomException.badRequest("User is required");
            return criteriaBuilder.equal(root.get("user"), user);
        };
    }

    public static Specification<OrderDishEntity> statusIn(List<EOrderDishStatus> status) {
        return (root, query, criteriaBuilder) -> {
            if (status.isEmpty()) throw CustomException.badRequest("Status is required");
            return root.get("status").in(status);
        };
    }

}
