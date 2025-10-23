package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.user.UserCreationRequest;
import com.example.ModaMint_Backend.dto.request.user.UserUpdateRequest;
import com.example.ModaMint_Backend.dto.response.user.UserResponse;
import com.example.ModaMint_Backend.entity.Role;
import com.example.ModaMint_Backend.entity.User;
import com.example.ModaMint_Backend.enums.RoleName;
import com.example.ModaMint_Backend.exception.AppException;
import com.example.ModaMint_Backend.exception.ErrorCode;
import com.example.ModaMint_Backend.mapper.UserMapper;
import com.example.ModaMint_Backend.repository.RoleRepository;
import com.example.ModaMint_Backend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    // Ảnh mặc định cố định
    private static final String DEFAULT_IMAGE_URL = "https://res.cloudinary.com/dhjksobmf/image/upload/v1729402353/default-avatar_c2opdo.png";

    public UserResponse createRequest(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Xử lý ảnh mặc định nếu không có ảnh
        if (!StringUtils.hasText(request.getImage())) {
            user.setImage(DEFAULT_IMAGE_URL);
        }

        Set<Role> roleEntities = new HashSet<>();

        if (request.getRoles() == null || request.getRoles().isEmpty()) {
            Role defaultRole = (Role) roleRepository.findByName(RoleName.CUSTOMER)
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
            roleEntities.add(defaultRole);
        } else {
            for (String roleNameStr : request.getRoles()) {
                RoleName roleName;
                try {
                    roleName = RoleName.valueOf(roleNameStr);
                } catch (IllegalArgumentException e) {
                    throw new AppException(ErrorCode.ROLE_NOT_FOUND);
                }
                Role role = (Role) roleRepository.findByName(roleName)
                        .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
                roleEntities.add(role);
            }
        }

        user.setRoles(roleEntities);

        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    public UserResponse getUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(request, user);

        // Xử lý ảnh mặc định nếu không có ảnh trong request update
        if (!StringUtils.hasText(request.getImage())) {
            user.setImage(DEFAULT_IMAGE_URL);
        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        userRepository.deleteById(userId);
    }
}
