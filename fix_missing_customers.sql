-- SQL Script to create missing Customer records for existing Users
-- Run this script if you have existing users who don't have Customer records

-- Insert Customer records for all Users with CUSTOMER role who don't have a Customer record yet
INSERT INTO customers (customer_id, user_id, email, name, phone)
SELECT 
    u.id as customer_id,
    u.id as user_id,
    u.email,
    CONCAT(COALESCE(u.first_name, ''), ' ', COALESCE(u.last_name, '')) as name,
    u.phone
FROM users u
INNER JOIN user_role ur ON u.id = ur.user_id
INNER JOIN roles r ON ur.role_id = r.id
WHERE r.name = 'CUSTOMER'
  AND NOT EXISTS (
    SELECT 1 FROM customers c WHERE c.customer_id = u.id
  );

-- Verify the results
SELECT 
    u.id as user_id,
    u.username,
    u.email,
    c.customer_id,
    CASE WHEN c.customer_id IS NULL THEN 'MISSING' ELSE 'EXISTS' END as customer_status
FROM users u
INNER JOIN user_role ur ON u.id = ur.user_id
INNER JOIN roles r ON ur.role_id = r.id
WHERE r.name = 'CUSTOMER'
ORDER BY customer_status DESC, u.username;
