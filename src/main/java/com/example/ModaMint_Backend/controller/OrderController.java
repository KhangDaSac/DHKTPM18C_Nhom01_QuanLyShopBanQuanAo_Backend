package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.dto.request.order.OrderRequest;
import com.example.ModaMint_Backend.dto.response.ApiResponse;
import com.example.ModaMint_Backend.dto.response.order.OrderResponse;
import com.example.ModaMint_Backend.service.OrderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<OrderResponse> createOrder(@RequestBody @Valid OrderRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.createOrder(request))
                .message("Tạo đơn hàng mới thành công")
                .build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<OrderResponse>> getAllOrders() {
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getAllOrders())
                .message("Lấy danh sách đơn hàng thành công")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<OrderResponse> getOrderById(@PathVariable Long id) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.getOrderById(id))
                .message("Lấy thông tin đơn hàng thành công")
                .build();
    }

    @GetMapping("/paginated")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Page<OrderResponse>> getOrdersWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ApiResponse.<Page<OrderResponse>>builder()
                .result(orderService.getOrdersWithPagination(pageable))
                .message("Lấy danh sách đơn hàng phân trang thành công")
                .build();
    }

    @GetMapping("/customer/{customerId}")
    public ApiResponse<List<OrderResponse>> getOrdersByCustomerId(@PathVariable String customerId) {
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getOrdersByCustomerId(customerId))
                .message("Lấy danh sách đơn hàng của khách hàng thành công")
                .build();
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<OrderResponse>> getOrdersByStatus(@PathVariable String status) {
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getOrdersByStatus(status))
                .message("Lấy danh sách đơn hàng theo trạng thái thành công")
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<OrderResponse> updateOrder(
            @PathVariable Long id,
            @RequestBody @Valid OrderRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.updateOrder(id, request))
                .message("Cập nhật đơn hàng thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ApiResponse.<String>builder()
                .result("Đơn hàng đã được xóa")
                .message("Xóa đơn hàng thành công")
                .build();
    }

    @GetMapping("/count")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Long> getTotalOrderCount() {
        return ApiResponse.<Long>builder()
                .result(orderService.getTotalOrderCount())
                .message("Lấy tổng số lượng đơn hàng thành công")
                .build();
    }

    @GetMapping("/detail/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')") 
    public ApiResponse<OrderResponse> getOrderDetailById(@PathVariable Long id) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.getOrderDetailById(id))
                .message("Lấy thông tin đơn hàng và chi tiết thành công") 
                .build();
    }

    /**
     * Hủy đơn hàng - Chỉ cho phép khách hàng hủy đơn ở trạng thái PENDING
     * @param id Order ID
     * @param customerId Customer ID (từ request param)
     * @param cancelReason Lý do hủy đơn hàng (từ request param)
     */
    @PutMapping("/{id}/cancel")
    public ApiResponse<String> cancelOrder(
            @PathVariable Long id,
            @RequestParam String customerId,
            @RequestParam String cancelReason) {
        orderService.cancelOrder(id, customerId, cancelReason);
        return ApiResponse.<String>builder()
                .result("Đơn hàng đã được hủy")
                .message("Hủy đơn hàng thành công")
                .build();
    }
}
