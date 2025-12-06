package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.chat.ChatAiRequest;
import com.example.ModaMint_Backend.dto.response.chat.ChatAiResponse;
import com.example.ModaMint_Backend.dto.response.chat.MessageChatAiResponse;
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
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatService {

    ChatClient chatClient;
    VectorStore vectorStore;
    JdbcChatMemoryRepository jdbcChatMemoryRepository;
    ProductVectorLoader productVectorLoader;

    public ChatService(ChatClient.Builder builder,
                       JdbcChatMemoryRepository jdbcChatMemoryRepository,
                       VectorStore vectorStore,
                          ProductVectorLoader productVectorLoader
    )
    {
        this.productVectorLoader = productVectorLoader;
        this.jdbcChatMemoryRepository = jdbcChatMemoryRepository;
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

        productVectorLoader.loadProductsToVectorDB();
        List<Document> similarProducts = vectorStore.similaritySearch(userMessage);

        if (similarProducts.isEmpty()) {
            similarProducts = vectorStore.similaritySearch("thời trang áo quần váy giày phụ kiện");
        }

        String productList = similarProducts.stream()
                .limit(5)
                .map(Document::getText)
                .collect(Collectors.joining("\n"));

        String systemPrompt = """
                Bạn là ModaMint AI — trợ lý thời trang thông minh và dễ thương của shop ModaMint.
                
                Phong cách: thân thiện, trẻ trung, dùng emoji nhẹ nhàng, tư vấn như bạn thân.
                Chỉ trả lời tiếng Việt.
                Nói gọn nhưng đầy đủ ý.
                
                Dưới đây là các sản phẩm hiện có trong shop (chỉ gợi ý khi phù hợp):
                
                %s
                
                QUY TẮC BẮT BUỘC:
                1. Chỉ gợi ý sản phẩm trong danh sách trên.
                2. Không được bịa ra sản phẩm không tồn tại.
                3. Nếu khách hỏi mẫu không có → trả lời lịch sự rồi gợi ý mẫu tương tự.
                4. Kết thúc mỗi câu trả lời bằng 1 câu hỏi ngắn để khách dễ tiếp tục.
                5. Khi khách hàng muốn thông tin chi tiết về một sản phẩm cụ thẻ nào đó thì trả lời về sản phẩm phải cho biết đầy đủ thông tin về sản phẩm đó như các biến thể, thương hiệu, các loại size, màu sắc,.... và bắt buộc phải có giá của từng biến thể của sản phẩm
                6. Khi khách hàng muốn tư vần về một loại sản phẩm thì hãy liệt kê một vài sản phẩm phù hợp với yêu cầu của khách hàng càng nhiều thông tin về sản phẩm càng tốt.
                7. Nếu biết tên khách hàng thì hãy gọi tên khách hàng trong câu trả lời để tạo sự thân mật.
                """.formatted(productList);
        System.out.println("hello" + productList);

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

    public List<MessageChatAiResponse> getFullHistory() {
        String conversationId = SecurityContextHolder.getContext().getAuthentication().getName();
        return jdbcChatMemoryRepository.findByConversationId(conversationId)
                .stream()
                .map(m -> MessageChatAiResponse.builder()
                        .type(m.getMessageType().toString())
                        .content(m.getText())
                        .build()
                )
                .toList();
    }
}