package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.dto.request.address.CreateAddressRequest;
import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.customer.AddressResponse;
import com.example.ModaMint_Backend.service.AddressService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AddressController {
    
    AddressService addressService;
    
    /**
     * Tạo địa chỉ mới
     * POST /api/v1/addresses
     * NOTE: Public endpoint to support guest checkout
     */
    @PostMapping
    public ApiResponse<AddressResponse> createAddress(@Valid @RequestBody CreateAddressRequest request) {
        log.info("Creating address for customer: {}", request.getCustomerId());
        
        AddressResponse response = addressService.createAddress(request);
        
        return ApiResponse.<AddressResponse>builder()
                .code(2000)
                .message("Tạo địa chỉ thành công!")
                .result(response)
                .build();
    }
    
    /**
     * Lấy danh sách địa chỉ của customer
     * GET /api/v1/addresses/customer/{customerId}
     */
    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ApiResponse<List<AddressResponse>> getCustomerAddresses(@PathVariable String customerId) {
        log.info("Fetching addresses for customer: {}", customerId);
        
        List<AddressResponse> addresses = addressService.getCustomerAddresses(customerId);
        
        return ApiResponse.<List<AddressResponse>>builder()
                .code(2000)
                .message("Lấy danh sách địa chỉ thành công!")
                .result(addresses)
                .build();
    }
    
    /**
     * Lấy thông tin địa chỉ theo ID
     * GET /api/v1/addresses/{id}
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ApiResponse<AddressResponse> getAddressById(@PathVariable Long id) {
        log.info("Fetching address with ID: {}", id);
        
        AddressResponse address = addressService.getAddressById(id);
        
        return ApiResponse.<AddressResponse>builder()
                .code(2000)
                .message("Lấy thông tin địa chỉ thành công!")
                .result(address)
                .build();
    }
}
