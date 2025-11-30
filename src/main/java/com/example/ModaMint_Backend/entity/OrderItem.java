package com.example.ModaMint_Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Entity
@Table(name = "order_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@IdClass(OrderItem.OrderItemId.class)
public class OrderItem {
    @Id
    @EqualsAndHashCode.Include
    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    @Id
    @EqualsAndHashCode.Include
    @ManyToOne
    @JoinColumn(name = "product_variant_id")
    ProductVariant productVariant;

    @Column(name = "unit_price", nullable = false)
    double unitPrice;

    @Column(nullable = false)
    long quantity;

    public double getLineTotal() {
        return unitPrice * quantity;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class OrderItemId implements Serializable {
        Order order;
        ProductVariant productVariant;
    }
}
