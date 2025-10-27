package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
    @Override
    @EntityGraph(attributePaths = {"productVariants", "brand", "category"})
    List<Product> findAll();
}
