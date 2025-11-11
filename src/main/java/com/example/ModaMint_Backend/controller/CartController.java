package com.example.ModaMint_Backend.controller;


import com.example.ModaMint_Backend.dto.request.cart.AddCartItemRequest;
import com.example.ModaMint_Backend.dto.request.cart.UpdateCartItemRequest;
import com.example.ModaMint_Backend.dto.request.cartitem.CartItemRequest;
import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.cart.CartDto;
import com.example.ModaMint_Backend.dto.response.cart.CartItemDto;
import com.example.ModaMint_Backend.entity.User;
import com.example.ModaMint_Backend.repository.UserRepository;
import com.example.ModaMint_Backend.dto.response.cart.CartResponse;
import com.example.ModaMint_Backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.access.prepost.PreAuthorize;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final UserRepository userRepository;

    // Helper method to extract userId from JWT token
    private String getUserIdFromAuth() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal() instanceof Jwt) {
                Jwt jwt = (Jwt) auth.getPrincipal();
                String username = jwt.getSubject(); // 'sub' claim contains username
                System.out.println("DEBUG: Extracted username from JWT: " + username);

                // Tìm user theo username để lấy userId
                User user = userRepository.findByUsername(username).orElse(null);
                if (user != null) {
                    String userId = user.getId();
                    System.out.println("DEBUG: Found userId from username: " + userId);
                    return userId;
                }
            }
        } catch (Exception e) {
            System.out.println("DEBUG: Failed to extract userId from JWT: " + e.getMessage());
        }
        return null;
    }

    // NOTE: For testing, pass X-User-Id header or sessionId query param
    @GetMapping
    public ResponseEntity<ApiResponse<CartDto>> getCart(@RequestHeader(value = "X-User-Id", required = false) String userIdHeader,
                                                        @RequestParam(value = "sessionId", required = false) String sessionId) {
        // Ưu tiên lấy từ JWT token, fallback sang header
        String userId = getUserIdFromAuth();
        if (userId == null) {
            userId = userIdHeader;
        }

        System.out.println("DEBUG: CartController.getCart - userId from JWT: " + getUserIdFromAuth() + ", from header: " + userIdHeader + ", final userId: " + userId + ", sessionId: " + sessionId);

        CartDto cart = cartService.getCart(userId, sessionId);
        ApiResponse<CartDto> resp = ApiResponse.<CartDto>builder().code(1000).message("Lấy giỏ hàng thành công").result(cart).build();
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/items")
    public ResponseEntity<ApiResponse<CartDto>> addItem(@RequestHeader(value = "X-User-Id", required = false) String userIdHeader,
                                                            @RequestBody AddCartItemRequest req) {
        try {
            // Ưu tiên lấy từ JWT token, fallback sang header
            String userId = getUserIdFromAuth();
            if (userId == null) {
                userId = userIdHeader;
            }

            System.out.println("DEBUG: CartController.addItem - userId from JWT: " + getUserIdFromAuth() + ", from header: " + userIdHeader + ", final userId: " + userId);

            CartDto dto = cartService.addItem(userId, req);
            ApiResponse<CartDto> resp = ApiResponse.<CartDto>builder().code(1000).message("Thêm vào giỏ hàng thành công").result(dto).build();
            return ResponseEntity.status(HttpStatus.CREATED).body(resp);
        } catch (IllegalArgumentException ex) {
            ApiResponse<CartDto> resp = ApiResponse.<CartDto>builder().code(400).message(ex.getMessage()).result(null).build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        } catch (IllegalStateException ex) {
            // User không tồn tại - yêu cầu đăng nhập lại
            ApiResponse<CartDto> resp = ApiResponse.<CartDto>builder()
                    .code(401)
                    .message("Phiên đăng nhập không hợp lệ. Vui lòng đăng nhập lại.")
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resp);
        }
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<ApiResponse<CartItemDto>> updateItemQuantity(
            @PathVariable Long itemId,
            @RequestBody UpdateCartItemRequest req) {
        try {
            CartItemDto updated = cartService.updateItemQuantity(itemId, req);
            ApiResponse<CartItemDto> resp = ApiResponse.<CartItemDto>builder()
                    .code(1000)
                    .message("Cập nhật số lượng thành công")
                    .result(updated)
                    .build();
            return ResponseEntity.ok(resp);
        } catch (Exception ex) {
            ApiResponse<CartItemDto> resp = ApiResponse.<CartItemDto>builder()
                    .code(400)
                    .message("Không thể cập nhật: " + ex.getMessage())
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        }
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<ApiResponse<Void>> deleteItem(@PathVariable Long itemId) {
        try {
            cartService.removeItem(itemId);
            ApiResponse<Void> resp = ApiResponse.<Void>builder()
                    .code(1000)
                    .message("Xóa sản phẩm thành công")
                    .result(null)
                    .build();
            return ResponseEntity.ok(resp);
        } catch (Exception ex) {
            ApiResponse<Void> resp = ApiResponse.<Void>builder()
                    .code(400)
                    .message("Không thể xóa: " + ex.getMessage())
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        }
    }

    @DeleteMapping("/remove/{variantId}")
    public ApiResponse<Void> removeItem(
            @RequestParam String customerId,
            @PathVariable Long variantId
    ) {
//        cartService.removeItem(customerId, variantId);
        return ApiResponse.<Void>builder().message("Item removed successfully").build();
    }

    @DeleteMapping("/remove/{variantId}/complete")
    public ApiResponse<Void> removeItemCompletely(
            @RequestParam String customerId,
            @PathVariable Long variantId
    ) {
//        cartService.removeItemCompletely(customerId, variantId);
        return ApiResponse.<Void>builder().message("Item completely removed successfully").build();
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> clearCart(@RequestHeader(value = "X-User-Id", required = false) String userIdHeader) {
        // Ưu tiên lấy từ JWT token, fallback sang header
        String userId = getUserIdFromAuth();
        if (userId == null) {
            userId = userIdHeader;
        }

        cartService.clearCartForUser(userId == null ? "" : userId);
        ApiResponse<Void> resp = ApiResponse.<Void>builder().code(1000).message("Giỏ hàng đã được làm trống").result(null).build();
        return ResponseEntity.ok(resp);
    }
}
