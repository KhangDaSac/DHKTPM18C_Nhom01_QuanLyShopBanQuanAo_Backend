-- ============================================
-- INSERT DATA FOR PROMOTIONS (TABLE_PER_CLASS)
-- ============================================

-- Insert trực tiếp vào bảng percent_promotion (bao gồm tất cả các trường)
-- Không cần chỉ định promotion_id, để database tự động generate
INSERT INTO percent_promotion (name, code, min_order_value, effective, expiration, quantity, is_active, create_at, percent, max_discount) VALUES
('Giảm 10% cho đơn từ 200k', 'GIAM10', 200000, '2024-11-01 00:00:00', '2025-12-31 23:59:59', 100, true, NOW(), 10.0, 100000),
('Giảm 15% cho đơn từ 500k', 'GIAM15', 500000, '2024-11-01 00:00:00', '2025-12-31 23:59:59', 80, true, NOW(), 15.0, 200000),
('Giảm 20% cho đơn từ 1 triệu', 'GIAM20', 1000000, '2024-11-01 00:00:00', '2025-12-31 23:59:59', 50, true, NOW(), 20.0, 500000),
('Giảm 25% Black Friday', 'BLACKFRIDAY25', 300000, '2024-11-24 00:00:00', '2024-11-30 23:59:59', 200, true, NOW(), 25.0, 300000),
('Giảm 30% Tết 2025', 'TET2025', 500000, '2025-01-20 00:00:00', '2025-02-05 23:59:59', 150, true, NOW(), 30.0, 800000),
('Giảm 5% cho khách hàng mới', 'NEWCUSTOMER5', 100000, '2024-11-01 00:00:00', '2025-12-31 23:59:59', 500, true, NOW(), 5.0, 50000),
('Flash Sale 50%', 'FLASH50', 800000, '2024-12-12 10:00:00', '2024-12-12 23:59:59', 30, true, NOW(), 50.0, 1000000),
('Sinh nhật shop giảm 35%', 'BIRTHDAY35', 1500000, '2024-12-15 00:00:00', '2024-12-20 23:59:59', 100, true, NOW(), 35.0, 1500000);

-- Insert trực tiếp vào bảng amount_promotion (bao gồm tất cả các trường)
-- Không cần chỉ định promotion_id, để database tự động generate
INSERT INTO amount_promotion (name, code, min_order_value, effective, expiration, quantity, is_active, create_at, discount) VALUES
('Giảm 50k cho đơn từ 300k', 'GIAM50K', 300000, '2024-11-01 00:00:00', '2025-12-31 23:59:59', 100, true, NOW(), 50000),
('Giảm 100k cho đơn từ 600k', 'GIAM100K', 600000, '2024-11-01 00:00:00', '2025-12-31 23:59:59', 80, true, NOW(), 100000),
('Giảm 200k cho đơn từ 1.2 triệu', 'GIAM200K', 1200000, '2024-11-01 00:00:00', '2025-12-31 23:59:59', 60, true, NOW(), 200000),
('Freeship 30k', 'FREESHIP30K', 150000, '2024-11-01 00:00:00', '2025-12-31 23:59:59', 300, true, NOW(), 30000),
('Giảm 500k đơn VIP', 'VIP500K', 5000000, '2024-11-01 00:00:00', '2025-12-31 23:59:59', 20, true, NOW(), 500000),
('Voucher 150k Noel', 'NOEL150K', 1000000, '2024-12-20 00:00:00', '2024-12-26 23:59:59', 100, true, NOW(), 150000);

-- ============================================
-- VERIFY DATA
-- ============================================

-- Kiểm tra dữ liệu percent_promotion
SELECT 
    promotion_id,
    name,
    code,
    min_order_value,
    effective,
    expiration,
    quantity,
    is_active,
    percent,
    max_discount,
    'PERCENT' as promotion_type
FROM percent_promotion

UNION ALL

-- Kiểm tra dữ liệu amount_promotion
SELECT 
    promotion_id,
    name,
    code,
    min_order_value,
    effective,
    expiration,
    quantity,
    is_active,
    NULL as percent,
    discount as max_discount,
    'AMOUNT' as promotion_type
FROM amount_promotion
ORDER BY promotion_id;
