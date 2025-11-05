package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.cartitem.CartItemRequest;
import com.example.ModaMint_Backend.dto.response.cart.CartResponse;

public interface CartService {
    CartResponse getCartByCustomer(String customerId);
    CartResponse addItem(String customerId, CartItemRequest request);
    void removeItem(String customerId, Long variantId);
    void removeItemCompletely(String customerId, Long variantId);
    void clearCart(String customerId);
}
