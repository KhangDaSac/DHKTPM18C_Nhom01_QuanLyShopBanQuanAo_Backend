package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.dto.request.product.CreateProductWithVariantsRequest;
import com.example.ModaMint_Backend.dto.request.product.UpdateProductWithVariantsRequest;
import com.example.ModaMint_Backend.dto.request.product.ProductRequest;
import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.product.ProductResponse;
import com.example.ModaMint_Backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;

    @GetMapping
    public ApiResponse<List<ProductResponse>> getAllProducts() {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getAllProducts())
                .message("Lấy danh sách sản phẩm thành công")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable Long id) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.getProductById(id))
                .message("Lấy thông tin sản phẩm thành công")
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<List<ProductResponse>> searchProductsByName(@RequestParam String name) {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.searchProductsByName(name))
                .message("Tìm kiếm sản phẩm thành công")
                .build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(request))
                .message("Tạo sản phẩm mới thành công")
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody @Valid ProductRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProduct(id, request))
                .message("Cập nhật sản phẩm thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ApiResponse.<String>builder()
                .result("Sản phẩm đã được vô hiệu hóa")
                .message("Xóa sản phẩm thành công")
                .build();
    }

    @PutMapping("/{id}/restore")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ProductResponse> restoreProduct(@PathVariable Long id) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.restoreProduct(id))
                .message("Kích hoạt lại sản phẩm thành công")
                .build();
    }

    @GetMapping("/paginated")
    public ApiResponse<Page<ProductResponse>> getProductsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ApiResponse.<Page<ProductResponse>>builder()
                .result(productService.getProductsWithPagination(pageable))
                .message("Lấy danh sách sản phẩm phân trang thành công")
                .build();
    }

    @GetMapping("/brand/{brandId}")
    public ApiResponse<List<ProductResponse>> getProductsByBrandId(@PathVariable Long brandId) {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getProductsByBrandId(brandId))
                .message("Lấy sản phẩm theo thương hiệu thành công")
                .build();
    }

    @GetMapping("/category/{categoryId}")
    public ApiResponse<List<ProductResponse>> getProductsByCategoryId(@PathVariable Long categoryId) {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getProductsByCategoryId(categoryId))
                .message("Lấy sản phẩm theo danh mục thành công")
                .build();
    }

    @GetMapping("/active")
    public ApiResponse<List<ProductResponse>> getActiveProducts() {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getActiveProducts())
                .message("Lấy danh sách sản phẩm đang hoạt động thành công")
                .build();
    }

    @GetMapping("/count")
    public ApiResponse<Long> getTotalProductCount() {
        return ApiResponse.<Long>builder()
                .result(productService.getTotalProductCount())
                .message("Lấy tổng số lượng sản phẩm thành công")
                .build();
    }

    @GetMapping("/count/active")
    public ApiResponse<Long> getActiveProductCount() {
        return ApiResponse.<Long>builder()
                .result(productService.getActiveProductCount())
                .message("Lấy số lượng sản phẩm đang hoạt động thành công")
                .build();
    }
    @GetMapping("/filter")
    public ApiResponse<List<ProductResponse>> filterProducts(
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) List<String> colors,
            @RequestParam(required = false) List<String> sizes
    ) {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.filterProducts(brandId, categoryId, minPrice, maxPrice, colors, sizes))
                .message("Lọc sản phẩm thành công")
                .build();
    }

    @GetMapping("/best-selling")
    public ApiResponse<List<ProductResponse>> getBestSellingProducts() {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getTop10BestSellingProducts())
                .message("Lấy 10 sản phẩm bán chạy nhất thành công")
                .build();
    }
    @GetMapping("/worst-selling")
    public ApiResponse<List<ProductResponse>> getWorstSellingProducts() {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getTop10WorstSellingProducts())
                .message("Lấy 10 sản phẩm bán ít nhất thành công")
                .build();
    }
    @GetMapping("/category/female")
    public ApiResponse<List<ProductResponse>> getTop10FemaleCategoryProducts() {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getTop10ProductsForFemaleCategory())
                .message("Lấy 10 sản phẩm thuộc danh mục 'nữ' thành công")
                .build();
    }
    @GetMapping("/category/male")
    public ApiResponse<List<ProductResponse>> getTop10MaleCategoryProducts() {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getTop10ProductsForMaleCategory())
                .message("Lấy 10 sản phẩm thuộc danh mục 'nam' thành công")
                .build();
    }
    @GetMapping("/random")
    public ApiResponse<List<ProductResponse>> getRandomProducts() {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getTop20RandomActiveProducts())
                .message("Lấy 20 sản phẩm ngẫu nhiên thành công")
                .build();
    }
    @GetMapping("/top-brands")
    public ApiResponse<List<ProductResponse>> getProductsFromTop5Brands() {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getProductsFromTop5Brands())
                .message("Lấy sản phẩm từ top 5 thương hiệu thành công")
                .build();
    }

    /**
     * API mới: Tạo Product + Variants cùng lúc trong 1 transaction
     * POST /api/products/with-variants
     * 
     * @param request - CreateProductWithVariantsRequest
     * @return ProductResponse với đầy đủ variants
     */
    @PostMapping("/with-variants")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ProductResponse> createProductWithVariants(
            @RequestBody @Valid CreateProductWithVariantsRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProductWithVariants(request))
                .message("Tạo sản phẩm với biến thể thành công")
                .build();
    }

    /**
     * API mới: Cập nhật Product + Variants cùng lúc trong 1 transaction
     * PUT /api/products/{id}/with-variants
     * 
     * @param id - Product ID
     * @param request - UpdateProductWithVariantsRequest
     * @return ProductResponse với đầy đủ variants
     */
    @PutMapping("/{id}/with-variants")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ProductResponse> updateProductWithVariants(
            @PathVariable Long id,
            @RequestBody @Valid UpdateProductWithVariantsRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProductWithVariants(id, request))
                .message("Cập nhật sản phẩm với biến thể thành công")
                .build();
    }
}
