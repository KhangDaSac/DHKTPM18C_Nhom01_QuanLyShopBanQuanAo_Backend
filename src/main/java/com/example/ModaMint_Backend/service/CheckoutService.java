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
import java.util.Optional;
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
    private final EmailService emailService;
    private final UserRepository userRepository;

    /**
     * Lấy danh sách mã giảm giá khả dụng cho đơn hàng
     */
    public List<PromotionSummary> getAvailablePromotions(String customerId) {
        log.info("Getting available promotions for customer: {}", customerId);
        
        BigDecimal cartTotal = BigDecimal.ZERO;
        LocalDateTime now = LocalDateTime.now();
        
        // For guest customers, return all active promotions
        if (!"guest".equalsIgnoreCase(customerId)) {
            // Lấy giỏ hàng và tính tổng tiền for registered customers
            Cart cart = cartRepository.findByCustomerId(customerId).orElse(null);
            if (cart != null) {
                cartTotal = calculateCartTotal(cart.getId());
            }
        }
        
        List<PromotionSummary> promotions = new ArrayList<>();

        List<PercentPromotion> percentPromotions = percentPromotionRepository.findByIsActive(true);
        for (PercentPromotion promo : percentPromotions) {
            if (isPromotionValidForGuest(promo, cartTotal, now, "guest".equalsIgnoreCase(customerId))) {
                promotions.add(buildPercentPromotionSummary(promo));
            }
        }

        List<AmountPromotion> amountPromotions = amountPromotionRepository.findByIsActive(true);
        for (AmountPromotion promo : amountPromotions) {
            if (isPromotionValidForGuest(promo, cartTotal, now, "guest".equalsIgnoreCase(customerId))) {
                promotions.add(buildAmountPromotionSummary(promo));
            }
        }
        
        log.info("Found {} available promotions", promotions.size());
        return promotions;
    }
    
    private boolean isPromotionValidForGuest(Promotion promo, BigDecimal cartTotal, LocalDateTime now, boolean isGuest) {
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
        
        // For guests, skip minimum order value check (will be validated at checkout)
        if (!isGuest && promo.getMinOrderValue() != null && cartTotal.compareTo(promo.getMinOrderValue()) < 0) {
            return false;
        }
        
        return true;
    }


    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) {
        log.info("=== Starting checkout process ===");
        log.info("Customer ID: {}, isGuest: {}", request.getCustomerId(), request.getIsGuest());
        log.info("Payment method: {}", request.getPaymentMethod());
        
        try {
            Customer customer;
            Cart cart = null;
            List<CartItem> cartItems = new ArrayList<>();
            Address address;
            
            // Handle guest vs registered customer
            boolean isGuestCheckout = Boolean.TRUE.equals(request.getIsGuest());
            log.info("Guest checkout: {}", isGuestCheckout);
        
        if (isGuestCheckout) {
            // For guest checkout, create and save customer record to database
            log.info("=== Processing GUEST checkout ===");
            log.info("Guest name: {}", request.getGuestName());
            log.info("Guest email: {}", request.getGuestEmail());
            log.info("Guest phone: {}", request.getPhone());
            
            // For guests, validate that cart items are provided in request
            if (request.getGuestCartItems() == null || request.getGuestCartItems().isEmpty()) {
                log.error("Guest cart items are null or empty");
                throw new AppException(ErrorCode.CART_EMPTY);
            }
            log.info("Guest cart has {} items", request.getGuestCartItems().size());
            
            // Validate guest address info
            if (request.getCity() == null || request.getDistrict() == null || 
                request.getWard() == null || request.getAddressDetail() == null) {
                log.error("Guest address info is incomplete: city={}, district={}, ward={}, detail={}", 
                    request.getCity(), request.getDistrict(), request.getWard(), request.getAddressDetail());
                throw new AppException(ErrorCode.INVALID_KEY);
            }
            log.info("Guest address: {}, {}, {}, {}", 
                request.getCity(), request.getDistrict(), request.getWard(), request.getAddressDetail());
            
            // Check if customer with same phone, email AND name already exists
            Optional<Customer> existingCustomer = customerRepository.findByPhoneAndEmailAndName(
                request.getPhone(), 
                request.getGuestEmail(), 
                request.getGuestName()
            );
            
            if (existingCustomer.isPresent()) {
                // Reuse existing customer (all 3 fields match)
                customer = existingCustomer.get();
                log.info("Reusing existing customer with phone: {}, email: {}, name: {} - ID: {}", 
                    request.getPhone(), request.getGuestEmail(), request.getGuestName(), customer.getCustomerId());
            } else {
                // Create new customer (at least one field is different)
                log.info("Creating new guest customer - phone: {}, email: {}, name: {}", 
                    request.getPhone(), request.getGuestEmail(), request.getGuestName());
                
                customer = Customer.builder()
                        .phone(request.getPhone())
                        .name(request.getGuestName())
                        .email(request.getGuestEmail())
                        .user(null) // No user for guest customers
                        .build();
                customer = customerRepository.save(customer);
                log.info("Created new guest customer with ID: {}", customer.getCustomerId());
            }
            
            // Create and save Address for guest
            address = Address.builder()
                    .customer(customer)
                    .city(request.getCity())
                    .district(request.getDistrict())
                    .ward(request.getWard())
                    .addressDetail(request.getAddressDetail())
                    .build();
            address = addressRepository.save(address);
            log.info("Created guest address with ID: {}", address.getId());
            
            // Convert guest cart items to CartItem objects for processing
            for (CheckoutRequest.GuestCartItem guestItem : request.getGuestCartItems()) {
                CartItem cartItem = new CartItem();
                cartItem.setVariantId(guestItem.getVariantId());
                cartItem.setQuantity(guestItem.getQuantity());
                cartItems.add(cartItem);
            }
        } else {
            // 1. Validate registered customer
            customer = customerRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
            
            // 2. Validate address
            address = addressRepository.findById(request.getShippingAddressId())
                    .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));
            
            // Check address ownership for registered users
            if (!address.getCustomer().getUser().getId().equals(request.getCustomerId())) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }
            
            // 3. Get cart items for registered customer
            cart = cartRepository.findByCustomerId(request.getCustomerId())
                    .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
            
            cartItems = cartItemRepository.findByCartId(cart.getId());
            
            if (cartItems.isEmpty()) {
                throw new AppException(ErrorCode.CART_EMPTY);
            }
        }
        
        // 4. Update shippingAddressId with the created address for guest
        Long finalShippingAddressId;
        if (isGuestCheckout) {
            finalShippingAddressId = address.getId();
        } else {
            finalShippingAddressId = request.getShippingAddressId();
        }
        
        // 5. Calculate prices
        BigDecimal subtotal;
        if (isGuestCheckout) {
            // For guests, calculate from provided cart items
            subtotal = cartItems.stream()
                    .map(item -> {
                        ProductVariant variant = productVariantRepository.findById(item.getVariantId()).orElse(null);
                        if (variant == null) return BigDecimal.ZERO;
                        return variant.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            subtotal = calculateCartTotal(cart.getId());
        }
        BigDecimal shippingFee = BigDecimal.valueOf(30000); // Fixed shipping fee
        
        // 6. Apply promotion
        PromotionResult promotionResult = applyPromotion(
                request.getPercentagePromotionCode(),
                request.getAmountPromotionCode(),
                subtotal
        );
        
        BigDecimal discountAmount = promotionResult.getDiscountAmount();
        BigDecimal totalAmount = subtotal.add(shippingFee).subtract(discountAmount);
        
        // 7. Create order
        String orderCode = generateOrderCode();
        
        // Use actual customer ID from database (important for guests)
        String actualCustomerId = customer.getCustomerId();
        
        Order order = Order.builder()
                .orderCode(orderCode)
                .customerId(actualCustomerId)
                .totalAmount(subtotal.add(shippingFee)) // Tổng TRƯỚC giảm (theo entity definition)
                .subTotal(totalAmount) // Tổng SAU giảm = final price (theo entity definition)
                .percentPromotionId(promotionResult.getPercentagePromotionId())
                .amountPromotionId(promotionResult.getAmountPromotionId())
                .promotionValue(discountAmount)
                .orderStatus(OrderStatus.PENDING)
                .paymentMethod(request.getPaymentMethod())
                .shippingAddressId(finalShippingAddressId) // Use the created address ID for guest
                .phone(request.getPhone() != null ? request.getPhone() 
                    : (customer.getUser() != null ? customer.getUser().getPhone() : customer.getPhone()))
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
//        // 8. Clear cart after successful order (only for registered users)
//        if (!isGuestCheckout) {
//            cartService.clearCartForUser(request.getCustomerId());
//
//        } else {
//
//        }
        
        // 9. Update promotion quantity
        if (promotionResult.getPercentagePromotionId() != null) {
            decreasePromotionQuantity(promotionResult.getPercentagePromotionId(), true);
        }
        if (promotionResult.getAmountPromotionId() != null) {
            decreasePromotionQuantity(promotionResult.getAmountPromotionId(), false);
        }
        
        // 10. Build response
        CheckoutResponse response = CheckoutResponse.builder()
                .orderId(savedOrder.getId())
                .orderCode(orderCode)
//                .customerId(customer.getUserId())
                .customerName(customer.getUser() != null 
                    ? customer.getUser().getFirstName() + " " + customer.getUser().getLastName()
                    : customer.getName())
                .customerEmail(customer.getUser() != null 
                    ? customer.getUser().getEmail() 
                    : customer.getEmail())
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
        
        // 11. Send order confirmation email
        try {
            String recipientEmail = customer.getUser() != null 
                ? customer.getUser().getEmail() 
                : customer.getEmail();
            emailService.sendOrderConfirmationEmail(response, recipientEmail);
            log.info("Order confirmation email queued for: {}", recipientEmail);
        } catch (Exception e) {
            log.error("Failed to queue order confirmation email", e);
            // Don't fail the order if email fails
        }
        
        log.info("=== Checkout completed successfully ===");
        log.info("Order ID: {}, Order Code: {}", response.getOrderId(), response.getOrderCode());
        return response;
        
        } catch (Exception e) {
            log.error("=== CHECKOUT FAILED ===");
            log.error("Error type: {}", e.getClass().getName());
            log.error("Error message: {}", e.getMessage());
            log.error("Stack trace:", e);
            throw e;
        }
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
