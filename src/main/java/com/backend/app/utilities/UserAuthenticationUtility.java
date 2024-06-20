package com.backend.app.utilities;

import com.backend.app.exceptions.CustomException;
import com.backend.app.persistence.entities.UserEntity;
import com.backend.app.persistence.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class  UserAuthenticationUtility {

    private final UserRepository userRepository;

    public UserAuthenticationUtility(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserEntity find() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        UserEntity user = userRepository.findById(Long.parseLong(userId)).orElse(null);
        if (user == null) throw CustomException.badRequest("User not found");
        return user;
    }
}
