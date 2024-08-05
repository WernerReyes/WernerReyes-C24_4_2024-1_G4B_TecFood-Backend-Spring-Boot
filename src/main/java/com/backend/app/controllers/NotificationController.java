package com.backend.app.controllers;

import com.backend.app.models.INotificationService;
import com.backend.app.models.dtos.requests.notification.SendNotificationRequest;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final INotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<Void>> sendNotification(SendNotificationRequest sendNotificationRequest) {
        return new ResponseEntity<>(notificationService.sendNotification(sendNotificationRequest), HttpStatus.CREATED);
    }

}
