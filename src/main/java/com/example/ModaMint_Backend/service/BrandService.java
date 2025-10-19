package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.brand.BrandRequest;
import com.example.ModaMint_Backend.dto.response.brand.BrandResponse;
import com.example.ModaMint_Backend.entity.Brand;
import com.example.ModaMint_Backend.exception.AppException;
import com.example.ModaMint_Backend.exception.ErrorCode;
import com.example.ModaMint_Backend.mapper.BrandMapper;
import com.example.ModaMint_Backend.repository.BrandRepository;
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
public class BrandService {
    BrandRepository brandRepository;
    BrandMapper brandMapper;

    // Create - Tạo thương hiệu mới
    public BrandResponse createBrand(BrandRequest request) {
        Brand brand = brandMapper.toBrand(request);
        Brand savedBrand = brandRepository.save(brand);
        return brandMapper.toBrandResponse(savedBrand);
    }

    // Read - Lấy tất cả thương hiệu
    public List<BrandResponse> getAllBrands() {
        return brandRepository.findAll()
                .stream()
                .map(brandMapper::toBrandResponse)
                .toList();
    }

    // Read - Lấy thương hiệu theo ID
    public BrandResponse getBrandById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));
        return brandMapper.toBrandResponse(brand);
    }

    // Read - Lấy thương hiệu với phân trang
    public Page<BrandResponse> getBrandsWithPagination(Pageable pageable) {
        Page<Brand> brandPage = brandRepository.findAll(pageable);
        return brandPage.map(brandMapper::toBrandResponse);
    }

    // Update - Cập nhật thương hiệu
    public BrandResponse updateBrand(Long id, BrandRequest request) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));

        brandMapper.updateBrand(request, brand);
        Brand updatedBrand = brandRepository.save(brand);
        return brandMapper.toBrandResponse(updatedBrand);
    }

    // Delete - Xóa thương hiệu (soft delete bằng cách set active = false)
    public void deleteBrand(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));

        brand.setActive(false);
        brandRepository.save(brand);
    }

    // Restore - Kích hoạt lại thương hiệu (chuyển từ active = false về true)
    public BrandResponse restoreBrand(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));

        brand.setActive(true);
        Brand restoredBrand = brandRepository.save(brand);
        return brandMapper.toBrandResponse(restoredBrand);
    }

    // Delete - Xóa thương hiệu vĩnh viễn (hard delete)
    public void permanentDeleteBrand(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new AppException(ErrorCode.BRAND_NOT_FOUND);
        }
        brandRepository.deleteById(id);
    }

    // Read - Lấy thương hiệu đang active
    public List<BrandResponse> getActiveBrands() {
        return brandRepository.findAll()
                .stream()
                .filter(Brand::getActive)
                .map(brandMapper::toBrandResponse)
                .toList();
    }

    // Read - Tìm kiếm thương hiệu theo tên
    public List<BrandResponse> searchBrandsByName(String name) {
        return brandRepository.findAll()
                .stream()
                .filter(brand -> brand.getName().toLowerCase().contains(name.toLowerCase()))
                .map(brandMapper::toBrandResponse)
                .toList();
    }

    // Utility - Đếm tổng số thương hiệu
    public long getTotalBrandCount() {
        return brandRepository.count();
    }

    // Utility - Đếm số thương hiệu active
    public long getActiveBrandCount() {
        return brandRepository.findAll()
                .stream()
                .filter(Brand::getActive)
                .count();
    }
}
