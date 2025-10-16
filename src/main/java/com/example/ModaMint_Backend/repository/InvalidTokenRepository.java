package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidTokenRepository extends JpaRepository<InvalidToken, String> {
    boolean existsById(String s);
}
