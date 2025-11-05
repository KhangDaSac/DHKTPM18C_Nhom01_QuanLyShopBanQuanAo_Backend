package com.example.ModaMint_Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Set;

@Entity
@Table(name = "categories")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    
    @Column(name = "is_active")
    Boolean isActive;

    // Chỉ lưu ID của danh mục cha
    @Column(name = "parent_id")
    Long parentId;

    @OneToMany(mappedBy = "category")
    Set<Product> products;
}
