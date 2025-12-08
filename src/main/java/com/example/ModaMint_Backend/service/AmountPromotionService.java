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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AmountPromotionService {
    AmountPromotionRepository amountPromotionRepository;
    AmountPromotionMapper amountPromotionMapper;

    public AmountPromotionResponse createAmountPromotion(AmountPromotionRequest request) {
        if (amountPromotionRepository.findByCode(request.getCode()).isPresent()) {
            throw new AppException(ErrorCode.PROMOTION_CODE_EXISTED);
        }

        AmountPromotion amountPromotion = amountPromotionMapper.toAmountPromotion(request);
        AmountPromotion savedPromotion = amountPromotionRepository.save(amountPromotion);
        return amountPromotionMapper.toAmountPromotionResponse(savedPromotion);
    }

    public List<AmountPromotionResponse> getAllAmountPromotions() {
        return amountPromotionRepository.findAll()
                .stream()
                .sorted((p1, p2) -> {
                    // Sắp xếp theo expiration: khuyến mãi sắp hết hạn lên đầu
                    if (p1.getExpiration() == null && p2.getExpiration() == null) return 0;
                    if (p1.getExpiration() == null) return 1;
                    if (p2.getExpiration() == null) return -1;
                    return p1.getExpiration().compareTo(p2.getExpiration());
                })
                .map(amountPromotionMapper::toAmountPromotionResponse)
                .toList();
    }

    // Read - Lấy khuyến mãi số tiền theo ID
    public AmountPromotionResponse getAmountPromotionById(String id) {
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
    public AmountPromotionResponse updateAmountPromotion(String id, AmountPromotionRequest request) {
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
    public void deleteAmountPromotion(String id) {
        if (!amountPromotionRepository.existsById(id)) {
            throw new AppException(ErrorCode.PROMOTION_NOT_FOUND);
        }
        amountPromotionRepository.deleteById(id);
    }

    // Read - Lấy các khuyến mãi số tiền đang hoạt động
    public List<AmountPromotionResponse> getActiveAmountPromotions() {
        LocalDateTime now = LocalDateTime.now();
        return amountPromotionRepository.findAll()
                .stream()
                .filter(p -> {
                    // Đang hoạt động = đã bắt đầu VÀ chưa hết hạn
                    boolean started = p.getEffective() == null || !p.getEffective().isAfter(now);
                    boolean notExpired = p.getExpiration() == null || p.getExpiration().isAfter(now);
                    return started && notExpired;
                })
                .sorted((p1, p2) -> {
                    if (p1.getExpiration() == null && p2.getExpiration() == null) return 0;
                    if (p1.getExpiration() == null) return 1;
                    if (p2.getExpiration() == null) return -1;
                    return p1.getExpiration().compareTo(p2.getExpiration());
                })
                .map(amountPromotionMapper::toAmountPromotionResponse)
                .toList();
    }

    // Read - Lấy các khuyến mãi số tiền chưa bắt đầu
    public List<AmountPromotionResponse> getNotStartedAmountPromotions() {
        LocalDateTime now = LocalDateTime.now();
        return amountPromotionRepository.findAll()
                .stream()
                .filter(p -> p.getEffective() != null && p.getEffective().isAfter(now))
                .sorted((p1, p2) -> p1.getEffective().compareTo(p2.getEffective()))
                .map(amountPromotionMapper::toAmountPromotionResponse)
                .toList();
    }

    // Read - Lấy các khuyến mãi số tiền đã hết hạn
    public List<AmountPromotionResponse> getExpiredAmountPromotions() {
        LocalDateTime now = LocalDateTime.now();
        return amountPromotionRepository.findAll()
                .stream()
                .filter(p -> p.getExpiration() != null && p.getExpiration().isBefore(now))
                .sorted((p1, p2) -> p2.getExpiration().compareTo(p1.getExpiration()))
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

    /**
     * Xác thực và lấy thông tin khuyến mãi
     */
    public AmountPromotion validateAndGetPromotion(String code, BigDecimal orderTotal) {
        AmountPromotion promotion = amountPromotionRepository.findByCode(code)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_NOT_FOUND));
        
        if (!isValidForOrder(promotion, orderTotal, java.time.LocalDateTime.now())) {
            throw new AppException(ErrorCode.PROMOTION_INVALID);
        }
        
        return promotion;
    }

    /**
     * Áp dụng khuyến mãi vào đơn hàng
     */
    public BigDecimal applyPromotionToOrder(String code, BigDecimal orderTotal) {
        AmountPromotion promotion = validateAndGetPromotion(code, orderTotal);
        BigDecimal discount = calculateDiscount(promotion, orderTotal);
        decreaseQuantity(promotion);
        amountPromotionRepository.save(promotion);
        return discount;
    }

    /**
     * Kiểm tra xem mã giảm giá có hợp lệ cho đơn hàng không
     */
    private boolean isValidForOrder(AmountPromotion promotion, BigDecimal orderTotal, java.time.LocalDateTime currentTime) {
        // Kiểm tra mã có đang hoạt động không
        if (promotion.getIsActive() == null || !promotion.getIsActive()) {
            return false;
        }

        // Kiểm tra số lượng còn lại
        if (promotion.getQuantity() != null && promotion.getQuantity() <= 0) {
            return false;
        }

        // Kiểm tra thời gian hiệu lực
        if (promotion.getEffective() != null && currentTime.isBefore(promotion.getEffective())) {
            return false;
        }

        if (promotion.getExpiration() != null && currentTime.isAfter(promotion.getExpiration())) {
            return false;
        }

        // Kiểm tra giá trị đơn hàng tối thiểu
        if (promotion.getMinOrderValue() != null && orderTotal.compareTo(promotion.getMinOrderValue()) < 0) {
            return false;
        }

        return true;
    }

    /**
     * Tính toán số tiền giảm giá
     */
    private BigDecimal calculateDiscount(AmountPromotion promotion, BigDecimal orderTotal) {
        if (promotion.getDiscount() <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal discount = BigDecimal.valueOf(promotion.getDiscount());

        // Đảm bảo giảm giá không vượt quá giá trị đơn hàng
        if (discount.compareTo(orderTotal) > 0) {
            return orderTotal;
        }

        return discount;
    }

    /**
     * Giảm số lượng mã khuyến mãi còn lại sau khi sử dụng
     */
    private void decreaseQuantity(AmountPromotion promotion) {
        if (promotion.getQuantity() != null && promotion.getQuantity() > 0) {
            promotion.setQuantity(promotion.getQuantity() - 1);
        }
    }

    /**
     * Tăng số lượng mã khuyến mãi (dùng khi hoàn trả đơn hàng)
     */
    public void increaseQuantity(String promotionId) {
        AmountPromotion promotion = amountPromotionRepository.findById(promotionId)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_NOT_FOUND));
        
        if (promotion.getQuantity() != null) {
            promotion.setQuantity(promotion.getQuantity() + 1);
            amountPromotionRepository.save(promotion);
        }
    }
}

