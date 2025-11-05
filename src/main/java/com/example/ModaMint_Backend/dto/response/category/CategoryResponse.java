package com.example.ModaMint_Backend.dto.response.category;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {
    Long id;
    String name;
    Boolean isActive;
    Integer productCount; // Số lượng sản phẩm trong danh mục
    Long parentId;
}
