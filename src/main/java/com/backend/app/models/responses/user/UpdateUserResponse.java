package com.backend.app.models.responses.user;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"message"})
public record UpdateUserResponse(
        String message
) {
}
