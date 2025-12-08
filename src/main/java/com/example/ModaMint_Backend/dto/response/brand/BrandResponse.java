package com.example.ModaMint_Backend.dto.response.brand;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandResponse {
    Long id;
    String name;
    String description;
    
    /**
     * URL ảnh của brand từ Cloudinary
     */
    String image;
    
    Boolean active;
    Integer productCount; // Số lượng sản phẩm của thương hiệu
}
