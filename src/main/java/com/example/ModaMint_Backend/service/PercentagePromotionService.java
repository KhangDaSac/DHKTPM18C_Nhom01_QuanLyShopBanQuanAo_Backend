package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.promotion.PercentagePromotionRequest;
import com.example.ModaMint_Backend.dto.response.promotion.PercentagePromotionResponse;
import com.example.ModaMint_Backend.entity.PercentagePromotion;
import com.example.ModaMint_Backend.exception.AppException;
import com.example.ModaMint_Backend.exception.ErrorCode;
import com.example.ModaMint_Backend.mapper.PercentagePromotionMapper;
import com.example.ModaMint_Backend.repository.PercentagePromotionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PercentagePromotionService {
    PercentagePromotionRepository percentagePromotionRepository;
    PercentagePromotionMapper percentagePromotionMapper;

    // Create - Tạo khuyến mãi phần trăm mới
    public PercentagePromotionResponse createPercentagePromotion(PercentagePromotionRequest request) {
        // Kiểm tra mã khuyến mãi đã tồn tại chưa
        if (percentagePromotionRepository.findByCode(request.getCode()).isPresent()) {
            throw new AppException(ErrorCode.PROMOTION_CODE_EXISTED);
        }

        PercentagePromotion percentagePromotion = percentagePromotionMapper.toPercentagePromotion(request);
        PercentagePromotion savedPromotion = percentagePromotionRepository.save(percentagePromotion);
        return percentagePromotionMapper.toPercentagePromotionResponse(savedPromotion);
    }

    // Read - Lấy tất cả khuyến mãi phần trăm
    public List<PercentagePromotionResponse> getAllPercentagePromotions() {
        return percentagePromotionRepository.findAll()
                .stream()
                .map(percentagePromotionMapper::toPercentagePromotionResponse)
                .toList();
    }

    // Read - Lấy khuyến mãi phần trăm theo ID
    public PercentagePromotionResponse getPercentagePromotionById(Long id) {
        PercentagePromotion percentagePromotion = percentagePromotionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_NOT_FOUND));
        return percentagePromotionMapper.toPercentagePromotionResponse(percentagePromotion);
    }

    // Read - Lấy khuyến mãi phần trăm theo mã
    public PercentagePromotionResponse getPercentagePromotionByCode(String code) {
        PercentagePromotion percentagePromotion = percentagePromotionRepository.findByCode(code)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_NOT_FOUND));
        return percentagePromotionMapper.toPercentagePromotionResponse(percentagePromotion);
    }

    // Update - Cập nhật khuyến mãi phần trăm
    public PercentagePromotionResponse updatePercentagePromotion(Long id, PercentagePromotionRequest request) {
        PercentagePromotion percentagePromotion = percentagePromotionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_NOT_FOUND));

        // Kiểm tra nếu mã khuyến mãi bị thay đổi và mã mới đã tồn tại
        if (!percentagePromotion.getCode().equals(request.getCode()) &&
                percentagePromotionRepository.findByCode(request.getCode()).isPresent()) {
            throw new AppException(ErrorCode.PROMOTION_CODE_EXISTED);
        }

        percentagePromotionMapper.updatePercentagePromotion(request, percentagePromotion);
        PercentagePromotion updatedPromotion = percentagePromotionRepository.save(percentagePromotion);
        return percentagePromotionMapper.toPercentagePromotionResponse(updatedPromotion);
    }

    // Delete - Xóa khuyến mãi phần trăm
    public void deletePercentagePromotion(Long id) {
        if (!percentagePromotionRepository.existsById(id)) {
            throw new AppException(ErrorCode.PROMOTION_NOT_FOUND);
        }
        percentagePromotionRepository.deleteById(id);
    }

    // Read - Lấy các khuyến mãi phần trăm đang hoạt động
    public List<PercentagePromotionResponse> getActivePercentagePromotions() {
        return percentagePromotionRepository.findByIsActive(true)
                .stream()
                .map(percentagePromotionMapper::toPercentagePromotionResponse)
                .toList();
    }

    // Utility - Đếm tổng số khuyến mãi phần trăm
    public long getTotalPercentagePromotionCount() {
        return percentagePromotionRepository.count();
    }

    // Utility - Đếm số khuyến mãi phần trăm đang hoạt động
    public long getActivePercentagePromotionCount() {
        return percentagePromotionRepository.findByIsActive(true).size();
    }
}

