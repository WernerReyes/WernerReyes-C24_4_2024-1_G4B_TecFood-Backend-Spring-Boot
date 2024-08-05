package com.backend.app.services;

import com.backend.app.models.INotificationService;
import com.backend.app.models.dtos.requests.notification.SendNotificationRequest;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.persistence.entities.NotificationEntity;
import com.backend.app.persistence.enums.EResponseStatus;
import com.backend.app.persistence.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements INotificationService {

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public ApiResponse<Void> sendNotification(SendNotificationRequest sendNotificationRequest) {

        List<NotificationEntity> notifications = new ArrayList<>();
        for (Long userId : sendNotificationRequest.getUserIds()) {
             NotificationEntity notification = NotificationEntity.builder()
                    .userId(userId)
                    .message(sendNotificationRequest.getMessage())
                    .details(sendNotificationRequest.getDetails())
                    .isRead(false)
                     .category(sendNotificationRequest.getCategory())
                    .createdAt(LocalDateTime.now())
                    .build();

            notifications.add(notification);

            simpMessagingTemplate.convertAndSendToUser(
                    userId.toString(),
                    "/queue/notifications",
                    new ApiResponse<>(
                            EResponseStatus.SUCCESS,
                            "New notification",
                            notification
                    )
            );
        }

        notificationRepository.saveAll(notifications);

        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Notification sent successfully to " + notifications.size() + " users",
                null
        );
    }
}
