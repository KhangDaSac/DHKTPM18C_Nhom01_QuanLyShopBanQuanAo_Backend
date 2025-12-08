package com.example.ModaMint_Backend.service.chart;

import com.example.ModaMint_Backend.dto.response.charts.BestProductChartResponse;
import com.example.ModaMint_Backend.repository.chart.BestProductChartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for Best Products Chart
 * Handles business logic for top selling products
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BestProductChartService {

    private final BestProductChartRepository bestProductRepository;
    private static final int DEFAULT_LIMIT = 10;

    /**
     * Get best selling products ordered by revenue
     * @param dateFrom Start date filter (nullable)
     * @param dateTo End date filter (nullable)
     * @param limit Maximum products to return (default 10)
     * @return List of best selling products
     */
    public List<BestProductChartResponse> getBestProducts(
            LocalDateTime dateFrom, 
            LocalDateTime dateTo,
            Integer limit) {
        
        log.info("Fetching best products from {} to {}, limit: {}", dateFrom, dateTo, limit);

        // Validate date range
        validateDateRange(dateFrom, dateTo);

        // Set default limit if not provided
        int pageSize = (limit != null && limit > 0) ? limit : DEFAULT_LIMIT;
        Pageable pageable = PageRequest.of(0, pageSize);

        List<Object[]> results = bestProductRepository.findBestSellingProducts(
                dateFrom, dateTo, pageable);

        List<BestProductChartResponse> products = results.stream()
                .map(row -> BestProductChartResponse.builder()
                        .productName((String) row[0])
                        .qtySold(((Number) row[1]).longValue())
                        .revenue((BigDecimal) row[2])
                        .build())
                .collect(Collectors.toList());

        log.info("Found {} best selling products", products.size());
        return products;
    }

    private void validateDateRange(LocalDateTime dateFrom, LocalDateTime dateTo) {
        if (dateFrom != null && dateTo != null && dateFrom.isAfter(dateTo)) {
            throw new IllegalArgumentException("dateFrom cannot be after dateTo");
        }
    }
}
