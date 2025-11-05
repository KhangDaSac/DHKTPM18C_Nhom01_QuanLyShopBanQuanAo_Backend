package com.example.ModaMint_Backend.service.impl;

import com.example.ModaMint_Backend.dto.request.cartitem.CartItemRequest;
import com.example.ModaMint_Backend.dto.response.cart.CartItemResponse;
import com.example.ModaMint_Backend.dto.response.cart.CartResponse;
import com.example.ModaMint_Backend.entity.Cart;
import com.example.ModaMint_Backend.entity.CartItem;
import com.example.ModaMint_Backend.entity.Customer;
import com.example.ModaMint_Backend.entity.ProductVariant;
import com.example.ModaMint_Backend.entity.User;
import com.example.ModaMint_Backend.exception.AppException;
import com.example.ModaMint_Backend.exception.ErrorCode;
import com.example.ModaMint_Backend.repository.CartItemRepository;
import com.example.ModaMint_Backend.repository.CartRepository;
import com.example.ModaMint_Backend.repository.CustomerRepository;
import com.example.ModaMint_Backend.repository.ProductVariantRepository;
import com.example.ModaMint_Backend.repository.UserRepository;
import com.example.ModaMint_Backend.service.CartService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductVariantRepository productVariantRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

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
    public CartResponse addItem(String customerId, CartItemRequest request) {
        log.info("Adding item to cart for customerId: {}, variantId: {}, quantity: {}", 
                customerId, request != null ? request.getVariantId() : null, 
                request != null ? request.getQuantity() : null);
        
        if (customerId == null || customerId.isBlank()) {
            log.error("customerId is required");
            throw new IllegalArgumentException("customerId is required");
        }
        if (request == null || request.getVariantId() == null) {
            log.error("variantId is required");
            throw new IllegalArgumentException("variantId is required");
        }
        
        // Đảm bảo Customer tồn tại, nếu không thì tạo mới
        ensureCustomerExists(customerId);
        
        Cart cart = resolveOrCreateCart(customerId);

        Long variantId = request.getVariantId();
        int addQty = request.getQuantity() == null || request.getQuantity() <= 0 ? 1 : request.getQuantity();

        // check variant exists
        ProductVariant variant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> {
                    log.error("ProductVariant not found: {}", variantId);
                    return new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND);
                });

        // tìm cartItem đã có
        CartItem item = cartItemRepository.findByCartIdAndVariantId(cart.getId(), variantId)
                .orElseGet(() -> {
                    log.info("Creating new CartItem for cartId: {}, variantId: {}", cart.getId(), variantId);
                    CartItem ci = CartItem.builder()
                            .cartId(cart.getId())
                            .variantId(variantId)
                            .quantity(0)
                            .build();
                    return ci;
                });

        int oldQuantity = item.getQuantity() == null ? 0 : item.getQuantity();
        int newQuantity = oldQuantity + addQty;
        item.setQuantity(newQuantity);
        
        log.info("Updating CartItem: cartItemId={}, variantId={}, oldQty={}, newQty={}", 
                item.getId(), variantId, oldQuantity, newQuantity);
        
        try {
            cartItemRepository.save(item);
            entityManager.flush(); // Ensure CartItem is saved before building response
            log.info("CartItem saved successfully");
        } catch (Exception e) {
            log.error("Error saving CartItem: cartItemId={}, variantId={}, quantity={}", 
                    item.getId(), variantId, newQuantity, e);
            throw new RuntimeException("Failed to save cart item: " + e.getMessage(), e);
        }

        // Reload cart to get latest state
        Cart updatedCart = cartRepository.findById(cart.getId())
                .orElseThrow(() -> {
                    log.error("Cart not found after saving item: cartId={}", cart.getId());
                    return new RuntimeException("Cart not found after saving item");
                });
        
        log.info("Building cart response for cartId: {}", updatedCart.getId());
        
        // trả về cart cập nhật
        try {
            CartResponse response = buildCartResponse(updatedCart);
            log.info("Cart response built successfully");
            return response;
        } catch (Exception e) {
            log.error("Error building cart response after adding item", e);
            throw new RuntimeException("Failed to build cart response: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void removeItem(String customerId, Long variantId) {
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("customerId is required");
        }
        Cart cart = resolveCartOrThrow(customerId);

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
    public void removeItemCompletely(String customerId, Long variantId) {
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("customerId is required");
        }
        Cart cart = resolveCartOrThrow(customerId);
        
        log.info("Removing item completely - customerId: {}, variantId: {}", customerId, variantId);
        
        // Xóa hoàn toàn item này (không phân biệt quantity)
        cartItemRepository.deleteByCartIdAndVariantId(cart.getId(), variantId);
        log.info("Item completely removed from cart");
    }


    @Override
    @Transactional
    public void clearCart(String customerId) {
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("customerId is required");
        }
        Cart cart = resolveCartOrThrow(customerId);
        cartItemRepository.deleteAllByCartId(cart.getId());
    }

    /* ------------------ Helper methods ------------------ */

    private Cart resolveOrCreateCart(String customerId) {
        return cartRepository.findByCustomerId(customerId)
                .orElseGet(() -> createCartForCustomer(customerId));
    }

    private Cart resolveCartOrThrow(String customerId) {
        return cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found for customer: " + customerId));
    }

    private void ensureCustomerExists(String customerId) {
        if (!customerRepository.existsByCustomerId(customerId)) {
            log.info("Customer not found for customerId: {}, creating new customer", customerId);
            
            // Kiểm tra User có tồn tại không
            User user = userRepository.findById(customerId)
                    .orElseThrow(() -> {
                        log.error("User not found for customerId: {}", customerId);
                        return new AppException(ErrorCode.USER_NOT_FOUND);
                    });
            
            // Tạo Customer mới
            Customer customer = Customer.builder()
                    .customerId(customerId)
                    .user(user)
                    .build();
            
            // Save và flush ngay để đảm bảo Customer tồn tại trong DB trước khi save Cart
            customerRepository.save(customer);
            entityManager.flush(); // Force flush để đảm bảo Customer được commit vào DB
            log.info("Customer created and flushed successfully for customerId: {}", customerId);
        }
    }

    private Cart createCartForCustomer(String customerId) {
        log.info("Creating cart for customerId: {}", customerId);
        Cart cart = Cart.builder()
                .customerId(customerId)
                .build();
        Cart savedCart = cartRepository.save(cart);
        log.info("Cart created successfully with id: {} for customerId: {}", savedCart.getId(), customerId);
        return savedCart;
    }

    private CartResponse emptyCartResponseForCustomer(String customerId) {
        return CartResponse.builder()
                .cartId(null)
                .customerId(customerId)
                .items(List.of())
                .totalPrice(BigDecimal.ZERO)
                .build();
    }

    private CartResponse buildCartResponse(Cart cart) {
        try {
            if (cart == null) {
                log.warn("Cart is null in buildCartResponse");
                return CartResponse.builder()
                        .cartId(null)
                        .items(List.of())
                        .totalPrice(BigDecimal.ZERO)
                        .build();
            }

            log.debug("Building cart response for cartId: {}, customerId: {}", cart.getId(), cart.getCustomerId());
            List<CartItem> items = cartItemRepository.findByCartId(cart.getId());
            log.debug("Found {} cart items", items.size());

            List<CartItemResponse> itemResponses = items.stream().map(ci -> {
                try {
                    ProductVariant variant = productVariantRepository.findById(ci.getVariantId()).orElse(null);
                    
                    if (variant == null) {
                        log.warn("ProductVariant not found for variantId: {}", ci.getVariantId());
                    }

                    BigDecimal price = variant != null && variant.getPrice() != null ? variant.getPrice() : BigDecimal.ZERO;
                    BigDecimal subtotal = price.multiply(BigDecimal.valueOf(ci.getQuantity() == null ? 0 : ci.getQuantity()));

                    // Safe access to Product through variant
                    String productName = null;
                    if (variant != null && variant.getProduct() != null) {
                        try {
                            productName = variant.getProduct().getName();
                        } catch (Exception e) {
                            log.warn("Error getting product name for variantId: {}", ci.getVariantId(), e);
                        }
                    }

                    return CartItemResponse.builder()
                            .id(ci.getId())
                            .variantId(ci.getVariantId())
                            .productName(productName)
                            .color(variant != null ? variant.getColor() : null)
                            .size(variant != null ? variant.getSize() : null)
                            .price(price)
                            .quantity(ci.getQuantity())
                            .imageUrl(variant != null ? variant.getImage() : null)
                            .build();
                } catch (Exception e) {
                    log.error("Error building CartItemResponse for cartItemId: {}, variantId: {}", 
                            ci.getId(), ci.getVariantId(), e);
                    // Return a basic response even if there's an error
                    return CartItemResponse.builder()
                            .id(ci.getId())
                            .variantId(ci.getVariantId())
                            .productName(null)
                            .color(null)
                            .size(null)
                            .price(BigDecimal.ZERO)
                            .quantity(ci.getQuantity())
                            .imageUrl(null)
                            .build();
                }
            }).collect(Collectors.toList());

            BigDecimal total = items.stream()
                    .map(ci -> {
                        try {
                            ProductVariant v = productVariantRepository.findById(ci.getVariantId()).orElse(null);
                            BigDecimal p = v != null && v.getPrice() != null ? v.getPrice() : BigDecimal.ZERO;
                            return p.multiply(BigDecimal.valueOf(ci.getQuantity() == null ? 0 : ci.getQuantity()));
                        } catch (Exception e) {
                            log.warn("Error calculating price for variantId: {}", ci.getVariantId(), e);
                            return BigDecimal.ZERO;
                        }
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            CartResponse response = CartResponse.builder()
                    .cartId(cart.getId())
                    .customerId(cart.getCustomerId())
                    .items(itemResponses)
                    .totalPrice(total)
                    .build();
            
            log.debug("Cart response built successfully with {} items, total: {}", itemResponses.size(), total);
            return response;
        } catch (Exception e) {
            log.error("Error building cart response for cartId: {}", cart != null ? cart.getId() : "null", e);
            throw new RuntimeException("Error building cart response: " + e.getMessage(), e);
        }
    }



}
