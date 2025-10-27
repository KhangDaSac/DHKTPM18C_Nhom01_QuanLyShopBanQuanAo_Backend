package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findById(Long id);
    Optional<Cart> findByCustomerId(String customerId);
}
