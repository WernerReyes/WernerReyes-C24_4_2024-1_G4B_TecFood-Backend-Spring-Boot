package com.backend.app.persistence.repositories;

import com.backend.app.persistence.entities.DishEntity;
import com.backend.app.persistence.enums.EStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface DishRepository extends JpaRepository<DishEntity, Long>, JpaSpecificationExecutor<DishEntity> {
    DishEntity findByNameIgnoreCase(String name);
    DishEntity findByName(String name);
    List<DishEntity> findAllByStatus(EStatus status);
    boolean existsByCategoriesId(Long categoryId);
    @Query("SELECT d FROM DishEntity d WHERE d.saleEndDate < :currentDateTime")
    List<DishEntity> findExpiredOffers(@Param("currentDateTime") LocalDateTime currentDateTime);
}
