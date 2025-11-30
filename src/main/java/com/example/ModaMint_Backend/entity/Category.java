package com.example.ModaMint_Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    long categoryId;

    @Column(name = "category_name")
    String categoryName;

    boolean active;

    String image;

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    Category parentCategory;

    @OneToMany(mappedBy = "category")
    @ToString.Exclude
    List<Product> products;
}
