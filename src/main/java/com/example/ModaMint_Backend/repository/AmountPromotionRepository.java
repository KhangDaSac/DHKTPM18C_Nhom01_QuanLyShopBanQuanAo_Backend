package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.AmountPromotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AmountPromotionRepository extends JpaRepository<AmountPromotion, Long> {
    Optional<AmountPromotion> findByCode(String code);
    
    List<AmountPromotion> findByIsActive(Boolean isActive);
}
