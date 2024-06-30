package com.backend.app.services;

import com.backend.app.exceptions.CustomException;
import com.backend.app.models.IAuthService;
import com.backend.app.models.dtos.auth.LoginGoogleUserDto;
import com.backend.app.models.dtos.auth.LoginUserDto;
import com.backend.app.models.dtos.auth.RegisterUserDto;
import com.backend.app.models.responses.auth.LoginUserResponse;
import com.backend.app.models.responses.auth.RegisterUserResponse;
import com.backend.app.persistence.enums.ERole;
import com.backend.app.persistence.entities.UserEntity;
import com.backend.app.persistence.repositories.RoleRepository;
import com.backend.app.persistence.repositories.UserRepository;
import com.backend.app.utilities.JwtUtility;
import com.backend.app.utilities.UserAuthenticationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private UserAuthenticationUtility userAuthenticationUtility;

    @Override
    public LoginUserResponse login(LoginUserDto loginUserDto) throws Exception {
        UserEntity user = userRepository.findByEmail(loginUserDto.getEmail());
        if (user == null) throw CustomException.badRequest("Email or password is incorrect");
        if(!passwordEncoder.matches(loginUserDto.getPassword(), user.getPassword())) {
            throw CustomException.badRequest("Email or password is incorrect");
        }
        String token = jwtUtility.generateJWT(user.getId());
        return new LoginUserResponse(
                "Login successful",
                user,
                token
        );
    }

    public LoginUserResponse loginGoogle(LoginGoogleUserDto loginGoogleUserDto) throws Exception {
        UserEntity user = userRepository.findByEmail(loginGoogleUserDto.getEmail());
        if (user == null) {
            user = UserEntity.builder()
                    .email(loginGoogleUserDto.getEmail())
                    .password(passwordEncoder.encode("google" + loginGoogleUserDto.getEmail()))
                    .role(roleRepository.findByName(ERole.ROLE_USER))
                    .firstName(loginGoogleUserDto.getFirstName())
                    .lastName(loginGoogleUserDto.getLastName())
                    .imgUrl(loginGoogleUserDto.getImgUrl())
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
        return new LoginUserResponse(
                "Login successful",
                user,
                token
        );
    }

    public RegisterUserResponse register(RegisterUserDto registerUserDto) {
        UserEntity user = userRepository.findByEmail(registerUserDto.getEmail());
        if (user != null) throw CustomException.badRequest("Email already exists");

        System.out.println("dni: " + registerUserDto.getDni().isEmpty());

        if(registerUserDto.getDni() != null && !registerUserDto.getDni().isEmpty()) {
            user = userRepository.findByDni(registerUserDto.getDni());
            if (user != null) throw CustomException.badRequest("DNI already exists");
        }

        user = UserEntity.builder()
                .email(registerUserDto.getEmail())
                .password(passwordEncoder.encode(registerUserDto.getPassword()))
                .role(roleRepository.findByName(ERole.ROLE_USER))
                .firstName(registerUserDto.getFirstName())
                .lastName(registerUserDto.getLastName())
                .phoneNumber(registerUserDto.getPhoneNumber().isEmpty() ? null : registerUserDto.getPhoneNumber())
                .dni(registerUserDto.getDni().isEmpty() ? null : registerUserDto.getDni())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return new RegisterUserResponse(
                "User " + user.getEmail() + " created successfully"
        );
    }

    @Override
    public LoginUserResponse renovateToken(
       String expiredToken
    ) throws Exception {
        Long userId = jwtUtility.getUserIdFromJWT(expiredToken);
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user == null) throw CustomException.badRequest("User not found");
        String token = jwtUtility.generateJWT(user.getId());
        return new LoginUserResponse(
                "Token refreshed",
                user,
                token
        );
    }

    @Override
    public LoginUserResponse revalidateToken(

    ) throws Exception {
        UserEntity user = userAuthenticationUtility.find();
        String token = jwtUtility.generateJWT(user.getId());
        return new LoginUserResponse(
                "Token refreshed",
                    user,
                    token
        );
    }

}
