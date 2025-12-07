package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.entity.Product;
import com.example.ModaMint_Backend.entity.ProductVariant;
import com.example.ModaMint_Backend.repository.ProductRepository;
import com.example.ModaMint_Backend.repository.ProductVariantRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductVectorLoader {

    ProductRepository productRepository;
    VectorStore vectorStore;
    ProductVariantRepository productVariantRepository;

    public List<Document> loadProductsToVectorDB() {
        List<Product> products = productRepository.findAll();
        List<Document> documents = products.stream()
                .map(p -> {
                    List<ProductVariant> productVariants = productVariantRepository.findByProductId(p.getId());
                    String variantsText = productVariants.stream()
                            .map(v -> "Màu: %s, Size: %s, Giá: %.0f₫, discount: %s"
                                    .formatted(v.getColor(), v.getSize(), v.getPrice(), v.getDiscount()))
                            .reduce((a, b) -> a + "; " + b)
                            .orElse("Không có biến thể");

                    return new Document("""
                            Tên sản phẩm: %s
                            Mô tả: %s
                            Thương hiệu: %s
                            Các biến thể: %s
                            """.formatted(
                            p.getName(),
                            p.getDescription(),
                            p.getBrand().getName(),
                            variantsText
                    ));
                })
                .toList();

        vectorStore.add(documents);

        System.out.println("Loaded " + documents.size() + " products into VectorDB.");
        return documents;
    }
}
