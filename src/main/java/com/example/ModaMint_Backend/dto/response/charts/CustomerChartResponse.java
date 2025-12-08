package com.example.ModaMint_Backend.dto.response.charts;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Response DTO for Customer Chart
 * Endpoint: GET /api/charts/customers
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerChartResponse {
    Long totalCustomers;
    Long newCustomers;
}
