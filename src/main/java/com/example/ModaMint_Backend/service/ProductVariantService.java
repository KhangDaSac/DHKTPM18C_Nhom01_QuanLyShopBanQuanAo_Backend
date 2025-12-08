package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.productvariant.ProductVariantRequest;
import com.example.ModaMint_Backend.dto.response.productvariant.ProductVariantColorResponse;
import com.example.ModaMint_Backend.dto.response.productvariant.ProductVariantResponse;
import com.example.ModaMint_Backend.dto.response.productvariant.VariantMatrixResponse;
import com.example.ModaMint_Backend.entity.Product;
import com.example.ModaMint_Backend.entity.ProductVariant;
import com.example.ModaMint_Backend.exception.AppException;
import com.example.ModaMint_Backend.exception.ErrorCode;
import com.example.ModaMint_Backend.mapper.ProductVariantMapper;
import com.example.ModaMint_Backend.repository.ProductRepository;
import com.example.ModaMint_Backend.repository.ProductVariantRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductVariantService {
    ProductVariantRepository productVariantRepository;
    ProductVariantMapper productVariantMapper;
    ProductRepository productRepository;

    public ProductVariantResponse createProductVariant(ProductVariantRequest request) {
        ProductVariant productVariant = productVariantMapper.toProductVariant(request);
        ProductVariant savedProductVariant = productVariantRepository.save(productVariant);
        return productVariantMapper.toProductVariantResponse(savedProductVariant);
    }

    public List<ProductVariantResponse> getAllProductVariants() {
        return productVariantRepository.findAll()
                .stream()
                .map(productVariantMapper::toProductVariantResponse)
                .toList();
    }

    public ProductVariantResponse getProductVariantById(Long id) {
        ProductVariant productVariant = productVariantRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));
        return productVariantMapper.toProductVariantResponse(productVariant);
    }

    public Page<ProductVariantResponse> getProductVariantsWithPagination(Pageable pageable) {
        Page<ProductVariant> productVariantPage = productVariantRepository.findAll(pageable);
        return productVariantPage.map(productVariantMapper::toProductVariantResponse);
    }

    @Transactional
    public ProductVariantResponse updateProductVariant(Long id, ProductVariantRequest request) {
        ProductVariant productVariant = productVariantRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));

        Boolean oldActiveStatus = productVariant.getActive();
        productVariantMapper.updateProductVariant(request, productVariant);
        ProductVariant updatedProductVariant = productVariantRepository.save(productVariant);
        
        // Kiểm tra và cập nhật trạng thái sản phẩm nếu cần
        Boolean newActiveStatus = request.getActive();
        if (newActiveStatus != null && !oldActiveStatus.equals(newActiveStatus)) {
            updateProductActiveStatus(productVariant.getProductId());
        }
        
        return productVariantMapper.toProductVariantResponse(updatedProductVariant);
    }

    @Transactional
    public void deleteProductVariant(Long id) {
        ProductVariant productVariant = productVariantRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));
        
        // Soft delete - set active = false
        productVariant.setActive(false);
        productVariantRepository.save(productVariant);
        
        // Kiểm tra và cập nhật trạng thái sản phẩm
        updateProductActiveStatus(productVariant.getProductId());
    }

    @Transactional
    public void restoreProductVariant(Long id) {
        ProductVariant productVariant = productVariantRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));
        
        // Restore - set active = true
        productVariant.setActive(true);
        productVariantRepository.save(productVariant);
        
        // Kiểm tra và cập nhật trạng thái sản phẩm
        updateProductActiveStatus(productVariant.getProductId());
    }
    
    /**
     * Cập nhật trạng thái active của sản phẩm dựa trên trạng thái của các biến thể
     * - Nếu tất cả biến thể đều inactive → sản phẩm inactive
     * - Nếu có ít nhất 1 biến thể active → sản phẩm active
     */
    private void updateProductActiveStatus(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        
        List<ProductVariant> variants = productVariantRepository.findByProductId(productId);
        
        // Kiểm tra xem có ít nhất 1 biến thể active không
        boolean hasActiveVariant = variants.stream()
                .anyMatch(variant -> variant.getActive() != null && variant.getActive());
        
        // Cập nhật trạng thái sản phẩm
        product.setActive(hasActiveVariant);
        productRepository.save(product);
    }

    public List<ProductVariantResponse> getProductVariantsByProductId(Long productId) {
        return productVariantRepository.findByProductId(productId)
                .stream()
                .map(productVariantMapper::toProductVariantResponse)
                .toList();
    }

    public long getTotalProductVariantCount() {
        return productVariantRepository.count();
    }
    
    public List<ProductVariantColorResponse> getTopColors(int limit) {
        List<Object[]> results = productVariantRepository.findTopColors();
        return results.stream()
                .limit(limit)  // Limit top 4
                .map(row -> new ProductVariantColorResponse((String) row[0], (Long) row[1]))
                .collect(Collectors.toList());
    }
    
    /**
     * Get variant matrix data for heatmap visualization
     * Returns list of {color, size, quantity} combinations
     */
    public List<VariantMatrixResponse> getVariantMatrix() {
        List<Object[]> results = productVariantRepository.findVariantMatrix();
        return results.stream()
                .map(row -> VariantMatrixResponse.builder()
                        .color((String) row[0])
                        .size((String) row[1])
                        .quantity(((Number) row[2]).intValue())
                        .build())
                .collect(Collectors.toList());
    }
}
