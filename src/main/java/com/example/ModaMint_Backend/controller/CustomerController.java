package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.dto.request.customer.CustomerRequest;
import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.customer.CustomerResponse;
import com.example.ModaMint_Backend.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CustomerResponse>> createCustomer(
            @Valid @RequestBody CustomerRequest request) {
        log.info("Creating customer with userId: {}", request.getUserId());
        
        CustomerResponse response = customerService.createCustomer(request);
        
        ApiResponse<CustomerResponse> apiResponse = ApiResponse.<CustomerResponse>builder()
                .code(2000)
                .message("Customer created successfully")
                .result(response)
                .build();
        
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or (#userId == authentication.name)")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerById(
            @PathVariable String userId) {
        log.info("Getting customer by userId: {}", userId);
        
        CustomerResponse response = customerService.getCustomerById(userId);
        
        ApiResponse<CustomerResponse> apiResponse = ApiResponse.<CustomerResponse>builder()
                .code(2000)
                .message("Customer retrieved successfully")
                .result(response)
                .build();
        
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getAllCustomers() {
        log.info("Getting all customers");
        
        List<CustomerResponse> response = customerService.getAllCustomers();
        
        ApiResponse<List<CustomerResponse>> apiResponse = ApiResponse.<List<CustomerResponse>>builder()
                .code(2000)
                .message("Customers retrieved successfully")
                .result(response)
                .build();
        
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/active")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getAllActiveCustomers() {
        log.info("Getting all active customers");
        
        List<CustomerResponse> response = customerService.getAllActiveCustomers();
        
        ApiResponse<List<CustomerResponse>> apiResponse = ApiResponse.<List<CustomerResponse>>builder()
                .code(2000)
                .message("Active customers retrieved successfully")
                .result(response)
                .build();
        
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or (#userId == authentication.name)")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(
            @PathVariable String userId,
            @Valid @RequestBody CustomerRequest request) {
        log.info("Updating customer with userId: {}", userId);
        
        CustomerResponse response = customerService.updateCustomer(userId, request);
        
        ApiResponse<CustomerResponse> apiResponse = ApiResponse.<CustomerResponse>builder()
                .code(2000)
                .message("Customer updated successfully")
                .result(response)
                .build();
        
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(@PathVariable String userId) {
        log.info("Deleting customer with userId: {}", userId);
        
        customerService.deleteCustomer(userId);
        
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code(2000)
                .message("Customer deleted successfully")
                .result(null)
                .build();
        
        return ResponseEntity.ok(apiResponse);
    }
}
