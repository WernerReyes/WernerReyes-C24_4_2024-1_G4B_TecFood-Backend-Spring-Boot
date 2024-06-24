package com.backend.app.persistence.specifications;

import com.backend.app.persistence.entities.DishEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class DishSpecification {

    public static Specification<DishEntity> idDishCategoryIn(List<Long> idCategory) {
        return (root, query, criteriaBuilder) -> {
            if (idCategory == null) return criteriaBuilder.conjunction();
            return root.get("category").get("idDishCategory").in(idCategory);
        };
    }

    public static Specification<DishEntity> priceBetween(Integer min, Integer max) {
        return (root, query, criteriaBuilder) -> {
            if (min == null && max == null) return criteriaBuilder.conjunction();
            if (min == null) return criteriaBuilder.lessThanOrEqualTo(root.get("price"), max);
            if (max == null) return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), min);
            return criteriaBuilder.between(root.get("price"), min, max);
        };
    }

    public static Specification<DishEntity> nameContaining(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.like(root.get("name"), "%" + name + "%");
        };
    }
}
