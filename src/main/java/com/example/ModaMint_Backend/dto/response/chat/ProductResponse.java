package com.example.ModaMint_Backend.dto.response.chat;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    Long id;
    String name;
    String brand;
    String category;
    String description;
    String active;
    Set<ProductVariantResponse> productVariants;
}
