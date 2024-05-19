package com.backend.app.persistence.repositories;

import com.backend.app.persistence.entities.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DishRepository extends JpaRepository<DishEntity, Long> {
    Page<DishEntity> findAll(Pageable pageable);
    Page<DishEntity> findByCategory_IdDishCategory(Long idCategory, Pageable pageable);
    Page<DishEntity> findByNameContaining(String name, Pageable pageable);
    Page<DishEntity> findByNameContainingAndCategory_IdDishCategory(String name, Long idCategory, Pageable pageable);
}
