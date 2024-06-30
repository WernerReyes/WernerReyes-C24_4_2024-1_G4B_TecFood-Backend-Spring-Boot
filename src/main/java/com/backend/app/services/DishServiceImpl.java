package com.backend.app.services;

import com.backend.app.exceptions.CustomException;
import com.backend.app.models.IDishService;
import com.backend.app.models.dtos.dish.FindDishesDto;
import com.backend.app.models.dtos.dish.FindDishesWithoutSelectedDishDto;
import com.backend.app.models.responses.dish.FindDishResponse;
import com.backend.app.models.responses.dish.FindDishesResponse;
import com.backend.app.models.responses.dish.FindDishesToSearchResponse;
import com.backend.app.models.responses.dish.FindDishesWithoutSelectedDishResponse;
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
    public FindDishesResponse findAll(FindDishesDto findDishesDto) {
        Pageable pageable = PageRequest.of(findDishesDto.getPage() - 1, findDishesDto.getLimit());
        Page<DishEntity> dishes = filters(pageable, findDishesDto);
        int total = (int) dishes.getTotalElements();

        return new FindDishesResponse(
                "All products",
                dishes.getContent(),
                findDishesDto.getPage(),
                dishes.getTotalPages(),
                findDishesDto.getLimit(),
                total,
                dishes.hasNext() ? "/api/dish?page=" + (findDishesDto.getPage() + 1) + "&limit=" + findDishesDto.getLimit() : null,
                dishes.hasPrevious() ? "/api/dish?page=" + (findDishesDto.getPage() - 1) + "&limit=" + findDishesDto.getLimit() : null
            );
    }

    @Override
    public FindDishesToSearchResponse findAllToSearch() {
        List<DishEntity> dishes = dishRepository.findAll();
        return new FindDishesToSearchResponse(
                "All products to search",
                dishes
        );
    }

    @Override
    public FindDishesWithoutSelectedDishResponse findAllWithoutSelectedDish(FindDishesWithoutSelectedDishDto dto) {
        Specification<DishEntity> spec = Specification.where(
                DishSpecification.idNotEqual(dto.getSelectedDishId())
        );

        Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getLimit());
        Page<DishEntity> dishes = dishRepository.findAll(spec, pageable);

        return new FindDishesWithoutSelectedDishResponse(
                "All products without selected product",
                dishes.getContent()
        );
    }

    @Override
    public FindDishResponse findById(Long id) {
        DishEntity dish = dishRepository.findById(id).orElse(null);
        if (dish  == null) throw CustomException.badRequest("Product not found");

        return new FindDishResponse(
                "Product found",
                dish
        );
    }

    @Override
    public FindDishResponse findByName(String name) {
        DishEntity dish = dishRepository.findByName(name);
        if (dish  == null) throw CustomException.badRequest("Product not found");

        return new FindDishResponse(
                "Product found",
                dish
        );
    }


    private Page<DishEntity> filters(Pageable pageable, FindDishesDto findDishesDto) {
        Specification<DishEntity> spec = Specification.where(
                DishSpecification.idDishCategoryIn(findDishesDto.getIdCategory())
        ).and(
                DishSpecification.priceBetween(findDishesDto.getMin(), findDishesDto.getMax())
        ).and(
                DishSpecification.nameContaining(findDishesDto.getSearch())
        );
        return dishRepository.findAll(spec, pageable);
    }

}
