package com.example.ModaMint_Backend.configuration;

import com.example.ModaMint_Backend.entity.User;
import com.example.ModaMint_Backend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Configuration
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()) {
//                Set<String> roles = new HashSet<>();
//                roles.add(Role.ADMIN.name());

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("123456789"))
                        .dob(LocalDate.parse("2004-04-20"))
                        .firstName("Huy")
                        .lastName("Nguyen")
//                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("Admin user has been created!");            }
        };
    }
}
