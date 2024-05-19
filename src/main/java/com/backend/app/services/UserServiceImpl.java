package com.backend.app.services;

import com.backend.app.exception.CustomException;
import com.backend.app.persistence.entities.UserEntity;
import com.backend.app.persistence.repositories.UserRepository;
import com.backend.app.models.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    /*
    @Transactional
   @Override
    public UserEntity save(UserDto userDto) {
       UserEntity user= UserEntity.builder()
               .id(userDto.getId())
               .firstName(userDto.getFirstName())
               .lastName(userDto.getLastName())
               .phoneNumber(userDto.getPhoneNumber())
               .email(userDto.getEmail())
               .password(userDto.getPassword())
               .dni(userDto.getDni())
               .imgUrl(userDto.getImgUrl())
               .isGoogleAccount(userDto.isGoogleAccount())
               .isVerifiedEmail(userDto.isVerifiedEmail())
               .role(userDto)
               .build();
        return userRepository.save(user);
    }
    */


    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> CustomException.badRequest("User not found")
        );
    }


    /*
    @Transactional
    @Override
    public void delete(User user) {

        userRepository.delete(user);

    }
    */



}
