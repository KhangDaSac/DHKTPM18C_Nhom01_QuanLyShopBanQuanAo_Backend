package com.example.ModaMint_Backend.mapper;

import com.example.ModaMint_Backend.dto.request.user.UserCreationRequest;
import com.example.ModaMint_Backend.dto.request.user.UserUpdateRequest;
import com.example.ModaMint_Backend.dto.response.user.UserResponse;
import com.example.ModaMint_Backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.cache.annotation.CachePut;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);
    void updateUser(UserUpdateRequest request, @MappingTarget User user);
}
