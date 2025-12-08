package com.example.ModaMint_Backend.entity;

import com.example.ModaMint_Backend.entity.Cart;
import com.example.ModaMint_Backend.entity.ProductVariant;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "cart_item")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "cart_id")
    Long cartId;

    @Column(name = "variant_id")
    Long variantId;

    Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id", insertable = false, updatable = false)
    Cart cart;

    @ManyToOne
    @JoinColumn(name = "variant_id", insertable = false, updatable = false)
    ProductVariant productVariant;
}
