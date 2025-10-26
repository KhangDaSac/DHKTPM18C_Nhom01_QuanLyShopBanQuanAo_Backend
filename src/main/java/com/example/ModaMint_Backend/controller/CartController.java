package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.dto.request.cart.AddCartItemRequest;
import com.example.ModaMint_Backend.dto.request.cart.UpdateCartItemRequest;
import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.cart.CartDto;
import com.example.ModaMint_Backend.dto.response.cart.CartItemDto;
import com.example.ModaMint_Backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    // NOTE: For testing, pass X-User-Id header or sessionId query param
    @GetMapping
    public ResponseEntity<ApiResponse<CartDto>> getCart(@RequestHeader(value = "X-User-Id", required = false) String userId,
                                                        @RequestParam(value = "sessionId", required = false) String sessionId) {
        CartDto cart = cartService.getCart(userId, sessionId);
        ApiResponse<CartDto> resp = ApiResponse.<CartDto>builder().code(1000).message("Lấy giỏ hàng thành công").result(cart).build();
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/items")
    public ResponseEntity<ApiResponse<CartDto>> addItem(@RequestHeader(value = "X-User-Id", required = false) String userId,
                                                            @RequestBody AddCartItemRequest req) {
        CartDto dto = cartService.addItem(userId, req);
        ApiResponse<CartDto> resp = ApiResponse.<CartDto>builder().code(1000).message("Thêm vào giỏ hàng thành công").result(dto).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<ApiResponse<CartItemDto>> updateItem(@PathVariable Long itemId, @RequestBody UpdateCartItemRequest req) {
        CartItemDto dto = cartService.updateItemQuantity(itemId, req);
        ApiResponse<CartItemDto> resp = ApiResponse.<CartItemDto>builder().code(1000).message("Cập nhật số lượng thành công").result(dto).build();
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<ApiResponse<Void>> deleteItem(@PathVariable Long itemId) {
        cartService.removeItem(itemId);
        ApiResponse<Void> resp = ApiResponse.<Void>builder().code(1000).message("Xóa item thành công").result(null).build();
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> clearCart(@RequestHeader(value = "X-User-Id", required = false) String userId) {
        cartService.clearCartForUser(userId == null ? "" : userId);
        ApiResponse<Void> resp = ApiResponse.<Void>builder().code(1000).message("Giỏ hàng đã được làm trống").result(null).build();
        return ResponseEntity.ok(resp);
    }
}
