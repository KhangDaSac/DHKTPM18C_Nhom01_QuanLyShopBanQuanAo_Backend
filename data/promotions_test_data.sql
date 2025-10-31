-- =====================================================
-- TEST DATA FOR PROMOTIONS
-- Tạo dữ liệu test cho khuyến mãi
-- =====================================================

-- Xóa dữ liệu cũ (nếu có)
DELETE FROM percentage_promotions;
DELETE FROM amount_promotions;

-- Reset AUTO_INCREMENT
ALTER TABLE percentage_promotions AUTO_INCREMENT = 1;
ALTER TABLE amount_promotions AUTO_INCREMENT = 1;

-- =====================================================
-- PERCENTAGE PROMOTIONS (Khuyến mãi theo phần trăm)
-- =====================================================

INSERT INTO percentage_promotions (name, code, discount_percent, min_order_value, start_at, end_at, quantity, is_active, create_at) VALUES
-- 1. Khuyến mãi chào mừng (Đang hoạt động)
('Chào mừng thành viên mới', 'WELCOME10', 10.00, 0.00, '2025-01-01 00:00:00', '2025-12-31 23:59:59', 1000, TRUE, NOW()),

-- 2. Khuyến mãi Tết (Đang hoạt động)
('Khuyến mãi Tết Nguyên Đán', 'TET2025', 20.00, 500000.00, '2025-01-15 00:00:00', '2025-02-15 23:59:59', 500, TRUE, NOW()),

-- 3. Khuyến mãi sinh nhật (Đang hoạt động)
('Sale sinh nhật thương hiệu', 'BIRTHDAY15', 15.00, 300000.00, '2025-01-01 00:00:00', '2025-03-31 23:59:59', 300, TRUE, NOW()),

-- 4. Flash sale cuối tuần (Đang hoạt động)
('Flash Sale Thứ 7 Chủ Nhật', 'WEEKEND25', 25.00, 800000.00, '2025-10-25 00:00:00', '2025-11-30 23:59:59', 200, TRUE, NOW()),

-- 5. Khuyến mãi Black Friday (Chưa bắt đầu)
('Black Friday Sale', 'BLACKFRIDAY30', 30.00, 1000000.00, '2025-11-25 00:00:00', '2025-11-30 23:59:59', 100, TRUE, NOW()),

-- 6. Giảm giá học sinh sinh viên (Đang hoạt động)
('Ưu đãi học sinh sinh viên', 'STUDENT12', 12.00, 200000.00, '2025-09-01 00:00:00', '2025-12-31 23:59:59', 500, TRUE, NOW()),

-- 7. Sale mùa hè (Đã hết hạn)
('Khuyến mãi mùa hè', 'SUMMER20', 20.00, 400000.00, '2024-06-01 00:00:00', '2024-08-31 23:59:59', 300, FALSE, '2024-06-01 00:00:00'),

-- 8. VIP member discount (Đang hoạt động)
('Ưu đãi thành viên VIP', 'VIP18', 18.00, 1500000.00, '2025-01-01 00:00:00', '2025-12-31 23:59:59', 50, TRUE, NOW()),

-- 9. Khuyến mãi giữa năm (Đã hết hạn)
('Sale giữa năm', 'MIDYEAR15', 15.00, 350000.00, '2024-06-01 00:00:00', '2024-07-31 23:59:59', 250, FALSE, '2024-06-01 00:00:00'),

-- 10. Khuyến mãi đầu mùa (Đang hoạt động)
('Đón đầu mùa thời trang mới', 'NEWSEASON22', 22.00, 600000.00, '2025-10-01 00:00:00', '2025-11-30 23:59:59', 400, TRUE, NOW());

-- =====================================================
-- AMOUNT PROMOTIONS (Khuyến mãi giá cố định)
-- =====================================================

INSERT INTO amount_promotions (name, code, discount_amount, min_order_value, start_at, end_at, quantity, is_active, create_at) VALUES
-- 1. Giảm giá cho đơn hàng đầu tiên
('Giảm giá đơn hàng đầu tiên', 'FIRST50K', 50000.00, 200000.00, '2025-01-01 00:00:00', '2025-12-31 23:59:59', 1000, TRUE, NOW()),

-- 2. Voucher Tết
('Voucher Tết may mắn', 'LUCKYTET100K', 100000.00, 500000.00, '2025-01-15 00:00:00', '2025-02-15 23:59:59', 500, TRUE, NOW()),

-- 3. Giảm giá sinh nhật
('Mừng sinh nhật khách hàng', 'BDAY80K', 80000.00, 400000.00, '2025-01-01 00:00:00', '2025-03-31 23:59:59', 300, TRUE, NOW()),

-- 4. Flash sale giờ vàng
('Flash Sale Giờ Vàng', 'GOLDHOUR150K', 150000.00, 1000000.00, '2025-10-25 00:00:00', '2025-11-30 23:59:59', 100, TRUE, NOW()),

-- 5. Siêu sale cuối năm (Chưa bắt đầu)
('Siêu Sale Cuối Năm', 'YEAREND200K', 200000.00, 1500000.00, '2025-12-15 00:00:00', '2025-12-31 23:59:59', 200, TRUE, NOW()),

-- 6. Ưu đãi thành viên mới
('Chào mừng thành viên mới', 'NEWMEM30K', 30000.00, 150000.00, '2025-09-01 00:00:00', '2025-12-31 23:59:59', 800, TRUE, NOW()),

-- 7. Giảm giá mùa đông (Đã hết hạn)
('Ấm áp mùa đông', 'WINTER70K', 70000.00, 350000.00, '2024-11-01 00:00:00', '2024-12-31 23:59:59', 400, FALSE, '2024-11-01 00:00:00'),

-- 8. Mega sale
('Mega Sale Đặc Biệt', 'MEGA250K', 250000.00, 2000000.00, '2025-01-01 00:00:00', '2025-12-31 23:59:59', 50, TRUE, NOW()),

-- 9. Sale hè (Đã hết hạn)
('Hè sôi động', 'SUMMER60K', 60000.00, 300000.00, '2024-06-01 00:00:00', '2024-08-31 23:59:59', 350, FALSE, '2024-06-01 00:00:00'),

-- 10. Ưu đãi mua sắm trực tuyến
('Mua Online Giảm Ngay', 'ONLINE120K', 120000.00, 700000.00, '2025-10-01 00:00:00', '2025-11-30 23:59:59', 600, TRUE, NOW());

-- =====================================================
-- VERIFY DATA
-- =====================================================

-- Kiểm tra dữ liệu đã insert
SELECT COUNT(*) as 'Tổng Percentage Promotions' FROM percentage_promotions;
SELECT COUNT(*) as 'Tổng Amount Promotions' FROM amount_promotions;

-- Xem chi tiết
SELECT id, name, code, discount_percent, is_active, start_at, end_at FROM percentage_promotions ORDER BY id;
SELECT id, name, code, discount_amount, is_active, start_at, end_at FROM amount_promotions ORDER BY id;

-- =====================================================
-- NOTES
-- =====================================================
-- Tổng cộng: 10 khuyến mãi phần trăm + 10 khuyến mãi giá cố định = 20 khuyến mãi
-- 
-- Phân loại trạng thái:
-- - Đang hoạt động: 7 percentage + 7 amount = 14 khuyến mãi
-- - Đã hết hạn: 2 percentage + 2 amount = 4 khuyến mãi  
-- - Chưa bắt đầu: 1 percentage + 1 amount = 2 khuyến mãi
--
-- Giá trị đa dạng:
-- - Percentage: 10% - 30%
-- - Amount: 30,000đ - 250,000đ
-- - Min order: 0đ - 2,000,000đ
-- =====================================================

