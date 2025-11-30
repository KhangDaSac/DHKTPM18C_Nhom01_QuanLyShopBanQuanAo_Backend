package com.example.ModaMint_Backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public enum SenderType {
    CUSTOMER("Khách hàng"),
    STAFF("Nhân viên"),
    AI("AI")
    ;
    String name;
}
