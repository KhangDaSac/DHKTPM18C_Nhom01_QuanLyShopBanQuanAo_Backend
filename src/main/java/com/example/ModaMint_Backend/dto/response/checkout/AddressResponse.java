package com.example.ModaMint_Backend.dto.response.checkout;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressResponse {
    Long id;
    String customerId;
    String city;
    String ward;
    String addressDetail;
    String fullAddress;
}
