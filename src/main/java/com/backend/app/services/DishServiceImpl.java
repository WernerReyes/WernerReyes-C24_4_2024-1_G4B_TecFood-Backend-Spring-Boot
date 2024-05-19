package com.backend.app.services;

import com.backend.app.exception.CustomException;
import com.backend.app.models.IDishService;
import com.backend.app.models.dtos.dish.GetDishesDto;
import com.backend.app.models.responses.dish.GetDishResponse;
import com.backend.app.models.responses.dish.GetDishesResponse;
import com.backend.app.persistence.entities.DishEntity;
import com.backend.app.persistence.repositories.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class DishServiceImpl implements IDishService {

    @Autowired
    private DishRepository dishRepository;

    @Override
    public GetDishesResponse findAll(GetDishesDto getDishesDto) {
        Pageable pageable = PageRequest.of(getDishesDto.getPage() - 1, getDishesDto.getLimit());
        Page<DishEntity> dishes = dishRepository.findAll(pageable);

        if(getDishesDto.getIdCategory() != null && getDishesDto.getSearch() != null) {
            dishes = dishRepository.findByNameContainingAndCategory_IdDishCategory(getDishesDto.getSearch(), getDishesDto.getIdCategory(), pageable);
        } else if(getDishesDto.getIdCategory() != null) {
            dishes = dishRepository.findByCategory_IdDishCategory(getDishesDto.getIdCategory(), pageable);
        } else if(getDishesDto.getSearch() != null) {
            dishes = dishRepository.findByNameContaining(getDishesDto.getSearch(), pageable);
        }
        int total = (int) dishes.getTotalElements();


        return new GetDishesResponse(
                "All products",
                dishes.getContent(),
                getDishesDto.getPage(),
                dishes.getTotalPages(),
                getDishesDto.getLimit(),
                total,
                dishes.hasNext() ? "/api/dish?page=" + (getDishesDto.getPage() + 1) + "&limit=" + getDishesDto.getLimit() : null,
                dishes.hasPrevious() ? "/api/dish?page=" + (getDishesDto.getPage() - 1) + "&limit=" + getDishesDto.getLimit() : null
            );
    }

    @Override
    public GetDishResponse findById(Long id) {
        DishEntity dish = dishRepository.findById(id).orElse(null);
        if (dish  == null) throw CustomException.badRequest("Product not found");

        return new GetDishResponse(
                "Product found",
                dish
        );
    }

}
