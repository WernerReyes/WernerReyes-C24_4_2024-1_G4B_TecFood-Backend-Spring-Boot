package com.backend.app.models;


import com.backend.app.models.dtos.user.UpdateUserDto;
import com.backend.app.models.dtos.user.UploadProfileDto;
import com.backend.app.models.responses.user.UpdateUserResponse;
import com.backend.app.models.responses.user.UploadProfileResponse;
import com.backend.app.persistence.entities.UserEntity;
import com.backend.app.persistence.enums.upload.ETypeFolder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserService {
    List<UserEntity> findAll();
    // UserEntity save(UserDto userDto);
    UserEntity findById(Long id);
    // void delete(UserEntity user);
    public UpdateUserResponse updateUser (UpdateUserDto updateUserDto) throws Exception;
   public UploadProfileResponse uploadProfile (UploadProfileDto uploadProfileDto) throws Exception;

}
