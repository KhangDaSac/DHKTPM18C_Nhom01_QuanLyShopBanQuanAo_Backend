package com.example.ModaMint_Backend.scheduler;

import com.example.ModaMint_Backend.entity.Order;
import com.example.ModaMint_Backend.enums.OrderStatus;
import com.example.ModaMint_Backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Scheduler để tự động hủy các đơn hàng PENDING quá 15 phút
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OrderScheduler {

    private final OrderRepository orderRepository;

    /**
     * Chạy mỗi 5 phút để kiểm tra và hủy các đơn PENDING quá hạn
     * DISABLED: Không tự động hủy đơn hàng theo yêu cầu mới
     */
    // @Scheduled(cron = "0 */5 * * * *")
    public void cancelExpiredPendingOrders() {
        log.info("Bat dau kiem tra don hang PENDING qua han...");

        try {
            // Lấy tất cả đơn hàng PENDING
            List<Order> pendingOrders = orderRepository.findByOrderStatus(OrderStatus.PENDING);

            if (pendingOrders.isEmpty()) {
                log.info("Khong co don hang PENDING nao");
                return;
            }

            log.info("Tim thay {} don hang PENDING", pendingOrders.size());

            LocalDateTime now = LocalDateTime.now();
            int cancelledCount = 0;

            for (Order order : pendingOrders) {
                LocalDateTime createAt = order.getCreateAt();
                long minutesElapsed = ChronoUnit.MINUTES.between(createAt, now);

                // Nếu đơn hàng đã quá 15 phút
                if (minutesElapsed >= 15) {
                    log.warn("Don hang #{} da qua {} phut - Tu dong huy",
                            order.getId(), minutesElapsed);

                    order.setOrderStatus(OrderStatus.CANCELLED);
                    orderRepository.save(order);
                    cancelledCount++;
                }
            }

            if (cancelledCount > 0) {
                log.info("Da tu dong huy {} don hang qua han", cancelledCount);
            } else {
                log.info("Tat ca don PENDING deu con trong thoi gian thanh toan");
            }

        } catch (Exception e) {
            log.error("Loi khi kiem tra don hang qua han: {}", e.getMessage(), e);
        }
    }
}
