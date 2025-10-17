package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.dto.request.category.CategoryRequest;
import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.category.CategoryResponse;
import com.example.ModaMint_Backend.service.CategoryService;
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
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
    CategoryService categoryService;

    @PostMapping
    public ApiResponse<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.createCategory(request))
                .message("Tạo danh mục mới thành công")
                .build();
    }

    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAllCategories() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getAllCategories())
                .message("Lấy danh sách danh mục thành công")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getCategoryById(@PathVariable Long id) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.getCategoryById(id))
                .message("Lấy thông tin danh mục thành công")
                .build();
    }

    @GetMapping("/paginated")
    public ApiResponse<Page<CategoryResponse>> getCategoriesWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ApiResponse.<Page<CategoryResponse>>builder()
                .result(categoryService.getCategoriesWithPagination(pageable))
                .message("Lấy danh sách danh mục phân trang thành công")
                .build();
    }

    @GetMapping("/active")
    public ApiResponse<List<CategoryResponse>> getActiveCategories() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getActiveCategories())
                .message("Lấy danh sách danh mục đang hoạt động thành công")
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<List<CategoryResponse>> searchCategoriesByName(@RequestParam String name) {
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.searchCategoriesByName(name))
                .message("Tìm kiếm danh mục thành công")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @RequestBody @Valid CategoryRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.updateCategory(id, request))
                .message("Cập nhật danh mục thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ApiResponse.<String>builder()
                .result("Danh mục đã được vô hiệu hóa")
                .message("Xóa danh mục thành công")
                .build();
    }

    @PutMapping("/{id}/restore")
    public ApiResponse<CategoryResponse> restoreCategory(@PathVariable Long id) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.restoreCategory(id))
                .message("Kích hoạt lại danh mục thành công")
                .build();
    }

    @DeleteMapping("/{id}/permanent")
    public ApiResponse<String> permanentDeleteCategory(@PathVariable Long id) {
        categoryService.permanentDeleteCategory(id);
        return ApiResponse.<String>builder()
                .result("Danh mục đã được xóa vĩnh viễn")
                .message("Xóa vĩnh viễn danh mục thành công")
                .build();
    }

    @GetMapping("/count")
    public ApiResponse<Long> getTotalCategoryCount() {
        return ApiResponse.<Long>builder()
                .result(categoryService.getTotalCategoryCount())
                .message("Lấy tổng số lượng danh mục thành công")
                .build();
    }

    @GetMapping("/count/active")
    public ApiResponse<Long> getActiveCategoryCount() {
        return ApiResponse.<Long>builder()
                .result(categoryService.getActiveCategoryCount())
                .message("Lấy số lượng danh mục đang hoạt ��ộng thành công")
                .build();
    }
}
