package com.example.ModaMint_Backend.repository.chart;

import com.example.ModaMint_Backend.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Inventory Chart Data
 * Provides queries for product stock levels
 */
@Repository
public interface InventoryChartRepository extends JpaRepository<Product, Long> {

    /**
     * Get inventory status for all active products
     * Aggregates total stock from all variants
     * minQty is set to 10 as default threshold
     * Returns: [productName, totalStock, minQty]
     */
    @Query("SELECT p.name, " +
           "COALESCE(SUM(pv.quantity), 0), " +
           "10 " +
           "FROM Product p " +
           "LEFT JOIN p.productVariants pv " +
           "WHERE p.active = true " +
           "GROUP BY p.id, p.name " +
           "ORDER BY COALESCE(SUM(pv.quantity), 0) ASC")
    List<Object[]> findInventoryStatus(Pageable pageable);

    /**
     * Get products with low stock (below threshold)
     */
    @Query("SELECT p.name, " +
           "COALESCE(SUM(pv.quantity), 0), " +
           "10 " +
           "FROM Product p " +
           "LEFT JOIN p.productVariants pv " +
           "WHERE p.active = true " +
           "GROUP BY p.id, p.name " +
           "HAVING COALESCE(SUM(pv.quantity), 0) < 10 " +
           "ORDER BY COALESCE(SUM(pv.quantity), 0) ASC")
    List<Object[]> findLowStockProducts(Pageable pageable);
}
