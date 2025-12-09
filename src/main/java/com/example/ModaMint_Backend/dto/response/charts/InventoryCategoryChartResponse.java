package com.example.ModaMint_Backend.dto.response.charts;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryCategoryChartResponse {
    String categoryName;
    Integer totalQuantity;
    Double percentage;
}
