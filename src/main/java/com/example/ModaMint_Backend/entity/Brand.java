package com.example.ModaMint_Backend.entity;

import com.example.ModaMint_Backend.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Set;

@Entity
@Table(name = "brands")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String description;
    
    /**
     * URL ảnh của brand từ Cloudinary
     */
    @Column(name = "image")
    String image;

    Boolean active = true;

    @OneToMany(mappedBy = "brand")
    Set<Product> products;
}
