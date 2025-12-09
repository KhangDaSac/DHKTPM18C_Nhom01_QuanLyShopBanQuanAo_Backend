package com.example.ModaMint_Backend.dto.response.charts;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DailySalesPoint {
    String date; // yyyy-MM-dd
    BigDecimal revenue;
    Long orders;
}
