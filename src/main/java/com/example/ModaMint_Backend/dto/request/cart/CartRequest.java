package com.example.ModaMint_Backend.dto.request.cart;

import com.example.ModaMint_Backend.dto.request.cartitem.CartItemRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CartRequest {
    @NotBlank(message = "Customer ID is required")
    String customerId;
    List<CartItemRequest> items;
}
