package com.backend.app.persistence.repositories;

import com.backend.app.persistence.entities.OrderDishEntity;
import com.backend.app.persistence.entities.OrderDishItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDishItemRepository extends JpaRepository<OrderDishItemEntity, Long> {
    List<OrderDishItemEntity> findByOrderDish(OrderDishEntity orderDish);
    boolean existsByDishId(Long dishId);
}
