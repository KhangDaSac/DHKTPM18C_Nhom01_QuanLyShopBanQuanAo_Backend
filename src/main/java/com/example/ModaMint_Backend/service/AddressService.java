package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.address.CreateAddressRequest;
import com.example.ModaMint_Backend.dto.response.customer.AddressResponse;
import com.example.ModaMint_Backend.entity.Address;
import com.example.ModaMint_Backend.entity.Customer;
import com.example.ModaMint_Backend.repository.AddressRepository;
import com.example.ModaMint_Backend.repository.CustomerRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AddressService {
    
    AddressRepository addressRepository;
    CustomerRepository customerRepository;
    
    /**
     * Tạo địa chỉ mới cho customer
     */
    public AddressResponse createAddress(CreateAddressRequest request) {
        log.info("Creating new address for customer: {}", request.getCustomerId());
        
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        // Build full address
        String fullAddress = String.format("%s, %s, %s, %s",
                request.getAddressDetail(),
                request.getWard(),
                request.getDistrict(),
                request.getCity());
        
        Address address = Address.builder()
                .customer(customer)
                .city(request.getCity())
                .district(request.getDistrict())
                .ward(request.getWard())
                .addressDetail(request.getAddressDetail())
                .fullAddress(fullAddress)
                .build();
        
        address = addressRepository.save(address);
        log.info("Address created with ID: {}", address.getId());
        
        return mapToResponse(address);
    }
    
    /**
     * Lấy danh sách địa chỉ của customer
     */
    public List<AddressResponse> getCustomerAddresses(String customerId) {
        log.info("Fetching addresses for customer: {}", customerId);
        
        List<Address> addresses = addressRepository.findByCustomer_UserId(customerId);
        
        return addresses.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Lấy thông tin địa chỉ theo ID
     */
    public AddressResponse getAddressById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        
        return mapToResponse(address);
    }
    
    private AddressResponse mapToResponse(Address address) {
        return AddressResponse.builder()
                .id(address.getId())
                .customerId(address.getCustomer().getUserId()) // Customer sử dụng userId thay vì id
                .city(address.getCity())
                .district(address.getDistrict())
                .ward(address.getWard())
                .addressDetail(address.getAddressDetail())
                .fullAddress(address.getFullAddress())
                .build();
    }
}
