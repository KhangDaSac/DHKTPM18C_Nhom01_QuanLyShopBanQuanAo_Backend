-- ============================================
-- KIỂM TRA VÀ TẠO CUSTOMER CHO USERS
-- Database: modamint_db
-- ============================================

-- BƯỚC 1: Kiểm tra users nào chưa có customer
SELECT 
    u.id, 
    u.username,
    u.email,
    c.user_id as has_customer_record,
    CASE 
        WHEN c.user_id IS NULL THEN 'MISSING CUSTOMER!'
        ELSE 'OK'
    END as status
FROM users u
LEFT JOIN customers c ON u.id = c.user_id
ORDER BY u.id;

-- BƯỚC 2: Tạo Customer cho TẤT CẢ users chưa có customer
INSERT INTO customers (user_id)
SELECT u.id 
FROM users u
LEFT JOIN customers c ON u.id = c.user_id
WHERE c.user_id IS NULL;

-- BƯỚC 3: Verify lại - Tất cả users phải có customer
SELECT 
    u.id, 
    u.username,
    c.user_id as customer_id,
    CASE 
        WHEN c.user_id IS NOT NULL THEN '✓ OK'
        ELSE '✗ MISSING'
    END as status
FROM users u
LEFT JOIN customers c ON u.id = c.user_id
ORDER BY u.id;
