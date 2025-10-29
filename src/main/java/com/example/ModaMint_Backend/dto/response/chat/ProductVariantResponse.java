package com.example.ModaMint_Backend.dto.response.chat;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariantResponse {
    Long id;
    String size;
    String color;
    BigDecimal price;
    BigDecimal discount;
    Integer quantity;
}
