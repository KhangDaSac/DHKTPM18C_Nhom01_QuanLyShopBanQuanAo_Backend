package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.dto.request.promotion.AmountPromotionRequest;
import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.promotion.AmountPromotionResponse;
import com.example.ModaMint_Backend.service.AmountPromotionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/amount-promotions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AmountPromotionController {
    AmountPromotionService amountPromotionService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<AmountPromotionResponse> createAmountPromotion(@RequestBody @Valid AmountPromotionRequest request) {
        return ApiResponse.<AmountPromotionResponse>builder()
                .result(amountPromotionService.createAmountPromotion(request))
                .message("Tạo khuyến mãi số tiền mới thành công")
                .build();
    }

    @GetMapping
    public ApiResponse<List<AmountPromotionResponse>> getAllAmountPromotions() {
        return ApiResponse.<List<AmountPromotionResponse>>builder()
                .result(amountPromotionService.getAllAmountPromotions())
                .message("Lấy danh sách khuyến mãi số tiền thành công")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<AmountPromotionResponse> getAmountPromotionById(@PathVariable String id) {
        return ApiResponse.<AmountPromotionResponse>builder()
                .result(amountPromotionService.getAmountPromotionById(id))
                .message("Lấy thông tin khuyến mãi số tiền thành công")
                .build();
    }

    @GetMapping("/code/{code}")
    public ApiResponse<AmountPromotionResponse> getAmountPromotionByCode(@PathVariable String code) {
        return ApiResponse.<AmountPromotionResponse>builder()
                .result(amountPromotionService.getAmountPromotionByCode(code))
                .message("Lấy thông tin khuyến mãi số tiền theo mã thành công")
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<AmountPromotionResponse> updateAmountPromotion(
            @PathVariable String id,
            @RequestBody @Valid AmountPromotionRequest request) {
        return ApiResponse.<AmountPromotionResponse>builder()
                .result(amountPromotionService.updateAmountPromotion(id, request))
                .message("Cập nhật khuyến mãi số tiền thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> deleteAmountPromotion(@PathVariable String id) {
        amountPromotionService.deleteAmountPromotion(id);
        return ApiResponse.<String>builder()
                .result("Khuyến mãi số tiền đã được xóa")
                .message("Xóa khuyến mãi số tiền thành công")
                .build();
    }

    @GetMapping("/active")
    public ApiResponse<List<AmountPromotionResponse>> getActiveAmountPromotions() {
        return ApiResponse.<List<AmountPromotionResponse>>builder()
                .result(amountPromotionService.getActiveAmountPromotions())
                .message("Lấy danh sách khuyến mãi số tiền đang hoạt động thành công")
                .build();
    }

    @GetMapping("/not-started")
    public ApiResponse<List<AmountPromotionResponse>> getNotStartedAmountPromotions() {
        return ApiResponse.<List<AmountPromotionResponse>>builder()
                .result(amountPromotionService.getNotStartedAmountPromotions())
                .message("Lấy danh sách khuyến mãi số tiền chưa bắt đầu thành công")
                .build();
    }

    @GetMapping("/expired")
    public ApiResponse<List<AmountPromotionResponse>> getExpiredAmountPromotions() {
        return ApiResponse.<List<AmountPromotionResponse>>builder()
                .result(amountPromotionService.getExpiredAmountPromotions())
                .message("Lấy danh sách khuyến mãi số tiền đã hết hạn thành công")
                .build();
    }

    @GetMapping("/count")
    public ApiResponse<Long> getTotalAmountPromotionCount() {
        return ApiResponse.<Long>builder()
                .result(amountPromotionService.getTotalAmountPromotionCount())
                .message("Lấy tổng số lượng khuyến mãi số tiền thành công")
                .build();
    }

    @GetMapping("/count/active")
    public ApiResponse<Long> getActiveAmountPromotionCount() {
        return ApiResponse.<Long>builder()
                .result(amountPromotionService.getActiveAmountPromotionCount())
                .message("Lấy tổng số lượng khuyến mãi số tiền đang hoạt động thành công")
                .build();
    }

    @PostMapping("/validate")
    public ApiResponse<String> validatePromotion(
            @RequestParam String code,
            @RequestParam java.math.BigDecimal orderTotal) {
        amountPromotionService.validateAndGetPromotion(code, orderTotal);
        return ApiResponse.<String>builder()
                .result("Mã khuyến mãi hợp lệ")
                .message("Xác thực mã khuyến mãi số tiền thành công")
                .build();
    }

    @PostMapping("/apply")
    public ApiResponse<java.math.BigDecimal> applyPromotion(
            @RequestParam String code,
            @RequestParam java.math.BigDecimal orderTotal) {
        java.math.BigDecimal discount = amountPromotionService.applyPromotionToOrder(code, orderTotal);
        return ApiResponse.<java.math.BigDecimal>builder()
                .result(discount)
                .message("Áp dụng mã khuyến mãi số tiền thành công")
                .build();
    }

    @PostMapping("/{id}/increase-quantity")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> increaseQuantity(@PathVariable String id) {
        amountPromotionService.increaseQuantity(id);
        return ApiResponse.<String>builder()
                .result("Đã tăng số lượng mã khuyến mãi")
                .message("Tăng số lượng mã khuyến mãi số tiền thành công")
                .build();
    }
}

