package com.example.ModaMint_Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "product_variants")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "product_id")
    Long productId;

    String size;
    String color;

    BigDecimal price;           // Giá của variant này
    BigDecimal discount;       // Giảm giá của variant này
    Integer quantity;          // Số lượng tồn kho

    @Column(name = "additional_price")
    BigDecimal additionalPrice;

    @CreationTimestamp
    @Column(name = "create_at")
    LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    Product product;


    @OneToMany(mappedBy = "productVariant")
    Set<CartItem> cartItems;

    @OneToMany(mappedBy = "productVariant")
    Set<OrderItem> orderItems;
}
