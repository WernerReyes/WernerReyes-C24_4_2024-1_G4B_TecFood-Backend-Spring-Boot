package com.backend.app.models;

public interface IEmailService {
    void sendEmail(String to, String subject, String text);
    void sendEmailWithAttachment(String fileName, String to, String subject, String text, String attachmentUrl);
}
