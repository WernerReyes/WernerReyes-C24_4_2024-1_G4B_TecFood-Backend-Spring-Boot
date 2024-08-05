package com.backend.app.persistence.repositories;

import com.backend.app.persistence.entities.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
}
