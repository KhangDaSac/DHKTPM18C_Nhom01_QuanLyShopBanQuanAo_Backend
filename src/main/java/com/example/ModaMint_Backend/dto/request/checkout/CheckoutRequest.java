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
    
    @NotNull(message = "Shipping address ID is required")
    Long shippingAddressId;
    
    String percentagePromotionCode; // Mã giảm giá theo phần trăm (optional)
    
    String amountPromotionCode; // Mã giảm giá theo số tiền (optional)
    
    @NotNull(message = "Payment method is required")
    PaymentMethod paymentMethod; // COD, BANK_TRANSFER, E_WALLET
    
    String phone; // Số điện thoại nhận hàng
    
    String note; // Ghi chú đơn hàng
}
