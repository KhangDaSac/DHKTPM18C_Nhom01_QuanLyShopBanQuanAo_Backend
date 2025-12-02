package com.example.ModaMint_Backend.dto.request.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * DTO gộp để tạo Product + Variants trong 1 transaction
 * Đảm bảo Product luôn có ít nhất 1 Variant
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateProductWithVariantsRequest {
    
    @NotNull(message = "Thông tin sản phẩm không được để trống")
    @Valid
    ProductRequest product;

    @NotEmpty(message = "Phải có ít nhất 1 biến thể sản phẩm")
    @Valid
    List<CreateProductVariantRequest> variants;
}
