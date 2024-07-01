package com.backend.app.persistence.repositories;

import com.backend.app.persistence.entities.DishCategoryEntity;
import com.backend.app.persistence.enums.EStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DishCategoryRepository extends JpaRepository<DishCategoryEntity, Long> {
    DishCategoryEntity findByNameIgnoreCase(String name);
    DishCategoryEntity findByNameIgnoreCaseAndIdNot(String name, Long id);
    List<DishCategoryEntity> findAllByIdInAndStatus(List<Long> ids, EStatus status);
    List<DishCategoryEntity> findAllByStatus(EStatus status);
}
