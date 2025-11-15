package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.chat.MessageRequest;
import com.example.ModaMint_Backend.dto.response.chat.ConversationResponse;
import com.example.ModaMint_Backend.dto.response.chat.MessageResponse;
import com.example.ModaMint_Backend.entity.Conversation;
import com.example.ModaMint_Backend.entity.Message;
import com.example.ModaMint_Backend.entity.User;
import com.example.ModaMint_Backend.enums.SenderType;
import com.example.ModaMint_Backend.exception.AppException;
import com.example.ModaMint_Backend.exception.ErrorCode;
import com.example.ModaMint_Backend.mapper.ConversationMapper;
import com.example.ModaMint_Backend.mapper.MessageMapper;
import com.example.ModaMint_Backend.repository.ConversationRepository;
import com.example.ModaMint_Backend.repository.MessageRepository;
import com.example.ModaMint_Backend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatService {

    ChatClient chatClient;
    VectorStore vectorStore;
    ProductVectorLoader productVectorLoader;

    MessageRepository messageRepository;
    ConversationRepository conversationRepository;
    UserRepository userRepository;

    MessageMapper messageMapper;
    ConversationMapper conversationMapper;


    public ChatService(
            ChatClient.Builder chatClientBuilder,
            VectorStore vectorStore,
            ProductVectorLoader productVectorLoader,
            MessageRepository messageRepository,
            ConversationRepository conversationRepository,
            UserRepository userRepository,
            MessageMapper messageMapper,
            ConversationMapper conversationMapper
    ) {
        this.chatClient = chatClientBuilder.build();
        this.vectorStore = vectorStore;
        this.productVectorLoader = productVectorLoader;
        this.conversationMapper = conversationMapper;
        this.messageMapper = messageMapper;
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    public MessageResponse chatWithAi(MessageRequest request) {
        System.out.println("ChatService.chatWithAi called with request: " + request);
        Conversation conversation = conversationRepository.findById(request.getConversationId())
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND));
        productVectorLoader.loadProductsToVectorDB();

        QuestionAnswerAdvisor qaAdvisor = new QuestionAnswerAdvisor(vectorStore);

        String content = chatClient.prompt()
                .system("""
                            Bạn là trợ lý AI bán hàng của cửa hàng thời trang ModaMint.
                            Hãy trả lời thân thiện, chuyên nghiệp, và gợi ý thêm sản phẩm phù hợp.
                            Nếu không chắc câu trả lời, hãy nói lịch sự rằng bạn sẽ tìm hiểu thêm.
                        """)
                .user(request.getContent())
                .advisors(qaAdvisor)
                .call()
                .content();

        Message message = Message.builder()
                .conversation(conversation)
                .content(content)
                .timestamp(LocalDateTime.now())
                .senderType(SenderType.AI)
                .build();

        Message messageSaved = messageRepository.save(message);
        return messageMapper.toMessageResponse(messageSaved);
    }

    public MessageResponse chatWithShop(MessageRequest request) {
        Conversation conversation = conversationRepository.findById(request.getConversationId())
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND));

        Message message = Message.builder()
                .conversation(conversation)
                .content(request.getContent())
                .timestamp(LocalDateTime.now())
                .senderType(request.getSenderType())
                .build();

        Message messageSaved = messageRepository.save(message);
        return messageMapper.toMessageResponse(messageSaved);
    }

    public ConversationResponse getConversationById(String userId) {
        Conversation conversation = conversationRepository.findByUserId(userId)
                .orElseGet(()-> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
                    Conversation newConversation = Conversation.builder()
                            .user(user)
                            .build();
                    return conversationRepository.save(newConversation);
                });

        return conversationMapper.toConversationResponse(conversation);
    }

    public List<MessageResponse> getChatHistory(Long conversationId) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND));

        List<Message> messages = messageRepository.findByConversationOrderByTimestampAsc(conversation);

        return messages.stream()
                .map(messageMapper::toMessageResponse)
                .toList();
    }
}
