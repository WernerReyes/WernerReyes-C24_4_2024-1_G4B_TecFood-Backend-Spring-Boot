package com.backend.app.models.dtos.common;

import com.backend.app.exceptions.DtoException;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PaginationDto {
    private int page;
    private int limit;

    public static DtoException<PaginationDto> create(int page, int limit) {
        if (page < 1 || page > 100) return new DtoException<>("Invalid page, must be between 1 and 100", null);
        if (limit < 1 || limit > 100) return new DtoException<>("Invalid limit, must be between 1 and 100", null);
        return new DtoException<>(null, new PaginationDto(page, limit));
    }
}

