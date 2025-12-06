package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.dto.request.user.UserCreationRequest;
import com.example.ModaMint_Backend.dto.request.user.UserUpdateRequest;
import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.user.UserResponse;
import com.example.ModaMint_Backend.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createRequest(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/deactivate")
    public ApiResponse<UserResponse> deactivateUser(@RequestParam("userId") String userId) {
        UserResponse response = userService.deactivateUser(userId);
        return ApiResponse.<UserResponse>builder()
                .result(response)
                .message("User has been deactivated")
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/activate")
    public ApiResponse<UserResponse> activateUser(@RequestParam("userId") String userId) {
        UserResponse response = userService.activateUser(userId);
        return ApiResponse.<UserResponse>builder()
                .result(response)
                .message("User has been activated")
                .build();
    }

    @PutMapping("/{userId}")
    public ApiResponse<UserResponse> updateUser(@PathVariable("userId") String userId,
                                               @RequestBody @Valid UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }
}
