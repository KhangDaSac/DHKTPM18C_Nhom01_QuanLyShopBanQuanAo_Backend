package com.example.ModaMint_Backend.dto.request.checkout;

import com.example.ModaMint_Backend.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckoutRequest {
    
    @NotBlank(message = "Customer ID is required")
    String customerId;
    
    Long shippingAddressId; // Can be null for guest checkout with new address
    
    String percentagePromotionCode; // Mã giảm giá theo phần trăm (optional)
    
    String amountPromotionCode; // Mã giảm giá theo số tiền (optional)
    
    @NotNull(message = "Payment method is required")
    PaymentMethod paymentMethod; // COD, BANK_TRANSFER, E_WALLET
    
    String phone; // Số điện thoại nhận hàng
    
    String note; // Ghi chú đơn hàng
    
    // Guest checkout fields
    Boolean isGuest; // true if guest checkout
    
    String guestName; // Name for guest customers
    
    String guestEmail; // Email for guest customers
    
    java.util.List<GuestCartItem> guestCartItems; // Cart items for guest checkout
    
    // Guest address fields (used when shippingAddressId is null)
    String city;
    String district;
    String ward;
    String addressDetail;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GuestCartItem {
        Long variantId;
        Integer quantity;
        Long unitPrice;
    }
}
