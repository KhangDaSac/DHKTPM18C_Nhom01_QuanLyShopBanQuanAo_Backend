package com.example.ModaMint_Backend.dto.response.order;

import com.example.ModaMint_Backend.enums.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class OrderStatusHistoryResponse {
    Long id;
    OrderStatus orderStatus;
    String message;
    LocalDateTime createdAt;
    String actor;
}
