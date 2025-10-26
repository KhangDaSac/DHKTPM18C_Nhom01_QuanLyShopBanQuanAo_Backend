/*
 * @ (#) f.java     1.0    26-Oct-25
 *
 * Copyright (c) 2025 IUH. All rights reserved.
 */

package com.example.ModaMint_Backend.dto.response.promotion;

/*
 * @description:
 * @author: Nguyen Quoc Huy
 * @date:26-Oct-25
 * @version: 1.0
 */
// (Thay đổi package cho đúng)
import com.example.ModaMint_Backend.enums.PromotionType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionResponse {
    Long id;
    String code;
    String value; // "10%" hoặc "100000"
    PromotionType type; // PERCENT hoặc AMOUNT
    BigDecimal minOrderValue;
    LocalDateTime startAt;
    LocalDateTime endAt;
    Integer quantity;
    Boolean isActive;
}