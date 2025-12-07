package com.example.ModaMint_Backend.dto.response.charts;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Response DTO for Order Status Distribution Chart
 * Endpoint: GET /api/charts/order-status
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderStatusChartResponse {
    String status;
    Long total;
}
