package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.product.CreateProductWithVariantsRequest;
import com.example.ModaMint_Backend.dto.request.product.UpdateProductWithVariantsRequest;
import com.example.ModaMint_Backend.dto.request.product.ProductRequest;
import com.example.ModaMint_Backend.dto.response.product.ProductResponse;
import com.example.ModaMint_Backend.entity.Product;
import com.example.ModaMint_Backend.entity.ProductVariant;
import com.example.ModaMint_Backend.exception.AppException;
import com.example.ModaMint_Backend.exception.ErrorCode;
import com.example.ModaMint_Backend.mapper.ProductMapper;
import com.example.ModaMint_Backend.repository.BrandRepository;
import com.example.ModaMint_Backend.repository.CategoryRepository;
import com.example.ModaMint_Backend.repository.ProductRepository;
import com.example.ModaMint_Backend.repository.ProductVariantRepository;
import com.example.ModaMint_Backend.specification.ProductSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductRepository productRepository;
    ProductVariantRepository productVariantRepository;
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
        return productRepository.findAllWithImagesAndVariants()
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findByIdWithImagesAndVariants(id)
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
    /**
     * Filter products theo nhiều điều kiện
     * @param brandId - ID của brand (nullable)
     * @param categoryId - ID của category (nullable)
     * @param minPrice - Giá tối thiểu (nullable)
     * @param maxPrice - Giá tối đa (nullable)
     * @param colors - Danh sách màu sắc (nullable)
     * @param sizes - Danh sách size (nullable)
     * @return List<ProductResponse>
     */
    public List<ProductResponse> filterProducts(
            Long brandId,
            Long categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            List<String> colors,
            List<String> sizes
    ) {
        Specification<Product> spec = ProductSpecification.filterProducts(
                brandId, categoryId, minPrice, maxPrice, colors, sizes
        );

        return productRepository.findAll(spec)
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    public List<ProductResponse> getTop10BestSellingProducts() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Product> productPage = productRepository.findBestSellingProducts(pageable);

        return productPage.getContent()
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    public List<ProductResponse> getTop10WorstSellingProducts() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Product> productPage = productRepository.findWorstSellingProducts(pageable);

        return productPage.getContent()
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    public List<ProductResponse> getTop10ProductsForFemaleCategory() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> productPage = productRepository.findByCategoryNameContainingIgnoreCaseAndActiveTrue("nữ", pageable);

        return productPage.getContent()
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    public List<ProductResponse> getTop10ProductsForMaleCategory() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> productPage = productRepository.findByCategoryNameContainingIgnoreCaseAndActiveTrue("nam", pageable);

        return productPage.getContent()
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
    }
    public List<ProductResponse> getTop20RandomActiveProducts() {
        Pageable pageable = PageRequest.of(0, 20);

        Page<Product> productPage = productRepository.findRandomActiveProducts(pageable);

        return productPage.getContent()
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
    }
    public List<ProductResponse> getProductsFromTop5Brands() {
        Pageable top5Pageable = PageRequest.of(0, 5);

        List<Long> top5BrandIds = productRepository.findTopBrandIdsByProductCount(top5Pageable).getContent();

        if (top5BrandIds.isEmpty()) {
            return Collections.emptyList();
        }
        List<Product> products = productRepository.findByActiveTrueAndBrandIdIn(top5BrandIds);
        return products.stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    /**
     * Tạo Product + Variants trong 1 transaction duy nhất
     * Đảm bảo atomicity và Product luôn có ít nhất 1 Variant
     * 
     * @param request - CreateProductWithVariantsRequest chứa product + variants
     * @return ProductResponse đầy đủ bao gồm variants
     */
    @Transactional
    public ProductResponse createProductWithVariants(CreateProductWithVariantsRequest request) {
        // Validate brand
        if (!brandRepository.existsById(request.getProduct().getBrandId())) {
            throw new AppException(ErrorCode.BRAND_NOT_FOUND);
        }

        // Validate category
        if (!categoryRepository.existsById(request.getProduct().getCategoryId())) {
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        // Bước 1: Tạo và lưu Product
        Product product = productMapper.toProduct(request.getProduct());
        Product savedProduct = productRepository.save(product);

        // Bước 2: Tạo danh sách ProductVariant từ request
        List<ProductVariant> variants = request.getVariants().stream()
                .map(variantRequest -> ProductVariant.builder()
                        .productId(savedProduct.getId()) // Gán productId từ savedProduct
                        .size(variantRequest.getSize())
                        .color(variantRequest.getColor())
                        .image(variantRequest.getImage())
                        .price(variantRequest.getPrice())
                        .discount(variantRequest.getDiscount())
                        .quantity(variantRequest.getQuantity())
                        .additionalPrice(variantRequest.getAdditionalPrice())
                        .build())
                .collect(Collectors.toList());

        // Bước 3: Lưu tất cả variants
        productVariantRepository.saveAll(variants);

        // Bước 4: Load lại product với đầy đủ variants để trả về
        Product productWithVariants = productRepository.findByIdWithImagesAndVariants(savedProduct.getId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        return productMapper.toProductResponse(productWithVariants);
    }

    /**
     * Cập nhật Product + Variants trong 1 transaction
     * Xóa toàn bộ variants cũ và tạo mới theo request
     * @param id ID của product cần update
     * @param request Chứa thông tin Product và danh sách Variants mới
     * @return ProductResponse đầy đủ bao gồm variants
     */
    @Transactional
    public ProductResponse updateProductWithVariants(Long id, UpdateProductWithVariantsRequest request) {
        // Validate product exists
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        // Validate brand
        if (!brandRepository.existsById(request.getProduct().getBrandId())) {
            throw new AppException(ErrorCode.BRAND_NOT_FOUND);
        }

        // Validate category
        if (!categoryRepository.existsById(request.getProduct().getCategoryId())) {
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        // Bước 1: Cập nhật thông tin Product
        productMapper.updateProduct(request.getProduct(), existingProduct);
        Product updatedProduct = productRepository.save(existingProduct);

        // Bước 2: Xóa tất cả variants cũ của product
        productVariantRepository.deleteByProductId(id);

        // Bước 3: Tạo danh sách ProductVariant mới từ request
        List<ProductVariant> newVariants = request.getVariants().stream()
                .map(variantRequest -> ProductVariant.builder()
                        .productId(updatedProduct.getId())
                        .size(variantRequest.getSize())
                        .color(variantRequest.getColor())
                        .image(variantRequest.getImage())
                        .price(variantRequest.getPrice())
                        .discount(variantRequest.getDiscount())
                        .quantity(variantRequest.getQuantity())
                        .additionalPrice(variantRequest.getAdditionalPrice())
                        .build())
                .collect(Collectors.toList());

        // Bước 4: Lưu tất cả variants mới
        productVariantRepository.saveAll(newVariants);

        // Bước 5: Load lại product với đầy đủ variants để trả về
        Product productWithVariants = productRepository.findByIdWithImagesAndVariants(updatedProduct.getId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        return productMapper.toProductResponse(productWithVariants);
    }
}
