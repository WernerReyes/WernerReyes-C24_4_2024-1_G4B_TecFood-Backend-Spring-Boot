package com.backend.app.controllers;

import com.backend.app.exception.CustomException;
import com.backend.app.exception.DtoException;
import com.backend.app.models.dtos.dish.GetDishesDto;
import com.backend.app.models.responses.dish.GetDishResponse;
import com.backend.app.models.responses.dish.GetDishesResponse;
import com.backend.app.services.DishServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dish")
public class DishController {

    @Autowired
    private DishServiceImpl dishServiceimpl;

    @GetMapping("")
    public ResponseEntity<GetDishesResponse> findAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(required = false) Long idCategory,
            @RequestParam(required = false) String search
    ) {
        DtoException<GetDishesDto> getDishesDto = GetDishesDto.create(page, limit, idCategory, search);
        if(getDishesDto.getError() != null) throw CustomException.badRequest(getDishesDto.getError());
        return new ResponseEntity<>(dishServiceimpl.findAll(getDishesDto.getData()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetDishResponse> findById(@PathVariable Long id) {
        return new ResponseEntity<>(dishServiceimpl.findById(id), HttpStatus.OK);
    }
}
