package com.example.ModaMint_Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class Promotion {
    @Id
    @Column(name = "promotion_id")
    long promotionId;

    @Column(name = "promotion_name", nullable = false)
    String promotionName;

    @Column(unique = true, nullable = false)
    String code;

    @Column(name = "min_order_value")
    double minOrderValue;

    LocalDateTime effective;

    LocalDateTime expiration;

    long quantity;

    boolean active;
}
