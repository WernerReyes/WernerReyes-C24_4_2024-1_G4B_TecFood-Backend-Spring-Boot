package com.backend.app.controllers;

import com.backend.app.models.IUserService;
import com.backend.app.models.dtos.requests.common.UploadImagesRequest;
import com.backend.app.models.dtos.requests.user.UpdateUserRequest;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final IUserService userService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<UserEntity>>> getAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResponse<UserEntity>> findById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<UserEntity>> updateUser(
            @RequestBody UpdateUserRequest updateUserRequest
    ) throws Exception {
        return new ResponseEntity<>(userService.updateUser(updateUserRequest), HttpStatus.OK);
    }

    @PostMapping("/upload-profile")
    public ResponseEntity<ApiResponse<String>> uploadProfile(
            @RequestParam("file") MultipartFile file
    ) throws Exception {
        UploadImagesRequest uploadImagesRequest = new UploadImagesRequest(List.of(file));
        return new ResponseEntity<>(userService.uploadProfile(uploadImagesRequest), HttpStatus.OK);
    }
}

