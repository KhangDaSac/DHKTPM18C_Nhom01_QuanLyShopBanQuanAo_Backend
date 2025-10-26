package com.example.ModaMint_Backend.dto.request.cart;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class AddCartItemRequest {
    Long variantId;
    Long productId;
    Integer quantity;
    String sessionId; // optional for anonymous
}
