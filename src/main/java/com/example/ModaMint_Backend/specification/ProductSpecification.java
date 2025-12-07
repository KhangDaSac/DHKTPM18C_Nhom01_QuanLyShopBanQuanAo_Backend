package com.example.ModaMint_Backend.specification;

import com.example.ModaMint_Backend.entity.Product;
import com.example.ModaMint_Backend.entity.ProductVariant;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProductSpecification {

    /**
     * Filter products theo nhiều điều kiện
     * @param brandId - ID của brand (nullable)
     * @param categoryIds - Set của category IDs (nullable) - hỗ trợ danh mục cha + danh mục con
     * @param minPrice - Giá tối thiểu (nullable)
     * @param maxPrice - Giá tối đa (nullable)
     * @param colors - Danh sách màu sắc (nullable)
     * @param sizes - Danh sách size (nullable)
     * @return Specification<Product>
     */
    public static Specification<Product> filterProducts(
            Long brandId,
            Set<Long> categoryIds,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            List<String> colors,
            List<String> sizes
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Join với ProductVariant để filter theo price, color, size
            Join<Product, ProductVariant> variantJoin = root.join("productVariants", JoinType.INNER);

            // 1. Filter theo Brand
            if (brandId != null) {
                predicates.add(criteriaBuilder.equal(root.get("brandId"), brandId));
            }

            // 2. Filter theo Category (hỗ trợ danh mục cha + danh mục con)
            if (categoryIds != null && !categoryIds.isEmpty()) {
                predicates.add(root.get("categoryId").in(categoryIds));
            }

            // 3. Filter theo Price Range
            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(variantJoin.get("price"), minPrice));
            }
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(variantJoin.get("price"), maxPrice));
            }

            // 4. Filter theo Colors (OR condition)
            if (colors != null && !colors.isEmpty()) {
                predicates.add(variantJoin.get("color").in(colors));
            }

            // 5. Filter theo Sizes (OR condition)
            if (sizes != null && !sizes.isEmpty()) {
                predicates.add(variantJoin.get("size").in(sizes));
            }

            // 6. Chỉ lấy products active
            predicates.add(criteriaBuilder.equal(root.get("active"), true));

            // Loại bỏ duplicate products (vì join có thể tạo ra nhiều rows)
            query.distinct(true);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}