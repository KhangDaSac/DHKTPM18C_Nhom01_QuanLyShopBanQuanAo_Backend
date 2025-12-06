package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.checkout.CheckoutRequest;
import com.example.ModaMint_Backend.dto.response.cart.CartItemDto;
import com.example.ModaMint_Backend.dto.response.checkout.CheckoutResponse;
import com.example.ModaMint_Backend.dto.response.customer.AddressResponse;
import com.example.ModaMint_Backend.dto.response.promotion.PromotionSummary;
import com.example.ModaMint_Backend.entity.*;
import com.example.ModaMint_Backend.enums.OrderStatus;
import com.example.ModaMint_Backend.exception.AppException;
import com.example.ModaMint_Backend.exception.ErrorCode;
import com.example.ModaMint_Backend.repository.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class    CheckoutService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final ProductVariantRepository productVariantRepository;
    private final PercentPromotionRepository percentPromotionRepository;
    private final AmountPromotionRepository amountPromotionRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartService cartService;

    /**
     * Lấy danh sách mã giảm giá khả dụng cho đơn hàng
     */
    public List<PromotionSummary> getAvailablePromotions(String customerId) {
        log.info("Getting available promotions for customer: {}", customerId);
        
        // Lấy giỏ hàng và tính tổng tiền
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
        
        BigDecimal cartTotal = calculateCartTotal(cart.getId());
        LocalDateTime now = LocalDateTime.now();
        
        List<PromotionSummary> promotions = new ArrayList<>();

        List<PercentPromotion> percentPromotions = percentPromotionRepository.findByIsActive(true);
        for (PercentPromotion promo : percentPromotions) {
            if (isPromotionValid(promo, cartTotal, now)) {
                promotions.add(buildPercentPromotionSummary(promo));
            }
        }

        List<AmountPromotion> amountPromotions = amountPromotionRepository.findByIsActive(true);
        for (AmountPromotion promo : amountPromotions) {
            if (isPromotionValid(promo, cartTotal, now)) {
                promotions.add(buildAmountPromotionSummary(promo));
            }
        }
        
        log.info("Found {} available promotions", promotions.size());
        return promotions;
    }


    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) {
        log.info("Processing checkout for customer: {}", request.getCustomerId());
        
        // 1. Validate customer
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
        
        // 2. Validate address
        Address address = addressRepository.findById(request.getShippingAddressId())
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));
        
        if (!address.getCustomer().getUser().getId().equals(request.getCustomerId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        
        // 3. Get cart items
        Cart cart = cartRepository.findByCustomerId(request.getCustomerId())
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
        
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        
        if (cartItems.isEmpty()) {
            throw new AppException(ErrorCode.CART_EMPTY);
        }
        
        // 4. Calculate prices
        BigDecimal subtotal = calculateCartTotal(cart.getId());
        BigDecimal shippingFee = BigDecimal.valueOf(30000); // Fixed shipping fee
        
        // 5. Apply promotion
        PromotionResult promotionResult = applyPromotion(
                request.getPercentagePromotionCode(),
                request.getAmountPromotionCode(),
                subtotal
        );
        
        BigDecimal discountAmount = promotionResult.getDiscountAmount();
        BigDecimal totalAmount = subtotal.add(shippingFee).subtract(discountAmount);
        
        // 6. Create order
        String orderCode = generateOrderCode();
        
        Order order = Order.builder()
                .orderCode(orderCode)
                .customerId(request.getCustomerId())
                .totalAmount(subtotal.add(shippingFee)) // Tổng trước khi giảm
                .subTotal(totalAmount) // Tổng sau khi giảm
                .percentPromotionId(promotionResult.getPercentagePromotionId())
                .amountPromotionId(promotionResult.getAmountPromotionId())
                .promotionValue(discountAmount)
                .orderStatus(OrderStatus.PENDING)
                .paymentMethod(request.getPaymentMethod())
                .shippingAddressId(request.getShippingAddressId())
                .phone(request.getPhone() != null ? request.getPhone() : customer.getUser().getPhone())
                .build();
        
        Order savedOrder = orderRepository.save(order);
        log.info("Order created with ID: {}, code: {}", savedOrder.getId(), orderCode);
        
        // 7. Create order items from cart items
        List<CartItemDto> orderItemResponses = new ArrayList<>();
        
        for (CartItem cartItem : cartItems) {
            ProductVariant variant = productVariantRepository.findById(cartItem.getVariantId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));
            
            OrderItem orderItem = OrderItem.builder()
                    .orderId(savedOrder.getId())
                    .productVariantId(cartItem.getVariantId())
                    .quantity(cartItem.getQuantity())
                    .unitPrice(variant.getPrice())
                    .build();
            
            orderItemRepository.save(orderItem);
            
            // Add to response
//            String imageUrl = null;
//            if (variant.getProduct() != null && variant.getProduct().getProductImages() != null &&
//                    !variant.getProduct().getProductImages().isEmpty()) {
//                imageUrl = variant.getProduct().getProductImages().iterator().next().getUrl();
//            }
            
            orderItemResponses.add(CartItemDto.builder()
                    .itemId(cartItem.getId())
                    .variantId(variant.getId())
                    .productId(variant.getProduct() != null ? variant.getProduct().getId() : null)
                    .productName(variant.getProduct() != null ? variant.getProduct().getName() : null)
//                    .image(imageUrl)
                    .unitPrice(variant.getPrice().longValue())
                    .quantity(cartItem.getQuantity())
                    .totalPrice(variant.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())).longValue())
                    .build());
        }
        
        // 8. Clear cart after successful order using CartService
        cartService.clearCart(request.getCustomerId());
        log.info("Cart cleared for customer: {}", request.getCustomerId());
        
        // 9. Update promotion quantity
        if (promotionResult.getPercentagePromotionId() != null) {
            decreasePromotionQuantity(promotionResult.getPercentagePromotionId(), true);
        }
        if (promotionResult.getAmountPromotionId() != null) {
            decreasePromotionQuantity(promotionResult.getAmountPromotionId(), false);
        }
        
        // 10. Build response
        return CheckoutResponse.builder()
                .orderId(savedOrder.getId())
                .orderCode(orderCode)
//                .customerId(customer.getUserId())
                .customerName(customer.getUser().getFirstName() + " " + customer.getUser().getLastName())
                .customerEmail(customer.getUser().getEmail())
                .customerPhone(savedOrder.getPhone())
                .shippingAddress(buildAddressResponse(address))
                .orderItems(orderItemResponses)
                .subtotal(subtotal)
                .shippingFee(shippingFee)
                .appliedPromotion(promotionResult.getPromotionSummary())
                .discountAmount(discountAmount)
                .totalAmount(totalAmount)
                .paymentMethod(request.getPaymentMethod().toString())
                .orderStatus(OrderStatus.PENDING.toString())
                .message("Đặt hàng thành công!")
                .build();
    }

    // ==================== HELPER METHODS ====================

    private BigDecimal calculateCartTotal(Long cartId) {
        List<CartItem> items = cartItemRepository.findByCartId(cartId);
        return items.stream()
                .map(item -> {
                    ProductVariant variant = productVariantRepository.findById(item.getVariantId()).orElse(null);
                    if (variant == null) return BigDecimal.ZERO;
                    return variant.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private PromotionResult applyPromotion(String percentageCode, String amountCode, BigDecimal orderTotal) {
        PromotionResult result = new PromotionResult();
        result.setDiscountAmount(BigDecimal.ZERO);
        
        LocalDateTime now = LocalDateTime.now();
        
        // Try percentage promotion first
        if (percentageCode != null && !percentageCode.isBlank()) {
            PercentPromotion promo = percentPromotionRepository.findByCode(percentageCode)
                    .orElse(null);
            
            if (promo != null && isPromotionValid(promo, orderTotal, now)) {
                BigDecimal discount = orderTotal.multiply(BigDecimal.valueOf(promo.getPercent()))
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                
                // Apply max discount limit if exists
                if (promo.getMaxDiscount() > 0 && discount.compareTo(BigDecimal.valueOf(promo.getMaxDiscount())) > 0) {
                    discount = BigDecimal.valueOf(promo.getMaxDiscount());
                }
                
                result.setPercentagePromotionId(promo.getPromotionId());
                result.setDiscountAmount(discount);
                result.setPromotionSummary(buildPercentPromotionSummary(promo));
                
                log.info("Applied percentage promotion: {} - {}%", promo.getCode(), promo.getPercent());
                return result;
            }
        }
        
        // Try amount promotion if percentage not applied
        if (amountCode != null && !amountCode.isBlank()) {
            AmountPromotion promo = amountPromotionRepository.findByCode(amountCode)
                    .orElse(null);
            
            if (promo != null && isPromotionValid(promo, orderTotal, now)) {
                BigDecimal discountAmount = BigDecimal.valueOf(promo.getDiscount());
                result.setAmountPromotionId(promo.getPromotionId());
                result.setDiscountAmount(discountAmount);
                result.setPromotionSummary(buildAmountPromotionSummary(promo));
                
                log.info("Applied amount promotion: {} - {}đ", promo.getCode(), promo.getDiscount());
                return result;
            }
        }
        
        return result;
    }

    private void decreasePromotionQuantity(String promotionId, boolean isPercentage) {
        if (isPercentage) {
            PercentPromotion promo = percentPromotionRepository.findById(promotionId).orElse(null);
            if (promo != null && promo.getQuantity() != null && promo.getQuantity() > 0) {
                promo.setQuantity(promo.getQuantity() - 1);
                percentPromotionRepository.save(promo);
                log.info("Decreased quantity for percent promotion: {} to {}", promo.getCode(), promo.getQuantity());
            }
        } else {
            AmountPromotion promo = amountPromotionRepository.findById(promotionId).orElse(null);
            if (promo != null && promo.getQuantity() != null && promo.getQuantity() > 0) {
                promo.setQuantity(promo.getQuantity() - 1);
                amountPromotionRepository.save(promo);
                log.info("Decreased quantity for amount promotion: {} to {}", promo.getCode(), promo.getQuantity());
            }
        }
    }

    private boolean isPromotionValid(Promotion promo, BigDecimal cartTotal, LocalDateTime now) {
        // Check if promotion is within valid date range
        if (promo.getEffective() != null && now.isBefore(promo.getEffective())) {
            return false;
        }
        if (promo.getExpiration() != null && now.isAfter(promo.getExpiration())) {
            return false;
        }
        
        // Check if quantity is available
        if (promo.getQuantity() != null && promo.getQuantity() <= 0) {
            return false;
        }
        
        // Check minimum order value
        if (promo.getMinOrderValue() != null && cartTotal.compareTo(promo.getMinOrderValue()) < 0) {
            return false;
        }
        
        return true;
    }

    private PromotionSummary buildPercentPromotionSummary(PercentPromotion promo) {
        return PromotionSummary.builder()
                .id(promo.getPromotionId())
                .name(promo.getName())
                .code(promo.getCode())
                .type("PERCENTAGE")
                .discountPercent(BigDecimal.valueOf(promo.getPercent()))
                .discountAmount(null)
                .minOrderValue(promo.getMinOrderValue())
                .startAt(promo.getEffective())
                .endAt(promo.getExpiration())
                .remainingQuantity(promo.getQuantity())
                .isActive(promo.getIsActive())
                .description("Giảm " + promo.getPercent() + "% cho đơn hàng từ " + 
                        (promo.getMinOrderValue() != null ? promo.getMinOrderValue() : 0) + "đ" +
                        (promo.getMaxDiscount() > 0 ? " (tối đa " + promo.getMaxDiscount() + "đ)" : ""))
                .build();
    }
    
    private PromotionSummary buildAmountPromotionSummary(AmountPromotion promo) {
        return PromotionSummary.builder()
                .id(promo.getPromotionId())
                .name(promo.getName())
                .code(promo.getCode())
                .type("AMOUNT")
                .discountPercent(null)
                .discountAmount(BigDecimal.valueOf(promo.getDiscount()))
                .minOrderValue(promo.getMinOrderValue())
                .startAt(promo.getEffective())
                .endAt(promo.getExpiration())
                .remainingQuantity(promo.getQuantity())
                .isActive(promo.getIsActive())
                .description("Giảm " + promo.getDiscount() + "đ cho đơn hàng từ " + 
                        (promo.getMinOrderValue() != null ? promo.getMinOrderValue() : 0) + "đ")
                .build();
    }

    private AddressResponse buildAddressResponse(Address address) {
        String fullAddress = String.join(", ", 
                address.getAddressDetail(),
                address.getWard(),
                address.getCity()
        );
        
        return AddressResponse.builder()
                .id(address.getId())
                .customerId(address.getCustomer() != null ? address.getCustomer().getCustomerId() : null)
                .city(address.getCity())
                .ward(address.getWard())
                .addressDetail(address.getAddressDetail())
                .fullAddress(fullAddress)
                .build();
    }

    private String generateOrderCode() {
        return "ORD" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    // Inner class for promotion result
    @Data
    private static class PromotionResult {
        private String percentagePromotionId;
        private String amountPromotionId;
        private BigDecimal discountAmount;
        private PromotionSummary promotionSummary;
    }
}
