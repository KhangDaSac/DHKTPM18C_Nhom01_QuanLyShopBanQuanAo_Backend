package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    
    @Query("SELECT c FROM Customer c JOIN FETCH c.user WHERE c.customerId = :customerId")
    Optional<Customer> findByCustomerIdWithUser(@Param("customerId") String customerId);
    
    @Query("SELECT c FROM Customer c JOIN FETCH c.user")
    List<Customer> findAllWithUser();
    
    @Query("SELECT c FROM Customer c JOIN FETCH c.user WHERE c.user.active = true")
    List<Customer> findAllActiveCustomers();
    
    boolean existsByCustomerId(String customerId);
}
