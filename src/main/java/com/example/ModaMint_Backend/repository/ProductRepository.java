package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    
    @Query("SELECT DISTINCT p FROM Product p " +
           "LEFT JOIN FETCH p.productVariants " +
           "LEFT JOIN FETCH p.brand " +
           "LEFT JOIN FETCH p.category")
    List<Product> findAllWithImagesAndVariants();
    
    @Query("SELECT DISTINCT p FROM Product p " +
           "LEFT JOIN FETCH p.productVariants " +
           "LEFT JOIN FETCH p.brand " +
           "LEFT JOIN FETCH p.category " +
           "WHERE p.id = :id")
    Optional<Product> findByIdWithImagesAndVariants(Long id);


    @Override
    @EntityGraph(attributePaths = {"productVariants", "brand", "category"})
    List<Product> findAll();

    @Query("SELECT p FROM Product p " +
            "LEFT JOIN p.productVariants pv " +
            "LEFT JOIN pv.orderItems oi " +
            "WHERE p.active = true " +  // <-- THÊM DÒNG NÀY
            "GROUP BY p.id " +
            "ORDER BY COALESCE(SUM(oi.quantity), 0) DESC")
    Page<Product> findBestSellingProducts(Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "LEFT JOIN p.productVariants pv " +
            "LEFT JOIN pv.orderItems oi " +
            "WHERE p.active = true " +  // <-- THÊM DÒNG NÀY
            "GROUP BY p.id " +
            "ORDER BY COALESCE(SUM(oi.quantity), 0) ASC")
    Page<Product> findWorstSellingProducts(Pageable pageable);
    Page<Product> findByCategoryNameContainingIgnoreCaseAndActiveTrue(String categoryName, Pageable pageable);
    @Query("SELECT p FROM Product p WHERE p.active = true ORDER BY RAND()")
    Page<Product> findRandomActiveProducts(Pageable pageable);
    @Query("SELECT p.brandId " +
            "FROM Product p " +
            "WHERE p.active = true " +
            "GROUP BY p.brandId " +
            "ORDER BY COUNT(p.id) DESC")
    Page<Long> findTopBrandIdsByProductCount(Pageable pageable);
    List<Product> findByActiveTrueAndBrandIdIn(List<Long> brandIds);
}

