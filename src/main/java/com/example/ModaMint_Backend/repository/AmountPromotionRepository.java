package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.AmountPromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AmountPromotionRepository extends JpaRepository<AmountPromotion, Long> {
    Optional<AmountPromotion> findByCode(String code);
    
    List<AmountPromotion> findByIsActive(Boolean isActive);
    
    @Query("SELECT a FROM AmountPromotion a WHERE a.isActive = true " +
           "AND (a.startAt IS NULL OR a.startAt <= :now) " +
           "AND (a.endAt IS NULL OR a.endAt >= :now) " +
           "AND (a.quantity IS NULL OR a.quantity > 0)")
    List<AmountPromotion> findActivePromotions(@Param("now") LocalDateTime now);
}
