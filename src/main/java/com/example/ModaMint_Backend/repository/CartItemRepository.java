package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.CartItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartItemRepository extends CrudRepository<CartItem, Integer> {
    List<CartItem> findByCartId(Long cartId);

    void deleteByCartId(Long id);
}
