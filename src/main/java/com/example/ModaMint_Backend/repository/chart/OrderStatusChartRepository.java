package com.example.ModaMint_Backend.repository.chart;

import com.example.ModaMint_Backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for Order Status Chart Data
 * Provides queries for order status distribution
 */
@Repository
public interface OrderStatusChartRepository extends JpaRepository<Order, Long> {

    /**
     * Get order count grouped by status
     * Returns all order statuses with their counts
     * Returns: [orderStatus, totalOrders]
     */
    @Query("SELECT o.orderStatus, " +
           "COUNT(o) " +
           "FROM Order o " +
           "WHERE (:dateFrom IS NULL OR o.createAt >= :dateFrom) " +
           "AND (:dateTo IS NULL OR o.createAt <= :dateTo) " +
           "GROUP BY o.orderStatus " +
           "ORDER BY COUNT(o) DESC")
    List<Object[]> findOrderStatusDistribution(@Param("dateFrom") LocalDateTime dateFrom,
                                               @Param("dateTo") LocalDateTime dateTo);
}
