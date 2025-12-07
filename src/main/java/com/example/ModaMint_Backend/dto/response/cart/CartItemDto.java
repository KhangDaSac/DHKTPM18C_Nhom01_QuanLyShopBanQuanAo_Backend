package com.example.ModaMint_Backend.dto.response.cart;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CartItemDto {
    Long itemId;
    Long variantId;
    Long productId;
    String productName;
    String image;
    Long unitPrice;
    Integer quantity;
    Long totalPrice;
    String size;
    String color;
}
