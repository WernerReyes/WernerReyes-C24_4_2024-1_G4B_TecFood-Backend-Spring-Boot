package com.backend.app.controllers;

import com.backend.app.models.IDishService;
import com.backend.app.models.dtos.requests.common.UpdateStatusRequest;
import com.backend.app.models.dtos.requests.common.UploadImagesRequest;
import com.backend.app.models.dtos.requests.dish.*;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.models.dtos.responses.common.PagedResponse;
import com.backend.app.persistence.entities.DishEntity;
import com.backend.app.persistence.entities.DishImageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/dish")
public class DishController {

    private final IDishService dishService;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole(T(com.backend.app.security.SecurityConstants).ROLE_ADMIN)")
    public ResponseEntity<ApiResponse<DishEntity>> create(
            @RequestPart List<MultipartFile> files,
            @RequestPart CreateDishRequest createDishRequest
            ) {
        UploadImagesRequest uploadImagesDto = new UploadImagesRequest(files);
        return new ResponseEntity<>(dishService.create(createDishRequest, uploadImagesDto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/offer")
    @PreAuthorize("hasRole(T(com.backend.app.security.SecurityConstants).ROLE_ADMIN)")
    public ResponseEntity<ApiResponse<DishEntity>> putOffer(
            @RequestBody PutDishOfferRequest putDishOfferRequest
    ) {
        return new ResponseEntity<>(dishService.putOffer(putDishOfferRequest), HttpStatus.OK);
    }

    @PutMapping("")
    @PreAuthorize("hasRole(T(com.backend.app.security.SecurityConstants).ROLE_ADMIN)")
    public ResponseEntity<ApiResponse<DishEntity>> update(
            @RequestBody UpdateDishRequest updateDishRequest
    ) {
        return new ResponseEntity<>(dishService.update(updateDishRequest), HttpStatus.OK);
    }

    @PutMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole(T(com.backend.app.security.SecurityConstants).ROLE_ADMIN)")
    public ResponseEntity<ApiResponse<List<DishImageEntity>>> updateImage(
            @RequestPart MultipartFile file,
            @RequestPart UpdateDishImageRequest updateDishImageRequest
    ) {
        updateDishImageRequest.setFiles(Collections.singletonList(file));
        updateDishImageRequest.validate();
        return new ResponseEntity<>(dishService.updateImage(updateDishImageRequest), HttpStatus.OK);
    }

    @PutMapping("/status")
    @PreAuthorize("hasRole(T(com.backend.app.security.SecurityConstants).ROLE_ADMIN)")
    public ResponseEntity<ApiResponse<DishEntity>> updateStatus(
            @RequestBody UpdateStatusRequest updateStatusRequest
    ) {
        return new ResponseEntity<>(dishService.updateStatus(updateStatusRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole(T(com.backend.app.security.SecurityConstants).ROLE_ADMIN)")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        return new ResponseEntity<>(dishService.delete(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete-many")
    @PreAuthorize("hasRole(T(com.backend.app.security.SecurityConstants).ROLE_ADMIN)")
    public ResponseEntity<ApiResponse<Void>> deleteMany(@RequestParam List<Long> dishesId) {
        return new ResponseEntity<>(dishService.deleteMany(dishesId), HttpStatus.OK);
    }

    @GetMapping("/all-paginated")
    public ResponseEntity<ApiResponse<PagedResponse<List<DishEntity>>>> findAllPaginated(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(required = false) List<Long> idCategory,
            @RequestParam(required = false) Integer min,
            @RequestParam(required = false) Integer max,
            @RequestParam(required = false) String search
    ) {
        FindDishesRequest findDishesRequest = new FindDishesRequest(page, limit, idCategory, search, min, max);
        return new ResponseEntity<>(dishService.findAllPaginated(findDishesRequest), HttpStatus.OK);
    }

    @GetMapping("")
    @PreAuthorize("hasRole(T(com.backend.app.security.SecurityConstants).ROLE_ADMIN)")
    public ResponseEntity<ApiResponse<List<DishEntity>>> findAll() {
        return new ResponseEntity<>(dishService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/published")
    public ResponseEntity<ApiResponse<List<DishEntity>>> findAllPublished() {
        return new ResponseEntity<>(dishService.findAllPublished(), HttpStatus.OK);
    }

    @GetMapping("/without/{selectedDishId}")
    public ResponseEntity<ApiResponse<List<DishEntity>>> findAllWithoutSelectedDish(
            @PathVariable Long selectedDishId,
            @RequestParam(defaultValue = "4") Integer limit
            ) {
        FindDishesWithoutSelectedDishRequest
                findDishesWithoutSelectedDishRequest =
                new FindDishesWithoutSelectedDishRequest(limit, selectedDishId);
        return new ResponseEntity<>(dishService.findAllWithoutSelectedDish(findDishesWithoutSelectedDishRequest), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DishEntity>> findById(@PathVariable Long id) {
        return new ResponseEntity<>(dishService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<DishEntity>> findByName(@PathVariable String name) {
        return new ResponseEntity<>(dishService.findByName(name), HttpStatus.OK);
    }
}
