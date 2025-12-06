package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.chat.ChatAiRequest;
import com.example.ModaMint_Backend.dto.response.chat.ChatAiResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatService {

    ChatClient chatClient;
    VectorStore vectorStore;

    public ChatService(ChatClient.Builder builder,
                       JdbcChatMemoryRepository jdbcChatMemoryRepository,
                       VectorStore vectorStore)
    {
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(jdbcChatMemoryRepository)
                .maxMessages(100)
                .build();

        this.chatClient = builder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();

        this.vectorStore = vectorStore;
    }

    public ChatAiResponse chatAi(ChatAiRequest request) {
        String conversationId = SecurityContextHolder.getContext().getAuthentication().getName();
        String userMessage = request.getMessage();

        List<Document> similarProducts = vectorStore.similaritySearch(userMessage);

        if (similarProducts.isEmpty()) {
            similarProducts = vectorStore.similaritySearch("thời trang áo quần váy giày phụ kiện");
        }

        String productList = similarProducts.stream()
                .limit(5)
                .map(doc -> {
                    String content = doc.getText();

                    String name = content.lines()
                            .filter(line -> line.startsWith("Tên sản phẩm:"))
                            .findFirst()
                            .map(line -> line.replace("Tên sản phẩm:", "").trim())
                            .orElse("Sản phẩm thời trang");

                    String price = content.lines()
                            .filter(line -> line.startsWith("Giá:"))
                            .findFirst()
                            .map(line -> line.replace("Giá:", "").trim())
                            .orElse("Không rõ giá");

                    return "• " + name + " — " + price;
                })
                .collect(java.util.stream.Collectors.joining("\n"));

        if (productList.isEmpty()) {
            productList = """
                    • Áo thun basic — 120.000đ
                    • Váy maxi hoa — 250.000đ
                    • Sơ mi trắng oversize — 180.000đ
                    """;
        }

        String systemPrompt = """
                Bạn là ModaMint AI — trợ lý thời trang thông minh và dễ thương của shop ModaMint.
                
                🎀 Phong cách: thân thiện, trẻ trung, dùng emoji nhẹ nhàng, tư vấn như bạn thân.
                🎀 Chỉ trả lời tiếng Việt.
                🎀 Nói gọn nhưng đầy đủ ý.
                
                Dưới đây là các sản phẩm hiện có trong shop (chỉ gợi ý khi phù hợp):
                
                %s
                
                ✨ QUY TẮC BẮT BUỘC:
                1. Chỉ gợi ý sản phẩm trong danh sách trên.
                2. Không được bịa ra sản phẩm không tồn tại.
                3. Nếu khách hỏi mẫu không có → trả lời lịch sự rồi gợi ý mẫu tương tự.
                4. Kết thúc mỗi câu trả lời bằng 1 câu hỏi ngắn để khách dễ tiếp tục.
                """.formatted(productList);

        SystemMessage systemMessage = new SystemMessage(systemPrompt);
        UserMessage userMsg = new UserMessage(userMessage);

        String response = chatClient.prompt()
                .messages(systemMessage, userMsg)
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();

        return ChatAiResponse.builder()
                .message(response)
                .build();
    }
}