package com.example.ModaMint_Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "order_code")
    String orderCode;

    @Column(name = "user_id")
    String userId;

    @Column(name = "total_amount")
    BigDecimal totalAmount;

    @Column(name = "sub_total")
    BigDecimal subTotal;

    @Column(name = "promotion_id")
    Long promotionId;

    @Column(name = "promotion_value")
    BigDecimal promotionValue;

    @Column(name = "order_status")
    String orderStatus;

    @Column(name = "shipping_address")
    String shippingAddress;

    String phone;

    @UpdateTimestamp
    @Column(name = "update_at")
    LocalDateTime updateAt;

    @CreationTimestamp
    @Column(name = "create_at")
    LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "promotion_id", insertable = false, updatable = false)
    Promotion promotion;

    @OneToMany(mappedBy = "order")
    Set<OrderItem> orderItems;

    @OneToMany(mappedBy = "order")
    Set<OrderStatusHistory> orderStatusHistories;

    @OneToMany(mappedBy = "order")
    Set<Shipment> shipments;

    @OneToMany(mappedBy = "order")
    Set<Payment> payments;
}
