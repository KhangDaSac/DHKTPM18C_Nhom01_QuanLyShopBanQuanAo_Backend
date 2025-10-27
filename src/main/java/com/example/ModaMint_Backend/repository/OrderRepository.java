package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderCode(String orderCode);
    
    List<Order> findByCustomerId(String customerId);
    
    List<Order> findByOrderStatus(String status);
}
