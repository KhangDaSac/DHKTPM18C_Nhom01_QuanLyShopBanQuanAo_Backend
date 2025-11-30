package com.example.ModaMint_Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    long productId;

    @Column(name = "product_name")
    String productName;

    String description;

    String image;

    boolean active;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    List<ProductVariant> productVariants;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    List<Review> reviews;
}
