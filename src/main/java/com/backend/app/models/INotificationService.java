package com.backend.app.models;

import com.backend.app.models.dtos.requests.notification.SendNotificationRequest;
import com.backend.app.models.dtos.responses.common.ApiResponse;

public interface INotificationService {
    ApiResponse<Void> sendNotification(SendNotificationRequest sendNotificationRequest);
}
