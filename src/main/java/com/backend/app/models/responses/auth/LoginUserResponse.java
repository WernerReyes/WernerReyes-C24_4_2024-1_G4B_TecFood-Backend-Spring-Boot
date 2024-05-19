package com.backend.app.models.responses.auth;

import com.backend.app.persistence.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"message","user", "token"})
public record LoginUserResponse(String message, UserEntity user, String token) { }
