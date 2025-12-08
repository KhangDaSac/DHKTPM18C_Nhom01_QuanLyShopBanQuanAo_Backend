package com.example.ModaMint_Backend.service.chart;

import com.example.ModaMint_Backend.dto.response.charts.InventoryChartResponse;
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
    public List<InventoryChartResponse> getInventoryData(Integer limit, Boolean lowStockOnly) {
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

        List<InventoryChartResponse> inventory = results.stream()
                .map(row -> InventoryChartResponse.builder()
                        .productName((String) row[0])
                        .stockQty(((Number) row[1]).intValue())
                        .minQty(((Number) row[2]).intValue())
                        .build())
                .collect(Collectors.toList());

        log.info("Found {} products in inventory", inventory.size());
        return inventory;
    }
}
