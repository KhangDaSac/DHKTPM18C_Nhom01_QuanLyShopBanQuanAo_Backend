package com.example.ModaMint_Backend.controller.chart;

import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.charts.VariantChartResponse;
import com.example.ModaMint_Backend.service.chart.VariantChartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Product Variant Chart
 * Endpoint: GET /api/charts/variants
 * 
 * Example Response:
 * [
 *   {
 *     "productName": "Áo thun cotton",
 *     "variantName": "XL - Đỏ",
 *     "sku": "PROD12-VAR45",
 *     "stockQty": 3
 *   },
 *   {
 *     "productName": "Quần short kaki",
 *     "variantName": "M - Xanh navy",
 *     "sku": "PROD23-VAR67",
 *     "stockQty": 7
 *   }
 * ]
 * 
 * Frontend Usage:
 * - Variant stock management table
 * - SKU-level inventory tracking
 * - Low stock variant alerts
 */
@RestController
@RequestMapping("/api/charts")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class VariantChartController {

    private final VariantChartService variantService;

    /**
     * Get product variant stock status
     * @param limit Maximum variants to return (default 100)
     * @param lowStockOnly If true, only return variants with quantity < 5 (default false)
     * @return List of variants with stock information
     */
    @GetMapping("/variants")
    public ResponseEntity<ApiResponse<List<VariantChartResponse>>> getVariantsChart(
            @RequestParam(required = false, defaultValue = "100") 
            Integer limit,
            
            @RequestParam(required = false, defaultValue = "false") 
            Boolean lowStockOnly) {
        
        log.info("GET /api/charts/variants - limit: {}, lowStockOnly: {}", limit, lowStockOnly);
        
        List<VariantChartResponse> data = variantService.getVariantData(
                limit, lowStockOnly);
        return ResponseEntity.ok(ApiResponse.<List<VariantChartResponse>>builder()
                .result(data)
                .message("Lấy dữ liệu variant thành công")
                .build());
    }
}
