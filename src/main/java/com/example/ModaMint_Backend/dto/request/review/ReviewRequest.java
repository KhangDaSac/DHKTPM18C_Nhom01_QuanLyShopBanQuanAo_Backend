package com.example.ModaMint_Backend.dto.request.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewRequest {
    @NotNull(message = "Mã sản phẩm không được để trống")
    Long productId;

    @NotNull(message = "Mã khách hàng không được để trống")
    String customerId;

    Long orderItemId;

    @NotNull(message = "Đánh giá không được để trống")
    @Min(value = 1, message = "Đánh giá tối thiểu là 1")
    @Max(value = 5, message = "Đánh giá tối đa là 5")
    Integer rating;

    String comment;
}
