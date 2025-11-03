package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByCustomer_CustomerId(String customerId);
}
