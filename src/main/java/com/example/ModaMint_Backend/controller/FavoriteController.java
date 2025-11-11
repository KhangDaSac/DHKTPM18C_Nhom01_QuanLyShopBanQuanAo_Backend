package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.favorite.FavoriteDto;
import com.example.ModaMint_Backend.entity.User;
import com.example.ModaMint_Backend.repository.UserRepository;
import com.example.ModaMint_Backend.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final UserRepository userRepository;


    private String getUserIdFromAuth() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal() instanceof Jwt) {
                Jwt jwt = (Jwt) auth.getPrincipal();
                String username = jwt.getSubject();
                
                User user = userRepository.findByUsername(username).orElse(null);
                if (user != null) {
                    return user.getId();
                }
            }
        } catch (Exception e) {
            System.out.println("DEBUG: Failed to extract userId from JWT: " + e.getMessage());
        }
        return null;
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<FavoriteDto>>> getFavorites(@RequestHeader(value = "X-User-Id", required = false) String userIdHeader) {
        String userId = getUserIdFromAuth();
        if (userId == null) {
            userId = userIdHeader;
        }
        
        List<FavoriteDto> list = favoriteService.getFavoritesForUser(userId == null ? "" : userId);
        ApiResponse<List<FavoriteDto>> resp = ApiResponse.<List<FavoriteDto>>builder()
                .code(1000)
                .message("Lấy danh sách yêu thích thành công")
                .result(list)
                .build();
        return ResponseEntity.ok(resp);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FavoriteDto>> addFavorite(
            @RequestHeader(value = "X-User-Id", required = false) String userIdHeader,
            @RequestBody(required = true) java.util.Map<String, Long> body) {
        String userId = getUserIdFromAuth();
        if (userId == null) {
            userId = userIdHeader;
        }
        
        Long productId = body.get("productId");
        FavoriteDto dto = favoriteService.addFavorite(userId == null ? "" : userId, productId);
        ApiResponse<FavoriteDto> resp = ApiResponse.<FavoriteDto>builder()
                .code(1000)
                .message("Thêm vào yêu thích thành công")
                .result(dto)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> removeFavorite(
            @RequestHeader(value = "X-User-Id", required = false) String userIdHeader,
            @PathVariable Long productId) {
        String userId = getUserIdFromAuth();
        if (userId == null) {
            userId = userIdHeader;
        }
        
        System.out.println("DEBUG: FavoriteController.removeFavorite - userId from JWT: " + getUserIdFromAuth() 
                + ", from header: " + userIdHeader + ", final userId: " + userId + ", productId: " + productId);
        
        favoriteService.removeFavorite(userId == null ? "" : userId, productId);
        ApiResponse<Void> resp = ApiResponse.<Void>builder()
                .code(1000)
                .message("Xóa khỏi yêu thích thành công")
                .result(null)
                .build();
        return ResponseEntity.ok(resp);
    }
}
