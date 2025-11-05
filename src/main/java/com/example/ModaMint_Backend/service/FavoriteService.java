package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.response.favorite.FavoriteDto;
import com.example.ModaMint_Backend.entity.Favorite;
import com.example.ModaMint_Backend.repository.FavoriteRepository;
import com.example.ModaMint_Backend.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FavoriteService {
    FavoriteRepository favoriteRepository;
    ProductService productService;

    public List<FavoriteDto> getFavoritesForUser(String userId) {
    return favoriteRepository.findByUserId(userId)
        .stream()
        .map(f -> {
            var p = productService.getProductById(f.getProductId());
            return FavoriteDto.builder()
                .id(f.getId())
                .productId(f.getProductId())
                .productName(p.getName())
                .price(p.getPrice())
                .build();
        })
        .collect(Collectors.toList());
    }

    public FavoriteDto addFavorite(String userId, Long productId) {
        if (favoriteRepository.existsByUserIdAndProductId(userId, productId)) {
            return null;
        }
        Favorite f = Favorite.builder().userId(userId).productId(productId).build();
        Favorite saved = favoriteRepository.save(f);

    var p = productService.getProductById(saved.getProductId());
    return FavoriteDto.builder()
        .id(saved.getId())
        .productId(saved.getProductId())
        .productName(p.getName())
        .price(p.getPrice())
        .build();
    }

    public void removeFavorite(String userId, Long productId) {
        favoriteRepository.deleteByUserIdAndProductId(userId, productId);
    }
}
