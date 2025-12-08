package com.example.ModaMint_Backend.service.chart;

import com.example.ModaMint_Backend.dto.response.charts.PromotionChartResponse;
import com.example.ModaMint_Backend.repository.chart.PromotionChartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for Promotion Chart
 * Handles business logic for promotion effectiveness tracking
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PromotionChartService {

    private final PromotionChartRepository promotionRepository;
    private static final int DEFAULT_LIMIT = 20;

    /**
     * Get promotion statistics combining percent and amount promotions
     * @param dateFrom Start date filter (nullable)
     * @param dateTo End date filter (nullable)
     * @param limit Maximum promotions to return (default 20)
     * @return List of promotions with usage statistics
     */
    public List<PromotionChartResponse> getPromotionData(
            LocalDateTime dateFrom, 
            LocalDateTime dateTo,
            Integer limit) {
        
        log.info("Fetching promotion data from {} to {}, limit: {}", dateFrom, dateTo, limit);

        // Validate date range
        validateDateRange(dateFrom, dateTo);

        // Set default limit if not provided
        int pageSize = (limit != null && limit > 0) ? limit : DEFAULT_LIMIT;
        Pageable pageable = PageRequest.of(0, pageSize);

        // Get percent promotions
        List<Object[]> percentResults = promotionRepository.findPercentPromotionStats(
                dateFrom, dateTo, pageable);
        
        List<PromotionChartResponse> percentPromotions = percentResults.stream()
                .map(row -> PromotionChartResponse.builder()
                        .promotionName((String) row[0])
                        .discountPercent(BigDecimal.valueOf(((Number) row[1]).doubleValue()))
                        .ordersApplied(((Number) row[2]).longValue())
                        .build())
                .collect(Collectors.toList());

        // Get amount promotions (convert amount to percentage for display)
        List<Object[]> amountResults = promotionRepository.findAmountPromotionStats(
                dateFrom, dateTo, pageable);
        
        List<PromotionChartResponse> amountPromotions = amountResults.stream()
                .map(row -> PromotionChartResponse.builder()
                        .promotionName((String) row[0])
                        .discountPercent(BigDecimal.valueOf(((Number) row[1]).doubleValue()))
                        .ordersApplied(((Number) row[2]).longValue())
                        .build())
                .collect(Collectors.toList());

        // Combine and sort by usage
        List<PromotionChartResponse> allPromotions = new ArrayList<>();
        allPromotions.addAll(percentPromotions);
        allPromotions.addAll(amountPromotions);

        List<PromotionChartResponse> sortedPromotions = allPromotions.stream()
                .sorted((a, b) -> b.getOrdersApplied().compareTo(a.getOrdersApplied()))
                .limit(pageSize)
                .collect(Collectors.toList());

        log.info("Found {} promotions", sortedPromotions.size());
        return sortedPromotions;
    }

    private void validateDateRange(LocalDateTime dateFrom, LocalDateTime dateTo) {
        if (dateFrom != null && dateTo != null && dateFrom.isAfter(dateTo)) {
            throw new IllegalArgumentException("dateFrom cannot be after dateTo");
        }
    }
}
