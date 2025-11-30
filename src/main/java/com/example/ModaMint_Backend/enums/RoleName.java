package com.example.ModaMint_Backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public enum RoleName {
    ADMIN("Quản trị viên"),
    STAFF("Nhân viên"),
    CUSTOMER("Khách hàng")
    ;
    String name;
}
