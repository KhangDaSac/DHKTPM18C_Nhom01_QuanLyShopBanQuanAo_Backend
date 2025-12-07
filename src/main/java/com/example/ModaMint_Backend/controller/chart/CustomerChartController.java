package com.example.ModaMint_Backend.controller.chart;

import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.charts.CustomerChartResponse;
import com.example.ModaMint_Backend.service.chart.CustomerChartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * REST Controller for Customer Chart
 * Endpoint: GET /api/charts/customers
 * 
 * Example Response:
 * {
 *   "totalCustomers": 1250,
 *   "newCustomers": 85
 * }
 * 
 * Frontend Usage:
 * - Customer growth chart
 * - User acquisition metrics
 * - Customer dashboard KPI
 */
@RestController
@RequestMapping("/api/charts")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class CustomerChartController {

    private final CustomerChartService customerService;

    /**
     * Get customer statistics
     * @param dateFrom Start date for new customers in format yyyy-MM-dd'T'HH:mm:ss (optional)
     * @param dateTo End date for new customers in format yyyy-MM-dd'T'HH:mm:ss (optional)
     * @return Customer statistics with total and new customer counts
     */
    @GetMapping("/customers")
    public ResponseEntity<ApiResponse<CustomerChartResponse>> getCustomersChart(
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
            LocalDateTime dateFrom,
            
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
            LocalDateTime dateTo) {
        
        log.info("GET /api/charts/customers - dateFrom: {}, dateTo: {}", dateFrom, dateTo);
        
        CustomerChartResponse data = customerService.getCustomerData(dateFrom, dateTo);
        return ResponseEntity.ok(ApiResponse.<CustomerChartResponse>builder()
                .result(data)
                .message("Lấy dữ liệu khách hàng thành công")
                .build());
    }
}
