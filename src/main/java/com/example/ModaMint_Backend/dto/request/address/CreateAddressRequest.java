package com.example.ModaMint_Backend.dto.request.address;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAddressRequest {
    
    @NotBlank(message = "Customer ID is required")
    String customerId;
    
    @NotBlank(message = "City is required")
    String city;
    
    @NotBlank(message = "District is required")
    String district;
    
    @NotBlank(message = "Ward is required")
    String ward;
    
    @NotBlank(message = "Address detail is required")
    String addressDetail;
}
