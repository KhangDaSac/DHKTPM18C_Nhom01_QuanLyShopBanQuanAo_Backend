package com.example.ModaMint_Backend.repository.chart;

import com.example.ModaMint_Backend.entity.ProductVariant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Product Variant Chart Data
 * Provides queries for variant-level stock information
 */
@Repository
public interface VariantChartRepository extends JpaRepository<ProductVariant, Long> {

    /**
     * Get all active product variants with stock information
     * SKU format: PROD{productId}-VAR{variantId}
     * Variant name: Size - Color
     * Returns: [productName, variantName, sku, stockQty]
     */
    @Query("SELECT p.name, " +
           "CONCAT(pv.size, ' - ', pv.color), " +
           "CONCAT('PROD', CAST(p.id AS string), '-VAR', CAST(pv.id AS string)), " +
           "pv.quantity " +
           "FROM ProductVariant pv " +
           "JOIN pv.product p " +
           "WHERE pv.active = true AND p.active = true " +
           "ORDER BY pv.quantity ASC")
    List<Object[]> findAllVariantStock(Pageable pageable);

    /**
     * Get low stock variants (quantity < 5)
     */
    @Query("SELECT p.name, " +
           "CONCAT(pv.size, ' - ', pv.color), " +
           "CONCAT('PROD', CAST(p.id AS string), '-VAR', CAST(pv.id AS string)), " +
           "pv.quantity " +
           "FROM ProductVariant pv " +
           "JOIN pv.product p " +
           "WHERE pv.active = true AND p.active = true " +
           "AND pv.quantity < 5 " +
           "ORDER BY pv.quantity ASC")
    List<Object[]> findLowStockVariants(Pageable pageable);
}
