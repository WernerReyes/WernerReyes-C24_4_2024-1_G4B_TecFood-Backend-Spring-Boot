package com.backend.app.models;

public interface INotificationService {
    <T> void sendNotification(String message, T data);
}
