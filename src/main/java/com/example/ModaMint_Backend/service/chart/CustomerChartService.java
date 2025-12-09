package com.example.ModaMint_Backend.service.chart;

import com.example.ModaMint_Backend.dto.response.charts.CustomerChartResponse;
import com.example.ModaMint_Backend.repository.chart.CustomerChartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Service for Customer Chart
 * Handles business logic for customer statistics
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CustomerChartService {

    private final CustomerChartRepository customerRepository;

    /**
     * Get customer statistics
     * @param dateFrom Start date for new customers filter (nullable)
     * @param dateTo End date for new customers filter (nullable)
     * @return Customer statistics with total and new counts
     */
    public CustomerChartResponse getCustomerData(LocalDateTime dateFrom, LocalDateTime dateTo) {
        log.info("Fetching customer data from {} to {}", dateFrom, dateTo);

        // Validate date range
        validateDateRange(dateFrom, dateTo);

        Long totalCustomers = customerRepository.countTotalCustomers();
        Long newCustomers = customerRepository.countNewCustomers(dateFrom, dateTo);

        log.info("Customer data - Total: {}, New: {}", totalCustomers, newCustomers);

        return CustomerChartResponse.builder()
                .totalCustomers(totalCustomers)
                .newCustomers(newCustomers)
                .build();
    }

    private void validateDateRange(LocalDateTime dateFrom, LocalDateTime dateTo) {
        if (dateFrom != null && dateTo != null && dateFrom.isAfter(dateTo)) {
            throw new IllegalArgumentException("dateFrom cannot be after dateTo");
        }
    }
}
