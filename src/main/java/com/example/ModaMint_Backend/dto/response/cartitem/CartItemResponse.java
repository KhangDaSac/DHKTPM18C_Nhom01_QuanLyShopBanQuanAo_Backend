package com.example.ModaMint_Backend.dto.response.cartitem;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {
    Long id;
    Long variantId;
    String productName;
    String color;
    String size;
    BigDecimal price;
    Integer quantity;
    String imageUrl;
}
