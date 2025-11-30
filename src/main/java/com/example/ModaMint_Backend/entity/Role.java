package com.example.ModaMint_Backend.entity;


import com.example.ModaMint_Backend.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long roleId;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    RoleName name;

    String description;

    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude
    List<User> users;

    @ManyToMany
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    @ToString.Exclude
    List<Permission> permissions;
}
