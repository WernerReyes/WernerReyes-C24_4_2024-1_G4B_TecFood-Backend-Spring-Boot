package com.backend.app.models.responses.dish;

import com.backend.app.persistence.entities.DishEntity;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"message","dishes", "currentPage", "totalPages", "limit", "total", "next", "previous"})
public record GetDishesResponse(
        String message
        , List<DishEntity> dishes
        , int currentPage
        , int totalPages
        , int limit
        , int total
        , String next
        , String previous
) { }
