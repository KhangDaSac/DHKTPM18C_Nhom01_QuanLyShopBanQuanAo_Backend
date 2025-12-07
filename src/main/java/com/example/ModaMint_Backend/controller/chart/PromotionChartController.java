package com.example.ModaMint_Backend.controller.chart;

import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.charts.PromotionChartResponse;
import com.example.ModaMint_Backend.service.chart.PromotionChartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST Controller for Promotion Chart
 * Endpoint: GET /api/charts/promotions
 * 
 * Example Response:
 * [
 *   {
 *     "promotionName": "Flash Sale 12.12",
 *     "discountPercent": 20.0,
 *     "ordersApplied": 230
 *   },
 *   {
 *     "promotionName": "Giảm 100K cho đơn 500K",
 *     "discountPercent": 100000.0,
 *     "ordersApplied": 180
 *   },
 *   {
 *     "promotionName": "Khách hàng thân thiết",
 *     "discountPercent": 15.0,
 *     "ordersApplied": 95
 *   }
 * ]
 * 
 * Frontend Usage:
 * - Promotion effectiveness chart
 * - Marketing campaign performance
 * - Discount usage analytics
 */
@RestController
@RequestMapping("/api/charts")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class PromotionChartController {

    private final PromotionChartService promotionService;

    /**
     * Get promotion statistics
     * @param dateFrom Start date in format yyyy-MM-dd'T'HH:mm:ss (optional)
     * @param dateTo End date in format yyyy-MM-dd'T'HH:mm:ss (optional)
     * @param limit Maximum promotions to return (default 20)
     * @return List of promotions with usage statistics
     */
    @GetMapping("/promotions")
    public ResponseEntity<ApiResponse<List<PromotionChartResponse>>> getPromotionsChart(
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
            LocalDateTime dateFrom,
            
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
            LocalDateTime dateTo,
            
            @RequestParam(required = false, defaultValue = "20") 
            Integer limit) {
        
        log.info("GET /api/charts/promotions - dateFrom: {}, dateTo: {}, limit: {}", 
                dateFrom, dateTo, limit);
        
        List<PromotionChartResponse> data = promotionService.getPromotionData(
                dateFrom, dateTo, limit);
        return ResponseEntity.ok(ApiResponse.<List<PromotionChartResponse>>builder()
                .result(data)
                .message("Lấy dữ liệu khuyến mãi thành công")
                .build());
    }
}
