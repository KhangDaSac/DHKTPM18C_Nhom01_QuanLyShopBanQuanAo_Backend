package com.example.ModaMint_Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.StringUtils;
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
    private static final String DEFAULT_IMAGE_URL = "https://res.cloudinary.com/dysjwopcc/image/upload/v1760844380/Clarification_4___Anime_Gallery___Tokyo_Otaku_Mode_TOM_Shop__Figures_Merch_From_Japan_zjhr4t.jpg";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String username;
    String email;
    String password;
    String phone;
    String firstName;
    String lastName;
    String image;
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

    @PrePersist
    public void prePersist() {
        if (!StringUtils.hasText(this.image)) {
            this.image = DEFAULT_IMAGE_URL;
        }
    }
}
