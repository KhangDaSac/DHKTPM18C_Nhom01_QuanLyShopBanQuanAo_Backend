package com.example.ModaMint_Backend.dto.request.product;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {

    @NotBlank(message = "Tên sản phẩm không được để trống")
    String name;

    @NotNull(message = "Thương hiệu không được để trống")
    Long brandId;

    @NotNull(message = "Danh mục không được để trống")
    Long categoryId;

    @NotBlank(message = "Mô tả sản phẩm không được để trống")
    String description;

    /**
     * Danh sách URL ảnh từ Cloudinary
     * Frontend upload ảnh lên /api/images/upload, nhận về imageUrl,
     * sau đó gửi danh sách URL này trong request
     */
    List<String> imageUrls;

    @Builder.Default
    Boolean active = true;
}