package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.Order;
import com.example.ModaMint_Backend.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(String customerId);
    
    List<Order> findByOrderCode(String orderCode);
    
    List<Order> findByOrderStatus(OrderStatus orderStatus);
}
