package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.PercentPromotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PercentPromotionRepository extends JpaRepository<PercentPromotion, String> {
    Optional<PercentPromotion> findByCode(String code);
    
    List<PercentPromotion> findByIsActive(Boolean isActive);
}
