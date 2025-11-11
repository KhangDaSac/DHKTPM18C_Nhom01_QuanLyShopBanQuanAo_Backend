package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    Optional<Conversation> findByUserId(String userId);
}
