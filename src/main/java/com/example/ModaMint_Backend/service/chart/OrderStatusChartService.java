package com.example.ModaMint_Backend.service.chart;

import com.example.ModaMint_Backend.dto.response.charts.OrderStatusChartResponse;
import com.example.ModaMint_Backend.repository.chart.OrderStatusChartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for Order Status Chart
 * Handles business logic for order status distribution
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderStatusChartService {

    private final OrderStatusChartRepository orderStatusRepository;

    /**
     * Get order status distribution
     * @param dateFrom Start date filter (nullable)
     * @param dateTo End date filter (nullable)
     * @return List of order statuses with counts
     */
    public List<OrderStatusChartResponse> getOrderStatusData(
            LocalDateTime dateFrom, 
            LocalDateTime dateTo) {
        
        log.info("Fetching order status data from {} to {}", dateFrom, dateTo);

        // Validate date range
        validateDateRange(dateFrom, dateTo);

        List<Object[]> results = orderStatusRepository.findOrderStatusDistribution(
                dateFrom, dateTo);

        List<OrderStatusChartResponse> statusList = results.stream()
                .map(row -> OrderStatusChartResponse.builder()
                        .status(row[0].toString())
                        .total(((Number) row[1]).longValue())
                        .build())
                .collect(Collectors.toList());

        log.info("Found {} order status categories", statusList.size());
        return statusList;
    }

    private void validateDateRange(LocalDateTime dateFrom, LocalDateTime dateTo) {
        if (dateFrom != null && dateTo != null && dateFrom.isAfter(dateTo)) {
            throw new IllegalArgumentException("dateFrom cannot be after dateTo");
        }
    }
}
