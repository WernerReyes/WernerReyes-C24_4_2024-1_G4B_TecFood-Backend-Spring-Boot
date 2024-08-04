package com.backend.app.services;

import com.backend.app.models.INotificationService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements INotificationService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private static final String TOPIC = "/topic/notification";

    @Override
    public <T> void sendNotification(String message, T data) {
        simpMessagingTemplate.convertAndSend(TOPIC, new Notification<>(message, data));
    }

    @Getter
    @AllArgsConstructor
    private static class Notification<T> {
        private String message;
        private T data;
    }
}
