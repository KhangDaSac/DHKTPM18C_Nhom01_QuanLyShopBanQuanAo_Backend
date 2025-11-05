package com.example.ModaMint_Backend.configuration;

import com.example.ModaMint_Backend.entity.Customer;
import com.example.ModaMint_Backend.entity.Role;
import com.example.ModaMint_Backend.entity.User;
import com.example.ModaMint_Backend.enums.Gender;
import com.example.ModaMint_Backend.enums.RoleName;
import com.example.ModaMint_Backend.repository.CustomerRepository;
import com.example.ModaMint_Backend.repository.RoleRepository;
import com.example.ModaMint_Backend.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Set;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class    ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final JdbcTemplate jdbcTemplate;

    private static final String DEFAULT_IMAGE_URL = "https://res.cloudinary.com/dysjwopcc/image/upload/v1760844380/Clarification_4___Anime_Gallery___Tokyo_Otaku_Mode_TOM_Shop__Figures_Merch_From_Japan_zjhr4t.jpg";

    @Bean
    @Transactional
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
            Role adminRole = roleRepository.findByName(RoleName.ADMIN).orElseThrow();
            if (userRepository.findByUsername("admin").isEmpty()) {
                log.info("Creating admin user...");
                
                User admin = User.builder()
                        .username("admin")
                        .email("admin@orientalfashion.com")
                        .password(passwordEncoder.encode("123456789"))
                        .phone("0987654321")
                        .firstName("Admin")
                        .lastName("System")
                        .dob(LocalDate.parse("1985-01-01"))
                        .gender(Gender.MALE)
                        .roles(Set.of(adminRole))
                        .active(true)
                        .build();

                admin.setImage("https://example.com/admin.jpg");
                userRepository.save(admin);
                log.warn("Admin user has been created!");
            } else {
                log.info("Admin user already exists.");
            }

            // 3️⃣ Tạo staff user nếu chưa có
            Role staffRole = roleRepository.findByName(RoleName.STAFF).orElseThrow();
            if (userRepository.findByUsername("staff1").isEmpty()) {
                log.info("Creating staff user...");
                
                User staff = User.builder()
                        .username("staff1")
                        .email("staff1@orientalfashion.com")
                        .password(passwordEncoder.encode("123456789"))
                        .phone("0987654322")
                        .firstName("Staff")
                        .lastName("Member")
                        .dob(LocalDate.parse("1990-01-01"))
                        .gender(Gender.FEMALE)
                        .roles(Set.of(staffRole))
                        .active(true)
                        .build();

                staff.setImage("https://example.com/staff.jpg");
                userRepository.save(staff);
                log.warn("Staff user has been created!");
            } else {
                log.info("Staff user already exists.");
            }

            // 4️⃣ Tạo 10 customer users
            Role customerRole = roleRepository.findByName(RoleName.CUSTOMER).orElseThrow();

            String[] customerData = {
                "customer1,customer1@email.com,0123456789,Nguyễn,Văn A,1990-01-15,MALE",
                "customer2,customer2@email.com,0123456790,Trần,Thị B,1992-03-20,FEMALE",
                "customer3,customer3@email.com,0123456791,Lê,Văn C,1988-07-10,MALE",
                "customer4,customer4@email.com,0123456792,Phạm,Thị D,1995-11-25,FEMALE",
                "customer5,customer5@email.com,0123456793,Hoàng,Văn E,1991-05-08,MALE",
                "customer6,customer6@email.com,0123456794,Vũ,Thị F,1993-09-12,FEMALE",
                "customer7,customer7@email.com,0123456795,Đặng,Văn G,1989-12-30,MALE",
                "customer8,customer8@email.com,0123456796,Bùi,Thị H,1994-04-18,FEMALE",
                "customer9,customer9@email.com,0123456797,Đinh,Văn I,1990-08-22,MALE",
                "customer10,customer10@email.com,0123456798,Ngô,Thị J,1992-06-14,FEMALE"
            };

            for (String data : customerData) {
                String[] parts = data.split(",");
                String username = parts[0];
                String email = parts[1];
                String phone = parts[2];
                String firstName = parts[3];
                String lastName = parts[4];
                String dob = parts[5];
                Gender gender = Gender.valueOf(parts[6]);

                if (userRepository.findByUsername(username).isEmpty()) {
                    User customer = User.builder()
                            .username(username)
                            .email(email)
                            .password(passwordEncoder.encode("123456789"))
                            .phone(phone)
                            .firstName(firstName)
                            .lastName(lastName)
                            .dob(LocalDate.parse(dob))
                            .gender(gender)
                            .roles(Set.of(customerRole))
                            .active(true)
                            .build();

                    // Set image
                    customer.setImage("https://example.com/avatar" + username.substring(8) + ".jpg");

                    User savedUser = userRepository.save(customer);
                    
                    // Tạo customer record bằng native SQL để tránh detached entity issue
                    jdbcTemplate.update("INSERT INTO customers (user_id) VALUES (?)", savedUser.getId());
                    
                    log.info("Customer user created: {}", username);
                } else {
                    log.info("Customer user {} already exists.", username);
                }
            }
        };
    }
}
