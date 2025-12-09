package com.example.ModaMint_Backend.dto.response.charts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromotionTopResponse {
    private String code;
    private long count;
}
