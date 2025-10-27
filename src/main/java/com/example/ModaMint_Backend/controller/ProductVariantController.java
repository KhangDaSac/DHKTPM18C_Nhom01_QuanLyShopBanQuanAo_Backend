package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.dto.request.productvariant.ProductVariantRequest;
import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.productvariant.ProductVariantResponse;
import com.example.ModaMint_Backend.service.ProductVariantService;
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

import java.util.List;

@RestController
@RequestMapping("/product-variants")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductVariantController {
    ProductVariantService productVariantService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ProductVariantResponse> createProductVariant(@RequestBody @Valid ProductVariantRequest request) {
        return ApiResponse.<ProductVariantResponse>builder()
                .result(productVariantService.createProductVariant(request))
                .message("Tạo biến thể sản phẩm mới thành công")
                .build();
    }

    @GetMapping
    public ApiResponse<List<ProductVariantResponse>> getAllProductVariants() {
        return ApiResponse.<List<ProductVariantResponse>>builder()
                .result(productVariantService.getAllProductVariants())
                .message("Lấy danh sách biến thể sản phẩm thành công")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductVariantResponse> getProductVariantById(@PathVariable Long id) {
        return ApiResponse.<ProductVariantResponse>builder()
                .result(productVariantService.getProductVariantById(id))
                .message("Lấy thông tin biến thể sản phẩm thành công")
                .build();
    }

    @GetMapping("/paginated")
    public ApiResponse<Page<ProductVariantResponse>> getProductVariantsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ApiResponse.<Page<ProductVariantResponse>>builder()
                .result(productVariantService.getProductVariantsWithPagination(pageable))
                .message("Lấy danh sách biến thể sản phẩm phân trang thành công")
                .build();
    }

    @GetMapping("/product/{productId}")
    public ApiResponse<List<ProductVariantResponse>> getProductVariantsByProductId(@PathVariable Long productId) {
        return ApiResponse.<List<ProductVariantResponse>>builder()
                .result(productVariantService.getProductVariantsByProductId(productId))
                .message("Lấy danh sách biến thể theo sản phẩm thành công")
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ProductVariantResponse> updateProductVariant(
            @PathVariable Long id,
            @RequestBody @Valid ProductVariantRequest request) {
        return ApiResponse.<ProductVariantResponse>builder()
                .result(productVariantService.updateProductVariant(id, request))
                .message("Cập nhật biến thể sản phẩm thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> deleteProductVariant(@PathVariable Long id) {
        productVariantService.deleteProductVariant(id);
        return ApiResponse.<String>builder()
                .result("Biến thể sản phẩm đã được xóa")
                .message("Xóa biến thể sản phẩm thành công")
                .build();
    }

    @GetMapping("/count")
    public ApiResponse<Long> getTotalProductVariantCount() {
        return ApiResponse.<Long>builder()
                .result(productVariantService.getTotalProductVariantCount())
                .message("Lấy tổng số lượng biến thể sản phẩm thành công")
                .build();
    }
}
