package com.example.ModaMint_Backend.entity;

import com.example.ModaMint_Backend.entity.Order;
import com.example.ModaMint_Backend.enums.ShipmentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "shipments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "order_id")
    Long orderId;

    String carrier; //Tên công ty vận chuyển

    @Column(name = "tracking_number")
    String trackingNumber; // Mã vận chuyển

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    ShipmentStatus status; // Trạng thái vận chuyển (Đang giao, Đã giao, Đã hủy)

    @Column(name = "shipped_at")
    LocalDateTime shippedAt; // Ngày giao hàng

    @Column(name = "expected_delivery_at")
    LocalDateTime expectedDeliveryAt; // Ngày dự kiến giao hàng

    @CreationTimestamp
    @Column(name = "create_at")
    LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    Order order;
}