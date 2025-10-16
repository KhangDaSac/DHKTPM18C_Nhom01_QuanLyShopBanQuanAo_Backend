package com.example.ModaMint_Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "promotions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String code;
    String value;

    @Column(name = "min_order_value")
    BigDecimal minOrderValue;

    @Column(name = "start_at")
    LocalDateTime startAt;

    @Column(name = "end_at")
    LocalDateTime endAt;

    @Column(name = "max_use")
    Integer maxUse;

    @Column(name = "is_active")
    Boolean isActive;

    @CreationTimestamp
    @Column(name = "create_at")
    LocalDateTime createAt;

    @OneToMany(mappedBy = "promotion")
    Set<Order> orders;

    @OneToMany(mappedBy = "promotion")
    Set<TypePromotion> typePromotions;
}
