-- Migration: Chuyển sang SINGLE_TABLE strategy
-- Tất cả promotion data sẽ được gộp vào 1 bảng promotions

-- 1. Tạo bảng promotions mới (nếu chưa có)
CREATE TABLE IF NOT EXISTS promotions (
    promotion_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    min_order_value DECIMAL(15, 2),
    effective DATETIME,
    expiration DATETIME,
    quantity INT,
    is_active BOOLEAN DEFAULT TRUE,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    promotion_type VARCHAR(50) NOT NULL,
    -- Fields cho PERCENTAGE
    percent DECIMAL(5, 2),
    max_discount DECIMAL(15, 2),
    -- Field cho AMOUNT
    discount DECIMAL(15, 2),
    INDEX idx_code (code),
    INDEX idx_active (is_active),
    INDEX idx_type (promotion_type)
);

-- 2. Migrate dữ liệu từ percentage_promotions (nếu có)
INSERT INTO promotions (name, code, min_order_value, effective, expiration, quantity, is_active, create_at, promotion_type, percent, max_discount)
SELECT name, code, min_order_value, start_at, end_at, quantity, is_active, create_at, 'PERCENTAGE', discount_percent, NULL
FROM percentage_promotions
WHERE NOT EXISTS (SELECT 1 FROM promotions WHERE promotions.code = percentage_promotions.code);

-- 3. Migrate dữ liệu từ amount_promotions (nếu có)
INSERT INTO promotions (name, code, min_order_value, effective, expiration, quantity, is_active, create_at, promotion_type, discount)
SELECT name, code, min_order_value, start_at, end_at, quantity, is_active, create_at, 'AMOUNT', discount_amount
FROM amount_promotions
WHERE NOT EXISTS (SELECT 1 FROM promotions WHERE promotions.code = amount_promotions.code);

-- 4. Drop các bảng cũ (chỉ chạy sau khi verify data)
-- DROP TABLE IF EXISTS percentage_promotions;
-- DROP TABLE IF EXISTS amount_promotions;
