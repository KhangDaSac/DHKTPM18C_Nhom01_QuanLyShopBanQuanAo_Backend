package com.example.ModaMint_Backend.dto.response.charts;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;

/**
 * Response DTO for Promotion Performance Chart
 * Endpoint: GET /api/charts/promotions
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionChartResponse {
    String promotionName;
    BigDecimal discountPercent;
    Long ordersApplied;
}
