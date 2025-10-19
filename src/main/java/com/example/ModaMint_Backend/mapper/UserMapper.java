package com.example.ModaMint_Backend.mapper;

import com.example.ModaMint_Backend.dto.request.user.UserCreationRequest;
import com.example.ModaMint_Backend.dto.request.user.UserUpdateRequest;
import com.example.ModaMint_Backend.dto.response.user.UserResponse;
import com.example.ModaMint_Backend.entity.User;
import com.example.ModaMint_Backend.entity.Role;
import com.example.ModaMint_Backend.enums.RoleName;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.cache.annotation.CachePut;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    User toUser(UserCreationRequest request);

    @Mapping(target = "roles", expression = "java(user.getRoles().stream().map(role -> role.getName().name()).collect(java.util.stream.Collectors.toSet()))")
    @Mapping(source = "image", target = "image")
    UserResponse toUserResponse(User user);
    void updateUser(UserUpdateRequest request, @MappingTarget User user);

    default Set<String> mapRolesToStrings(Set<Role> roles) {
        return roles.stream().map(role -> role.getName().name()).collect(java.util.stream.Collectors.toSet());
    }

    default Set<Role> mapStringsToRoles(Set<String> roleNames) {
        return roleNames.stream().map(roleName -> {
            Role role = new Role();
            role.setName(RoleName.valueOf(roleName));
            return role;
        }).collect(java.util.stream.Collectors.toSet());
    }
}
