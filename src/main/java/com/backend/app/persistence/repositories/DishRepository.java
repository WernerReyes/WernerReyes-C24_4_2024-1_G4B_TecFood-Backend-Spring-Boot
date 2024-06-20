package com.backend.app.persistence.repositories;

import com.backend.app.persistence.entities.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DishRepository extends JpaRepository<DishEntity, Long>, JpaSpecificationExecutor<DishEntity> {

    DishEntity findByName(String name);
}
