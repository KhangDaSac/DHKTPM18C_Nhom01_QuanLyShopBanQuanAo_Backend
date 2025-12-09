package com.example.ModaMint_Backend.service.chart;

import com.example.ModaMint_Backend.dto.response.charts.InventoryCategoryChartResponse;
import com.example.ModaMint_Backend.dto.response.charts.InventoryProductChartResponse;
import com.example.ModaMint_Backend.repository.chart.InventoryChartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for Inventory Chart
 * Handles business logic for stock level monitoring
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class InventoryChartService {

    private final InventoryChartRepository inventoryRepository;
    private static final int DEFAULT_LIMIT = 50;

    /**
     * Get inventory status for products
     * @param limit Maximum products to return (default 50)
     * @param lowStockOnly If true, only return products below threshold
     * @return List of products with stock information
     */
    public List<InventoryProductChartResponse> getInventoryData(Integer limit, Boolean lowStockOnly) {
        log.info("Fetching inventory data, limit: {}, lowStockOnly: {}", limit, lowStockOnly);

        // Set default limit if not provided
        int pageSize = (limit != null && limit > 0) ? limit : DEFAULT_LIMIT;
        Pageable pageable = PageRequest.of(0, pageSize);

        List<Object[]> results;
        if (Boolean.TRUE.equals(lowStockOnly)) {
            results = inventoryRepository.findLowStockProducts(pageable);
        } else {
            results = inventoryRepository.findInventoryStatus(pageable);
        }

        List<InventoryProductChartResponse> inventory = results.stream()
            .map(row -> InventoryProductChartResponse.builder()
                .productId(((Number) row[0]).longValue())
                .productName((String) row[1])
                .quantity(((Number) row[2]).intValue())
                .categoryName((String) row[3])
                .build())
            .collect(Collectors.toList());

        log.info("Found {} products in inventory", inventory.size());
        return inventory;
    }

    /**
     * Get inventory aggregated by category with percentage share
     */
    public List<InventoryCategoryChartResponse> getInventoryByCategory(Integer limit, Boolean lowStockOnly) {
        log.info("Fetching inventory by category, limit: {}, lowStockOnly: {}", limit, lowStockOnly);

        int pageSize = (limit != null && limit > 0) ? limit : DEFAULT_LIMIT;
        Pageable pageable = PageRequest.of(0, pageSize);

        List<Object[]> results = inventoryRepository.findInventoryByCategory(pageable);

        // Compute total across categories
        int total = results.stream()
                .mapToInt(r -> ((Number) r[1]).intValue())
                .sum();

        List<InventoryCategoryChartResponse> categories = results.stream()
                .map(row -> {
                    String categoryName = (String) row[0];
                    int qty = ((Number) row[1]).intValue();
                    double pct = total > 0 ? (qty * 100.0) / total : 0.0;
                    return InventoryCategoryChartResponse.builder()
                            .categoryName(categoryName == null ? "Uncategorized" : categoryName)
                            .totalQuantity(qty)
                            .percentage(Math.round(pct * 100.0) / 100.0) // round to 2 decimals
                            .build();
                })
                .collect(Collectors.toList());

        log.info("Found {} categories for inventory", categories.size());
        return categories;
    }
}
