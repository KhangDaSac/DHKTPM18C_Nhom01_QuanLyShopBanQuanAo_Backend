package com.example.ModaMint_Backend.dto.request.productvariant;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariantRequest {
    @NotNull(message = "Mã sản phẩm không được để trống")
    Long productId;

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
