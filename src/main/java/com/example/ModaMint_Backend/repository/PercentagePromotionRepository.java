package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.PercentagePromotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PercentagePromotionRepository extends JpaRepository<PercentagePromotion, Long> {
    Optional<PercentagePromotion> findByCode(String code);
    
    List<PercentagePromotion> findByIsActive(Boolean isActive);
}
