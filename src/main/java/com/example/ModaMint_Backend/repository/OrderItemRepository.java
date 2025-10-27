package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);
    
    List<OrderItem> findByProductVariantId(Long productVariantId);
}
