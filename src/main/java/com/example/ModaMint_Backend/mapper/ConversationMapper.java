package com.example.ModaMint_Backend.mapper;

import com.example.ModaMint_Backend.dto.response.chat.ConversationResponse;
import com.example.ModaMint_Backend.entity.Conversation;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ConversationMapper {
    ConversationResponse toConversationResponse(Conversation conversation);
}
