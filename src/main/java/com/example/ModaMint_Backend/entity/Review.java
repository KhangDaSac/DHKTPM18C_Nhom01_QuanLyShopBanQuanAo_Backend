package com.example.ModaMint_Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "product_id")
    Long productId;

    @Column(name = "customer_id")
    String customerId;

    @Column(name = "order_item_id")
    Long orderItemId;

    Integer rating;

    String comment;

    @CreationTimestamp
    @Column(name = "create_at")
    LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    Customer customer;
}
