package com.backend.app;

import com.backend.app.persistence.entities.ERole;
import com.backend.app.persistence.entities.RoleEntity;
import com.backend.app.persistence.entities.UserEntity;
import com.backend.app.persistence.repositories.RoleRepository;
import com.backend.app.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
public class TecFoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(TecFoodApplication.class, args);
	}

	/*@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	CommandLineRunner init(UserRepository userRepository, RoleRepository roleRepository) {

		RoleEntity roleAdmin = RoleEntity.builder()
				.name(ERole.ROLE_ADMIN)
				.build();

		RoleEntity roleUser = RoleEntity.builder()
				.name(ERole.ROLE_USER)
				.build();

		UserEntity userWerner = UserEntity.builder()
				.firstName("Werner")
				.lastName("Werner")
				.email("werner@tecsup.edu.pe")
				.password(passwordEncoder.encode("aLTEC2021"))
				.dni("12345678")
				.isGoogleAccount(false)
				.isVerifiedEmail(false)
				.phoneNumber("12345678")
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.role(roleAdmin)

				.build();


		UserEntity userHans = UserEntity.builder()
				.firstName("Hans")
				.lastName("Hans")
				.email("hans@gmail.com")
				.password(passwordEncoder.encode("aLTEC2021"))
				.dni("62345678")
				.isGoogleAccount(false)
				.isVerifiedEmail(false)
				.phoneNumber("12345678")
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.role(roleUser)
				.build();

		roleRepository.saveAll(List.of(roleAdmin, roleUser));
		userRepository.saveAll(List.of(userWerner, userHans));

		return (args -> {
			System.out.println("Users added");
		});

	}
*/

	}
