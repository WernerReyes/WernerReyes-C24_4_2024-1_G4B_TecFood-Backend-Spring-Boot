package com.backend.app.models.responses.user;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"message", "profileUrl"})
public record UploadProfileResponse(String message, String profileUrl) { }
