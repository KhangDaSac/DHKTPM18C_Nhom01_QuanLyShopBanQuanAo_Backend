package com.example.ModaMint_Backend.configuration;

import com.example.ModaMint_Backend.entity.Role;
import com.example.ModaMint_Backend.entity.User;
import com.example.ModaMint_Backend.enums.RoleName;
import com.example.ModaMint_Backend.repository.RoleRepository;
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
    RoleRepository roleRepository;
    UserRepository userRepository;

    @Bean
    ApplicationRunner init() {
        return args -> {
            // 1️⃣ Khởi tạo tất cả role từ enum
            for (RoleName roleName : RoleName.values()) {
                roleRepository.findByName(roleName)
                        .orElseGet(() -> {
                            log.info("Creating role: {}", roleName);
                            Role newRole = roleRepository.save(
                                    Role.builder()
                                            .name(roleName)
                                            .description("Default role: " + roleName)
                                            .build()
                            );
                            log.info("Role created: {}", newRole);
                            return newRole;
                        });
            }

            // 2️⃣ Tạo admin user nếu chưa có
            if (userRepository.findByUsername("admin").isEmpty()) {
                log.info("Checking for ADMIN role...");
                Role adminRole = (Role) roleRepository.findByName(RoleName.ADMIN)
                        .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found"));

                log.info("Admin Role found: {}", adminRole);

                User admin = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("123456789"))
                        .firstName("Huy")
                        .lastName("Nguyen")
                        .dob(LocalDate.parse("2004-04-20"))
                        .roles(Set.of(adminRole))
                        .active(true)
                        .build();

                userRepository.save(admin);
                log.warn("Admin user has been created!");
            } else {
                log.info("Admin user already exists.");
            }
        };
    }
}
