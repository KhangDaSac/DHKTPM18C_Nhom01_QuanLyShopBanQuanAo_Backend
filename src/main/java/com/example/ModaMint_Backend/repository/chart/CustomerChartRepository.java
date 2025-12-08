package com.example.ModaMint_Backend.repository.chart;

import com.example.ModaMint_Backend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * Repository for Customer Chart Data
 * Provides queries for customer statistics
 */
@Repository
public interface CustomerChartRepository extends JpaRepository<Customer, String> {

    /**
     * Get total number of customers with User account
     * Excludes guest customers (those without User)
     */
    @Query("SELECT COUNT(c) FROM Customer c WHERE c.user IS NOT NULL")
    Long countTotalCustomers();

    /**
     * Count new customers created within date range
     * Based on User creation date
     */
    @Query("SELECT COUNT(c) FROM Customer c " +
           "JOIN c.user u " +
           "WHERE (:dateFrom IS NULL OR u.createAt >= :dateFrom) " +
           "AND (:dateTo IS NULL OR u.createAt <= :dateTo)")
    Long countNewCustomers(@Param("dateFrom") LocalDateTime dateFrom,
                           @Param("dateTo") LocalDateTime dateTo);
}
