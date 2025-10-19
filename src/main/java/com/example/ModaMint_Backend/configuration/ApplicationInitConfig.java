package com.example.ModaMint_Backend.configuration;

import com.example.ModaMint_Backend.entity.Role;
import com.example.ModaMint_Backend.entity.User;
import com.example.ModaMint_Backend.enums.RoleName;
import com.example.ModaMint_Backend.repository.RoleRepository;
import com.example.ModaMint_Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Set;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private static final String DEFAULT_IMAGE_URL = "https://res.cloudinary.com/dysjwopcc/image/upload/v1760844380/Clarification_4___Anime_Gallery___Tokyo_Otaku_Mode_TOM_Shop__Figures_Merch_From_Japan_zjhr4t.jpg";

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
                        .email("admin@modamint.com")
                        .password(passwordEncoder.encode("123456789"))
                        .phone("0123456789")
                        .firstName("Huy")
                        .lastName("Nguyen")
                        .dob(LocalDate.parse("2004-04-20"))
                        .roles(Set.of(adminRole))
                        .active(true)
                        .build();

                // Đảm bảo set image sau khi build
                admin.setImage(DEFAULT_IMAGE_URL);

                userRepository.save(admin);
                log.warn("Admin user has been created with default image!");
            } else {
                log.info("Admin user already exists.");
            }

            // 3️⃣ Tạo user thường nếu chưa có
            if (userRepository.findByUsername("user01").isEmpty()) {
                log.info("Checking for USER role...");
                Role userRole = (Role) roleRepository.findByName(RoleName.USER)
                        .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

                log.info("User Role found: {}", userRole);

                User normalUser = User.builder()
                        .username("Nguyen Quang Huy")
                        .email("user01@modamint.com")
                        .password(passwordEncoder.encode("123456789"))
                        .phone("0123456789")
                        .firstName("Huy")
                        .lastName("Nguyen")
                        .dob(LocalDate.parse("2004-04-20"))
                        .roles(Set.of(userRole))
                        .active(true)
                        .build();

                // Đảm bảo set image sau khi build
                normalUser.setImage(DEFAULT_IMAGE_URL);

                userRepository.save(normalUser);
                log.warn("Normal user has been created with default image!");
            } else {
                log.info("Normal user already exists.");
            }
        };
    }
}
