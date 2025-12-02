package com.example.ModaMint_Backend.dto.request.product;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

/**
 * DTO để tạo ProductVariant không cần productId
 * Dùng khi tạo Product + Variants cùng lúc
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateProductVariantRequest {
    
    String size;

    String color;
    
    String image;

    @NotNull(message = "Giá không được để trống")
    BigDecimal price;

    BigDecimal discount;

    @NotNull(message = "Số lượng không được để trống")
    Integer quantity;

    BigDecimal additionalPrice;
}
