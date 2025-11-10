package com.example.ModaMint_Backend.dto.response.productvariant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantColorResponse {
    private String color;  // Tên color, ví dụ "Trắng"
    private Long count;    // Số lượng variants có color này (để biết độ phổ biến)
}
