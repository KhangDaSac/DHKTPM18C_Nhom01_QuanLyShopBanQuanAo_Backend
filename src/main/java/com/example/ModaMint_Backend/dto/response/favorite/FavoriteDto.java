package com.example.ModaMint_Backend.dto.response.favorite;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class FavoriteDto {
    Long id;
    Long productId;
    String productName;
    BigDecimal price;
}
