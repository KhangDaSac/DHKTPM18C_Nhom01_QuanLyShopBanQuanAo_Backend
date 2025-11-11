package com.example.ModaMint_Backend.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatService {

    ChatClient chatClient;
    VectorStore vectorStore;
    ProductVectorLoader productVectorLoader;

    public ChatService(ChatClient.Builder chatClientBuilder,
                       VectorStore vectorStore,
                       ProductVectorLoader productVectorLoader) {
        this.chatClient = chatClientBuilder.build();
        this.vectorStore = vectorStore;
        this.productVectorLoader = productVectorLoader;
    }

    public String processUserInput(String userInput) {
        productVectorLoader.loadProductsToVectorDB();

        QuestionAnswerAdvisor qaAdvisor = new QuestionAnswerAdvisor(vectorStore);

        return chatClient.prompt()
                .system("""
                Bạn là trợ lý AI bán hàng của cửa hàng thời trang ModaMint.
                Hãy trả lời thân thiện, chuyên nghiệp, và gợi ý thêm sản phẩm phù hợp.
                Nếu không chắc câu trả lời, hãy nói lịch sự rằng bạn sẽ tìm hiểu thêm.
            """)
                .user(userInput)
                .advisors(qaAdvisor)
                .call()
                .content();
    }
}
