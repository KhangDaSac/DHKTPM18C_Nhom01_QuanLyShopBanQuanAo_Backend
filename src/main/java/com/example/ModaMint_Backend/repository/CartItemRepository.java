package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.Cart;
import com.example.ModaMint_Backend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartIdAndVariantId(Long cartId, Long variantId);
    void deleteByCartIdAndVariantId(Long cartId, Long variantId);
    void deleteAllByCartId(Long cartId);
    List<CartItem> findByCartId(Long cartId);
}