package com.backend.app.models.dtos.responses.auth;

import com.backend.app.persistence.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"user", "token"})
public record LoginResponse(UserEntity user, String token) { }
