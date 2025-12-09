package com.example.ModaMint_Backend.controller.chart;

import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.charts.PromotionTopResponse;
import com.example.ModaMint_Backend.dto.response.charts.PromotionUsageHistoryResponse;
import com.example.ModaMint_Backend.service.chart.PromotionUsageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/charts/promotions")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class PromotionUsageChartController {

    private final PromotionUsageService promotionUsageService;

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<PromotionUsageHistoryResponse>>> getUsageHistory(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo) {

        log.info("GET /api/charts/promotions/history - from: {} to: {}", dateFrom, dateTo);
        List<PromotionUsageHistoryResponse> data = promotionUsageService.getUsageHistory(dateFrom, dateTo);
        return ResponseEntity.ok(ApiResponse.<List<PromotionUsageHistoryResponse>>builder()
                .result(data)
                .message("Lấy lịch sử sử dụng mã khuyến mãi theo ngày thành công")
                .build());
    }

    @GetMapping("/top")
    public ResponseEntity<ApiResponse<List<PromotionTopResponse>>> getTopPromotions(
            @RequestParam(defaultValue = "10") int limit) {

        log.info("GET /api/charts/promotions/top - limit: {}", limit);
        List<PromotionTopResponse> data = promotionUsageService.getTopPromotions(limit);
        return ResponseEntity.ok(ApiResponse.<List<PromotionTopResponse>>builder()
                .result(data)
                .message("Lấy top mã khuyến mãi được sử dụng nhiều nhất thành công")
                .build());
    }
}
