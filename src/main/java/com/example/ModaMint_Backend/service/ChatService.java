package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.chat.ChatAiRequest;
import com.example.ModaMint_Backend.dto.response.chat.ChatAiResponse;
import com.example.ModaMint_Backend.entity.Message;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatService {
    ChatClient chatClient;

    public ChatService(ChatClient.Builder builder, JdbcChatMemoryRepository jdbcChatMemoryRepository) {

        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(jdbcChatMemoryRepository)
                .maxMessages(100)
                .build();

        this.chatClient = builder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();

    }

    public ChatAiResponse chatAi(ChatAiRequest request) {
        SecurityContext context = SecurityContextHolder.getContext();
        String conversationId = context.getAuthentication().getName();

        SystemMessage systemMessage = new SystemMessage("""
                You are ModaMint AI, an AI specialized in fashion and clothing.
                Provide helpful and concise responses to user inquiries about fashion trends, styling tips, and clothing recommendations.
                """);

        UserMessage userMessage = new UserMessage(request.getMessage());

        Prompt prompt = new Prompt(systemMessage, userMessage);

        return ChatAiResponse.builder()
                .message(
                        chatClient
                                .prompt(prompt)
                                .advisors(advisorSpec -> advisorSpec.param(
                                        ChatMemory.CONVERSATION_ID, conversationId
                                ))
                                .call()

                                .content()
                )
                .build();
    }
}
