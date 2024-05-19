package com.backend.app.models;


import com.backend.app.persistence.entities.UserEntity;

import java.util.List;

public interface IUserService {
    List<UserEntity> findAll();
    // UserEntity save(UserDto userDto);
    UserEntity findById(Long id);
    // void delete(UserEntity user);

}
