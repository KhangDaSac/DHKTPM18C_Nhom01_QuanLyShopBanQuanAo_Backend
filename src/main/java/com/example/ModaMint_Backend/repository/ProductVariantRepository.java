package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
}

