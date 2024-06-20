package com.backend.app.controllers;

import com.backend.app.exceptions.CustomException;
import com.backend.app.exceptions.DtoException;
import com.backend.app.models.IUserService;
import com.backend.app.models.dtos.user.UpdateUserDto;
import com.backend.app.models.dtos.user.UploadProfileDto;
import com.backend.app.models.responses.user.UpdateUserResponse;
import com.backend.app.models.responses.user.UploadProfileResponse;
import com.backend.app.persistence.enums.upload.ETypeFile;
import com.backend.app.persistence.enums.upload.ETypeFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @RequestBody UpdateUserDto updateUserDto
    ) throws Exception {
        DtoException<UpdateUserDto> updateUserDtoException = UpdateUserDto.create(updateUserDto);
        if (updateUserDtoException.getError() != null) throw CustomException.badRequest(updateUserDtoException.getError());
        return new ResponseEntity<>(userService.updateUser(updateUserDtoException.getData()), HttpStatus.OK);
    }

    @PostMapping("/upload-profile")
    public ResponseEntity<UploadProfileResponse> uploadProfile(
            @RequestParam("file") MultipartFile file
    ) throws Exception {
        UploadProfileDto uploadProfileDto = new UploadProfileDto();
        uploadProfileDto.setFile(file);
        uploadProfileDto.setTypeFile(ETypeFile.IMAGE);
        uploadProfileDto.setTypeFolder(ETypeFolder.USER);
        DtoException<UploadProfileDto> uploadProfileDtoException = UploadProfileDto.create(uploadProfileDto);
        if (uploadProfileDtoException.getError() != null) throw CustomException.badRequest(uploadProfileDtoException.getError());
        return new ResponseEntity<>(userService.uploadProfile(uploadProfileDtoException.getData()), HttpStatus.OK);
    }
}

