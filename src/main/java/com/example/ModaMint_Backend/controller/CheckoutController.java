package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.dto.request.checkout.CheckoutRequest;
import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.checkout.CheckoutResponse;
import com.example.ModaMint_Backend.dto.response.promotion.PromotionSummary;
import com.example.ModaMint_Backend.service.CheckoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
@Slf4j
public class CheckoutController {

    private final CheckoutService checkoutService;

    @GetMapping("/promotions")
    public ApiResponse<List<PromotionSummary>> getAvailablePromotions(
            @RequestParam String customerId) {
        log.info("Getting available promotions for customer: {}", customerId);
        
        return ApiResponse.<List<PromotionSummary>>builder()
                .code(2000)
                .message("Lấy danh sách mã giảm giá thành công")
                .result(checkoutService.getAvailablePromotions(customerId))
                .build();
    }


    @PostMapping
    public ApiResponse<CheckoutResponse> checkout(@Valid @RequestBody CheckoutRequest request) {
        log.info("Processing checkout for customer: {} (isGuest: {})", 
                request.getCustomerId(), request.getIsGuest());
        
        CheckoutResponse response = checkoutService.checkout(request);
        
        return ApiResponse.<CheckoutResponse>builder()
                .code(2000)
                .message("Đặt hàng thành công!")
                .result(response)
                .build();
    }
}
