package com.example.ModaMint_Backend.entity;


import com.example.ModaMint_Backend.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderStatusHistory {
    @Column(nullable = false)
    LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    OrderStatus orderStatus;

    String message;
}
