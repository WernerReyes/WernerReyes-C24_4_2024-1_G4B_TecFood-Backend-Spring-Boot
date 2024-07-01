package com.backend.app.models.dtos.requests.common;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PaginationRequest {
    @NotNull(message = "Page is required")
    @Min(value = 1, message = "Page must be greater than 0")
    @Max(value = 100, message = "Page must be less than 100")
    protected int page;

    @NotNull(message = "Limit is required")
    @Min(value = 1, message = "Limit must be greater than 0")
    @Max(value = 100, message = "Limit must be less than 100")
    protected int limit;
}

