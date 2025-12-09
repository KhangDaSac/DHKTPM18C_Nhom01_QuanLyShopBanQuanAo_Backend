package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    //QuocHuy
    @Query("""
    SELECT c FROM Category c
    LEFT JOIN c.products p
    WHERE c.isActive = true 
      AND c.parentId IS NOT NULL
    GROUP BY c
    ORDER BY COUNT(p) DESC
            """)
    Page<Category> findTopLeafCategoriesByProductCount(Pageable pageable);

    // Tìm tất cả danh mục con của một danh mục cha
    List<Category> findAllByParentId(Long parentId);
}