package com.backend.app.services;

import com.backend.app.exception.CustomException;
import com.backend.app.models.IDishService;
import com.backend.app.models.dtos.dish.GetDishesDto;
import com.backend.app.models.responses.dish.GetDishResponse;
import com.backend.app.models.responses.dish.GetDishesResponse;
import com.backend.app.models.responses.dish.GetDishesToSearchResponse;
import com.backend.app.persistence.entities.DishEntity;
import com.backend.app.persistence.repositories.DishRepository;
import com.backend.app.persistence.specifications.DishSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DishServiceImpl implements IDishService {

    @Autowired
    private DishRepository dishRepository;

    @Override
    public GetDishesResponse findAll(GetDishesDto getDishesDto) {
        Pageable pageable = PageRequest.of(getDishesDto.getPage() - 1, getDishesDto.getLimit());
        Page<DishEntity> dishes = dishRepository.findAll(pageable);
        dishes = filters(pageable, getDishesDto);
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
    public GetDishesToSearchResponse findAllToSearch() {
        List<DishEntity> dishes = dishRepository.findAll();
        return new GetDishesToSearchResponse(
                "All products to search",
                dishes
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

    @Override
    public GetDishResponse findByName(String name) {
        DishEntity dish = dishRepository.findByName(name);
        if (dish  == null) throw CustomException.badRequest("Product not found");

        return new GetDishResponse(
                "Product found",
                dish
        );
    }


    private Page<DishEntity> filters(Pageable pageable,GetDishesDto getDishesDto) {
        Specification<DishEntity> spec = Specification.where(
                DishSpecification.idDishCategoryIn(getDishesDto.getIdCategory())
        ).and(
                DishSpecification.priceBetween(getDishesDto.getMin(), getDishesDto.getMax())
        ).and(
                DishSpecification.nameContaining(getDishesDto.getSearch())
        );
        return dishRepository.findAll(spec, pageable);
    }

}
