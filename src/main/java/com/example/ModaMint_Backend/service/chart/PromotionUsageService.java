package com.example.ModaMint_Backend.service.chart;

import com.example.ModaMint_Backend.dto.response.charts.PromotionTopResponse;
import com.example.ModaMint_Backend.dto.response.charts.PromotionUsageHistoryResponse;
import com.example.ModaMint_Backend.entity.Order;
import com.example.ModaMint_Backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromotionUsageService {

    private final OrderRepository orderRepository;

    public List<PromotionUsageHistoryResponse> getUsageHistory(LocalDate dateFrom, LocalDate dateTo) {
        List<Order> orders = orderRepository.findAll();

        Map<LocalDate, Long> counts = new TreeMap<>();

        for (Order order : orders) {
            String code = extractPromotionCode(order);
            if (code == null) continue;
            LocalDateTime created = order.getCreateAt();
            if (created == null) continue;
            LocalDate date = created.toLocalDate();

            if (dateFrom != null && date.isBefore(dateFrom)) continue;
            if (dateTo != null && date.isAfter(dateTo)) continue;

            counts.put(date, counts.getOrDefault(date, 0L) + 1L);
        }

        return counts.entrySet().stream()
            .map(e -> {
                PromotionUsageHistoryResponse r = new PromotionUsageHistoryResponse();
                r.setDate(e.getKey());
                r.setCount(e.getValue());
                return r;
            })
            .collect(Collectors.toList());
    }

    public List<PromotionTopResponse> getTopPromotions(int limit) {
        List<Order> orders = orderRepository.findAll();

        Map<String, Long> counts = new HashMap<>();

        for (Order order : orders) {
            String code = extractPromotionCode(order);
            if (code == null) continue;
            counts.put(code, counts.getOrDefault(code, 0L) + 1L);
        }

        return counts.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(limit)
            .map(e -> {
                PromotionTopResponse r = new PromotionTopResponse();
                r.setCode(e.getKey());
                r.setCount(e.getValue());
                return r;
            })
            .collect(Collectors.toList());
    }

    private String extractPromotionCode(Order order) {
        if (order == null) return null;
        if (order.getAmountPromotion() != null && order.getAmountPromotion().getCode() != null) {
            return order.getAmountPromotion().getCode();
        }
        if (order.getPercentPromotion() != null && order.getPercentPromotion().getCode() != null) {
            return order.getPercentPromotion().getCode();
        }
        return null;
    }
}
