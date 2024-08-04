package com.backend.app.services;

import com.backend.app.exceptions.CustomException;
import com.backend.app.persistence.entities.UserEntity;
import com.backend.app.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserAuthenticationService {

    private final UserRepository userRepository;

    public UserEntity find() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        UserEntity user = userRepository.findById(Long.parseLong(userId)).orElse(null);
        if (user == null) throw CustomException.badRequest("User not found");
        return user;
    }


}
