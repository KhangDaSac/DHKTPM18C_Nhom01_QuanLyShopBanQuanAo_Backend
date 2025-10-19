package com.example.ModaMint_Backend.controller;

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
import org.springframework.web.bind.annotation.*;

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
    public ApiResponse<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(request))
                .message("Tạo sản phẩm mới thành công")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody @Valid ProductRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProduct(id, request))
                .message("Cập nhật sản phẩm thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ApiResponse.<String>builder()
                .result("Sản phẩm đã được vô hiệu hóa")
                .message("Xóa sản phẩm thành công")
                .build();
    }

    @PutMapping("/{id}/restore")
    public ApiResponse<ProductResponse> restoreProduct(@PathVariable Long id) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.restoreProduct(id))
                .message("Kích hoạt lại sản phẩm thành công")
                .build();
    }

    @DeleteMapping("/{id}/permanent")
    public ApiResponse<String> permanentDeleteProduct(@PathVariable Long id) {
        productService.permanentDeleteProduct(id);
        return ApiResponse.<String>builder()
                .result("Sản phẩm đã được xóa vĩnh viễn")
                .message("Xóa vĩnh viễn sản phẩm thành công")
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
}
