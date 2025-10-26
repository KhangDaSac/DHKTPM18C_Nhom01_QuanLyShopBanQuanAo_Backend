package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.chat.ChatRequest;
import com.example.ModaMint_Backend.dto.response.chat.ChatAIResponse;
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
import org.springframework.ai.content.Media;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatService {

    ChatClient chatClient;
    JdbcChatMemoryRepository chatMemoryRepository;
    RagService ragService;

    public ChatService(ChatClient.Builder builder, JdbcChatMemoryRepository chatMemoryRepository, RagService ragService) {
        this.chatMemoryRepository = chatMemoryRepository;
        this.ragService = ragService;
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(200)
                .build();
        this.chatClient = builder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    public ChatAIResponse chat(ChatRequest request) {
        String conversationId = "fashion-assistant-chat";
        String context = ragService.retrieveContext();
        SystemMessage systemMessage = new SystemMessage(
                """
                        You are a fashion assistant. Help users with fashion advice and recommendations.
                        Recommend some products in the store.
                        Provide product information in store.
                        Search products by image
                        """
        );


        UserMessage userMessage = new UserMessage(request.message() + "\n\nContext:\n" + context );
        Prompt prompt = new Prompt(systemMessage, userMessage);

        return chatClient
                .prompt(prompt)
                .advisors(advisorSpec -> advisorSpec.param(
                        ChatMemory.CONVERSATION_ID, conversationId
                ))
                .call()
                .entity(new ParameterizedTypeReference<>(){});
    }

    public String chatWithImage(MultipartFile file, String message) {
        Media media = Media.builder()
                .mimeType(MimeTypeUtils.parseMimeType(Objects.requireNonNull(file.getContentType())))
                .data(file.getResource())
                .build();
        return chatClient
                .prompt()
                .system("You are a fashion assistant. Help users with fashion advice and recommendations. Analyze the image provided by the user to recommend similar products available in the store.")
                .user(promptUserSpec ->
                        promptUserSpec.media(media).text(message)
                ).call().content();
    }
}
