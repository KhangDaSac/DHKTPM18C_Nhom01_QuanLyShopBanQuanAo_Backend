package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.promotion.PercentPromotionRequest;
import com.example.ModaMint_Backend.dto.response.promotion.PercentPromotionResponse;
import com.example.ModaMint_Backend.entity.PercentPromotion;
import com.example.ModaMint_Backend.exception.AppException;
import com.example.ModaMint_Backend.exception.ErrorCode;
import com.example.ModaMint_Backend.mapper.PercentPromotionMapper;
import com.example.ModaMint_Backend.repository.PercentPromotionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PercentPromotionService {
    PercentPromotionRepository percentPromotionRepository;
    PercentPromotionMapper percentPromotionMapper;

    public PercentPromotionResponse createPercentagePromotion(PercentPromotionRequest request) {
        if (percentPromotionRepository.findByCode(request.getCode()).isPresent()) {
            throw new AppException(ErrorCode.PROMOTION_CODE_EXISTED);
        }

        PercentPromotion percentPromotion = percentPromotionMapper.toPercentPromotion(request);
        PercentPromotion savedPromotion = percentPromotionRepository.save(percentPromotion);
        return percentPromotionMapper.toPercentPromotionResponse(savedPromotion);
    }

    public List<PercentPromotionResponse> getAllPercentagePromotions() {
        return percentPromotionRepository.findAll()
                .stream()
                .map(percentPromotionMapper::toPercentPromotionResponse)
                .toList();
    }


    public PercentPromotionResponse getPercentagePromotionById(String id) {
        PercentPromotion percentPromotion = percentPromotionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_NOT_FOUND));
        return percentPromotionMapper.toPercentPromotionResponse(percentPromotion);
    }

    public PercentPromotionResponse getPercentagePromotionByCode(String code) {
        PercentPromotion percentPromotion = percentPromotionRepository.findByCode(code)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_NOT_FOUND));
        return percentPromotionMapper.toPercentPromotionResponse(percentPromotion);
    }

    public PercentPromotionResponse updatePercentagePromotion(String id, PercentPromotionRequest request) {
        PercentPromotion percentPromotion = percentPromotionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_NOT_FOUND));

        if (!percentPromotion.getCode().equals(request.getCode()) &&
                percentPromotionRepository.findByCode(request.getCode()).isPresent()) {
            throw new AppException(ErrorCode.PROMOTION_CODE_EXISTED);
        }

        percentPromotionMapper.updatePercentagePromotion(request, percentPromotion);
        PercentPromotion updatedPromotion = percentPromotionRepository.save(percentPromotion);
        return percentPromotionMapper.toPercentPromotionResponse(updatedPromotion);
    }

    public void deletePercentagePromotion(String id) {
        if (!percentPromotionRepository.existsById(id)) {
            throw new AppException(ErrorCode.PROMOTION_NOT_FOUND);
        }
        percentPromotionRepository.deleteById(id);
    }

    public List<PercentPromotionResponse> getActivePercentagePromotions() {
        return percentPromotionRepository.findByIsActive(true)
                .stream()
                .map(percentPromotionMapper::toPercentPromotionResponse)
                .toList();
    }

    public long getTotalPercentagePromotionCount() {
        return percentPromotionRepository.count();
    }

    public long getActivePercentagePromotionCount() {
        return percentPromotionRepository.findByIsActive(true).size();
    }


    public PercentPromotion validateAndGetPromotion(String code, BigDecimal orderTotal) {
        PercentPromotion promotion = percentPromotionRepository.findByCode(code)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_NOT_FOUND));
        
        if (!isValidForOrder(promotion, orderTotal, LocalDateTime.now())) {
            throw new AppException(ErrorCode.PROMOTION_INVALID);
        }
        
        return promotion;
    }


    public BigDecimal applyPromotionToOrder(String code, BigDecimal orderTotal) {
        PercentPromotion promotion = validateAndGetPromotion(code, orderTotal);
        BigDecimal discount = calculateDiscount(promotion, orderTotal);
        decreaseQuantity(promotion);
        percentPromotionRepository.save(promotion);
        return discount;
    }


    private boolean isValidForOrder(PercentPromotion promotion, BigDecimal orderTotal, LocalDateTime currentTime) {
        if (promotion.getIsActive() == null || !promotion.getIsActive()) {
            return false;
        }

        if (promotion.getQuantity() != null && promotion.getQuantity() <= 0) {
            return false;
        }

        if (promotion.getEffective() != null && currentTime.isBefore(promotion.getEffective())) {
            return false;
        }

        if (promotion.getExpiration() != null && currentTime.isAfter(promotion.getExpiration())) {
            return false;
        }
        if (promotion.getMinOrderValue() != null && orderTotal.compareTo(promotion.getMinOrderValue()) < 0) {
            return false;
        }

        return true;
    }


    private BigDecimal calculateDiscount(PercentPromotion promotion, BigDecimal orderTotal) {
        if (promotion.getPercent() <= 0 || orderTotal == null) {
            return BigDecimal.ZERO;
        }


        BigDecimal discount = orderTotal
                .multiply(BigDecimal.valueOf(promotion.getPercent()))
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

        if (promotion.getMaxDiscount() > 0) {
            BigDecimal maxDiscount = BigDecimal.valueOf(promotion.getMaxDiscount());
            if (discount.compareTo(maxDiscount) > 0) {
                return maxDiscount;
            }
        }

        return discount;
    }


    private void decreaseQuantity(PercentPromotion promotion) {
        if (promotion.getQuantity() != null && promotion.getQuantity() > 0) {
            promotion.setQuantity(promotion.getQuantity() - 1);
        }
    }


    public void increaseQuantity(String promotionId) {
        PercentPromotion promotion = percentPromotionRepository.findById(promotionId)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_NOT_FOUND));
        
        if (promotion.getQuantity() != null) {
            promotion.setQuantity(promotion.getQuantity() + 1);
            percentPromotionRepository.save(promotion);
        }
    }
}

