package com.example.ModaMint_Backend.entity;

import com.example.ModaMint_Backend.enums.PromotionType;
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
    PromotionType type; // Phần trăm hoặc Tiền

    @ManyToOne
    @JoinColumn(name = "promotion_id", insertable = false, updatable = false)
    Promotion promotion;
}
