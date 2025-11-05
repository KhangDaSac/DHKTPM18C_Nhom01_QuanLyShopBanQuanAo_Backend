package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.dto.request.promotion.PercentagePromotionRequest;
import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.promotion.PercentagePromotionResponse;
import com.example.ModaMint_Backend.service.PercentagePromotionService;
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
public class PercentagePromotionController {
    PercentagePromotionService percentagePromotionService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<PercentagePromotionResponse> createPercentagePromotion(@RequestBody @Valid PercentagePromotionRequest request) {
        return ApiResponse.<PercentagePromotionResponse>builder()
                .result(percentagePromotionService.createPercentagePromotion(request))
                .message("Tạo khuyến mãi phần trăm mới thành công")
                .build();
    }

    @GetMapping
    public ApiResponse<List<PercentagePromotionResponse>> getAllPercentagePromotions() {
        return ApiResponse.<List<PercentagePromotionResponse>>builder()
                .result(percentagePromotionService.getAllPercentagePromotions())
                .message("Lấy danh sách khuyến mãi phần trăm thành công")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<PercentagePromotionResponse> getPercentagePromotionById(@PathVariable Long id) {
        return ApiResponse.<PercentagePromotionResponse>builder()
                .result(percentagePromotionService.getPercentagePromotionById(id))
                .message("Lấy thông tin khuyến mãi phần trăm thành công")
                .build();
    }

    @GetMapping("/code/{code}")
    public ApiResponse<PercentagePromotionResponse> getPercentagePromotionByCode(@PathVariable String code) {
        return ApiResponse.<PercentagePromotionResponse>builder()
                .result(percentagePromotionService.getPercentagePromotionByCode(code))
                .message("Lấy thông tin khuyến mãi phần trăm theo mã thành công")
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<PercentagePromotionResponse> updatePercentagePromotion(
            @PathVariable Long id,
            @RequestBody @Valid PercentagePromotionRequest request) {
        return ApiResponse.<PercentagePromotionResponse>builder()
                .result(percentagePromotionService.updatePercentagePromotion(id, request))
                .message("Cập nhật khuyến mãi phần trăm thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> deletePercentagePromotion(@PathVariable Long id) {
        percentagePromotionService.deletePercentagePromotion(id);
        return ApiResponse.<String>builder()
                .result("Khuyến mãi phần trăm đã được xóa")
                .message("Xóa khuyến mãi phần trăm thành công")
                .build();
    }

    @GetMapping("/active")
    public ApiResponse<List<PercentagePromotionResponse>> getActivePercentagePromotions() {
        return ApiResponse.<List<PercentagePromotionResponse>>builder()
                .result(percentagePromotionService.getActivePercentagePromotions())
                .message("Lấy danh sách khuyến mãi phần trăm đang hoạt động thành công")
                .build();
    }

    @GetMapping("/count")
    public ApiResponse<Long> getTotalPercentagePromotionCount() {
        return ApiResponse.<Long>builder()
                .result(percentagePromotionService.getTotalPercentagePromotionCount())
                .message("Lấy tổng số lượng khuyến mãi phần trăm thành công")
                .build();
    }

    @GetMapping("/count/active")
    public ApiResponse<Long> getActivePercentagePromotionCount() {
        return ApiResponse.<Long>builder()
                .result(percentagePromotionService.getActivePercentagePromotionCount())
                .message("Lấy tổng số lượng khuyến mãi phần trăm đang hoạt động thành công")
                .build();
    }
}

