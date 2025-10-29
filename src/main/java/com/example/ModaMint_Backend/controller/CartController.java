package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.dto.request.cartitem.CartItemRequest;
import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.cart.CartResponse;
import com.example.ModaMint_Backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/session/{sessionId}")
    public ApiResponse<CartResponse> getCartBySession(@PathVariable String sessionId) {
        return ApiResponse.<CartResponse>builder()
                .message("Cart (session) fetched successfully")
                .result(cartService.getCartBySession(sessionId))
                .build();
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/customer/{customerId}")
    public ApiResponse<CartResponse> getCartByCustomer(@PathVariable String customerId) {
        return ApiResponse.<CartResponse>builder()
                .message("Cart (customer) fetched successfully")
                .result(cartService.getCartByCustomer(customerId))
                .build();
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/add")
    public ApiResponse<CartResponse> addItem(
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) String sessionId,
            @RequestBody CartItemRequest request
    ) {
        return ApiResponse.<CartResponse>builder()
                .message("Item added successfully")
                .result(cartService.addItem(customerId, sessionId, request))
                .build();
    }

    @DeleteMapping("/remove/{variantId}")
    public ApiResponse<Void> removeItem(
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) String sessionId,
            @PathVariable Long variantId
    ) {
        cartService.removeItem(customerId, sessionId, variantId);
        return ApiResponse.<Void>builder().message("Item removed successfully").build();
    }

    @DeleteMapping("/clear")
    public ApiResponse<Void> clearCart(
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) String sessionId
    ) {
        cartService.clearCart(customerId, sessionId);
        return ApiResponse.<Void>builder().message("Cart cleared successfully").build();
    }
}
