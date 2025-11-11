package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserId(String userId);
    boolean existsByUserIdAndProductId(String userId, Long productId);
    
    @Transactional
    void deleteByUserIdAndProductId(String userId, Long productId);
}
