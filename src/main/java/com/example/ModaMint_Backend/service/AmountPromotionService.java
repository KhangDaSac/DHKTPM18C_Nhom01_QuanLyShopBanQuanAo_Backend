package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.promotion.AmountPromotionRequest;
import com.example.ModaMint_Backend.dto.response.promotion.AmountPromotionResponse;
import com.example.ModaMint_Backend.entity.AmountPromotion;
import com.example.ModaMint_Backend.exception.AppException;
import com.example.ModaMint_Backend.exception.ErrorCode;
import com.example.ModaMint_Backend.mapper.AmountPromotionMapper;
import com.example.ModaMint_Backend.repository.AmountPromotionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AmountPromotionService {
    AmountPromotionRepository amountPromotionRepository;
    AmountPromotionMapper amountPromotionMapper;

    // Create - Tạo khuyến mãi số tiền mới
    public AmountPromotionResponse createAmountPromotion(AmountPromotionRequest request) {
        // Kiểm tra mã khuyến mãi đã tồn tại chưa
        if (amountPromotionRepository.findByCode(request.getCode()).isPresent()) {
            throw new AppException(ErrorCode.PROMOTION_CODE_EXISTED);
        }

        AmountPromotion amountPromotion = amountPromotionMapper.toAmountPromotion(request);
        AmountPromotion savedPromotion = amountPromotionRepository.save(amountPromotion);
        return amountPromotionMapper.toAmountPromotionResponse(savedPromotion);
    }

    // Read - Lấy tất cả khuyến mãi số tiền
    public List<AmountPromotionResponse> getAllAmountPromotions() {
        return amountPromotionRepository.findAll()
                .stream()
                .map(amountPromotionMapper::toAmountPromotionResponse)
                .toList();
    }

    // Read - Lấy khuyến mãi số tiền theo ID
    public AmountPromotionResponse getAmountPromotionById(Long id) {
        AmountPromotion amountPromotion = amountPromotionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_NOT_FOUND));
        return amountPromotionMapper.toAmountPromotionResponse(amountPromotion);
    }

    // Read - Lấy khuyến mãi số tiền theo mã
    public AmountPromotionResponse getAmountPromotionByCode(String code) {
        AmountPromotion amountPromotion = amountPromotionRepository.findByCode(code)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_NOT_FOUND));
        return amountPromotionMapper.toAmountPromotionResponse(amountPromotion);
    }

    // Update - Cập nhật khuyến mãi số tiền
    public AmountPromotionResponse updateAmountPromotion(Long id, AmountPromotionRequest request) {
        AmountPromotion amountPromotion = amountPromotionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_NOT_FOUND));

        // Kiểm tra nếu mã khuyến mãi bị thay đổi và mã mới đã tồn tại
        if (!amountPromotion.getCode().equals(request.getCode()) &&
                amountPromotionRepository.findByCode(request.getCode()).isPresent()) {
            throw new AppException(ErrorCode.PROMOTION_CODE_EXISTED);
        }

        amountPromotionMapper.updateAmountPromotion(request, amountPromotion);
        AmountPromotion updatedPromotion = amountPromotionRepository.save(amountPromotion);
        return amountPromotionMapper.toAmountPromotionResponse(updatedPromotion);
    }

    // Delete - Xóa khuyến mãi số tiền
    public void deleteAmountPromotion(Long id) {
        if (!amountPromotionRepository.existsById(id)) {
            throw new AppException(ErrorCode.PROMOTION_NOT_FOUND);
        }
        amountPromotionRepository.deleteById(id);
    }

    // Read - Lấy các khuyến mãi số tiền đang hoạt động
    public List<AmountPromotionResponse> getActiveAmountPromotions() {
        return amountPromotionRepository.findByIsActive(true)
                .stream()
                .map(amountPromotionMapper::toAmountPromotionResponse)
                .toList();
    }

    // Utility - Đếm tổng số khuyến mãi số tiền
    public long getTotalAmountPromotionCount() {
        return amountPromotionRepository.count();
    }

    // Utility - Đếm số khuyến mãi số tiền đang hoạt động
    public long getActiveAmountPromotionCount() {
        return amountPromotionRepository.findByIsActive(true).size();
    }
}

