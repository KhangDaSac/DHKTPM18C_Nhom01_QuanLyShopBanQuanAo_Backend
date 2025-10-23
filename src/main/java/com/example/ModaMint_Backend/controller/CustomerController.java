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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
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
