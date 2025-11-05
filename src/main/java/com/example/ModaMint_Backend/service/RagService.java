package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.entity.Product;
import com.example.ModaMint_Backend.entity.ProductVariant;
import com.example.ModaMint_Backend.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RagService {

    ProductRepository productRepository;


    public String retrieveContext() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            return "No products found in the store.";
        }

        return products.stream()
                .map(p -> {
                    // Lấy danh sách biến thể chi tiết
                    String variantsInfo = p.getProductVariants().stream()
                            .map(v -> String.format(
                                    """
                                    • Biến thể #%d:
                                        - Màu sắc: %s
                                        - Kích thước: %s
                                        - Giá gốc: %,.0f VNĐ
                                        - Giảm giá: %s
                                        - Giá phụ thêm: %,.0f VNĐ
                                        - Số lượng tồn kho: %d
                                    """,
                                    v.getId(),
                                    v.getColor() != null ? v.getColor() : "Không xác định",
                                    v.getSize() != null ? v.getSize() : "Không xác định",
                                    v.getPrice() != null ? v.getPrice().doubleValue() : 0,
                                    v.getDiscount() != null ? v.getDiscount() + " VNĐ" : "Không có",
                                    v.getAdditionalPrice() != null ? v.getAdditionalPrice().doubleValue() : 0,
                                    v.getQuantity() != null ? v.getQuantity() : 0
                            ))
                            .collect(Collectors.joining("\n"));

                    // Tạo chuỗi mô tả chi tiết sản phẩm
                    return String.format(
                            """
                            🧾 Mã sản phẩm: %d
                            🔹 Tên: %s
                            🔹 Mô tả: %s
                            🔹 Danh mục ID: %d
                            🔹 Thương hiệu ID: %d
                            🔹 Trạng thái: %s
                            🔹 Số biến thể: %d
                            %s
                            """,
                            p.getId(),
                            p.getName(),
                            p.getDescription(),
                            p.getCategoryId(),
                            p.getBrandId(),
                            p.getActive() ? "Đang bán" : "Ngừng bán",
                            p.getProductVariants().size(),
                            variantsInfo.isEmpty() ? "   (Chưa có biến thể)" : variantsInfo
                    );
                })
                .collect(Collectors.joining("\n------------------------------------------------\n"));
    }
}
