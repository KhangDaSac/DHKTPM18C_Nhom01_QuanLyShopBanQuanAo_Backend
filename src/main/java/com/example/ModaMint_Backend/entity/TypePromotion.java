package com.example.ModaMint_Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "type_promotions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypePromotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "promotion_id")
    Long promotionId;

    @Enumerated(EnumType.STRING)
    PromotionType type; // PERCENT or AMOUNT

    @ManyToOne
    @JoinColumn(name = "promotion_id", insertable = false, updatable = false)
    Promotion promotion;
}

enum PromotionType {
    PERCENT,
    AMOUNT
}
