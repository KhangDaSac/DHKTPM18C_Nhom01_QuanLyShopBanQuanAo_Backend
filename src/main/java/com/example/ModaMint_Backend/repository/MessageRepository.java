package com.example.ModaMint_Backend.repository;

import com.example.ModaMint_Backend.entity.Conversation;
import com.example.ModaMint_Backend.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByConversationOrderByTimestampAsc(Conversation conversation);
}
