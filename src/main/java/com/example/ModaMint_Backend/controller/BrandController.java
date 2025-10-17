package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.dto.request.brand.BrandRequest;
import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.brand.BrandResponse;
import com.example.ModaMint_Backend.service.BrandService;
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
@RequestMapping("/brands")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandController {
    BrandService brandService;

    @PostMapping
    public ApiResponse<BrandResponse> createBrand(@RequestBody @Valid BrandRequest request) {
        return ApiResponse.<BrandResponse>builder()
                .result(brandService.createBrand(request))
                .message("Tạo thương hiệu mới thành công")
                .build();
    }

    @GetMapping
    public ApiResponse<List<BrandResponse>> getAllBrands() {
        return ApiResponse.<List<BrandResponse>>builder()
                .result(brandService.getAllBrands())
                .message("Lấy danh sách thương hiệu thành công")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<BrandResponse> getBrandById(@PathVariable Long id) {
        return ApiResponse.<BrandResponse>builder()
                .result(brandService.getBrandById(id))
                .message("Lấy thông tin thương hiệu thành công")
                .build();
    }

    @GetMapping("/paginated")
    public ApiResponse<Page<BrandResponse>> getBrandsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ApiResponse.<Page<BrandResponse>>builder()
                .result(brandService.getBrandsWithPagination(pageable))
                .message("Lấy danh sách thương hiệu phân trang thành công")
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<List<BrandResponse>> searchBrandsByName(@RequestParam String name) {
        return ApiResponse.<List<BrandResponse>>builder()
                .result(brandService.searchBrandsByName(name))
                .message("Tìm kiếm thương hiệu thành công")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<BrandResponse> updateBrand(
            @PathVariable Long id,
            @RequestBody @Valid BrandRequest request) {
        return ApiResponse.<BrandResponse>builder()
                .result(brandService.updateBrand(id, request))
                .message("Cập nhật thương hiệu thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ApiResponse.<String>builder()
                .result("Thương hiệu đã được xóa")
                .message("Xóa thương hiệu thành công")
                .build();
    }

    @PutMapping("/{id}/restore")
    public ApiResponse<BrandResponse> restoreBrand(@PathVariable Long id) {
        return ApiResponse.<BrandResponse>builder()
                .result(brandService.restoreBrand(id))
                .message("Kích hoạt lại thương hiệu thành công")
                .build();
    }

    @DeleteMapping("/{id}/permanent")
    public ApiResponse<String> permanentDeleteBrand(@PathVariable Long id) {
        brandService.permanentDeleteBrand(id);
        return ApiResponse.<String>builder()
                .result("Thương hiệu đã được xóa vĩnh viễn")
                .message("Xóa vĩnh viễn thương hiệu thành công")
                .build();
    }

    @GetMapping("/active")
    public ApiResponse<List<BrandResponse>> getActiveBrands() {
        return ApiResponse.<List<BrandResponse>>builder()
                .result(brandService.getActiveBrands())
                .message("Lấy danh sách thương hiệu đang hoạt động thành công")
                .build();
    }

    @GetMapping("/count")
    public ApiResponse<Long> getTotalBrandCount() {
        return ApiResponse.<Long>builder()
                .result(brandService.getTotalBrandCount())
                .message("Lấy tổng số lượng thương hiệu thành công")
                .build();
    }

    @GetMapping("/count/active")
    public ApiResponse<Long> getActiveBrandCount() {
        return ApiResponse.<Long>builder()
                .result(brandService.getActiveBrandCount())
                .message("Lấy tổng số lượng thương hiệu đang hoạt động thành công")
                .build();
    }
}
