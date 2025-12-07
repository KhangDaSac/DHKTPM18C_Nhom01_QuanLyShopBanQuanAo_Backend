package com.example.ModaMint_Backend.service.chart;

import com.example.ModaMint_Backend.dto.response.charts.VariantChartResponse;
import com.example.ModaMint_Backend.repository.chart.VariantChartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for Product Variant Chart
 * Handles business logic for variant-level stock monitoring
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class VariantChartService {

    private final VariantChartRepository variantRepository;
    private static final int DEFAULT_LIMIT = 100;

    /**
     * Get product variant stock status
     * @param limit Maximum variants to return (default 100)
     * @param lowStockOnly If true, only return variants with quantity < 5
     * @return List of variants with stock information
     */
    public List<VariantChartResponse> getVariantData(Integer limit, Boolean lowStockOnly) {
        log.info("Fetching variant data, limit: {}, lowStockOnly: {}", limit, lowStockOnly);

        // Set default limit if not provided
        int pageSize = (limit != null && limit > 0) ? limit : DEFAULT_LIMIT;
        Pageable pageable = PageRequest.of(0, pageSize);

        List<Object[]> results;
        if (Boolean.TRUE.equals(lowStockOnly)) {
            results = variantRepository.findLowStockVariants(pageable);
        } else {
            results = variantRepository.findAllVariantStock(pageable);
        }

        List<VariantChartResponse> variants = results.stream()
                .map(row -> VariantChartResponse.builder()
                        .productName((String) row[0])
                        .variantName((String) row[1])
                        .sku((String) row[2])
                        .stockQty(((Number) row[3]).intValue())
                        .build())
                .collect(Collectors.toList());

        log.info("Found {} variants", variants.size());
        return variants;
    }
}
