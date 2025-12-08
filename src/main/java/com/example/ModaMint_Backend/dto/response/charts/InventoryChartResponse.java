package com.example.ModaMint_Backend.dto.response.charts;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Response DTO for Inventory Chart
 * Endpoint: GET /api/charts/inventory
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryChartResponse {
    String productName;
    Integer stockQty;
    Integer minQty;
}
