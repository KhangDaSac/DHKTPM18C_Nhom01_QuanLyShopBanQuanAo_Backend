package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductId(Long productId);
    
    List<Review> findByCustomerId(String customerId);
    
    List<Review> findByOrderItemId(Long orderItemId);
}
