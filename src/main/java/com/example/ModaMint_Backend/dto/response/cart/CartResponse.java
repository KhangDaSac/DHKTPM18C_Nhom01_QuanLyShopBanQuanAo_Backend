package com.example.ModaMint_Backend.dto.response.cart;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {
    Long id;
    String sessionId;
    List<CartItemDto> items;
    Long subtotal;
    Long shipping;
    Long total;

}
