package com.backend.app.services;

import com.backend.app.exceptions.CustomException;
import com.backend.app.models.IUploadFileService;
import com.backend.app.models.dtos.requests.common.UploadImagesRequest;
import com.backend.app.models.dtos.requests.user.UpdateUserRequest;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.persistence.entities.UserEntity;
import com.backend.app.persistence.enums.EResponseStatus;
import com.backend.app.persistence.repositories.UserRepository;
import com.backend.app.models.IUserService;
import com.backend.app.utilities.ValidationsUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    private final UserAuthenticationService userAuthenticationService;
    private final IUploadFileService uploadFileService;

    @Override
    public ApiResponse<List<UserEntity>> findAll() {
        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Users found",
                userRepository.findAll()
        );
    }

    @Override
    public ApiResponse<UserEntity> findById(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(
                () -> CustomException.badRequest("User not found")
        );
        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "User found",
                user
        );
    }

    @Override
    public ApiResponse<UserEntity> updateUser(UpdateUserRequest updateUserRequest) {
        UserEntity user = userAuthenticationService.find();

        if(isTheSameData(user, updateUserRequest)) throw CustomException.badRequest("You must change at least one field");

        if(user.getPhoneNumber() != null && !StringUtils.hasText(updateUserRequest.getPhoneNumber())) throw CustomException.badRequest("You can't change your phone number");
        if(user.getDni() != null && !StringUtils.hasText(updateUserRequest.getDni())) throw CustomException.badRequest("You can't change your dni");

        user.setFirstName(updateUserRequest.getFirstName());
        user.setLastName(updateUserRequest.getLastName());
        user.setPhoneNumber(ValidationsUtility.hasText(updateUserRequest.getPhoneNumber()));
        user.setDni(ValidationsUtility.hasText(updateUserRequest.getDni()));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Data updated successfully",
                user
        );
    }

    @Override
    public ApiResponse<String> uploadProfile(UploadImagesRequest uploadImagesRequest) {
        UserEntity user = userAuthenticationService.find();
        try {
            if(uploadImagesRequest.getFiles().size() > 1) throw CustomException.badRequest("You can only upload one image");
            //* Delete previous image
            if(user.getImgUrl() != null) uploadFileService.deleteFile(user.getImgUrl(), uploadImagesRequest.getTypeFile());

            String url = uploadFileService.uploadFile(
                uploadImagesRequest.getFiles().get(0),
                uploadImagesRequest.getTypeFile()
                );
            user.setImgUrl(url);
            userRepository.save(user);
            return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Image uploaded successfully",
                url
            );
        } catch (Exception e) {
            throw CustomException.badRequest("Error uploading image, try again later");
        }
    }

    private boolean isTheSameData(UserEntity user, UpdateUserRequest updateUserRequest) {
        return (user.getFirstName() != null && user.getFirstName().equals(updateUserRequest.getFirstName())) &&
                (user.getLastName() != null && user.getLastName().equals(updateUserRequest.getLastName())) &&
                (Objects.equals(user.getPhoneNumber(), ValidationsUtility.hasText(updateUserRequest.getPhoneNumber())) &&
                (Objects.equals(user.getDni(), ValidationsUtility.hasText(updateUserRequest.getDni()))));
    }


}
