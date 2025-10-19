package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.product.ProductRequest;
import com.example.ModaMint_Backend.dto.response.product.ProductResponse;
import com.example.ModaMint_Backend.entity.Product;
import com.example.ModaMint_Backend.exception.AppException;
import com.example.ModaMint_Backend.exception.ErrorCode;
import com.example.ModaMint_Backend.mapper.ProductMapper;
import com.example.ModaMint_Backend.repository.BrandRepository;
import com.example.ModaMint_Backend.repository.CategoryRepository;
import com.example.ModaMint_Backend.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductRepository productRepository;
    BrandRepository brandRepository;
    CategoryRepository categoryRepository;
    ProductMapper productMapper;

    public ProductResponse createProduct(ProductRequest request) {
        if (!brandRepository.existsById(request.getBrandId())) {
            throw new AppException(ErrorCode.BRAND_NOT_FOUND);
        }

        if (!categoryRepository.existsById(request.getCategoryId())) {
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        Product product = productMapper.toProduct(request);
        Product savedProduct = productRepository.save(product);

        return productMapper.toProductResponse(savedProduct);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        return productMapper.toProductResponse(product);
    }

    public Page<ProductResponse> getProductsWithPagination(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(productMapper::toProductResponse);
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        if (!brandRepository.existsById(request.getBrandId())) {
            throw new AppException(ErrorCode.BRAND_NOT_FOUND);
        }
        if (!categoryRepository.existsById(request.getCategoryId())) {
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        productMapper.updateProduct(request, product);
        Product updatedProduct = productRepository.save(product);

        return productMapper.toProductResponse(updatedProduct);
    }


    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        product.setActive(false);
        productRepository.save(product);
    }

    // Restore - Kích hoạt lại sản phẩm (chuyển từ active = false về true)
    public ProductResponse restoreProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        product.setActive(true);
        Product restoredProduct = productRepository.save(product);
        return productMapper.toProductResponse(restoredProduct);
    }


    public void permanentDeleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        productRepository.deleteById(id);
    }


    public List<ProductResponse> getProductsByBrandId(Long brandId) {
        if (!brandRepository.existsById(brandId)) {
            throw new AppException(ErrorCode.BRAND_NOT_FOUND);
        }

        return productRepository.findAll()
                .stream()
                .filter(product -> product.getBrandId().equals(brandId))
                .map(productMapper::toProductResponse)
                .toList();
    }


    public List<ProductResponse> getProductsByCategoryId(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        return productRepository.findAll()
                .stream()
                .filter(product -> product.getCategoryId().equals(categoryId))
                .map(productMapper::toProductResponse)
                .toList();
    }

    public List<ProductResponse> getActiveProducts() {
        return productRepository.findAll()
                .stream()
                .filter(Product::getActive)
                .map(productMapper::toProductResponse)
                .toList();
    }

    public long getTotalProductCount() {
        return productRepository.count();
    }

    public long getActiveProductCount() {
        return productRepository.findAll()
                .stream()
                .filter(Product::getActive)
                .count();
    }

    public List<ProductResponse> searchProductsByName(String name) {
        return productRepository.findAll()
                .stream()
                .filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()))
                .map(productMapper::toProductResponse)
                .toList();
    }
}
