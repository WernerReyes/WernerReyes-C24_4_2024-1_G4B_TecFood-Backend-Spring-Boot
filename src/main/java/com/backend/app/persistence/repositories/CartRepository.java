package com.backend.app.persistence.repositories;

import com.backend.app.persistence.entities.CartEntity;
import com.backend.app.persistence.entities.DishEntity;
import com.backend.app.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    CartEntity findByUserAndDish(UserEntity user, DishEntity dish);
    List<CartEntity> findByUser(UserEntity user);
    CartEntity findByDish_IdDish(Long idDish);
}
