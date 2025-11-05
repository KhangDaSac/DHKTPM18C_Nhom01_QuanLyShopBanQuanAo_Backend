package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.customer.CustomerRequest;
import com.example.ModaMint_Backend.dto.response.customer.CustomerResponse;
import com.example.ModaMint_Backend.entity.Customer;
import com.example.ModaMint_Backend.entity.User;
import com.example.ModaMint_Backend.exception.AppException;
import com.example.ModaMint_Backend.exception.ErrorCode;
import com.example.ModaMint_Backend.mapper.CustomerMapper;
import com.example.ModaMint_Backend.repository.CustomerRepository;
import com.example.ModaMint_Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final CustomerMapper customerMapper;

    public CustomerResponse createCustomer(CustomerRequest request) {
        log.info("Creating customer with customerId: {}", request.getCustomerId());
        
        // Check if user exists (customerId maps to user.id via @MapsId)
        User user = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        
        // Check if customer already exists
        if (customerRepository.existsByCustomerId(request.getCustomerId())) {
            throw new AppException(ErrorCode.CUSTOMER_ALREADY_EXISTS);
        }
        
        Customer customer = Customer.builder()
                .customerId(request.getCustomerId())
                .user(user)
                .build();
        
        Customer savedCustomer = customerRepository.save(customer);
        log.info("Customer created successfully with customerId: {}", savedCustomer.getCustomerId());
        
        return customerMapper.toCustomerResponse(savedCustomer);
    }

    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(String customerId) {
        log.info("Getting customer by customerId: {}", customerId);
        
        Customer customer = customerRepository.findByCustomerIdWithUser(customerId)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
        
        return customerMapper.toCustomerResponse(customer);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllCustomers() {
        log.info("Getting all customers");
        
        List<Customer> customers = customerRepository.findAllWithUser();
        return customers.stream()
                .map(customerMapper::toCustomerResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllActiveCustomers() {
        log.info("Getting all active customers");
        
        List<Customer> customers = customerRepository.findAllActiveCustomers();
        return customers.stream()
                .map(customerMapper::toCustomerResponse)
                .collect(Collectors.toList());
    }

    public CustomerResponse updateCustomer(String customerId, CustomerRequest request) {
        log.info("Updating customer with customerId: {}", customerId);
        
        Customer customer = customerRepository.findByCustomerIdWithUser(customerId)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
        
        // Check if new customerId exists (must exist as a User)
        User user = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        
        // Check if new customerId is already used by another customer
        if (!customerId.equals(request.getCustomerId()) && customerRepository.existsByCustomerId(request.getCustomerId())) {
            throw new AppException(ErrorCode.CUSTOMER_ALREADY_EXISTS);
        }
        
        customer.setCustomerId(request.getCustomerId());
        customer.setUser(user);
        
        Customer updatedCustomer = customerRepository.save(customer);
        log.info("Customer updated successfully with customerId: {}", updatedCustomer.getCustomerId());
        
        return customerMapper.toCustomerResponse(updatedCustomer);
    }

    public void deleteCustomer(String customerId) {
        log.info("Deleting customer with customerId: {}", customerId);
        
        Customer customer = customerRepository.findByCustomerIdWithUser(customerId)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
        
        customerRepository.delete(customer);
        log.info("Customer deleted successfully with customerId: {}", customerId);
    }

}
