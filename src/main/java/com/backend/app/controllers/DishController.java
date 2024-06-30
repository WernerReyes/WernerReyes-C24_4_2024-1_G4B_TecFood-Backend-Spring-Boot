package com.backend.app.controllers;

import com.backend.app.exceptions.CustomException;
import com.backend.app.exceptions.DtoException;
import com.backend.app.models.dtos.dish.FindDishesDto;
import com.backend.app.models.dtos.dish.FindDishesWithoutSelectedDishDto;
import com.backend.app.models.responses.dish.FindDishResponse;
import com.backend.app.models.responses.dish.FindDishesResponse;
import com.backend.app.models.responses.dish.FindDishesToSearchResponse;
import com.backend.app.models.responses.dish.FindDishesWithoutSelectedDishResponse;
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
    public ResponseEntity<FindDishesResponse> findAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(required = false) List<Long> idCategory,
            @RequestParam(required = false) Integer min,
            @RequestParam(required = false) Integer max,
            @RequestParam(required = false) String search
    ) {
        FindDishesDto findDishesDto = new FindDishesDto(page, limit, idCategory, search, min, max);
        DtoException<FindDishesDto> findDishesDtoException = DtoValidatorUtility.validate(findDishesDto);
        if(findDishesDtoException.getError() != null) throw CustomException.badRequest(findDishesDtoException.getError());
        return new ResponseEntity<>(dishServiceimpl.findAll(findDishesDtoException.getData()), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<FindDishesToSearchResponse> findAllToSearch() {
        return new ResponseEntity<>(dishServiceimpl.findAllToSearch(), HttpStatus.OK);
    }

    @GetMapping("/without/{selectedDishId}")
    public ResponseEntity<FindDishesWithoutSelectedDishResponse> findAllWithoutSelectedDish(
            @PathVariable Long selectedDishId,
            @RequestParam(defaultValue = "4") Integer limit
            ) {
        FindDishesWithoutSelectedDishDto findDishesWithoutSelectedDishDto = new FindDishesWithoutSelectedDishDto(limit, selectedDishId);
        DtoException<FindDishesWithoutSelectedDishDto> findDishesWithoutSelectedDishDtoException = DtoValidatorUtility.validate(findDishesWithoutSelectedDishDto);
        if(findDishesWithoutSelectedDishDtoException.getError() != null) throw CustomException.badRequest(findDishesWithoutSelectedDishDtoException.getError());
        return new ResponseEntity<>(dishServiceimpl.findAllWithoutSelectedDish(findDishesWithoutSelectedDishDtoException.getData()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FindDishResponse> findById(@PathVariable Long id) {
        return new ResponseEntity<>(dishServiceimpl.findById(id), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<FindDishResponse> findByName(@PathVariable String name) {
        return new ResponseEntity<>(dishServiceimpl.findByName(name), HttpStatus.OK);
    }
}
