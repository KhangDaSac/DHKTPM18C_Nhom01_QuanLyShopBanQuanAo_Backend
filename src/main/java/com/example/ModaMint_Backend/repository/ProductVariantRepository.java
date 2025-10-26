package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.ProductVariant;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
	Optional<ProductVariant> findFirstByProductIdOrderByIdAsc(Long productId);
}
