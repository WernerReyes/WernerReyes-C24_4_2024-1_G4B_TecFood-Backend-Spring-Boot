package com.backend.app.persistence.specifications;

import com.backend.app.persistence.entities.DishEntity;
import com.backend.app.persistence.enums.EStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class DishSpecification {

    public static Specification<DishEntity> idDishCategoryIn(List<Long> categoriesId) {
        return (root, query, criteriaBuilder) -> {
            if (categoriesId == null) return criteriaBuilder.conjunction();
            return root.get("categories").get("id").in(categoriesId);
        };
    }

    public static Specification<DishEntity>  idNotEqual(Long id) {
        return (root, query, criteriaBuilder) -> {
            if (id == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.notEqual(root.get("id"), id);
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

    public static Specification<DishEntity> statusEqual(EStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

}
