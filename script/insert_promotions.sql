-- ============================================
-- INSERT DATA FOR PROMOTIONS
-- ============================================

-- Insert vào bảng promotions (parent table)
-- Percent Promotions
INSERT INTO promotions (name, code, min_order_value, effective, expiration, quantity, is_active, create_at) VALUES
('Giảm 10% cho đơn từ 200k', 'GIAM10', 200000, '2024-11-01 00:00:00', '2025-12-31 23:59:59', 100, true, NOW()),
('Giảm 15% cho đơn từ 500k', 'GIAM15', 500000, '2024-11-01 00:00:00', '2025-12-31 23:59:59', 80, true, NOW()),
('Giảm 20% cho đơn từ 1 triệu', 'GIAM20', 1000000, '2024-11-01 00:00:00', '2025-12-31 23:59:59', 50, true, NOW()),
('Giảm 25% Black Friday', 'BLACKFRIDAY25', 300000, '2024-11-24 00:00:00', '2024-11-30 23:59:59', 200, true, NOW()),
('Giảm 30% Tết 2025', 'TET2025', 500000, '2025-01-20 00:00:00', '2025-02-05 23:59:59', 150, true, NOW()),
('Giảm 5% cho khách hàng mới', 'NEWCUSTOMER5', 100000, '2024-11-01 00:00:00', '2025-12-31 23:59:59', 500, true, NOW()),
('Flash Sale 50%', 'FLASH50', 800000, '2024-12-12 10:00:00', '2024-12-12 23:59:59', 30, true, NOW()),
('Sinh nhật shop giảm 35%', 'BIRTHDAY35', 1500000, '2024-12-15 00:00:00', '2024-12-20 23:59:59', 100, true, NOW());

-- Amount Promotions
INSERT INTO promotions (name, code, min_order_value, effective, expiration, quantity, is_active, create_at) VALUES
('Giảm 50k cho đơn từ 300k', 'GIAM50K', 300000, '2024-11-01 00:00:00', '2025-12-31 23:59:59', 100, true, NOW()),
('Giảm 100k cho đơn từ 600k', 'GIAM100K', 600000, '2024-11-01 00:00:00', '2025-12-31 23:59:59', 80, true, NOW()),
('Giảm 200k cho đơn từ 1.2 triệu', 'GIAM200K', 1200000, '2024-11-01 00:00:00', '2025-12-31 23:59:59', 60, true, NOW()),
('Freeship 30k', 'FREESHIP30K', 150000, '2024-11-01 00:00:00', '2025-12-31 23:59:59', 300, true, NOW()),
('Giảm 500k đơn VIP', 'VIP500K', 5000000, '2024-11-01 00:00:00', '2025-12-31 23:59:59', 20, true, NOW()),
('Voucher 150k Noel', 'NOEL150K', 1000000, '2024-12-20 00:00:00', '2024-12-26 23:59:59', 100, true, NOW());

-- Insert vào bảng percent_promotion
-- Lấy promotion_id tương ứng và insert vào bảng con
INSERT INTO percent_promotion (promotion_id, percent, max_discount) VALUES
(1, 10.0, 100000),    -- GIAM10: 10%, tối đa giảm 100k
(2, 15.0, 200000),    -- GIAM15: 15%, tối đa giảm 200k
(3, 20.0, 500000),    -- GIAM20: 20%, tối đa giảm 500k
(4, 25.0, 300000),    -- BLACKFRIDAY25: 25%, tối đa giảm 300k
(5, 30.0, 800000),    -- TET2025: 30%, tối đa giảm 800k
(6, 5.0, 50000),      -- NEWCUSTOMER5: 5%, tối đa giảm 50k
(7, 50.0, 1000000),   -- FLASH50: 50%, tối đa giảm 1 triệu
(8, 35.0, 1500000);   -- BIRTHDAY35: 35%, tối đa giảm 1.5 triệu

-- Insert vào bảng amount_promotion
INSERT INTO amount_promotion (promotion_id, discount) VALUES
(9, 50000),     -- GIAM50K: giảm 50k
(10, 100000),   -- GIAM100K: giảm 100k
(11, 200000),   -- GIAM200K: giảm 200k
(12, 30000),    -- FREESHIP30K: giảm 30k
(13, 500000),   -- VIP500K: giảm 500k
(14, 150000);   -- NOEL150K: giảm 150k

-- ============================================
-- VERIFY DATA
-- ============================================

-- Kiểm tra dữ liệu đã insert
SELECT 
    p.promotion_id,
    p.name,
    p.code,
    p.min_order_value,
    p.effective,
    p.expiration,
    p.quantity,
    p.is_active,
    pp.percent,
    pp.max_discount,
    'PERCENT' as promotion_type
FROM promotions p
JOIN percent_promotion pp ON p.promotion_id = pp.promotion_id

UNION ALL

SELECT 
    p.promotion_id,
    p.name,
    p.code,
    p.min_order_value,
    p.effective,
    p.expiration,
    p.quantity,
    p.is_active,
    NULL as percent,
    ap.discount as max_discount,
    'AMOUNT' as promotion_type
FROM promotions p
JOIN amount_promotion ap ON p.promotion_id = ap.promotion_id
ORDER BY promotion_id;
