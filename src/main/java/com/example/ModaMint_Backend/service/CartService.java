package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.cart.AddCartItemRequest;
import com.example.ModaMint_Backend.dto.request.cart.UpdateCartItemRequest;
import com.example.ModaMint_Backend.dto.response.cart.CartDto;
import com.example.ModaMint_Backend.dto.response.cart.CartItemDto;
import com.example.ModaMint_Backend.entity.Cart;
import com.example.ModaMint_Backend.entity.CartItem;
import com.example.ModaMint_Backend.entity.ProductVariant;
import com.example.ModaMint_Backend.repository.CartItemRepository;
import com.example.ModaMint_Backend.repository.CartRepository;
import com.example.ModaMint_Backend.repository.ProductVariantRepository;
import com.example.ModaMint_Backend.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartService {
    CartRepository cartRepository;
    CartItemRepository cartItemRepository;
    ProductVariantRepository productVariantRepository;
    ProductService productService;

    public CartDto getCart(String userId, String sessionId) {
        Cart cart = null;
        if (userId != null) {
            cart = cartRepository.findByCustomerId(userId).orElse(null);
        }
        if (cart == null && sessionId != null) {
            cart = cartRepository.findBySessionId(sessionId).orElse(null);
        }
        if (cart == null) {
            return CartDto.builder().items(List.of()).subtotal(0L).shipping(0L).total(0L).build();
        }

        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());
        List<CartItemDto> itemDtos = items.stream().map(it -> {
            ProductVariant variant = it.getProductVariant();
            Long productId = variant != null ? variant.getProductId() : null;
            String productName = null;
            BigDecimal unitPrice = BigDecimal.ZERO;
            if (productId != null) {
                var p = productService.getProductById(productId);
                productName = p.getName();
            }
            if (variant != null && variant.getPrice() != null) unitPrice = variant.getPrice();

            long qty = it.getQuantity() != null ? it.getQuantity() : 0;
            long total = unitPrice.multiply(BigDecimal.valueOf(qty)).longValue();

            return CartItemDto.builder()
                    .itemId(it.getId())
                    .variantId(it.getVariantId())
                    .productId(productId)
                    .productName(productName)
                    .image(null)
                    .unitPrice(unitPrice.longValue())
                    .quantity(it.getQuantity())
                    .totalPrice(total)
                    .build();
        }).collect(Collectors.toList());

        long subtotal = itemDtos.stream().mapToLong(CartItemDto::getTotalPrice).sum();
        long shipping = 0L;
        long total = subtotal + shipping;

        return CartDto.builder()
                .id(cart.getId())
                .sessionId(cart.getSessionId())
                .items(itemDtos)
                .subtotal(subtotal)
                .shipping(shipping)
                .total(total)
                .build();
    }

    public CartDto addItem(String userId, AddCartItemRequest req) {
        // find or create cart
        Cart cart = null;
        if (userId != null) {
            cart = cartRepository.findByCustomerId(userId).orElse(null);
        }
        if (cart == null && req.getSessionId() != null) {
            cart = cartRepository.findBySessionId(req.getSessionId()).orElse(null);
        }
        if (cart == null) {
            // create new cart; if no sessionId provided, generate one so client can persist it
            String session = req.getSessionId();
            if (session == null || session.isBlank()) {
                session = java.util.UUID.randomUUID().toString();
            }
            cart = Cart.builder().customerId(userId).sessionId(session).build();
            cart = cartRepository.save(cart);
        }

        Long variantId = req.getVariantId();
        Integer qty = req.getQuantity() != null ? req.getQuantity() : 1;

        // Resolve variant: prefer explicit variantId; if not found, try productId -> default variant
        // Log inputs for debugging
        try {
            System.out.println("CartService.addItem request: variantId=" + req.getVariantId() + ", productId=" + req.getProductId() + ", sessionId=" + req.getSessionId());
        } catch (Exception e) {}
        ProductVariant variant = null;
        if (variantId != null) {
            variant = productVariantRepository.findById(variantId).orElse(null);
        }
        if (variant == null && req.getProductId() != null) {
            variant = productVariantRepository.findFirstByProductIdOrderByIdAsc(req.getProductId()).orElse(null);
        }
        // It's possible frontend passed productId in the variantId slot (older behavior). Try that too.
        if (variant == null && variantId != null) {
            variant = productVariantRepository.findFirstByProductIdOrderByIdAsc(variantId).orElse(null);
        }

        if (variant == null) {
            throw new IllegalArgumentException("Không tìm thấy variant cho sản phẩm");
        }

        Long resolvedVariantId = variant.getId();

        try {
            System.out.println("CartService.addItem resolved variantId=" + resolvedVariantId + ", productId=" + variant.getProductId() + ", qty=" + qty + ", cartSession=" + cart.getSessionId());
        } catch (Exception e) {}

        var existing = cartItemRepository.findByCartIdAndVariantId(cart.getId(), resolvedVariantId);
        CartItem item;
        if (existing.isPresent()) {
            item = existing.get();
            item.setQuantity(item.getQuantity() + qty);
        } else {
            item = CartItem.builder().cartId(cart.getId()).variantId(resolvedVariantId).quantity(qty).build();
        }
    cartItemRepository.save(item);

        // Log cart items count for debugging
        try {
            var c = getCart(null, cart.getSessionId());
            System.out.println("CartService.addItem returning cart with session=" + cart.getSessionId() + " itemsCount=" + (c.getItems() != null ? c.getItems().size() : 0));
        } catch (Exception e) {}

        // Return the full cart DTO so client can persist sessionId and show updated cart
        return getCart(userId, cart.getSessionId());
    }

    public CartItemDto updateItemQuantity(Long itemId, UpdateCartItemRequest req) {
        CartItem it = cartItemRepository.findById(itemId).orElseThrow();
        it.setQuantity(req.getQuantity());
        CartItem saved = cartItemRepository.save(it);

        ProductVariant variant = productVariantRepository.findById(saved.getVariantId()).orElse(null);
        long unit = variant != null && variant.getPrice() != null ? variant.getPrice().longValue() : 0L;

        return CartItemDto.builder()
                .itemId(saved.getId())
                .variantId(saved.getVariantId())
                .quantity(saved.getQuantity())
                .unitPrice(unit)
                .totalPrice(unit * saved.getQuantity())
                .build();
    }

    public void removeItem(Long itemId) {
        cartItemRepository.deleteById(itemId);
    }

    public void clearCartForUser(String userId) {
        var cartOpt = cartRepository.findByCustomerId(userId);
        cartOpt.ifPresent(cart -> {
            var items = cartItemRepository.findByCartId(cart.getId());
            cartItemRepository.deleteAll(items);
        });
    }
}
