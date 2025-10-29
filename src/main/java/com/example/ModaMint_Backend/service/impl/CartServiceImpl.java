package com.example.ModaMint_Backend.service.impl;

import com.example.ModaMint_Backend.dto.request.cartitem.CartItemRequest;
import com.example.ModaMint_Backend.dto.response.cart.CartItemResponse;
import com.example.ModaMint_Backend.dto.response.cart.CartResponse;
import com.example.ModaMint_Backend.entity.Cart;
import com.example.ModaMint_Backend.entity.CartItem;
import com.example.ModaMint_Backend.entity.ProductVariant;
import com.example.ModaMint_Backend.repository.CartItemRepository;
import com.example.ModaMint_Backend.repository.CartRepository;
import com.example.ModaMint_Backend.repository.ProductVariantRepository;
import com.example.ModaMint_Backend.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductVariantRepository productVariantRepository;

    @Override
    @Transactional
    public CartResponse getCartBySession(String sessionId) {
        if (sessionId == null || sessionId.isBlank()) {
            return emptyCartResponseForSession(sessionId);
        }
        Cart cart = cartRepository.findBySessionId(sessionId)
                .orElseGet(() -> createCartForSession(sessionId));
        return buildCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse getCartByCustomer(String customerId) {
        if (customerId == null || customerId.isBlank()) {
            return emptyCartResponseForCustomer(customerId);
        }
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseGet(() -> createCartForCustomer(customerId));
        return buildCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse addItem(String customerId, String sessionId, CartItemRequest request) {


        if (request == null || request.getVariantId() == null) {
            throw new IllegalArgumentException("variantId is required");
        }
        // quyết định dùng customer hay session
        Cart cart = resolveOrCreateCart(customerId, sessionId);

        Long variantId = request.getVariantId();
        int addQty = request.getQuantity() == null || request.getQuantity() <= 0 ? 1 : request.getQuantity();

        // check variant exists (optional but recommended)
        ProductVariant variant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("ProductVariant not found: " + variantId));

        // tìm cartItem đã có
        CartItem item = cartItemRepository.findByCartIdAndVariantId(cart.getId(), variantId)
                .orElseGet(() -> {
                    CartItem ci = CartItem.builder()
                            .cartId(cart.getId())
                            .variantId(variantId)
                            .quantity(0)
                            .build();
                    return ci;
                });

        item.setQuantity((item.getQuantity() == null ? 0 : item.getQuantity()) + addQty);
        cartItemRepository.save(item);

        // trả về cart cập nhật
        return buildCartResponse(cartRepository.findById(cart.getId()).orElse(cart));
    }

    @Override
    @Transactional
    public void removeItem(String customerId, String sessionId, Long variantId) {
        Cart cart = resolveCartOrThrow(customerId, sessionId);

        // Tìm item trong cart
        CartItem item = cartItemRepository.findByCartIdAndVariantId(cart.getId(), variantId)
                .orElseThrow(() -> new RuntimeException("Item not found in cart for variant: " + variantId));

        // Nếu quantity > 1 thì giảm 1, ngược lại xóa luôn
        if (item.getQuantity() != null && item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
            cartItemRepository.save(item);
        } else {
            cartItemRepository.delete(item);
        }
    }


    @Override
    @Transactional
    public void clearCart(String customerId, String sessionId) {
        Cart cart = resolveCartOrThrow(customerId, sessionId);
        cartItemRepository.deleteAllByCartId(cart.getId());
    }

    /* ------------------ Helper methods ------------------ */

    private Cart resolveOrCreateCart(String customerId, String sessionId) {
        if (customerId != null && !customerId.isBlank()) {
            return cartRepository.findByCustomerId(customerId)
                    .orElseGet(() -> createCartForCustomer(customerId));
        }
        if (sessionId != null && !sessionId.isBlank()) {
            return cartRepository.findBySessionId(sessionId)
                    .orElseGet(() -> createCartForSession(sessionId));
        }
        throw new IllegalArgumentException("Either customerId or sessionId must be provided");
    }

    private Cart resolveCartOrThrow(String customerId, String sessionId) {
        if (customerId != null && !customerId.isBlank()) {
            return cartRepository.findByCustomerId(customerId)
                    .orElseThrow(() -> new RuntimeException("Cart not found for customer: " + customerId));
        }
        if (sessionId != null && !sessionId.isBlank()) {
            return cartRepository.findBySessionId(sessionId)
                    .orElseThrow(() -> new RuntimeException("Cart not found for session: " + sessionId));
        }
        throw new IllegalArgumentException("Either customerId or sessionId must be provided");
    }

    private Cart createCartForCustomer(String customerId) {
        Cart cart = Cart.builder()
                .customerId(customerId)
                .build();
        return cartRepository.save(cart);
    }

    private Cart createCartForSession(String sessionId) {
        Cart cart = Cart.builder()
                .sessionId(sessionId)
                .build();
        return cartRepository.save(cart);
    }

    private CartResponse emptyCartResponseForSession(String sessionId) {
        return CartResponse.builder()
                .cartId(null)
                .sessionId(sessionId)
                .customerId(null)
                .items(List.of())
                .totalPrice(BigDecimal.ZERO)
                .build();
    }

    private CartResponse emptyCartResponseForCustomer(String customerId) {
        return CartResponse.builder()
                .cartId(null)
                .sessionId(null)
                .customerId(customerId)
                .items(List.of())
                .totalPrice(BigDecimal.ZERO)
                .build();
    }

    private CartResponse buildCartResponse(Cart cart) {
        if (cart == null) {
            return CartResponse.builder()
                    .cartId(null)
                    .items(List.of())
                    .totalPrice(BigDecimal.ZERO)
                    .build();
        }

        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());
        List<CartItemResponse> itemResponses = items.stream().map(ci -> {
            ProductVariant variant = productVariantRepository.findById(ci.getVariantId()).orElse(null);

            BigDecimal price = variant != null && variant.getPrice() != null ? variant.getPrice() : BigDecimal.ZERO;
            BigDecimal subtotal = price.multiply(BigDecimal.valueOf(ci.getQuantity() == null ? 0 : ci.getQuantity()));

            return CartItemResponse.builder()
                    .id(ci.getId())
                    .variantId(ci.getVariantId())
                    .productName(variant != null && variant.getProduct() != null ? variant.getProduct().getName() : null)
                    .color(variant != null ? variant.getColor() : null)
                    .size(variant != null ? variant.getSize() : null)
                    .price(price)
                    .quantity(ci.getQuantity())
                    .imageUrl(variant != null ? variant.getImage() : null)
                    .build();
        }).collect(Collectors.toList());

        BigDecimal total = items.stream()
                .map(ci -> {
                    ProductVariant v = productVariantRepository.findById(ci.getVariantId()).orElse(null);
                    BigDecimal p = v != null && v.getPrice() != null ? v.getPrice() : BigDecimal.ZERO;
                    return p.multiply(BigDecimal.valueOf(ci.getQuantity() == null ? 0 : ci.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartResponse.builder()
                .cartId(cart.getId())
                .sessionId(cart.getSessionId())
                .customerId(cart.getCustomerId())
                .items(itemResponses)
                .totalPrice(total)
                .build();
    }



}
