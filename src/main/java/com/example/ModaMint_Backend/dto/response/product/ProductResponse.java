package com.example.ModaMint_Backend.dto.response.product;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

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
    String brandName;
    String categoryName;
}