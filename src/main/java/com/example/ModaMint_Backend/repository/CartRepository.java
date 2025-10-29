package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.Cart;
<<<<<<< HEAD
import com.example.ModaMint_Backend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
=======
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

>>>>>>> tongquockhanh2510
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCustomerId(String customerId);
    Optional<Cart> findBySessionId(String sessionId);
<<<<<<< HEAD
}
=======
}
>>>>>>> tongquockhanh2510
