package com.example.ModaMint_Backend.dto.request.cartitem;

import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CartItemRequest {
    @NotNull(message = "Variant ID is required")
    Long variantId;

    @Positive(message = "Quantity must be greater than 0")
    Integer quantity;
}
