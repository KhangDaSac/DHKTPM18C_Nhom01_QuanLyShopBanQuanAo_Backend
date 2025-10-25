package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.chat.ChatRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatService {

    ChatClient chatClient;

    public ChatService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String chat(ChatRequest request) {
        return chatClient
                .prompt(request.message())
                .call()
                .content();
    }
}
