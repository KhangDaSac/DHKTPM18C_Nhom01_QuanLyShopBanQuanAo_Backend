package com.example.ModaMint_Backend.mapper;

import com.example.ModaMint_Backend.dto.request.customer.CustomerRequest;
import com.example.ModaMint_Backend.dto.response.customer.CustomerResponse;
import com.example.ModaMint_Backend.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CustomerMapper {
    
    @Mapping(target = "user", ignore = true)
    Customer toCustomer(CustomerRequest request);
    
    // MapStruct sẽ tự động map name, email, phone từ Customer entity
    CustomerResponse toCustomerResponse(Customer customer);
}
