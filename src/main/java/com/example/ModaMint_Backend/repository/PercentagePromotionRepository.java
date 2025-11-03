package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.PercentagePromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PercentagePromotionRepository extends JpaRepository<PercentagePromotion, Long> {
    Optional<PercentagePromotion> findByCode(String code);
    
    List<PercentagePromotion> findByIsActive(Boolean isActive);
    
    @Query("SELECT p FROM PercentagePromotion p WHERE p.isActive = true " +
           "AND (p.startAt IS NULL OR p.startAt <= :now) " +
           "AND (p.endAt IS NULL OR p.endAt >= :now) " +
           "AND (p.quantity IS NULL OR p.quantity > 0)")
    List<PercentagePromotion> findActivePromotions(@Param("now") LocalDateTime now);
}
