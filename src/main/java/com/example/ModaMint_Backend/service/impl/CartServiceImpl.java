package com.example.ModaMint_Backend.service.impl;

import com.example.ModaMint_Backend.dto.request.cartitem.CartItemRequest;
import com.example.ModaMint_Backend.dto.response.cartitem.CartItemResponse;
import com.example.ModaMint_Backend.dto.response.cart.CartResponse;
import com.example.ModaMint_Backend.entity.*;
import com.example.ModaMint_Backend.repository.*;
import com.example.ModaMint_Backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductVariantRepository productVariantRepository;

    // ✅ Lấy giỏ hàng theo customerId
    @Override
    public CartResponse getCart(String customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .customerId(customerId)
                            .build();
                    return cartRepository.save(newCart);
                });

        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());

        List<CartItemResponse> itemResponses = items.stream()
                .map(this::mapToCartItemResponse)
                .collect(Collectors.toList());

        BigDecimal total = itemResponses.stream()
                .map(CartItemResponse::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartResponse.builder()
                .id(cart.getId())
                .customerId(cart.getCustomerId())
                .items(itemResponses)
                .totalPrice(total)
                .build();
    }

    // ✅ Thêm sản phẩm vào giỏ hàng
    @Override
    public CartResponse addItem(String customerId, CartItemRequest request) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseGet(() -> cartRepository.save(Cart.builder()
                        .customerId(customerId)
                        .build()));

        // Kiểm tra xem variant có tồn tại không
        ProductVariant variant = productVariantRepository.findById(request.getVariantId())
                .orElseThrow(() -> new RuntimeException("Variant not found"));

        // Kiểm tra nếu sản phẩm đã có trong giỏ => cộng thêm số lượng
        CartItem existingItem = cartItemRepository.findByCartId(cart.getId()).stream()
                .filter(i -> i.getVariantId().equals(request.getVariantId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = CartItem.builder()
                    .cartId(cart.getId())
                    .variantId(request.getVariantId())
                    .quantity(request.getQuantity())
                    .build();
            cartItemRepository.save(newItem);
        }

        return getCart(customerId);
    }

    // ✅ Xóa 1 sản phẩm khỏi giỏ hàng
    @Override
    public void removeItem(String customerId, Long variantId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cartItemRepository.findByCartId(cart.getId())
                .stream()
                .filter(i -> i.getVariantId().equals(variantId))
                .findFirst()
                .ifPresent(i -> cartItemRepository.deleteById(Math.toIntExact(i.getId())));
    }

    // ✅ Xóa toàn bộ giỏ hàng
    @Override
    public void clearCart(String customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cartItemRepository.deleteByCartId(cart.getId());
    }

    // ✅ Hàm map từ CartItem → CartItemResponse
    private CartItemResponse mapToCartItemResponse(CartItem item) {
        ProductVariant variant = item.getProductVariant();
        if (variant == null) {
            variant = productVariantRepository.findById(item.getVariantId()).orElse(null);
        }

        BigDecimal price = variant != null ? variant.getPrice() : BigDecimal.ZERO;
        BigDecimal total = price.multiply(BigDecimal.valueOf(item.getQuantity()));

        return CartItemResponse.builder()
                .id(item.getId())
                .variantId(item.getVariantId())
                .productName(variant != null ? variant.getProduct().getName() : "Unknown")
                .color(variant != null ? variant.getColor() : null)
                .size(variant != null ? variant.getSize() : null)
                .price(price)
                .quantity(item.getQuantity())
                .total(total)
                .build();
    }
}
