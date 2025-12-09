package com.example.ModaMint_Backend.dto.response.productvariant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for Variant Matrix (Heatmap data)
 * Contains color, size, and quantity for dashboard visualization
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VariantMatrixResponse {
    private String color;     // Màu sắc, ví dụ: "Đen", "Trắng", "Xanh"
    private String size;      // Kích thước, ví dụ: "S", "M", "L", "XL", "XXL"
    private Integer quantity; // Tổng số lượng tồn kho cho combo color-size này
}
