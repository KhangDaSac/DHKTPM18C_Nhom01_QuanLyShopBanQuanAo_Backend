package com.example.ModaMint_Backend.dto.response.product;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

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
    List<String> images; // Danh sách URL ảnh
    List<ProductVariantResponse> productVariants; // Danh sách variants
}