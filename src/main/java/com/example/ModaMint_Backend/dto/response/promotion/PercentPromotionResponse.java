package com.example.ModaMint_Backend.dto.response.promotion;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PercentPromotionResponse {
    String id;
    String name;
    String code;
    BigDecimal discountPercent;
    BigDecimal maxDiscount;
    BigDecimal minOrderValue;
    LocalDateTime startAt;
    LocalDateTime endAt;
    Integer quantity;
    Boolean isActive;
    LocalDateTime createAt;
}

