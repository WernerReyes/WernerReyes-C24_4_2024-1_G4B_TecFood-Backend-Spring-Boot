package com.backend.app.persistence.repositories;

import com.backend.app.persistence.entities.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository extends JpaRepository<NotificationEntity, Long> {
}
