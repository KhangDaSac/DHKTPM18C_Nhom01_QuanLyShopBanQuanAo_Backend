package com.example.ModaMint_Backend.dto.request.cartitem;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemRequest {
    Long variantId;   // ID của ProductVariant
    Integer quantity; // Số lượng muốn thêm
}
