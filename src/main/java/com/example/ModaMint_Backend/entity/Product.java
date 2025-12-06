package com.example.ModaMint_Backend.entity;

import com.example.ModaMint_Backend.utils.StringSetConverter;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @Column(name = "brand_id")
    Long brandId;

    @Column(name = "category_id")
    Long categoryId;

    String description;

    @Convert(converter = StringSetConverter.class)
    @Column(name = "images", columnDefinition = "TEXT")
    Set<String> images;

    Boolean active = true;

    @CreationTimestamp
    @Column(name = "create_at")
    LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    LocalDateTime updateAt;

    @ManyToOne
    @JoinColumn(name = "brand_id", insertable = false, updatable = false)
    Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    Category category;

    @OneToMany(mappedBy = "product")
    Set<ProductVariant> productVariants;

    @OneToMany(mappedBy = "product")
    Set<Review> reviews;
}
