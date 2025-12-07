package com.example.ModaMint_Backend.dto.response.charts;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;

/**
 * Response DTO for Best Selling Products Chart
 * Endpoint: GET /api/charts/best-products
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BestProductChartResponse {
    String productName;
    Long qtySold;
    BigDecimal revenue;
}
