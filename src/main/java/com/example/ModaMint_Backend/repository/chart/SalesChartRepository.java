package com.example.ModaMint_Backend.repository.chart;

import com.example.ModaMint_Backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

// Native queries return Object[] per row: [date, revenue, orders]

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
           "WHERE (o.orderStatus = 'DELIVERED' OR o.payment.paymentStatus = 'PAID') " +
           "AND (:dateFrom IS NULL OR o.createAt >= :dateFrom) " +
           "AND (:dateTo IS NULL OR o.createAt <= :dateTo)")
    BigDecimal calculateTotalRevenue(@Param("dateFrom") LocalDateTime dateFrom,
                                      @Param("dateTo") LocalDateTime dateTo);

    /**
     * Count total DELIVERED orders within date range
     */
    @Query("SELECT COUNT(o) FROM Order o " +
           "WHERE (o.orderStatus = 'DELIVERED' OR o.payment.paymentStatus = 'PAID') " +
           "AND (:dateFrom IS NULL OR o.createAt >= :dateFrom) " +
           "AND (:dateTo IS NULL OR o.createAt <= :dateTo)")
    Long countTotalOrders(@Param("dateFrom") LocalDateTime dateFrom,
                          @Param("dateTo") LocalDateTime dateTo);

    /**
     * Calculate average order value from DELIVERED orders
     */
    @Query("SELECT COALESCE(AVG(o.subTotal), 0) FROM Order o " +
           "WHERE (o.orderStatus = 'DELIVERED' OR o.payment.paymentStatus = 'PAID') " +
           "AND (:dateFrom IS NULL OR o.createAt >= :dateFrom) " +
           "AND (:dateTo IS NULL OR o.createAt <= :dateTo)")
    BigDecimal calculateAvgOrderValue(@Param("dateFrom") LocalDateTime dateFrom,
                                       @Param("dateTo") LocalDateTime dateTo);

    /**
     * Aggregate revenue and order count per day (yyyy-MM-dd) for orders that are DELIVERED or have PAID payment.
     */
    @Query(value = "SELECT DATE(o.create_at) as dt, COALESCE(SUM(o.sub_total),0) as revenue, COUNT(*) as orders " +
           "FROM orders o LEFT JOIN payments p ON p.order_id = o.id AND p.payment_status = 'PAID' " +
           "WHERE (o.order_status = 'DELIVERED' OR p.payment_status = 'PAID') " +
           "AND (:dateFrom IS NULL OR o.create_at >= :dateFrom) " +
           "AND (:dateTo IS NULL OR o.create_at <= :dateTo) " +
           "GROUP BY DATE(o.create_at) ORDER BY DATE(o.create_at)", nativeQuery = true)
    List<Object[]> findDailySales(@Param("dateFrom") LocalDateTime dateFrom,
                              @Param("dateTo") LocalDateTime dateTo);

    /**
     * Aggregate revenue and order count per month (YYYY-MM) for orders that are DELIVERED or have PAID payment.
     */
    @Query(value = "SELECT DATE_FORMAT(o.create_at, '%Y-%m') as mth, COALESCE(SUM(o.sub_total),0) as revenue, COUNT(*) as orders " +
           "FROM orders o LEFT JOIN payments p ON p.order_id = o.id AND p.payment_status = 'PAID' " +
           "WHERE (o.order_status = 'DELIVERED' OR p.payment_status = 'PAID') " +
           "AND (:dateFrom IS NULL OR o.create_at >= :dateFrom) " +
           "AND (:dateTo IS NULL OR o.create_at <= :dateTo) " +
           "GROUP BY DATE_FORMAT(o.create_at, '%Y-%m') ORDER BY DATE_FORMAT(o.create_at, '%Y-%m')", nativeQuery = true)
    List<Object[]> findMonthlySales(@Param("dateFrom") LocalDateTime dateFrom,
                                @Param("dateTo") LocalDateTime dateTo);
}
