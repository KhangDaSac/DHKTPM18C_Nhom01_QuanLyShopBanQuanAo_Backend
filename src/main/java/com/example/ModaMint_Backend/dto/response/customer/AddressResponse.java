package com.example.ModaMint_Backend.dto.response.customer;

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
    
    String district;
    
    String ward;
    
    String addressDetail;
    
    String fullAddress; // city + district + ward + addressDetail
}
