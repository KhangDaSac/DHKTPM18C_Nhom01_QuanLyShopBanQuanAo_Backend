package com.example.ModaMint_Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "order_id")
    Long orderId;

    @Column(name = "variant_id")
    Long variantId;

    @Column(name = "price_at_purchase")
    BigDecimal priceAtPurchase;

    Integer quantity;
    BigDecimal discount;

    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    Order order;

    @ManyToOne
    @JoinColumn(name = "variant_id", insertable = false, updatable = false)
    ProductVariant productVariant;
}
