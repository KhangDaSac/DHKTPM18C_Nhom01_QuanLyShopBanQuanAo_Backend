package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.dto.request.promotion.PercentPromotionRequest;
import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.promotion.PercentPromotionResponse;
import com.example.ModaMint_Backend.service.PercentPromotionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/percentage-promotions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PercentPromotionController {
    PercentPromotionService percentPromotionService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<PercentPromotionResponse> createPercentagePromotion(@RequestBody @Valid PercentPromotionRequest request) {
        return ApiResponse.<PercentPromotionResponse>builder()
                .result(percentPromotionService.createPercentagePromotion(request))
                .message("Tạo khuyến mãi phần trăm mới thành công")
                .build();
    }

    @GetMapping
    public ApiResponse<List<PercentPromotionResponse>> getAllPercentagePromotions() {
        return ApiResponse.<List<PercentPromotionResponse>>builder()
                .result(percentPromotionService.getAllPercentagePromotions())
                .message("Lấy danh sách khuyến mãi phần trăm thành công")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<PercentPromotionResponse> getPercentagePromotionById(@PathVariable Long id) {
        return ApiResponse.<PercentPromotionResponse>builder()
                .result(percentPromotionService.getPercentagePromotionById(id))
                .message("Lấy thông tin khuyến mãi phần trăm thành công")
                .build();
    }

    @GetMapping("/code/{code}")
    public ApiResponse<PercentPromotionResponse> getPercentagePromotionByCode(@PathVariable String code) {
        return ApiResponse.<PercentPromotionResponse>builder()
                .result(percentPromotionService.getPercentagePromotionByCode(code))
                .message("Lấy thông tin khuyến mãi phần trăm theo mã thành công")
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<PercentPromotionResponse> updatePercentagePromotion(
            @PathVariable Long id,
            @RequestBody @Valid PercentPromotionRequest request) {
        return ApiResponse.<PercentPromotionResponse>builder()
                .result(percentPromotionService.updatePercentagePromotion(id, request))
                .message("Cập nhật khuyến mãi phần trăm thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> deletePercentagePromotion(@PathVariable Long id) {
        percentPromotionService.deletePercentagePromotion(id);
        return ApiResponse.<String>builder()
                .result("Khuyến mãi phần trăm đã được xóa")
                .message("Xóa khuyến mãi phần trăm thành công")
                .build();
    }

    @GetMapping("/active")
    public ApiResponse<List<PercentPromotionResponse>> getActivePercentagePromotions() {
        return ApiResponse.<List<PercentPromotionResponse>>builder()
                .result(percentPromotionService.getActivePercentagePromotions())
                .message("Lấy danh sách khuyến mãi phần trăm đang hoạt động thành công")
                .build();
    }

    @GetMapping("/count")
    public ApiResponse<Long> getTotalPercentagePromotionCount() {
        return ApiResponse.<Long>builder()
                .result(percentPromotionService.getTotalPercentagePromotionCount())
                .message("Lấy tổng số lượng khuyến mãi phần trăm thành công")
                .build();
    }

    @GetMapping("/count/active")
    public ApiResponse<Long> getActivePercentagePromotionCount() {
        return ApiResponse.<Long>builder()
                .result(percentPromotionService.getActivePercentagePromotionCount())
                .message("Lấy tổng số lượng khuyến mãi phần trăm đang hoạt động thành công")
                .build();
    }

    @PostMapping("/validate")
    public ApiResponse<String> validatePromotion(
            @RequestParam String code,
            @RequestParam java.math.BigDecimal orderTotal) {
        percentPromotionService.validateAndGetPromotion(code, orderTotal);
        return ApiResponse.<String>builder()
                .result("Mã khuyến mãi hợp lệ")
                .message("Xác thực mã khuyến mãi phần trăm thành công")
                .build();
    }

    @PostMapping("/apply")
    public ApiResponse<java.math.BigDecimal> applyPromotion(
            @RequestParam String code,
            @RequestParam java.math.BigDecimal orderTotal) {
        java.math.BigDecimal discount = percentPromotionService.applyPromotionToOrder(code, orderTotal);
        return ApiResponse.<java.math.BigDecimal>builder()
                .result(discount)
                .message("Áp dụng mã khuyến mãi phần trăm thành công")
                .build();
    }

    @PostMapping("/{id}/increase-quantity")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> increaseQuantity(@PathVariable Long id) {
        percentPromotionService.increaseQuantity(id);
        return ApiResponse.<String>builder()
                .result("Đã tăng số lượng mã khuyến mãi")
                .message("Tăng số lượng mã khuyến mãi phần trăm thành công")
                .build();
    }
}


