package com.example.ModaMint_Backend.dto.response.charts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromotionUsageHistoryResponse {
    private LocalDate date;
    private long count;
}
