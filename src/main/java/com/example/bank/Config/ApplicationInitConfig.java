package com.example.bank.Config;

import com.example.bank.Entity.Role;
import com.example.bank.Entity.User;

import com.example.bank.Exception.AppException;
import com.example.bank.Exception.ErrorCode;
import com.example.bank.Repository.RoleRepository;
import com.example.bank.Repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSecurity
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    @Bean
//    , RoleRepository roleRepository
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {

            if (roleRepository.findByName("ADMIN").isEmpty()) {
                Role role = Role.builder()
                        .name("ADMIN")
                        .description("Administrator role")
                        .build();
                roleRepository.save(role);
                log.info("Role ADMIN has been created.");
            }
            if (userRepository.findByUsername("admin").isEmpty()) {
                Role adminRole = roleRepository.findByName("ADMIN").orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));
                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("12345"))
                        .roles(Set.of(adminRole))
                        .build();
                userRepository.save(user);
                log.warn("admin user has been created with default password is: 1234, please change it");
            }
        };
    }
}
