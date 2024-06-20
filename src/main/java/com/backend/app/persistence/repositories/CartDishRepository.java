package com.backend.app.persistence.repositories;

import com.backend.app.persistence.entities.CartDishEntity;
import com.backend.app.persistence.entities.DishEntity;
import com.backend.app.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDishRepository extends JpaRepository<CartDishEntity, Long> {
    CartDishEntity findByUserAndDish(UserEntity user, DishEntity dish);
    List<CartDishEntity> findByUser(UserEntity user);
    CartDishEntity findByDish_IdDish(Long idDish);
}
