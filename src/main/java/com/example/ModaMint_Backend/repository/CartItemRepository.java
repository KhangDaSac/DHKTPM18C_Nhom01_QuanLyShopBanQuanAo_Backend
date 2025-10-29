package com.example.ModaMint_Backend.repository;

<<<<<<< HEAD
import com.example.ModaMint_Backend.entity.Cart;
=======
>>>>>>> tongquockhanh2510
import com.example.ModaMint_Backend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

<<<<<<< HEAD
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartIdAndVariantId(Long cartId, Long variantId);
    void deleteByCartIdAndVariantId(Long cartId, Long variantId);
    void deleteAllByCartId(Long cartId);
    List<CartItem> findByCartId(Long cartId);
}
=======
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCartId(Long cartId);
    Optional<CartItem> findByCartIdAndVariantId(Long cartId, Long variantId);
}
>>>>>>> tongquockhanh2510
