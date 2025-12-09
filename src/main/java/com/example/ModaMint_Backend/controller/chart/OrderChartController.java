package com.example.ModaMint_Backend.controller.chart;

import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.charts.OrderStatusChartResponse;
import com.example.ModaMint_Backend.service.chart.OrderStatusChartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST Controller for Order-related Charts
 * Base Path: /api/charts/orders
 * 
 * Endpoints:
 * - GET /api/charts/orders/status-summary - Order status distribution
 * - GET /api/charts/orders/stats/daily - Daily order statistics (future)
 * - GET /api/charts/orders/stats/monthly - Monthly order statistics (future)
 * 
 * Example Response:
 * [
 *   {
 *     "status": "DELIVERED",
 *     "total": 450
 *   },
 *   {
 *     "status": "PENDING",
 *     "total": 125
 *   }
 * ]
 * 
 * FIXED: Changed base path from "/api/charts" to "/api/charts/orders"
 * to enable sub-paths like /status-summary, /stats/daily, /stats/monthly
 */
@RestController
@RequestMapping("/api/charts/orders")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class OrderChartController {

    private final OrderStatusChartService orderStatusService;

    /**
     * Get order status distribution summary
     * URL: GET /api/charts/orders/status-summary
     * 
     * Returns count of orders grouped by status (PENDING, DELIVERED, SHIPPING, CANCELLED)
     * 
     * Frontend Usage:
     * - Pie chart showing order status distribution
     * - Order fulfillment dashboard
     * - Status breakdown bar chart
     * 
     * @param dateFrom Start date (optional)
     * @param dateTo End date (optional)
     * @return List of order status counts
     */
    @GetMapping("/status-summary")
    public ResponseEntity<ApiResponse<List<OrderStatusChartResponse>>> getOrderStatusSummary(
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
            LocalDateTime dateFrom,
            
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
            LocalDateTime dateTo) {
        
        log.info("GET /api/charts/orders/status-summary - dateFrom: {}, dateTo: {}", 
                dateFrom, dateTo);
        
        List<OrderStatusChartResponse> data = orderStatusService.getOrderStatusData(dateFrom, dateTo);
        return ResponseEntity.ok(ApiResponse.<List<OrderStatusChartResponse>>builder()
                .result(data)
                .message("Lấy thống kê trạng thái đơn hàng thành công")
                .build());
    }

    /**
     * Get daily order statistics
     * URL: GET /api/charts/orders/stats/daily
     * 
     * Future enhancement: Can return daily order counts, revenue, etc.
     * Currently uses same data as status-summary
     * 
     * @param dateFrom Start date (optional)
     * @param dateTo End date (optional)
     * @return List of order statistics
     */
    @GetMapping("/stats/daily")
    public ResponseEntity<ApiResponse<List<OrderStatusChartResponse>>> getDailyOrderStats(
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
            LocalDateTime dateFrom,
            
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
            LocalDateTime dateTo) {
        
        log.info("GET /api/charts/orders/stats/daily - dateFrom: {}, dateTo: {}", 
                dateFrom, dateTo);
        
        // TODO: Implement daily aggregation logic in service layer
        List<OrderStatusChartResponse> data = orderStatusService.getOrderStatusData(dateFrom, dateTo);
        return ResponseEntity.ok(ApiResponse.<List<OrderStatusChartResponse>>builder()
                .result(data)
                .message("Lấy thống kê đơn hàng theo ngày thành công")
                .build());
    }

    /**
     * Get monthly order statistics
     * URL: GET /api/charts/orders/stats/monthly
     * 
     * Future enhancement: Can return monthly order trends
     * Currently uses same data as status-summary
     * 
     * @param dateFrom Start date (optional)
     * @param dateTo End date (optional)
     * @return List of order statistics
     */
    @GetMapping("/stats/monthly")
    public ResponseEntity<ApiResponse<List<OrderStatusChartResponse>>> getMonthlyOrderStats(
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
            LocalDateTime dateFrom,
            
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
            LocalDateTime dateTo) {
        
        log.info("GET /api/charts/orders/stats/monthly - dateFrom: {}, dateTo: {}", 
                dateFrom, dateTo);
        
        // TODO: Implement monthly aggregation logic in service layer
        List<OrderStatusChartResponse> data = orderStatusService.getOrderStatusData(dateFrom, dateTo);
        return ResponseEntity.ok(ApiResponse.<List<OrderStatusChartResponse>>builder()
                .result(data)
                .message("Lấy thống kê đơn hàng theo tháng thành công")
                .build());
    }
}
