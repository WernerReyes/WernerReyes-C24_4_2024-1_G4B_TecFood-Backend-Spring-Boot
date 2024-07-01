package com.backend.app.controllers;

import com.backend.app.models.IDishCategoryService;
import com.backend.app.models.dtos.requests.common.UploadImagesRequest;
import com.backend.app.models.dtos.requests.dishCategory.CreateDishCategoryRequest;
import com.backend.app.models.dtos.requests.dishCategory.UpdateDishCategoryRequest;
import com.backend.app.models.dtos.requests.dishCategory.UpdateDishCategoryImageRequest;
import com.backend.app.models.dtos.requests.common.UpdateStatusRequest;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.persistence.entities.DishCategoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/dish-category")
public class DishCategoryController {

    private final IDishCategoryService dishCategoryService;


    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole(T(com.backend.app.security.SecurityConstants).ROLE_ADMIN)")
    public ResponseEntity<ApiResponse<DishCategoryEntity>> create(
            @RequestPart MultipartFile file,
            @RequestPart CreateDishCategoryRequest createDishCategoryRequest
    ) {
        UploadImagesRequest uploadImagesRequest = new UploadImagesRequest(file);
        return new ResponseEntity<>(dishCategoryService.create(uploadImagesRequest, createDishCategoryRequest), HttpStatus.CREATED);
    }

    @PutMapping("")
    @PreAuthorize("hasRole(T(com.backend.app.security.SecurityConstants).ROLE_ADMIN)")
    public ResponseEntity<ApiResponse<DishCategoryEntity>> update(@RequestBody UpdateDishCategoryRequest updateDishCategoryRequest) {
        return new ResponseEntity<>(dishCategoryService.update(updateDishCategoryRequest), HttpStatus.OK);
    }

    @PutMapping("/image")
    @PreAuthorize("hasRole(T(com.backend.app.security.SecurityConstants).ROLE_ADMIN)")
    public ResponseEntity<ApiResponse<DishCategoryEntity>> updateImage(
            @RequestParam MultipartFile file,
            @RequestParam Long dishCategoryId
    ) {
        UpdateDishCategoryImageRequest updateDishCategoryImageRequest = new UpdateDishCategoryImageRequest(dishCategoryId);
        updateDishCategoryImageRequest.setFiles(Collections.singletonList(file));
        updateDishCategoryImageRequest.validated();
        return new ResponseEntity<>(dishCategoryService.updateImage(updateDishCategoryImageRequest), HttpStatus.OK);
    }

    @PutMapping("/status")
    @PreAuthorize("hasRole(T(com.backend.app.security.SecurityConstants).ROLE_ADMIN)")
    public ResponseEntity<ApiResponse<DishCategoryEntity>> updateStatus(@RequestBody UpdateStatusRequest updateStatusRequest) {
        return new ResponseEntity<>(dishCategoryService.updateStatus(updateStatusRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{dishCategoryId}")
    @PreAuthorize("hasRole(T(com.backend.app.security.SecurityConstants).ROLE_ADMIN)")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long dishCategoryId) {
        return new ResponseEntity<>(dishCategoryService.delete(dishCategoryId), HttpStatus.OK);
    }

    @DeleteMapping("/delete-many")
    @PreAuthorize("hasRole(T(com.backend.app.security.SecurityConstants).ROLE_ADMIN)")
    public ResponseEntity<ApiResponse<Void>> deleteMany(@RequestParam List<Long> dishCategoryIds) {
        return new ResponseEntity<>(dishCategoryService.deleteMany(dishCategoryIds), HttpStatus.OK);
    }

    @GetMapping("")
    @PreAuthorize("hasRole(T(com.backend.app.security.SecurityConstants).ROLE_ADMIN)")
    public ResponseEntity<ApiResponse<List<DishCategoryEntity>>> findAll() {
        return new ResponseEntity<>(dishCategoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/published")
    public ResponseEntity<ApiResponse<List<DishCategoryEntity>>> findAllPublished() {
        return new ResponseEntity<>(dishCategoryService.findAllPublished(), HttpStatus.OK);
    }

    @GetMapping("/{dishCategoryId}")
    public ResponseEntity<ApiResponse<DishCategoryEntity>> findById(@PathVariable Long dishCategoryId) {
        return new ResponseEntity<>(dishCategoryService.findById(dishCategoryId), HttpStatus.OK);
    }
}