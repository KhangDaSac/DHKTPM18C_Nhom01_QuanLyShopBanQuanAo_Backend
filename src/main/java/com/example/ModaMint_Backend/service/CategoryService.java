package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.category.CategoryRequest;
import com.example.ModaMint_Backend.dto.response.category.CategoryResponse;
import com.example.ModaMint_Backend.entity.Category;
import com.example.ModaMint_Backend.exception.AppException;
import com.example.ModaMint_Backend.exception.ErrorCode;
import com.example.ModaMint_Backend.mapper.CategoryMapper;
import com.example.ModaMint_Backend.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    // Create - Tạo danh mục mới
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = categoryMapper.toCategory(request);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(savedCategory);
    }

    // Read - Lấy tất cả danh mục
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();
    }

    // Read - Lấy danh mục theo ID
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        return categoryMapper.toCategoryResponse(category);
    }

    // Read - Lấy danh mục với phân trang
    public Page<CategoryResponse> getCategoriesWithPagination(Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        return categoryPage.map(categoryMapper::toCategoryResponse);
    }

    // Update - Cập nhật danh mục
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        categoryMapper.updateCategory(request, category);
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(updatedCategory);
    }

    // Delete - Xóa danh mục (soft delete bằng cách set isActive = false)
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        category.setIsActive(false);
        categoryRepository.save(category);
    }

    // Restore - Kích hoạt lại danh mục (chuyển từ isActive = false về true)
    public CategoryResponse restoreCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        category.setIsActive(true);
        Category restoredCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(restoredCategory);
    }

    // Delete - Xóa danh mục vĩnh viễn (hard delete)
    public void permanentDeleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        categoryRepository.deleteById(id);
    }

    // Read - Lấy danh mục đang active
    public List<CategoryResponse> getActiveCategories() {
        return categoryRepository.findAll()
                .stream()
                .filter(Category::getIsActive)
                .map(categoryMapper::toCategoryResponse)
                .toList();
    }

    // Read - Tìm kiếm danh mục theo tên
    public List<CategoryResponse> searchCategoriesByName(String name) {
        return categoryRepository.findAll()
                .stream()
                .filter(category -> category.getName().toLowerCase().contains(name.toLowerCase()))
                .map(categoryMapper::toCategoryResponse)
                .toList();
    }

    // Utility - Đếm tổng số danh mục
    public long getTotalCategoryCount() {
        return categoryRepository.count();
    }

    // Utility - Đếm số danh mục active
    public long getActiveCategoryCount() {
        return categoryRepository.findAll()
                .stream()
                .filter(Category::getIsActive)
                .count();
    }


    //QuocHuy
    public List<CategoryResponse> getTopActiveLeafCategoriesByProductCount() {
        // 1. Tạo Pageable để yêu cầu trang đầu tiên (0) và 8 phần tử (size 8).
        Pageable top8Pageable = PageRequest.of(0, 8); // <-- THAY ĐỔI TỪ 10 -> 8

        // 2. Gọi phương thức repository (đã được cập nhật query)
        Page<Category> categoryPage = categoryRepository.findTopLeafCategoriesByProductCount(top8Pageable);

        // 3. Chuyển đổi Page<Category> thành List<CategoryResponse>
        return categoryPage.getContent()
                .stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();
    }
}
