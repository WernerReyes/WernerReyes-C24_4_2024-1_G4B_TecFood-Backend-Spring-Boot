package com.backend.app.models.dtos.responses.common;


public record PagedResponse<T>(
        T content,
        int currentPage,
        int totalPages,
        int limit,
        int total,
        String next,
        String previous
) { }
