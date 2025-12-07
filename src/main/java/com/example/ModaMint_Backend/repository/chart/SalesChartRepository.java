package com.example.ModaMint_Backend.repository.chart;

import com.example.ModaMint_Backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Repository for Sales Chart Data
 * Provides queries for revenue calculations
 */
@Repository
public interface SalesChartRepository extends JpaRepository<Order, Long> {

    /**
     * Calculate total revenue from DELIVERED orders within date range
     * Uses subTotal which is the final amount after promotions
     */
    @Query("SELECT COALESCE(SUM(o.subTotal), 0) FROM Order o " +
           "WHERE o.orderStatus = 'DELIVERED' " +
           "AND (:dateFrom IS NULL OR o.createAt >= :dateFrom) " +
           "AND (:dateTo IS NULL OR o.createAt <= :dateTo)")
    BigDecimal calculateTotalRevenue(@Param("dateFrom") LocalDateTime dateFrom,
                                      @Param("dateTo") LocalDateTime dateTo);

    /**
     * Count total DELIVERED orders within date range
     */
    @Query("SELECT COUNT(o) FROM Order o " +
           "WHERE o.orderStatus = 'DELIVERED' " +
           "AND (:dateFrom IS NULL OR o.createAt >= :dateFrom) " +
           "AND (:dateTo IS NULL OR o.createAt <= :dateTo)")
    Long countTotalOrders(@Param("dateFrom") LocalDateTime dateFrom,
                          @Param("dateTo") LocalDateTime dateTo);

    /**
     * Calculate average order value from DELIVERED orders
     */
    @Query("SELECT COALESCE(AVG(o.subTotal), 0) FROM Order o " +
           "WHERE o.orderStatus = 'DELIVERED' " +
           "AND (:dateFrom IS NULL OR o.createAt >= :dateFrom) " +
           "AND (:dateTo IS NULL OR o.createAt <= :dateTo)")
    BigDecimal calculateAvgOrderValue(@Param("dateFrom") LocalDateTime dateFrom,
                                       @Param("dateTo") LocalDateTime dateTo);
}
