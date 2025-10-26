package com.example.ModaMint_Backend.repository;


import com.example.ModaMint_Backend.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    //QuocHuy
    @Query("SELECT c FROM Category c " +
            "WHERE c.isActive = true AND c.subCategories IS EMPTY " +
            "ORDER BY size(c.products) DESC")
    Page<Category> findTopLeafCategoriesByProductCount(Pageable pageable);
}
