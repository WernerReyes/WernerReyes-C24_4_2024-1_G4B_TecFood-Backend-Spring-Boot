package com.backend.app.persistence.repositories;

import com.backend.app.persistence.entities.DishEntity;
import com.backend.app.persistence.entities.DishImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishImageRepository extends JpaRepository<DishImageEntity, Long> {
    List<DishImageEntity> findAllByDish(DishEntity dish);
}
