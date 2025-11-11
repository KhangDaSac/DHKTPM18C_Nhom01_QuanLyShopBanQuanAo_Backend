package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.dto.request.chat.MessageRequest;
import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.chat.ConversationResponse;
import com.example.ModaMint_Backend.dto.response.chat.MessageResponse;
import com.example.ModaMint_Backend.service.ChatService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatController {

    ChatService chatService;

    @GetMapping("/conversation/{userId}")
    public ApiResponse<ConversationResponse> getConversationByUserId(@PathVariable String userId) {
        ConversationResponse conversation = chatService.getConversationById(userId);
        return ApiResponse.<ConversationResponse>builder()
                .result(conversation)
                .message("Lấy cuộc trò chuyện thành công")
                .build();
    }

    @GetMapping("/history/{conversationId}")
    public ApiResponse<List<MessageResponse>> getChatHistory(@PathVariable Long conversationId) {
        List<MessageResponse> history = chatService.getChatHistory(conversationId);
        return ApiResponse.<List<MessageResponse>>builder()
                .result(history)
                .message("Lấy lịch sử trò chuyện thành công")
                .build();
    }

    @MessageMapping("/sendMessage/shop")
    @SendTo("/topic/messages/shop")
    public ApiResponse<MessageResponse> handleShopMessage(@RequestBody MessageRequest request) {
        return ApiResponse.<MessageResponse>builder()
                .result(chatService.chatWithShop(request))
                .message("Thành công")
                .build();
    }

    @PostMapping("/sendMessage/ai")
    public ApiResponse<MessageResponse>  handleAiMessage(@RequestBody MessageRequest request) {
        return ApiResponse.<MessageResponse>builder()
                .result(chatService.chatWithAi(request))
                .message("Thành công")
                .build();
    }
 }