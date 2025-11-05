package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.productvariant.ProductVariantRequest;
import com.example.ModaMint_Backend.dto.response.productvariant.ProductVariantColorResponse;
import com.example.ModaMint_Backend.dto.response.productvariant.ProductVariantResponse;
import com.example.ModaMint_Backend.entity.ProductVariant;
import com.example.ModaMint_Backend.exception.AppException;
import com.example.ModaMint_Backend.exception.ErrorCode;
import com.example.ModaMint_Backend.mapper.ProductVariantMapper;
import com.example.ModaMint_Backend.repository.ProductVariantRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductVariantService {
    ProductVariantRepository productVariantRepository;
    ProductVariantMapper productVariantMapper;

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

    public ProductVariantResponse updateProductVariant(Long id, ProductVariantRequest request) {
        ProductVariant productVariant = productVariantRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));

        productVariantMapper.updateProductVariant(request, productVariant);
        ProductVariant updatedProductVariant = productVariantRepository.save(productVariant);
        return productVariantMapper.toProductVariantResponse(updatedProductVariant);
    }

    public void deleteProductVariant(Long id) {
        if (!productVariantRepository.existsById(id)) {
            throw new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND);
        }
        productVariantRepository.deleteById(id);
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
}
