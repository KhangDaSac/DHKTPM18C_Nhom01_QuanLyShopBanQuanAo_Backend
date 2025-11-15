package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @EntityGraph(attributePaths = {"customer", "customer.user", "product"})
    List<Review> findByProductId(Long productId);
    @EntityGraph(attributePaths = {"customer", "customer.user", "product"})
    List<Review> findByCustomerId(String customerId);
    @EntityGraph(attributePaths = {"customer", "customer.user", "product"})
    List<Review> findByOrderItemId(Long orderItemId);
    @EntityGraph(attributePaths = {"customer", "customer.user", "product"})
    List<Review> findTop10ByOrderByCreateAtDesc();
}
