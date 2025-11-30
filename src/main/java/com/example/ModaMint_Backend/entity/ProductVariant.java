package com.example.ModaMint_Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "product_variants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_variant_id")
    long productVariantId;

    @Column(name = "product_variant_name")
    String productVariantName;

    String size;

    String color;

    String image;

    double price;

    /* Dạng phần trăm */
    double discount;

    @Column(name = "inventory_quantity")
    long inventoryQuantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    public double getSalePrice() {
        if (discount > 0) {
            return price - (price * discount / 100);
        }
        return price;
    }
}
