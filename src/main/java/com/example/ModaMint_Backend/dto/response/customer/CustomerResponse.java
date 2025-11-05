package com.example.ModaMint_Backend.dto.response.customer;

import com.example.ModaMint_Backend.dto.response.user.UserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CustomerResponse {
    String customerId;
    UserResponse user;
    Set<AddressResponse> addresses;
    CartResponse cart;
    Set<OrderResponse> orders;
    Set<ReviewResponse> reviews;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @FieldDefaults(level = lombok.AccessLevel.PRIVATE)
    public static class AddressResponse {
        Long id;
        String city;
        String district;
        String ward;
        String addressDetail;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @FieldDefaults(level = lombok.AccessLevel.PRIVATE)
    public static class CartResponse {
        Long id;
        String customerId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @FieldDefaults(level = lombok.AccessLevel.PRIVATE)
    public static class OrderResponse {
        Long id;
        String orderCode;
        String orderStatus;
        String paymentMethod;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @FieldDefaults(level = lombok.AccessLevel.PRIVATE)
    public static class ReviewResponse {
        Long id;
        Integer rating;
        String comment;
    }
}
