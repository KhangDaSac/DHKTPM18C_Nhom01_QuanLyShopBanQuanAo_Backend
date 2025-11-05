package com.example.ModaMint_Backend.entity;

import com.example.ModaMint_Backend.entity.Order;
import com.example.ModaMint_Backend.entity.ProductVariant;
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

    @Column(name = "product_variant_id")
    Long productVariantId; 

    BigDecimal unitPrice; // Giá đơn vị của sản phẩm
    Integer quantity; // Số lượng sản phẩm

    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    Order order;

    @ManyToOne
    @JoinColumn(name = "product_variant_id", insertable = false, updatable = false)
    ProductVariant productVariant;

    public BigDecimal getLineTotal() { // Tổng tiền của sản phẩm
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
