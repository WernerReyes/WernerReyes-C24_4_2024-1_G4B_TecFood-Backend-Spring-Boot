package com.backend.app.models.responses.auth;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"message"})
public record RegisterUserResponse(String message) { }
