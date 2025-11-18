/*
 * @ (#) j.java     1.0    15-Nov-25
 *
 * Copyright (c) 2025 IUH. All rights reserved.
 */

package com.example.ModaMint_Backend.service;

/*
 * @description:
 * @author: Nguyen Quoc Huy
 * @date:15-Nov-25
 * @version: 1.0
 */
import com.example.ModaMint_Backend.dto.request.cartitem.CartItemRequest;
import com.example.ModaMint_Backend.dto.response.cart.CartResponse;

public interface CartService {
    CartResponse getCartByCustomer(String customerId);
    CartResponse addItem(String customerId, CartItemRequest request);
    void removeItem(String customerId, Long variantId);
    void removeItemCompletely(String customerId, Long variantId);
    void clearCart(String customerId);
}