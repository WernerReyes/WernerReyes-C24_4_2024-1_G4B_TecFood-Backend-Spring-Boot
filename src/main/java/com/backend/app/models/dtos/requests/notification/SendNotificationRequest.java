package com.backend.app.models.dtos.requests.notification;

import com.backend.app.models.dtos.annotations.NotNullAndNotEmpty;
import com.backend.app.models.dtos.annotations.UniqueElements;
import com.backend.app.persistence.enums.ECategoryNotification;
import com.backend.app.utilities.RequestValidatorUtility;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SendNotificationRequest {
    @NotNull(message = "User ids are required")
    @Size(min = 1, message = "At least one user id is required")
    @UniqueElements(message = "User ids must be unique")
    private List<Long> userIds;

    @NotNullAndNotEmpty(message = "Message is required")
    @Size(message = "Message must be between 3 and 255 characters", min = 3, max = 255)
    private String message;

    @NotNullAndNotEmpty(message = "Details is required")
    @Size(message = "Details must be between 3 and 255 characters", min = 3, max = 255)
    private String details;

    @NotNull(message = "Category is required")
    private ECategoryNotification category;

    public SendNotificationRequest(List<Long> userIds, String message, String details, ECategoryNotification category) {
        this.userIds = userIds;
        this.message = message;
        this.details = details;
        this.category = category;

        RequestValidatorUtility.validate(this);
    }
}

