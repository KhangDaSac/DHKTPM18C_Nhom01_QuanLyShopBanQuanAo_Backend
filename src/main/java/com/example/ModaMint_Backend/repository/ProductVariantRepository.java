package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.ProductVariant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    List<ProductVariant> findByProductId(Long productId);
    
    void deleteByProductId(Long productId);
    
    @Query("SELECT pv.color AS color, COUNT(pv.id) AS count " +
            "FROM ProductVariant pv " +
            "GROUP BY pv.color " +
            "ORDER BY COUNT(pv.id) DESC")
    List<Object[]> findTopColors();
    
    /**
     * Query for Variant Matrix (Heatmap data)
     * Returns: color, size, total quantity
     */
    @Query("SELECT pv.color, pv.size, SUM(pv.quantity) " +
           "FROM ProductVariant pv " +
           "WHERE pv.active = true " +
           "GROUP BY pv.color, pv.size " +
           "ORDER BY pv.color, pv.size")
    List<Object[]> findVariantMatrix();
}
