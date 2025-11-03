package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.checkout.CheckoutRequest;
import com.example.ModaMint_Backend.dto.response.cart.CartItemResponse;
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
public class CheckoutService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final ProductVariantRepository productVariantRepository;
    private final PercentagePromotionRepository percentagePromotionRepository;
    private final AmountPromotionRepository amountPromotionRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

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
        
        // Lấy percentage promotions
        List<PercentagePromotion> percentagePromotions = percentagePromotionRepository
                .findActivePromotions(now);
        
        for (PercentagePromotion promo : percentagePromotions) {
            if (promo.getQuantity() != null && promo.getQuantity() > 0) {
                if (promo.getMinOrderValue() == null || cartTotal.compareTo(promo.getMinOrderValue()) >= 0) {
                    promotions.add(buildPromotionSummary(promo));
                }
            }
        }
        
        // Lấy amount promotions
        List<AmountPromotion> amountPromotions = amountPromotionRepository
                .findActivePromotions(now);
        
        for (AmountPromotion promo : amountPromotions) {
            if (promo.getQuantity() != null && promo.getQuantity() > 0) {
                if (promo.getMinOrderValue() == null || cartTotal.compareTo(promo.getMinOrderValue()) >= 0) {
                    promotions.add(buildPromotionSummary(promo));
                }
            }
        }
        
        log.info("Found {} available promotions", promotions.size());
        return promotions;
    }

    /**
     * Thực hiện checkout và tạo đơn hàng
     */
    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) {
        log.info("Processing checkout for customer: {}", request.getCustomerId());
        
        // 1. Validate customer
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
        
        // 2. Validate address
        Address address = addressRepository.findById(request.getShippingAddressId())
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));
        
        if (!address.getCustomer().getCustomerId().equals(request.getCustomerId())) {
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
                .percentagePromotionId(promotionResult.getPercentagePromotionId())
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
        List<CartItemResponse> orderItemResponses = new ArrayList<>();
        
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
            orderItemResponses.add(CartItemResponse.builder()
                    .id(cartItem.getId())
                    .variantId(variant.getId())
                    .productName(variant.getProduct() != null ? variant.getProduct().getName() : null)
                    .color(variant.getColor())
                    .size(variant.getSize())
                    .price(variant.getPrice())
                    .quantity(cartItem.getQuantity())
                    .imageUrl(variant.getImage())
                    .build());
        }
        
        // 8. Clear cart after successful order
        cartItemRepository.deleteAllByCartId(cart.getId());
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
                .customerId(customer.getCustomerId())
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
            PercentagePromotion promo = percentagePromotionRepository.findByCode(percentageCode)
                    .orElse(null);
            
            if (promo != null && validatePromotion(promo, orderTotal, now)) {
                BigDecimal discount = orderTotal.multiply(promo.getDiscountPercent())
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                
                result.setPercentagePromotionId(promo.getId());
                result.setDiscountAmount(discount);
                result.setPromotionSummary(buildPromotionSummary(promo));
                
                log.info("Applied percentage promotion: {} - {}%", promo.getCode(), promo.getDiscountPercent());
                return result;
            }
        }
        
        // Try amount promotion if percentage not applied
        if (amountCode != null && !amountCode.isBlank()) {
            AmountPromotion promo = amountPromotionRepository.findByCode(amountCode)
                    .orElse(null);
            
            if (promo != null && validatePromotion(promo, orderTotal, now)) {
                result.setAmountPromotionId(promo.getId());
                result.setDiscountAmount(promo.getDiscountAmount());
                result.setPromotionSummary(buildPromotionSummary(promo));
                
                log.info("Applied amount promotion: {} - {}", promo.getCode(), promo.getDiscountAmount());
                return result;
            }
        }
        
        return result;
    }

    private boolean validatePromotion(PercentagePromotion promo, BigDecimal orderTotal, LocalDateTime now) {
        if (!promo.getIsActive()) return false;
        if (promo.getQuantity() == null || promo.getQuantity() <= 0) return false;
        if (promo.getStartAt() != null && now.isBefore(promo.getStartAt())) return false;
        if (promo.getEndAt() != null && now.isAfter(promo.getEndAt())) return false;
        if (promo.getMinOrderValue() != null && orderTotal.compareTo(promo.getMinOrderValue()) < 0) return false;
        
        return true;
    }

    private boolean validatePromotion(AmountPromotion promo, BigDecimal orderTotal, LocalDateTime now) {
        if (!promo.getIsActive()) return false;
        if (promo.getQuantity() == null || promo.getQuantity() <= 0) return false;
        if (promo.getStartAt() != null && now.isBefore(promo.getStartAt())) return false;
        if (promo.getEndAt() != null && now.isAfter(promo.getEndAt())) return false;
        if (promo.getMinOrderValue() != null && orderTotal.compareTo(promo.getMinOrderValue()) < 0) return false;
        
        return true;
    }

    private void decreasePromotionQuantity(Long promotionId, boolean isPercentage) {
        if (isPercentage) {
            PercentagePromotion promo = percentagePromotionRepository.findById(promotionId).orElse(null);
            if (promo != null && promo.getQuantity() != null) {
                promo.setQuantity(promo.getQuantity() - 1);
                percentagePromotionRepository.save(promo);
            }
        } else {
            AmountPromotion promo = amountPromotionRepository.findById(promotionId).orElse(null);
            if (promo != null && promo.getQuantity() != null) {
                promo.setQuantity(promo.getQuantity() - 1);
                amountPromotionRepository.save(promo);
            }
        }
    }

    private PromotionSummary buildPromotionSummary(PercentagePromotion promo) {
        return PromotionSummary.builder()
                .id(promo.getId())
                .name(promo.getName())
                .code(promo.getCode())
                .type("PERCENTAGE")
                .discountPercent(promo.getDiscountPercent())
                .minOrderValue(promo.getMinOrderValue())
                .startAt(promo.getStartAt())
                .endAt(promo.getEndAt())
                .remainingQuantity(promo.getQuantity())
                .isActive(promo.getIsActive())
                .description("Giảm " + promo.getDiscountPercent() + "% cho đơn hàng từ " + 
                        (promo.getMinOrderValue() != null ? promo.getMinOrderValue() : 0) + "đ")
                .build();
    }

    private PromotionSummary buildPromotionSummary(AmountPromotion promo) {
        return PromotionSummary.builder()
                .id(promo.getId())
                .name(promo.getName())
                .code(promo.getCode())
                .type("AMOUNT")
                .discountAmount(promo.getDiscountAmount())
                .minOrderValue(promo.getMinOrderValue())
                .startAt(promo.getStartAt())
                .endAt(promo.getEndAt())
                .remainingQuantity(promo.getQuantity())
                .isActive(promo.getIsActive())
                .description("Giảm " + promo.getDiscountAmount() + "đ cho đơn hàng từ " + 
                        (promo.getMinOrderValue() != null ? promo.getMinOrderValue() : 0) + "đ")
                .build();
    }

    private AddressResponse buildAddressResponse(Address address) {
        String fullAddress = String.join(", ", 
                address.getAddressDetail(),
                address.getWard(),
                address.getDistrict(),
                address.getCity()
        );
        
        return AddressResponse.builder()
                .id(address.getId())
                .customerId(address.getCustomer() != null ? address.getCustomer().getCustomerId() : null)
                .city(address.getCity())
                .district(address.getDistrict())
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
        private Long percentagePromotionId;
        private Long amountPromotionId;
        private BigDecimal discountAmount;
        private PromotionSummary promotionSummary;
    }
}
