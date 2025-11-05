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
    public ApiResponse<AmountPromotionResponse> getAmountPromotionById(@PathVariable Long id) {
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
            @PathVariable Long id,
            @RequestBody @Valid AmountPromotionRequest request) {
        return ApiResponse.<AmountPromotionResponse>builder()
                .result(amountPromotionService.updateAmountPromotion(id, request))
                .message("Cập nhật khuyến mãi số tiền thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> deleteAmountPromotion(@PathVariable Long id) {
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
}

