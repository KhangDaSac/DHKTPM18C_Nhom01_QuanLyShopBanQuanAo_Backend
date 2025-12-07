package com.example.ModaMint_Backend.dto.response.charts;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Response DTO for Product Variant (SKU) Chart
 * Endpoint: GET /api/charts/variants
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VariantChartResponse {
    String productName;
    String variantName;
    String sku;
    Integer stockQty;
}
