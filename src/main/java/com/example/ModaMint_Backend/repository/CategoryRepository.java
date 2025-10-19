package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
