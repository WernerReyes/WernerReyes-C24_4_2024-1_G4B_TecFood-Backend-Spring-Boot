package com.backend.app.persistence.repositories;

import com.backend.app.persistence.entities.ERole;
import com.backend.app.persistence.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    public RoleEntity findByName(ERole name);
}
