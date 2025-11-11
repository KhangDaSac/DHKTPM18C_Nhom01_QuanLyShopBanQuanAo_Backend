package com.example.ModaMint_Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "promotions")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "promotion_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id")
    Long promotionId;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "code", unique = true, nullable = false)
    String code;

    @Column(name = "min_order_value")
    BigDecimal minOrderValue;

    @Column(name = "effective")
    LocalDateTime effective;

    @Column(name = "expiration")
    LocalDateTime expiration;

    @Column(name = "quantity")
    Integer quantity;

    @Column(name = "is_active")
    Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "create_at", updatable = false)
    LocalDateTime createAt;


}
