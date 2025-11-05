package com.example.ModaMint_Backend.dto.response.review;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewResponse {
    Long id;
    Long productId;
    String customerId;
    Long orderItemId;
    Integer rating;
    String comment;
    LocalDateTime createAt;
    Set<String> images;
}
