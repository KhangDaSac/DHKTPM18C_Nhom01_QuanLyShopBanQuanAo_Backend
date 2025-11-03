package com.example.ModaMint_Backend.dto.response.promotion;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionSummary {
    
    Long id;
    
    String name;
    
    String code;
    
    String type; // "PERCENTAGE" hoặc "AMOUNT"
    
    BigDecimal discountPercent; // Cho percentage promotion
    
    BigDecimal discountAmount; // Cho amount promotion
    
    BigDecimal minOrderValue; // Giá trị đơn hàng tối thiểu
    
    LocalDateTime startAt;
    
    LocalDateTime endAt;
    
    Integer remainingQuantity; // Số lượng còn lại
    
    Boolean isActive;
    
    String description; // Mô tả chi tiết
}
