package com.backend.app.services;

import com.backend.app.exceptions.CustomException;
import com.backend.app.models.dtos.user.UpdateUserDto;
import com.backend.app.models.dtos.user.UploadProfileDto;
import com.backend.app.models.responses.user.UpdateUserResponse;
import com.backend.app.models.responses.user.UploadProfileResponse;
import com.backend.app.persistence.entities.UserEntity;
import com.backend.app.persistence.repositories.UserRepository;
import com.backend.app.models.IUserService;
import com.backend.app.utilities.CloudinaryUtility;
import com.backend.app.utilities.UserAuthenticationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CloudinaryUtility cloudinaryUtility;

    @Autowired
    private UserAuthenticationUtility userAuthenticationUtility;

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> CustomException.badRequest("User not found")
        );
    }

    @Override
    public UpdateUserResponse updateUser(UpdateUserDto updateUserDto) {
        UserEntity user = userAuthenticationUtility.find();
        user.setPhoneNumber(updateUserDto.getPhoneNumber().isEmpty() ? null : updateUserDto.getPhoneNumber());
        user.setDni(updateUserDto.getDni().isEmpty() ? null : updateUserDto.getDni());

        if(isTheSameData(user, updateUserDto)) throw CustomException.badRequest("You must change at least one field");

        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return new UpdateUserResponse(
                "Your data has been updated"
        );
    }

    @Override
    public UploadProfileResponse uploadProfile(UploadProfileDto uploadProfileDto) {
        UserEntity user = userAuthenticationUtility.find();
        
        String url = cloudinaryUtility.uploadFile(
                uploadProfileDto.getFile()
                ,uploadProfileDto.getTypeFile()
                , uploadProfileDto.getTypeFolder()
                , user.getId().intValue());
        user.setImgUrl(url);
        userRepository.save(user);


        return new UploadProfileResponse(
                "Profile image uploaded",
                url
        );
    }

    private boolean isTheSameData(UserEntity user, UpdateUserDto updateUserDto) {
        return (user.getFirstName() != null && user.getFirstName().equals(updateUserDto.getFirstName())) &&
                (user.getLastName() != null && user.getLastName().equals(updateUserDto.getLastName())) &&
                (user.getPhoneNumber() != null && user.getPhoneNumber().equals(updateUserDto.getPhoneNumber())) &&
                (user.getDni() != null && user.getDni().equals(updateUserDto.getDni()));
    }







    /*
    @Transactional
    @Override
    public void delete(User user) {

        userRepository.delete(user);

    }
    */



}
