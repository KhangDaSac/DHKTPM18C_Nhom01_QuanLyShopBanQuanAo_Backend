package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.cartitem.CartItemRequest;
import com.example.ModaMint_Backend.dto.response.cart.CartResponse;

public interface CartService {
    CartResponse getCartBySession(String sessionId);
    CartResponse getCartByCustomer(String customerId);
    CartResponse addItem(String customerId, String sessionId, CartItemRequest request);
    void removeItem(String customerId, String sessionId, Long variantId);
    void clearCart(String customerId, String sessionId);
}
