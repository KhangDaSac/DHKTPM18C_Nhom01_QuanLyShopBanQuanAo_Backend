package com.example.ModaMint_Backend.entity;

import com.example.ModaMint_Backend.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    private static final String DEFAULT_IMAGE_URL =
            "https://res.cloudinary.com/dysjwopcc/image/upload/v1760844380/" +
                    "Clarification_4___Anime_Gallery___Tokyo_Otaku_Mode_TOM_Shop__Figures_Merch_From_Japan_zjhr4t.jpg";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Size(min = 3, message = "Username must be at least 3 characters")
    String username;
    
    String email;

    @Size(min = 8, message = "Password must be at least 8 characters")
    String password;
    String phone;
    String firstName;
    String lastName;
    String image;
    LocalDate dob;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    Gender gender;

    @Builder.Default
    boolean active = true;

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
    Set<Conversation> conversations;

    @PrePersist
    public void prePersist() {
        if (!StringUtils.hasText(this.image)) {
            this.image = DEFAULT_IMAGE_URL;
        }
    }
}
