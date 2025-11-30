package com.example.ModaMint_Backend.entity;


import com.example.ModaMint_Backend.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    String userId;

    @Size(min = 3, message = "Username must be at least 3 characters")
    String username;
    
    String email;

    @Size(min = 8, message = "Password must be at least 8 characters")
    String password;
    String phone;
    @Column(name = "first_name")
    String firstName;
    @Column(name = "last_name")
    String lastName;
    String image;
    LocalDate dob;
    
    @Enumerated(EnumType.STRING)
    Gender gender;

    boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    List<Role> roles;

    @OneToOne(mappedBy = "user")
    Customer customer;
}
