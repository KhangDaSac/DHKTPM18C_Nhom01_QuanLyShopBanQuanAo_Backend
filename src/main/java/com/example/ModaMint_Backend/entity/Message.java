package com.example.ModaMint_Backend.entity;

import com.example.ModaMint_Backend.entity.Conversation;
import com.example.ModaMint_Backend.enums.SenderType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String content;
    LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "sender_type")
    SenderType senderType;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    Conversation conversation;
}
