package com.example.ModaMint_Backend.dto.response.cart;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CartDto {
    Long id;
    String sessionId;
    List<CartItemDto> items;
    Long subtotal;
    Long shipping;
    Long total;
}
