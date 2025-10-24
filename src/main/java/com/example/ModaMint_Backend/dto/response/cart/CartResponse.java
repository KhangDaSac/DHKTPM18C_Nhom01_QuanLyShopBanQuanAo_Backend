package com.example.ModaMint_Backend.dto.response.cart;

import com.example.ModaMint_Backend.dto.response.cartitem.CartItemResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CartResponse {
    Long id;
    String customerId;
    List<CartItemResponse> items;
    BigDecimal totalPrice;
}
