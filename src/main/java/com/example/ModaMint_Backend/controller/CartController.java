package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.dto.request.cartitem.CartItemRequest;
import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.cart.CartResponse;
import com.example.ModaMint_Backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor

public class CartController {
    @Autowired
    private final CartService cartService;

    @GetMapping("/{customerId}")
    public ApiResponse<CartResponse> getCart(@PathVariable String customerId) {
        return ApiResponse.<CartResponse>builder()
                .message("Cart fetched successfully")
                .result(cartService.getCart(customerId))
                .build();
    }

    @PostMapping("/{customerId}/add")
    public ApiResponse<CartResponse> addItem(
            @PathVariable String customerId,
            @RequestBody CartItemRequest request
    ) {
        return ApiResponse.<CartResponse>builder()
                .message("Item added successfully")
                .result(cartService.addItem(customerId, request))
                .build();
    }

    @DeleteMapping("/{customerId}/remove/{variantId}")
    public ApiResponse<Void> removeItem(@PathVariable String customerId, @PathVariable Long variantId) {
        cartService.removeItem(customerId, variantId);
        return ApiResponse.<Void>builder().message("Item removed successfully").build();
    }

    @DeleteMapping("/{customerId}/clear")
    public ApiResponse<Void> clearCart(@PathVariable String customerId) {
        cartService.clearCart(customerId);
        return ApiResponse.<Void>builder().message("Cart cleared successfully").build();
    }
}
