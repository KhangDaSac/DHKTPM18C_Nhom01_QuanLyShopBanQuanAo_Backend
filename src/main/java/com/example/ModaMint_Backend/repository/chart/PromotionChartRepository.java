package com.example.ModaMint_Backend.repository.chart;

import com.example.ModaMint_Backend.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for Promotion Chart Data
 * Provides queries for promotion effectiveness
 */
@Repository
public interface PromotionChartRepository extends JpaRepository<Order, Long> {

    /**
     * Get percent promotion statistics
     * Returns: [promotionName, discountPercent, ordersApplied]
     */
    @Query("SELECT pp.name, " +
           "pp.percent, " +
           "COUNT(o) " +
           "FROM Order o " +
           "JOIN o.percentPromotion pp " +
           "WHERE pp IS NOT NULL " +
           "AND (:dateFrom IS NULL OR o.createAt >= :dateFrom) " +
           "AND (:dateTo IS NULL OR o.createAt <= :dateTo) " +
           "GROUP BY pp.promotionId, pp.name, pp.percent " +
           "ORDER BY COUNT(o) DESC")
    List<Object[]> findPercentPromotionStats(@Param("dateFrom") LocalDateTime dateFrom,
                                             @Param("dateTo") LocalDateTime dateTo,
                                             Pageable pageable);

    /**
     * Get amount promotion statistics
     * Returns discount as negative value for chart display
     * Returns: [promotionName, discountAmount, ordersApplied]
     */
    @Query("SELECT ap.name, " +
           "ap.discount, " +
           "COUNT(o) " +
           "FROM Order o " +
           "JOIN o.amountPromotion ap " +
           "WHERE ap IS NOT NULL " +
           "AND (:dateFrom IS NULL OR o.createAt >= :dateFrom) " +
           "AND (:dateTo IS NULL OR o.createAt <= :dateTo) " +
           "GROUP BY ap.promotionId, ap.name, ap.discount " +
           "ORDER BY COUNT(o) DESC")
    List<Object[]> findAmountPromotionStats(@Param("dateFrom") LocalDateTime dateFrom,
                                            @Param("dateTo") LocalDateTime dateTo,
                                            Pageable pageable);
}
