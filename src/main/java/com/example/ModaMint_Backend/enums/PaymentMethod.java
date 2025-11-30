package com.example.ModaMint_Backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public enum PaymentMethod {
    CASH_ON_DELIVERY("Thanh toán khi nhận hàng"),
    BANK_TRANSFER("Chuyển khoản ngân hàng"),
    E_WALLET("Ví điện tử")
    ;
    String name;
}
