package com.example.ModaMint_Backend.dto.request.order;

import com.example.ModaMint_Backend.enums.OrderStatus;
import com.example.ModaMint_Backend.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    @NotBlank(message = "Mã đơn hàng không được để trống")
    String orderCode;

    @NotBlank(message = "Mã khách hàng không được để trống")
    String customerId;

    @NotNull(message = "Tổng tiền không được để trống")
    BigDecimal totalAmount;

    BigDecimal subTotal;

    Long promotionId;

    BigDecimal promotionValue;

    OrderStatus orderStatus;

    PaymentMethod paymentMethod;

    Long shippingAddressId;

    String phone;
}
