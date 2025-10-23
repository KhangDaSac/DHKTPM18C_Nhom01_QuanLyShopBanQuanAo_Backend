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
        log.info("Creating customer with userId: {}", request.getUserId());
        
        // Check if user exists
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        
        // Check if customer already exists
        if (customerRepository.existsByUserId(request.getUserId())) {
            throw new AppException(ErrorCode.CUSTOMER_ALREADY_EXISTS);
        }
        
        Customer customer = Customer.builder()
                .userId(request.getUserId())
                .user(user)
                .build();
        
        Customer savedCustomer = customerRepository.save(customer);
        log.info("Customer created successfully with userId: {}", savedCustomer.getUserId());
        
        return customerMapper.toCustomerResponse(savedCustomer);
    }

    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(String userId) {
        log.info("Getting customer by userId: {}", userId);
        
        Customer customer = customerRepository.findByUserIdWithUser(userId)
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

    public CustomerResponse updateCustomer(String userId, CustomerRequest request) {
        log.info("Updating customer with userId: {}", userId);
        
        Customer customer = customerRepository.findByUserIdWithUser(userId)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
        
        // Check if new userId exists
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        
        // Check if new userId is already used by another customer
        if (!userId.equals(request.getUserId()) && customerRepository.existsByUserId(request.getUserId())) {
            throw new AppException(ErrorCode.CUSTOMER_ALREADY_EXISTS);
        }
        
        customer.setUserId(request.getUserId());
        customer.setUser(user);
        
        Customer updatedCustomer = customerRepository.save(customer);
        log.info("Customer updated successfully with userId: {}", updatedCustomer.getUserId());
        
        return customerMapper.toCustomerResponse(updatedCustomer);
    }

    public void deleteCustomer(String userId) {
        log.info("Deleting customer with userId: {}", userId);
        
        Customer customer = customerRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
        
        customerRepository.delete(customer);
        log.info("Customer deleted successfully with userId: {}", userId);
    }

}
