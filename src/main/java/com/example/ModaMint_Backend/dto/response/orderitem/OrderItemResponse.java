/*
 * @ (#) f.java     1.0    02-Dec-25
 *
 * Copyright (c) 2025 IUH. All rights reserved.
 */

package com.example.ModaMint_Backend.dto.response.orderitem;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

/*
 * @description:
 * @author: Nguyen Quoc Huy
 * @date:02-Dec-25
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemResponse {
    Long id;
    Long productId;
    Long productVariantId;
    String productVariantName; // Tên sản phẩm, cần ánh xạ từ ProductVariant (ví dụ: ProductVariant.product.name)
    String size;
    String color;
    BigDecimal unitPrice;
    Integer quantity;
    BigDecimal lineTotal;
    String productVariantImage;

}