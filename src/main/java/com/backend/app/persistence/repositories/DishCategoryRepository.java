package com.backend.app.persistence.repositories;

import com.backend.app.persistence.entities.DishCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DishCategoryRepository extends JpaRepository<DishCategoryEntity, Long> {
}
