package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.cart.AddCartItemRequest;
import com.example.ModaMint_Backend.dto.request.cart.UpdateCartItemRequest;
import com.example.ModaMint_Backend.dto.response.cart.CartDto;
import com.example.ModaMint_Backend.dto.response.cart.CartItemDto;
import com.example.ModaMint_Backend.entity.Cart;
import com.example.ModaMint_Backend.entity.CartItem;
import com.example.ModaMint_Backend.entity.Customer;
import com.example.ModaMint_Backend.entity.Product;
import com.example.ModaMint_Backend.entity.ProductVariant;
import com.example.ModaMint_Backend.entity.User;
import com.example.ModaMint_Backend.repository.CartItemRepository;
import com.example.ModaMint_Backend.repository.CartRepository;
import com.example.ModaMint_Backend.repository.CustomerRepository;
import com.example.ModaMint_Backend.repository.ProductRepository;
import com.example.ModaMint_Backend.repository.ProductVariantRepository;
import com.example.ModaMint_Backend.repository.UserRepository;
import com.example.ModaMint_Backend.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    ProductRepository productRepository;
    ProductService productService;
    CustomerRepository customerRepository;
    UserRepository userRepository;

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
            String imageUrl = null;
            BigDecimal unitPrice = BigDecimal.ZERO;
            if (productId != null) {
                // Get Product entity directly from repository
                Product product = productRepository.findById(productId).orElse(null);
                if (product != null) {
                    productName = product.getName();
                    // Get first image from product
                    if (product.getProductImages() != null && !product.getProductImages().isEmpty()) {
                        imageUrl = product.getProductImages().iterator().next().getUrl();
                    }
                }
            }
            if (variant != null && variant.getPrice() != null) unitPrice = variant.getPrice();

            long qty = it.getQuantity() != null ? it.getQuantity() : 0;
            long total = unitPrice.multiply(BigDecimal.valueOf(qty)).longValue();

            return CartItemDto.builder()
                    .itemId(it.getId())
                    .variantId(it.getVariantId())
                    .productId(productId)
                    .productName(productName)
                    .image(imageUrl)
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

    @Transactional
    public CartDto addItem(String userId, AddCartItemRequest req) {
        System.out.println("DEBUG addItem: userId=" + userId + ", productId=" + req.getProductId() + ", variantId=" + req.getVariantId());
        
        if (userId != null) {
            ensureCustomerExists(userId);
        }

        Cart cart = null;
        if (userId != null) {
            cart = cartRepository.findByCustomerId(userId).orElse(null);
            System.out.println("DEBUG: Cart found by customerId: " + (cart != null ? cart.getId() : "null"));
        }
        if (cart == null && req.getSessionId() != null) {
            cart = cartRepository.findBySessionId(req.getSessionId()).orElse(null);
            System.out.println("DEBUG: Cart found by sessionId: " + (cart != null ? cart.getId() : "null"));
        }
        if (cart == null) {
            String session = req.getSessionId();
            if (session == null || session.isBlank()) {
                session = java.util.UUID.randomUUID().toString();
            }
            cart = Cart.builder().customerId(userId).sessionId(session).build();
            cart = cartRepository.save(cart);
            System.out.println("DEBUG: New cart created with id: " + cart.getId() + ", customerId: " + cart.getCustomerId());
        }

        Long variantId = req.getVariantId();
        Integer qty = req.getQuantity() != null ? req.getQuantity() : 1;

        ProductVariant variant = null;
        if (variantId != null) {
            variant = productVariantRepository.findById(variantId).orElse(null);
        }
        if (variant == null && req.getProductId() != null) {
            variant = productVariantRepository.findFirstByProductIdOrderByIdAsc(req.getProductId()).orElse(null);
        }
        if (variant == null && variantId != null) {
            variant = productVariantRepository.findFirstByProductIdOrderByIdAsc(variantId).orElse(null);
        }

        if (variant == null) {
            throw new IllegalArgumentException("Không tìm thấy variant cho sản phẩm");
        }

        Long resolvedVariantId = variant.getId();

        var existing = cartItemRepository.findByCartIdAndVariantId(cart.getId(), resolvedVariantId);
        CartItem item;
        if (existing.isPresent()) {
            item = existing.get();
            item.setQuantity(item.getQuantity() + qty);
        } else {
            item = CartItem.builder().cartId(cart.getId()).variantId(resolvedVariantId).quantity(qty).build();
        }
        cartItemRepository.save(item);

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

    private void ensureCustomerExists(String userId) {
        System.out.println("DEBUG: Checking customer for userId: " + userId);
        
        if (customerRepository.existsById(userId)) {
            System.out.println("DEBUG: Customer exists for userId: " + userId);
            return;
        }

        System.out.println("DEBUG: Customer NOT exists, creating new customer for userId: " + userId);
        
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            System.out.println("DEBUG: User found: " + user.getUsername() + ", creating Customer...");
            Customer customer = Customer.builder()
                    .userId(userId)
                    .user(user)
                    .build();
            customerRepository.save(customer);
            customerRepository.flush();
            System.out.println("DEBUG: Customer created and flushed successfully for userId: " + userId);
        } else {
            System.out.println("ERROR: User not found for userId: " + userId);
            throw new IllegalStateException("User not found for userId: " + userId);
        }
    }
}
