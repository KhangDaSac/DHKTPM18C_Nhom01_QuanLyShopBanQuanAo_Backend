package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    
    @Query("SELECT DISTINCT p FROM Product p " +
           "LEFT JOIN FETCH p.productVariants " +
           "LEFT JOIN FETCH p.brand " +
           "LEFT JOIN FETCH p.category")
    List<Product> findAllWithImagesAndVariants();
    
    @Query("SELECT DISTINCT p FROM Product p " +
           "LEFT JOIN FETCH p.productVariants " +
           "LEFT JOIN FETCH p.brand " +
           "LEFT JOIN FETCH p.category " +
           "WHERE p.id = :id")
    Optional<Product> findByIdWithImagesAndVariants(Long id);
}
