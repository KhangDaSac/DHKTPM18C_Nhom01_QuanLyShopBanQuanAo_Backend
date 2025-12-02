package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.entity.Product;
import com.example.ModaMint_Backend.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductVectorLoader {

    ProductRepository productRepository;

    public ProductVectorLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Dummy loader – giữ structure nhưng không dùng VectorStore nữa.
     * Tránh lỗi khi chạy app nhưng vẫn sẵn sàng nếu sau này bạn bật lại RAG.
     */
    public void loadProductsToVectorDB() {
        List<Product> products = productRepository.findAll();
        System.out.println("ProductVectorLoader: Loaded " + products.size() + " products (no vector store).");
    }
}
