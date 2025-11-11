package com.example.ModaMint_Backend.dto.request.promotion;

 import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PercentPromotionRequest {

    @NotBlank(message = "Tên khuyến mãi không được để trống")
    String name;

    @NotBlank(message = "Mã khuyến mãi không được để trống")
    String code;

    @NotNull(message = "Phần trăm giảm giá không được để trống")
    @DecimalMin(value = "0.01", message = "Phần trăm giảm giá phải lớn hơn 0")
    @DecimalMax(value = "100", message = "Phần trăm giảm giá không được vượt quá 100")
    BigDecimal discountPercent;

    @DecimalMin(value = "0", message = "Giảm giá tối đa phải lớn hơn hoặc bằng 0")
    BigDecimal maxDiscount;

    @NotNull(message = "Giá trị đơn tối thiểu không được để trống")
    @DecimalMin(value = "0", message = "Giá trị đơn tối thiểu phải lớn hơn hoặc bằng 0")
    BigDecimal minOrderValue;

    @NotNull(message = "Thời gian bắt đầu không được để trống")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime startAt;

    @NotNull(message = "Thời gian kết thúc không được để trống")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime endAt;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    Integer quantity;

    @Builder.Default
    Boolean isActive = true;
}

