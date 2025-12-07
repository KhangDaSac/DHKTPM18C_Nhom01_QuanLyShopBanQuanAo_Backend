package com.example.ModaMint_Backend.dto.response.charts;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;

/**
 * Response DTO for Sales Chart
 * Endpoint: GET /api/charts/sales
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SalesChartResponse {
    BigDecimal totalRevenue;
    Long totalOrders;
    BigDecimal avgOrderValue;
}
