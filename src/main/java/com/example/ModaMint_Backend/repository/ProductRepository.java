package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
