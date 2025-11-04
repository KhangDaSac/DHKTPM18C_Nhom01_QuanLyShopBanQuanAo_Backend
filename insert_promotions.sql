-- ===================================================
-- SCRIPT THÊM KHUYẾN MÃI CHO MODAMINT
-- ===================================================

-- 1. KHUYẾN MÃI GIẢM PHẦN TRĂM (PERCENT)
-- ===================================================

-- Khuyến mãi chào mừng khách hàng mới - Giảm 10%
INSERT INTO promotions (code, value, min_order_value, start_at, end_at, quantity, is_active, create_at)
VALUES ('WELCOME10', '10', 100000, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY), 100, TRUE, NOW());

-- Lấy ID của promotion vừa insert để thêm vào type_promotions
SET @promotion_id = LAST_INSERT_ID();

INSERT INTO type_promotions (promotion_id, type)
VALUES (@promotion_id, 'PERCENT');


-- Khuyến mãi mùa hè - Giảm 15%
INSERT INTO promotions (code, value, min_order_value, start_at, end_at, quantity, is_active, create_at)
VALUES ('SUMMER15', '15', 200000, NOW(), DATE_ADD(NOW(), INTERVAL 60 DAY), 200, TRUE, NOW());

SET @promotion_id = LAST_INSERT_ID();

INSERT INTO type_promotions (promotion_id, type)
VALUES (@promotion_id, 'PERCENT');


-- Khuyến mãi sinh nhật - Giảm 20%
INSERT INTO promotions (code, value, min_order_value, start_at, end_at, quantity, is_active, create_at)
VALUES ('BIRTHDAY20', '20', 500000, NOW(), DATE_ADD(NOW(), INTERVAL 90 DAY), 50, TRUE, NOW());

SET @promotion_id = LAST_INSERT_ID();

INSERT INTO type_promotions (promotion_id, type)
VALUES (@promotion_id, 'PERCENT');


-- Flash Sale - Giảm 25%
INSERT INTO promotions (code, value, min_order_value, start_at, end_at, quantity, is_active, create_at)
VALUES ('FLASH25', '25', 300000, NOW(), DATE_ADD(NOW(), INTERVAL 7 DAY), 30, TRUE, NOW());

SET @promotion_id = LAST_INSERT_ID();

INSERT INTO type_promotions (promotion_id, type)
VALUES (@promotion_id, 'PERCENT');


-- 2. KHUYẾN MÃI GIẢM TIỀN CỐ ĐỊNH (AMOUNT)
-- ===================================================

-- Giảm 50,000 VNĐ cho đơn hàng từ 300,000 VNĐ
INSERT INTO promotions (code, value, min_order_value, start_at, end_at, quantity, is_active, create_at)
VALUES ('SAVE50K', '50000', 300000, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY), 150, TRUE, NOW());

SET @promotion_id = LAST_INSERT_ID();

INSERT INTO type_promotions (promotion_id, type)
VALUES (@promotion_id, 'AMOUNT');


-- Giảm 100,000 VNĐ cho đơn hàng từ 500,000 VNĐ
INSERT INTO promotions (code, value, min_order_value, start_at, end_at, quantity, is_active, create_at)
VALUES ('SAVE100K', '100000', 500000, NOW(), DATE_ADD(NOW(), INTERVAL 45 DAY), 100, TRUE, NOW());

SET @promotion_id = LAST_INSERT_ID();

INSERT INTO type_promotions (promotion_id, type)
VALUES (@promotion_id, 'AMOUNT');


-- Giảm 200,000 VNĐ cho đơn hàng từ 1,000,000 VNĐ
INSERT INTO promotions (code, value, min_order_value, start_at, end_at, quantity, is_active, create_at)
VALUES ('SAVE200K', '200000', 1000000, NOW(), DATE_ADD(NOW(), INTERVAL 60 DAY), 50, TRUE, NOW());

SET @promotion_id = LAST_INSERT_ID();

INSERT INTO type_promotions (promotion_id, type)
VALUES (@promotion_id, 'AMOUNT');


-- Khuyến mãi đặc biệt - Giảm 500,000 VNĐ cho đơn hàng từ 2,000,000 VNĐ
INSERT INTO promotions (code, value, min_order_value, start_at, end_at, quantity, is_active, create_at)
VALUES ('VIP500K', '500000', 2000000, NOW(), DATE_ADD(NOW(), INTERVAL 90 DAY), 20, TRUE, NOW());

SET @promotion_id = LAST_INSERT_ID();

INSERT INTO type_promotions (promotion_id, type)
VALUES (@promotion_id, 'AMOUNT');


-- 3. KHUYẾN MÃI MỘT SỐ SỰ KIỆN ĐẶC BIỆT
-- ===================================================

-- Black Friday - Giảm 30%
INSERT INTO promotions (code, value, min_order_value, start_at, end_at, quantity, is_active, create_at)
VALUES ('BLACKFRIDAY30', '30', 400000, '2025-11-29 00:00:00', '2025-11-30 23:59:59', 500, TRUE, NOW());

SET @promotion_id = LAST_INSERT_ID();

INSERT INTO type_promotions (promotion_id, type)
VALUES (@promotion_id, 'PERCENT');


-- Tết Nguyên Đán - Giảm 150,000 VNĐ
INSERT INTO promotions (code, value, min_order_value, start_at, end_at, quantity, is_active, create_at)
VALUES ('TET2026', '150000', 600000, '2026-01-28 00:00:00', '2026-02-05 23:59:59', 300, TRUE, NOW());

SET @promotion_id = LAST_INSERT_ID();

INSERT INTO type_promotions (promotion_id, type)
VALUES (@promotion_id, 'AMOUNT');


-- Ngày Phụ Nữ Việt Nam 20/10 - Giảm 20%
INSERT INTO promotions (code, value, min_order_value, start_at, end_at, quantity, is_active, create_at)
VALUES ('WOMEN20OCT', '20', 250000, '2025-10-18 00:00:00', '2025-10-21 23:59:59', 200, TRUE, NOW());

SET @promotion_id = LAST_INSERT_ID();

INSERT INTO type_promotions (promotion_id, type)
VALUES (@promotion_id, 'PERCENT');


-- 4. KHUYẾN MÃI KHÁCH HÀNG THÂN THIẾT
-- ===================================================

-- Member Gold - Giảm 12%
INSERT INTO promotions (code, value, min_order_value, start_at, end_at, quantity, is_active, create_at)
VALUES ('GOLD12', '12', 0, NOW(), DATE_ADD(NOW(), INTERVAL 365 DAY), 1000, TRUE, NOW());

SET @promotion_id = LAST_INSERT_ID();

INSERT INTO type_promotions (promotion_id, type)
VALUES (@promotion_id, 'PERCENT');


-- Member Platinum - Giảm 18%
INSERT INTO promotions (code, value, min_order_value, start_at, end_at, quantity, is_active, create_at)
VALUES ('PLATINUM18', '18', 0, NOW(), DATE_ADD(NOW(), INTERVAL 365 DAY), 500, TRUE, NOW());

SET @promotion_id = LAST_INSERT_ID();

INSERT INTO type_promotions (promotion_id, type)
VALUES (@promotion_id, 'PERCENT');


-- ===================================================
-- KIỂM TRA DỮ LIỆU VỪA INSERT
-- ===================================================

-- Xem tất cả khuyến mãi
SELECT 
    p.id,
    p.code,
    p.value,
    tp.type,
    p.min_order_value,
    p.start_at,
    p.end_at,
    p.quantity,
    p.is_active,
    p.create_at
FROM promotions p
LEFT JOIN type_promotions tp ON p.id = tp.promotion_id
ORDER BY p.id DESC;

-- Xem khuyến mãi đang active
SELECT 
    p.id,
    p.code,
    CONCAT(p.value, ' ', IF(tp.type = 'PERCENT', '%', 'VNĐ')) as discount,
    p.min_order_value,
    p.quantity as remaining
FROM promotions p
LEFT JOIN type_promotions tp ON p.id = tp.promotion_id
WHERE p.is_active = TRUE
AND NOW() BETWEEN p.start_at AND p.end_at
ORDER BY p.code;
