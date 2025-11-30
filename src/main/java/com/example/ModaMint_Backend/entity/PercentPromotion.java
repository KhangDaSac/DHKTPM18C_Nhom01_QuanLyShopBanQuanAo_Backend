package com.example.ModaMint_Backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "percent_promotions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PercentPromotion extends Promotion {
    @Column(nullable = false)
    double percent;

    @Column(name = "max_discount")
    double maxDiscount;
}