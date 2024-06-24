package com.backend.app.controllers;

import com.backend.app.exceptions.CustomException;
import com.backend.app.exceptions.DtoException;
import com.backend.app.models.dtos.dish.GetDishesDto;
import com.backend.app.models.responses.dish.GetDishResponse;
import com.backend.app.models.responses.dish.GetDishesResponse;
import com.backend.app.models.responses.dish.GetDishesToSearchResponse;
import com.backend.app.services.DishServiceImpl;
import com.backend.app.utilities.DtoValidatorUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/dish")
public class DishController {

    @Autowired
    private DishServiceImpl dishServiceimpl;

    @GetMapping("")
    public ResponseEntity<GetDishesResponse> findAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(required = false) List<Long> idCategory,
            @RequestParam(required = false) Integer min,
            @RequestParam(required = false) Integer max,
            @RequestParam(required = false) String search
    ) {
        GetDishesDto getDishesDto = new GetDishesDto(page, limit, idCategory, search, min, max);
        DtoException<GetDishesDto> getDishesDtoException = DtoValidatorUtility.validate(getDishesDto);
        if(getDishesDtoException.getError() != null) throw CustomException.badRequest(getDishesDtoException.getError());
        return new ResponseEntity<>(dishServiceimpl.findAll(getDishesDtoException.getData()), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<GetDishesToSearchResponse> findAllToSearch() {
        return new ResponseEntity<>(dishServiceimpl.findAllToSearch(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetDishResponse> findById(@PathVariable Long id) {
        return new ResponseEntity<>(dishServiceimpl.findById(id), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<GetDishResponse> findByName(@PathVariable String name) {
        return new ResponseEntity<>(dishServiceimpl.findByName(name), HttpStatus.OK);
    }
}
