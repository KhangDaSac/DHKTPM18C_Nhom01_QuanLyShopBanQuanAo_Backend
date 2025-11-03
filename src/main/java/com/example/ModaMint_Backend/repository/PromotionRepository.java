package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    
    Optional<Promotion> findByCode(String code);
    
    @Query("SELECT p FROM Promotion p WHERE p.isActive = true " +
           "AND p.startAt <= :now AND p.endAt >= :now " +
           "AND (p.quantity IS NULL OR p.quantity > 0)")
    List<Promotion> findActivePromotions(@Param("now") LocalDateTime now);
}
