-- Fix: Tạo Customer cho tất cả Users hiện có chưa có Customer
INSERT INTO customers (user_id)
SELECT u.id 
FROM users u
LEFT JOIN customers c ON u.id = c.user_id
WHERE c.user_id IS NULL
AND EXISTS (
    SELECT 1 FROM user_role ur
    JOIN roles r ON ur.role_id = r.id
    WHERE ur.user_id = u.id
    AND r.name = 'CUSTOMER'
);

-- Verify
SELECT u.id, u.username, c.user_id as customer_id
FROM users u
LEFT JOIN customers c ON u.id = c.user_id
WHERE EXISTS (
    SELECT 1 FROM user_role ur
    JOIN roles r ON ur.role_id = r.id
    WHERE ur.user_id = u.id
    AND r.name = 'CUSTOMER'
)
ORDER BY u.id;
