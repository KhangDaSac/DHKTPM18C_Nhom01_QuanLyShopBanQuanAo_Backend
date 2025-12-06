package com.example.ModaMint_Backend.dto.response.productvariant;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariantResponse {
    Long id;
    Long productId;
    String size;
    String color;
    String image;
    BigDecimal price;
    BigDecimal discount;
    Integer quantity;
    BigDecimal additionalPrice;
    Boolean active;
    LocalDateTime createAt;
}
