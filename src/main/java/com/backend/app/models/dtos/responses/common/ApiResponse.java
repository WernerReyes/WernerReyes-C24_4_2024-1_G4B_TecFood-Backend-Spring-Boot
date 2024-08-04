package com.backend.app.models.dtos.responses.common;

import com.backend.app.persistence.enums.EResponseStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"status", "message", "data"})
public record ApiResponse<T>(
        EResponseStatus status,
        String message,
        T data
) { }
