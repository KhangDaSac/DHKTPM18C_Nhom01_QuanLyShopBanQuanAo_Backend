package com.example.ModaMint_Backend.repository.chart;

import com.example.ModaMint_Backend.entity.OrderItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for Best Products Chart Data
 * Provides queries for top selling products
 */
@Repository
public interface BestProductChartRepository extends JpaRepository<OrderItem, Long> {

    /**
     * Find best selling products ordered by total revenue DESC
     * Counts items from orders except those that were cancelled or returned.
     * This ensures products in active/orders-in-progress are included (not only DELIVERED).
     * Returns: [productName, qtySold, revenue]
     */
    @Query("SELECT p.name, " +
           "SUM(oi.quantity), " +
           "SUM(oi.unitPrice * oi.quantity) " +
           "FROM OrderItem oi " +
           "JOIN oi.productVariant pv " +
           "JOIN pv.product p " +
           "JOIN oi.order o " +
           "WHERE o.orderStatus NOT IN ('CANCELLED', 'RETURNED') " +
           "AND (:dateFrom IS NULL OR o.createAt >= :dateFrom) " +
           "AND (:dateTo IS NULL OR o.createAt <= :dateTo) " +
           "GROUP BY p.id, p.name " +
           "ORDER BY SUM(oi.unitPrice * oi.quantity) DESC")
    List<Object[]> findBestSellingProducts(@Param("dateFrom") LocalDateTime dateFrom,
                                           @Param("dateTo") LocalDateTime dateTo,
                                           Pageable pageable);
}
