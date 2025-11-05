package com.example.ModaMint_Backend.dto.response.order;

import com.example.ModaMint_Backend.enums.OrderStatus;
import com.example.ModaMint_Backend.enums.PaymentMethod;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Long id;
    String orderCode;
    String customerId;
    BigDecimal totalAmount;
    BigDecimal subTotal;
    Long promotionId;
    BigDecimal promotionValue;
    OrderStatus orderStatus;
    PaymentMethod paymentMethod;
    Long shippingAddressId;
    String phone;
    LocalDateTime createAt;
    LocalDateTime updateAt;
}
