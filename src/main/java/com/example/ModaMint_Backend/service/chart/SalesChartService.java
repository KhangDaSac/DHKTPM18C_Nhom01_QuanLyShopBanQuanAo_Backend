package com.example.ModaMint_Backend.service.chart;

import com.example.ModaMint_Backend.dto.response.charts.SalesChartResponse;
import com.example.ModaMint_Backend.repository.chart.SalesChartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * Service for Sales Chart
 * Handles business logic for revenue calculations
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class SalesChartService {

    private final SalesChartRepository salesChartRepository;

    /**
     * Get sales overview statistics
     * @param dateFrom Start date filter (nullable)
     * @param dateTo End date filter (nullable)
     * @return Sales statistics with revenue, order count, and average
     */
    public SalesChartResponse getSalesData(LocalDateTime dateFrom, LocalDateTime dateTo) {
        log.info("Fetching sales data from {} to {}", dateFrom, dateTo);

        // Validate date range
        validateDateRange(dateFrom, dateTo);

        BigDecimal totalRevenue = salesChartRepository.calculateTotalRevenue(dateFrom, dateTo);
        Long totalOrders = salesChartRepository.countTotalOrders(dateFrom, dateTo);
        BigDecimal avgOrderValue = salesChartRepository.calculateAvgOrderValue(dateFrom, dateTo);

        // Round to 2 decimal places for currency
        totalRevenue = totalRevenue.setScale(2, RoundingMode.HALF_UP);
        avgOrderValue = avgOrderValue.setScale(2, RoundingMode.HALF_UP);

        log.info("Sales data - Revenue: {}, Orders: {}, Avg: {}", 
                totalRevenue, totalOrders, avgOrderValue);

        return SalesChartResponse.builder()
                .totalRevenue(totalRevenue)
                .totalOrders(totalOrders)
                .avgOrderValue(avgOrderValue)
                .build();
    }

    private void validateDateRange(LocalDateTime dateFrom, LocalDateTime dateTo) {
        if (dateFrom != null && dateTo != null && dateFrom.isAfter(dateTo)) {
            throw new IllegalArgumentException("dateFrom cannot be after dateTo");
        }
    }
}
