package com.example.ModaMint_Backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public enum OrderStatus {
    PENDING("Chờ xác nhận"),
    PREPARING("Đang chuẩn bị hàng"),
    ARRIVED_AT_LOCATION("Đã đến địa điểm giao hàng"),
    SHIPPED("Đang giao hàng"),
    DELIVERED("Đã giao hàng"),
    CANCELLED("Đã hủy"),
    RETURNED("Đã trả hàng")
    ;

    String name;
}
