package com.example.ModaMint_Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String username;
    String email;
    String password;
    String phone;
    String firstName;
    String lastName;
    LocalDate dob;
    boolean active;
    @CreationTimestamp
    LocalDateTime createAt;
    @UpdateTimestamp
    LocalDateTime updateAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<Role> roles;

    @OneToOne(mappedBy = "user")
    Customer customer;

    @OneToMany(mappedBy = "user")
    Set<Review> reviews;

    @OneToMany(mappedBy = "user")
    Set<Cart> carts;

    @OneToMany(mappedBy = "user")
    Set<Order> orders;

    @OneToMany(mappedBy = "user")
    Set<OrderStatusHistory> orderStatusHistories;

    @OneToMany(mappedBy = "user")
    Set<ChatSession> chatSessions;
}
