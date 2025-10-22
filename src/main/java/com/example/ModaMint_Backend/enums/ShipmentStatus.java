package com.example.ModaMint_Backend.enums;

public enum ShipmentStatus {
    PENDING,           // Chờ xử lý
    PICKED_UP,         // Đã lấy hàng
    IN_TRANSIT,        // Đang vận chuyển
    OUT_FOR_DELIVERY,  // Đang giao hàng
    DELIVERED,         // Đã giao thành công
    FAILED_DELIVERY,   // Giao hàng thất bại
    RETURNED,          // Đã trả hàng
    CANCELLED          // Đã hủy
}
