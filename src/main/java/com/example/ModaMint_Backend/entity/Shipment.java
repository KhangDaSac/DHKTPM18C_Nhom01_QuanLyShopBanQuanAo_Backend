package com.example.ModaMint_Backend.entity;

import com.example.ModaMint_Backend.enums.ShipmentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Shipment {
    String carrier;                    // Tên công ty vận chuyển
    String trackingNumber;             // Mã vận đơn
    
    @Enumerated(EnumType.STRING)
    ShipmentStatus status;             // Trạng thái vận chuyển
    
    @Column(name = "shipped_at")
    LocalDateTime shippedAt;           // Thời gian gửi hàng
    
    @Column(name = "expected_delivery_at")
    LocalDateTime expectedDeliveryAt;  // Thời gian dự kiến giao hàng
}
