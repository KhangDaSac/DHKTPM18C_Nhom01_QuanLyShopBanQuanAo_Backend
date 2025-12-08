package com.example.ModaMint_Backend.dto.request.category;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryRequest {

    @NotBlank(message = "Tên danh mục không được để trống")
    String name;

    @Builder.Default
    Boolean isActive = true;

    // ID danh mục cha (có thể null nếu là danh mục cấp 1)
    Long parentId;
    
    /**
     * URL ảnh của category từ Cloudinary
     */
    String image;
}
