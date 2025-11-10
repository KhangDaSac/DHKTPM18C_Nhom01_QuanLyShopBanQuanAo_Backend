package com.example.ModaMint_Backend.dto.response.product;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    Long id;
    String name;
    BigDecimal price;
    Boolean active;
    String description;
    Set<String> images;
    String brandName;
    String categoryName;
    Integer quantity; // Tổng số lượng từ tất cả variants

    LocalDateTime createAt;
    LocalDateTime updateAt;
}