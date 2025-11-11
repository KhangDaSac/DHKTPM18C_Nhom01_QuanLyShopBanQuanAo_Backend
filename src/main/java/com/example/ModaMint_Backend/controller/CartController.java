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
    @GetMapping("/customer/{customerId}")
    public ApiResponse<CartResponse> getCartByCustomer(@PathVariable String customerId) {
        return ApiResponse.<CartResponse>builder()
                .message("Cart fetched successfully")
                .result(cartService.getCartByCustomer(customerId))
                .build();
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/add")
    public ApiResponse<CartResponse> addItem(
            @RequestParam String customerId,
            @RequestBody CartItemRequest request
    ) {
        try {
            System.out.println("CartController.addItem - customerId: " + customerId);
            System.out.println("CartController.addItem - request: " + request);
            System.out.println("CartController.addItem - variantId: " + (request != null ? request.getVariantId() : "null"));
            System.out.println("CartController.addItem - quantity: " + (request != null ? request.getQuantity() : "null"));
            
            CartResponse result = cartService.addItem(customerId, request);
            
            return ApiResponse.<CartResponse>builder()
                    .code(1000)
                    .message("Item added successfully")
                    .result(result)
                    .build();
        } catch (Exception e) {
            System.err.println("Error in CartController.addItem: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw để GlobalHandlerException xử lý
        }
    }

    @DeleteMapping("/remove/{variantId}")
    public ApiResponse<Void> removeItem(
            @RequestParam String customerId,
            @PathVariable Long variantId
    ) {
        cartService.removeItem(customerId, variantId);
        return ApiResponse.<Void>builder().message("Item removed successfully").build();
    }

    @DeleteMapping("/remove/{variantId}/complete")
    public ApiResponse<Void> removeItemCompletely(
            @RequestParam String customerId,
            @PathVariable Long variantId
    ) {
        cartService.removeItemCompletely(customerId, variantId);
        return ApiResponse.<Void>builder().message("Item completely removed successfully").build();
    }

    @DeleteMapping("/clear")
    public ApiResponse<Void> clearCart(@RequestParam String customerId) {
        cartService.clearCart(customerId);
        return ApiResponse.<Void>builder().message("Cart cleared successfully").build();
    }
}
