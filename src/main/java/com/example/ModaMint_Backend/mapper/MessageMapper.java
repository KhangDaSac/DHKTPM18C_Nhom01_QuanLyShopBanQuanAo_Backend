package com.example.ModaMint_Backend.mapper;

import com.example.ModaMint_Backend.dto.response.chat.MessageResponse;
import com.example.ModaMint_Backend.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MessageMapper {
    MessageResponse toMessageResponse(Message message);
}
