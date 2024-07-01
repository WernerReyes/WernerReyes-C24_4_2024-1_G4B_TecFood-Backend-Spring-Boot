package com.backend.app.services;

import com.backend.app.exceptions.CustomException;
import com.backend.app.models.IAuthService;
import com.backend.app.models.dtos.requests.auth.LoginGoogleRequest;
import com.backend.app.models.dtos.requests.auth.LoginRequest;
import com.backend.app.models.dtos.requests.auth.RegisterRequest;
import com.backend.app.models.dtos.responses.auth.*;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.persistence.enums.EResponseStatus;
import com.backend.app.persistence.enums.ERole;
import com.backend.app.persistence.entities.UserEntity;
import com.backend.app.persistence.repositories.RoleRepository;
import com.backend.app.persistence.repositories.UserRepository;
import com.backend.app.utilities.JwtUtility;
import com.backend.app.utilities.ValidationsUtility;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements IAuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserAuthenticationService userAuthenticationService;

    private final JwtUtility jwtUtility;

    @Override
    public ApiResponse<LoginResponse> login(LoginRequest loginRequest) throws Exception {
        UserEntity user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null) throw CustomException.badRequest("Email or password is incorrect");
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw CustomException.badRequest("Email or password is incorrect");
        }
        String token = jwtUtility.generateJWT(user.getId());
        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Login successful",
                new LoginResponse(
                        user,
                        token
                )
        );
    }

    public ApiResponse<LoginResponse> loginGoogle(LoginGoogleRequest loginGoogleRequest) throws Exception {
        UserEntity user = userRepository.findByEmail(loginGoogleRequest.getEmail());
        if (user == null) {
            user = UserEntity.builder()
                    .email(loginGoogleRequest.getEmail())
                    .password(passwordEncoder.encode(loginGoogleRequest.getPassword()))
                    .role(roleRepository.findByName(ERole.ROLE_USER))
                    .firstName(loginGoogleRequest.getFirstName())
                    .lastName(loginGoogleRequest.getLastName())
                    .imgUrl(loginGoogleRequest.getImgUrl())
                    .isGoogleAccount(true)
                    .isVerifiedEmail(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            userRepository.save(user);
        } else {
            if (!user.isGoogleAccount() || !user.isVerifiedEmail()) {
                user.setGoogleAccount(true);
                user.setVerifiedEmail(true);
                user.setUpdatedAt(LocalDateTime.now());
                userRepository.save(user);
            }
        }

        String token = jwtUtility.generateJWT(user.getId());
        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Login successful",
                new LoginResponse(
                        user,
                        token
                )
        );
    }

    public ApiResponse<Void> register(RegisterRequest registerRequest) {
        UserEntity user = userRepository.findByEmail(registerRequest.getEmail());
        if (user != null) throw CustomException.badRequest("Email already exists");
        if(!StringUtils.isEmpty(registerRequest.getDni())) {
            user = userRepository.findByDni(registerRequest.getDni());
            if (user != null) throw CustomException.badRequest("DNI already exists");
        }

        user = UserEntity.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(roleRepository.findByName(ERole.ROLE_USER))
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .phoneNumber(ValidationsUtility.hasText(registerRequest.getPhoneNumber()))
                .dni(ValidationsUtility.hasText(registerRequest.getDni()))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();



        userRepository.save(user);

        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "User registered successfully",
                null

        );
    }

    @Override
    public ApiResponse<String> renovateToken(
            String expiredToken
    ) throws Exception {
        Long userId = jwtUtility.getUserIdFromJWT(expiredToken);
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user == null) throw CustomException.badRequest("User not found");
        String token = jwtUtility.generateJWT(user.getId());
        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Token renovated successfully",
                token
        );
    }

    @Override
    public ApiResponse<UserEntity> revalidateToken(

    ) {
        UserEntity user = userAuthenticationService.find();
        return new ApiResponse<>(
                EResponseStatus.SUCCESS,
                "Token revalidated successfully",
                user
        );
    }

}