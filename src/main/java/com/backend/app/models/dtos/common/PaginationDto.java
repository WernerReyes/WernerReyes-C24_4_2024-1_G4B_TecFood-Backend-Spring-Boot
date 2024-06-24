package com.backend.app.models.dtos.common;
import javax.validation.constraints.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PaginationDto {
    @NotNull(message = "Page is required")
    @Min(value = 1, message = "Page must be greater than 0")
    @Max(value = 100, message = "Page must be less than 100")
    protected int page;

    @NotNull(message = "Limit is required")
    @Min(value = 1, message = "Limit must be greater than 0")
    @Max(value = 100, message = "Limit must be less than 100")
    protected int limit;
}

