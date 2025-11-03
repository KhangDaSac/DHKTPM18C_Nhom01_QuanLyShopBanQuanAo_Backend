package com.example.ModaMint_Backend.dto.response.checkout;

import com.example.ModaMint_Backend.dto.response.cart.CartItemResponse;
import com.example.ModaMint_Backend.dto.response.customer.AddressResponse;
import com.example.ModaMint_Backend.dto.response.promotion.PromotionSummary;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckoutResponse {
    
    Long orderId;
    
    String orderCode;
    
    String customerId;
    
    String customerName;
    
    String customerEmail;
    
    String customerPhone;
    
    AddressResponse shippingAddress;
    
    List<CartItemResponse> orderItems;
    
    BigDecimal subtotal; // Tổng tiền hàng
    
    BigDecimal shippingFee; // Phí vận chuyển
    
    PromotionSummary appliedPromotion; // Thông tin mã giảm giá đã áp dụng
    
    BigDecimal discountAmount; // Số tiền được giảm
    
    BigDecimal totalAmount; // Tổng tiền cuối cùng (subtotal + shippingFee - discountAmount)
    
    String paymentMethod;
    
    String orderStatus;
    
    String message;
}
