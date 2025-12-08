package com.example.ModaMint_Backend.mapper;

import com.example.ModaMint_Backend.dto.request.user.UserCreationRequest;
import com.example.ModaMint_Backend.dto.request.user.UserUpdateRequest;
import com.example.ModaMint_Backend.dto.response.user.UserResponse;
import com.example.ModaMint_Backend.entity.User;
import com.example.ModaMint_Backend.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "roles", ignore = true) // Roles will be handled by UserService
    User toUser(UserCreationRequest request);

    @Mapping(target = "roles", expression = "java(user.getRoles().stream().map(role -> role.getName().name()).collect(java.util.stream.Collectors.toSet()))")
    @Mapping(source = "image", target = "image")
    UserResponse toUserResponse(User user);
    
    void updateUser(UserUpdateRequest request, @MappingTarget User user);

    default Set<String> mapRolesToStrings(Set<Role> roles) {
        return roles.stream().map(role -> role.getName().name()).collect(java.util.stream.Collectors.toSet());
    }
}