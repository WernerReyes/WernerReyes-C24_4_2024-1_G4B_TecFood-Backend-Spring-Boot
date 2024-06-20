package com.backend.app.persistence.repositories;

import com.backend.app.persistence.entities.OrderDishEntity;
import com.backend.app.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderDishRepository extends JpaRepository<OrderDishEntity, Long>, JpaSpecificationExecutor<OrderDishEntity> {
    List<OrderDishEntity> findByUserOrderByOrderDateDesc(UserEntity user);
}
