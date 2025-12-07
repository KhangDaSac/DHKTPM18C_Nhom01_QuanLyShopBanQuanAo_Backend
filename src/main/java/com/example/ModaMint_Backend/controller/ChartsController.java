package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.service.ChartsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

/**
 * Charts Controller
 * API endpoints for dashboard analytics charts
 * 
 * Endpoints:
 * - GET /api/charts/sales/daily - Doanh số theo ngày (30 ngày gần nhất)
 * - GET /api/charts/sales/monthly - Doanh số theo tháng (12 tháng gần nhất)
 * - GET /api/charts/top-products - Top sản phẩm bán chạy
 * - GET /api/charts/inventory - Phân tích tồn kho
 * - GET /api/charts/order-status - Trạng thái đơn hàng
 * - GET /api/charts/customers - Phân tích khách hàng
 */
@RestController
@RequestMapping("/api/charts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ChartsController {

    private final ChartsService chartsService;

    /**
     * GET /api/charts/sales/daily
     * Lấy dữ liệu doanh số theo ngày (30 ngày gần nhất)
     * 
     * Response:
     * [
     *   {
     *     "date": "2025-12-01",
     *     "revenue": 12500000,
     *     "orders": 45
     *   },
     *   ...
     * ]
     */
    @GetMapping("/sales/daily")
    public ResponseEntity<?> getDailySales(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        try {
            // Mặc định lấy 30 ngày gần nhất
            if (startDate == null) {
                startDate = LocalDate.now().minusDays(30);
            }
            if (endDate == null) {
                endDate = LocalDate.now();
            }

            return ResponseEntity.ok(chartsService.getDailySales(startDate, endDate));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Lỗi khi lấy dữ liệu doanh số theo ngày",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * GET /api/charts/sales/monthly
     * Lấy dữ liệu doanh số theo tháng (12 tháng gần nhất)
     * 
     * Response:
     * [
     *   {
     *     "month": "2025-11",
     *     "revenue": 250000000,
     *     "orders": 680
     *   },
     *   ...
     * ]
     */
    @GetMapping("/sales/monthly")
    public ResponseEntity<?> getMonthlySales(
            @RequestParam(required = false) Integer months
    ) {
        try {
            // Mặc định lấy 12 tháng
            if (months == null) {
                months = 12;
            }

            return ResponseEntity.ok(chartsService.getMonthlySales(months));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Lỗi khi lấy dữ liệu doanh số theo tháng",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * GET /api/charts/top-products
     * Lấy danh sách top sản phẩm bán chạy
     * 
     * Response:
     * [
     *   {
     *     "productId": 1,
     *     "productName": "Áo Thun Nam",
     *     "sold": 150,
     *     "revenue": 45000000,
     *     "image": "url"
     *   },
     *   ...
     * ]
     */
    @GetMapping("/top-products")
    public ResponseEntity<?> getTopProducts(
            @RequestParam(required = false, defaultValue = "10") Integer limit
    ) {
        try {
            return ResponseEntity.ok(chartsService.getTopProducts(limit));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Lỗi khi lấy top sản phẩm bán chạy",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * GET /api/charts/inventory
     * Phân tích tồn kho theo danh mục
     * 
     * Response:
     * {
     *   "byCategory": [
     *     {
     *       "categoryName": "Áo",
     *       "totalStock": 500,
     *       "totalValue": 150000000
     *     }
     *   ],
     *   "lowStock": [
     *     {
     *       "productId": 1,
     *       "productName": "Áo Thun Nam",
     *       "stock": 5
     *     }
     *   ]
     * }
     */
    @GetMapping("/inventory")
    public ResponseEntity<?> getInventoryAnalytics() {
        try {
            return ResponseEntity.ok(chartsService.getInventoryAnalytics());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Lỗi khi lấy dữ liệu tồn kho",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * GET /api/charts/order-status
     * Phân tích trạng thái đơn hàng
     * 
     * Response:
     * [
     *   {
     *     "status": "PENDING",
     *     "statusName": "Chờ xử lý",
     *     "count": 25,
     *     "totalValue": 75000000
     *   },
     *   ...
     * ]
     */
    @GetMapping("/order-status")
    public ResponseEntity<?> getOrderStatusAnalytics() {
        try {
            return ResponseEntity.ok(chartsService.getOrderStatusAnalytics());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Lỗi khi lấy dữ liệu trạng thái đơn hàng",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * GET /api/charts/customers
     * Phân tích khách hàng
     * 
     * Response:
     * {
     *   "newCustomersDaily": [...],
     *   "topSpenders": [...],
     *   "segmentation": {
     *     "vip": 50,
     *     "regular": 200,
     *     "new": 100
     *   }
     * }
     */
    @GetMapping("/customers")
    public ResponseEntity<?> getCustomerAnalytics() {
        try {
            return ResponseEntity.ok(chartsService.getCustomerAnalytics());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Lỗi khi lấy dữ liệu khách hàng",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * GET /api/charts/promotions
     * Phân tích khuyến mãi
     * 
     * Response:
     * {
     *   "usageByType": [...],
     *   "topUsed": [...],
     *   "statusBreakdown": [...]
     * }
     */
    @GetMapping("/promotions")
    public ResponseEntity<?> getPromotionAnalytics() {
        try {
            return ResponseEntity.ok(chartsService.getPromotionAnalytics());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Lỗi khi lấy dữ liệu khuyến mãi",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * GET /api/charts/variant-matrix
     * Ma trận biến thể sản phẩm (Color x Size)
     * 
     * Response:
     * {
     *   "colors": ["Đỏ", "Xanh", "Vàng"],
     *   "sizes": ["S", "M", "L", "XL"],
     *   "data": [
     *     {
     *       "color": "Đỏ",
     *       "S": 10,
     *       "M": 15,
     *       "L": 20,
     *       "XL": 8
     *     }
     *   ]
     * }
     */
    @GetMapping("/variant-matrix")
    public ResponseEntity<?> getVariantMatrix() {
        try {
            return ResponseEntity.ok(chartsService.getVariantMatrix());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Lỗi khi lấy ma trận biến thể",
                "message", e.getMessage()
            ));
        }
    }
}
