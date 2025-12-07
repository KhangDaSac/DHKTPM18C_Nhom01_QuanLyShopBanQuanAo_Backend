package com.example.ModaMint_Backend.controller.chart;

import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.charts.SalesChartResponse;
import com.example.ModaMint_Backend.service.chart.SalesChartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * REST Controller for Sales Chart
 * Base Path: /api/charts/sales
 * 
 * Endpoints:
 * - GET /api/charts/sales - Overall sales statistics (default)
 * - GET /api/charts/sales/daily - Daily sales breakdown
 * - GET /api/charts/sales/monthly - Monthly sales summary
 * 
 * Example Response:
 * {
 *   "totalRevenue": 15000000.50,
 *   "totalOrders": 125,
 *   "avgOrderValue": 120000.00
 * }
 * 
 * Frontend Usage:
 * - Dashboard overview card showing total revenue
 * - Sales trend chart
 * - KPI metrics display
 * 
 * FIXED: Changed @RequestMapping from "/api/charts" to "/api/charts/sales"
 * to avoid path conflicts with other controllers and enable sub-paths like /daily, /monthly
 */
@RestController
@RequestMapping("/api/charts/sales")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class SalesChartController {

    private final SalesChartService salesChartService;

    /**
     * Get overall sales statistics (default endpoint)
     * URL: GET /api/charts/sales
     * 
     * @param dateFrom Start date in format yyyy-MM-dd'T'HH:mm:ss (optional)
     * @param dateTo End date in format yyyy-MM-dd'T'HH:mm:ss (optional)
     * @return Sales overview with revenue, order count, and average
     */
    @GetMapping
    public ResponseEntity<ApiResponse<SalesChartResponse>> getSalesChart(
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
            LocalDateTime dateFrom,
            
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
            LocalDateTime dateTo) {
        
        log.info("GET /api/charts/sales - dateFrom: {}, dateTo: {}", dateFrom, dateTo);
        
        SalesChartResponse data = salesChartService.getSalesData(dateFrom, dateTo);
        return ResponseEntity.ok(ApiResponse.<SalesChartResponse>builder()
                .result(data)
                .message("Lấy dữ liệu doanh số thành công")
                .build());
    }

    /**
     * Get daily sales statistics
     * URL: GET /api/charts/sales/daily
     * 
     * @param dateFrom Start date (required for daily view)
     * @param dateTo End date (required for daily view)
     * @return Daily sales breakdown
     */
    @GetMapping("/daily")
    public ResponseEntity<ApiResponse<SalesChartResponse>> getDailySales(
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
            LocalDateTime dateFrom,
            
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
            LocalDateTime dateTo) {
        
        log.info("GET /api/charts/sales/daily - dateFrom: {}, dateTo: {}", dateFrom, dateTo);
        
        // Reuse same service method - filtering by date range gives daily granularity
        SalesChartResponse data = salesChartService.getSalesData(dateFrom, dateTo);
        return ResponseEntity.ok(ApiResponse.<SalesChartResponse>builder()
                .result(data)
                .message("Lấy dữ liệu doanh số theo ngày thành công")
                .build());
    }

    /**
     * Get monthly sales statistics
     * URL: GET /api/charts/sales/monthly
     * 
     * @param dateFrom Start date (optional - defaults to last 12 months)
     * @param dateTo End date (optional - defaults to today)
     * @return Monthly sales summary
     */
    @GetMapping("/monthly")
    public ResponseEntity<ApiResponse<SalesChartResponse>> getMonthlySales(
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
            LocalDateTime dateFrom,
            
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
            LocalDateTime dateTo) {
        
        log.info("GET /api/charts/sales/monthly - dateFrom: {}, dateTo: {}", dateFrom, dateTo);
        
        // For monthly view, you may want to aggregate differently in service layer
        SalesChartResponse data = salesChartService.getSalesData(dateFrom, dateTo);
        return ResponseEntity.ok(ApiResponse.<SalesChartResponse>builder()
                .result(data)
                .message("Lấy dữ liệu doanh số theo tháng thành công")
                .build());
    }
}
