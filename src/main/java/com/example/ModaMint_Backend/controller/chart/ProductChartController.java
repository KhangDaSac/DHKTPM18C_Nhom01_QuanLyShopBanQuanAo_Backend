package com.example.ModaMint_Backend.controller.chart;

import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.charts.BestProductChartResponse;
import com.example.ModaMint_Backend.dto.response.charts.InventoryProductChartResponse;
import com.example.ModaMint_Backend.dto.response.charts.InventoryCategoryChartResponse;
import com.example.ModaMint_Backend.service.chart.BestProductChartService;
import com.example.ModaMint_Backend.service.chart.InventoryChartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST Controller for Product-related Charts
 * Base Path: /api/charts/products
 * 
 * Endpoints:
 * - GET /api/charts/products/top-selling - Best selling products
 * - GET /api/charts/products/inventory - Overall inventory status
 * - GET /api/charts/products/inventory/by-category - Inventory grouped by category
 * 
 * FIXED: Consolidated product-related endpoints into single controller
 * to avoid conflicts with main ProductController (/products/{id})
 */
@RestController
@RequestMapping("/api/charts/products")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ProductChartController {

    private final BestProductChartService bestProductService;
    private final InventoryChartService inventoryService;

    /**
     * Get top selling products
     * URL: GET /api/charts/products/top-selling
     * 
     * Example Response:
     * [
     *   {
     *     "productName": "Áo sơ mi nam",
     *     "qtySold": 150,
     *     "revenue": 4500000.00
     *   }
     * ]
     * 
     * @param dateFrom Start date (optional)
     * @param dateTo End date (optional)
     * @param limit Maximum products to return (default 10)
     * @return List of best selling products
     */
    @GetMapping("/top-selling")
    public ResponseEntity<ApiResponse<List<BestProductChartResponse>>> getTopSellingProducts(
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
            LocalDateTime dateFrom,
            
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
            LocalDateTime dateTo,
            
            @RequestParam(defaultValue = "10") 
            int limit) {
        
        log.info("GET /api/charts/products/top-selling - dateFrom: {}, dateTo: {}, limit: {}", 
                dateFrom, dateTo, limit);
        
        List<BestProductChartResponse> data = bestProductService.getBestProducts(dateFrom, dateTo, limit);
        return ResponseEntity.ok(ApiResponse.<List<BestProductChartResponse>>builder()
                .result(data)
                .message("Lấy danh sách sản phẩm bán chạy thành công")
                .build());
    }

    /**
     * Get inventory status for all products
     * URL: GET /api/charts/products/inventory
     * 
     * Example Response:
     * [
     *   {
     *     "productName": "Áo khoác da",
     *     "stockQty": 5,
     *     "minQty": 10
     *   }
     * ]
     * 
     * @param limit Maximum products to return (default 50)
     * @param lowStockOnly If true, only return low stock items (default false)
     * @return List of products with inventory status
     */
    @GetMapping("/inventory")
    public ResponseEntity<ApiResponse<List<InventoryProductChartResponse>>> getInventoryStatus(
            @RequestParam(defaultValue = "50") int limit,
            @RequestParam(defaultValue = "false") boolean lowStockOnly) {
        
        log.info("GET /api/charts/products/inventory - limit: {}, lowStockOnly: {}", 
                limit, lowStockOnly);
        
        List<InventoryProductChartResponse> data = inventoryService.getInventoryData(limit, lowStockOnly);
        return ResponseEntity.ok(ApiResponse.<List<InventoryProductChartResponse>>builder()
                .result(data)
                .message("Lấy tình trạng kho hàng thành công")
                .build());
    }

    /**
     * Get inventory status grouped by category
     * URL: GET /api/charts/products/inventory/by-category
     * 
     * This endpoint can be enhanced to group inventory by product category
     * Currently returns same data as /inventory but can be extended
     * 
     * @param limit Maximum products to return (default 50)
     * @param lowStockOnly If true, only return low stock items (default false)
     * @return List of products with inventory status (can be enhanced to group by category)
     */
    @GetMapping("/inventory/by-category")
    public ResponseEntity<ApiResponse<List<InventoryCategoryChartResponse>>> getInventoryByCategory(
            @RequestParam(defaultValue = "50") int limit,
            @RequestParam(defaultValue = "false") boolean lowStockOnly) {
        
        log.info("GET /api/charts/products/inventory/by-category - limit: {}, lowStockOnly: {}", 
                limit, lowStockOnly);
        
        List<InventoryCategoryChartResponse> data = inventoryService.getInventoryByCategory(limit, lowStockOnly);
        return ResponseEntity.ok(ApiResponse.<List<InventoryCategoryChartResponse>>builder()
                .result(data)
                .message("Lấy kho hàng theo danh mục thành công")
                .build());
    }
}
