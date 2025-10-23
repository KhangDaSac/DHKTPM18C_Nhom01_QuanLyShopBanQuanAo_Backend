-- Sample Data for Oriental Fashion Shop Backend
-- This file contains test data for development and testing purposes

-- ==============================================
-- 1. ROLES AND PERMISSIONS
-- ==============================================

-- Insert Roles
INSERT INTO roles (id, name, description) VALUES
(1, 'ADMIN', 'Administrator role with full access'),
(2, 'STAFF', 'Staff role with limited access'),
(3, 'CUSTOMER', 'Customer role for regular users');

-- Insert Permissions
INSERT INTO permissions (id, name, description) VALUES
(1, 'USER_MANAGEMENT', 'Manage users'),
(2, 'PRODUCT_MANAGEMENT', 'Manage products'),
(3, 'ORDER_MANAGEMENT', 'Manage orders'),
(4, 'CUSTOMER_MANAGEMENT', 'Manage customers'),
(5, 'BRAND_MANAGEMENT', 'Manage brands'),
(6, 'CATEGORY_MANAGEMENT', 'Manage categories'),
(7, 'REVIEW_MANAGEMENT', 'Manage reviews'),
(8, 'PROMOTION_MANAGEMENT', 'Manage promotions');

-- Link Roles and Permissions
INSERT INTO role_permissions (role_id, permission_id) VALUES
-- Admin has all permissions
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8),
-- Staff has limited permissions
(2, 2), (2, 3), (2, 4), (2, 7),
-- Customer has basic permissions
(3, 7);

-- ==============================================
-- 2. USERS AND CUSTOMERS
-- ==============================================

-- Insert Users (10 customers)
INSERT INTO users (id, username, email, password, phone, first_name, last_name, image, dob, gender, active, create_at, update_at) VALUES
('user-001', 'customer1', 'customer1@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '0123456789', 'Nguyễn', 'Văn A', 'https://example.com/avatar1.jpg', '1990-01-15', 'MALE', true, NOW(), NOW()),
('user-002', 'customer2', 'customer2@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '0123456790', 'Trần', 'Thị B', 'https://example.com/avatar2.jpg', '1992-03-20', 'FEMALE', true, NOW(), NOW()),
('user-003', 'customer3', 'customer3@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '0123456791', 'Lê', 'Văn C', 'https://example.com/avatar3.jpg', '1988-07-10', 'MALE', true, NOW(), NOW()),
('user-004', 'customer4', 'customer4@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '0123456792', 'Phạm', 'Thị D', 'https://example.com/avatar4.jpg', '1995-11-25', 'FEMALE', true, NOW(), NOW()),
('user-005', 'customer5', 'customer5@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '0123456793', 'Hoàng', 'Văn E', 'https://example.com/avatar5.jpg', '1991-05-08', 'MALE', true, NOW(), NOW()),
('user-006', 'customer6', 'customer6@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '0123456794', 'Vũ', 'Thị F', 'https://example.com/avatar6.jpg', '1993-09-12', 'FEMALE', true, NOW(), NOW()),
('user-007', 'customer7', 'customer7@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '0123456795', 'Đặng', 'Văn G', 'https://example.com/avatar7.jpg', '1989-12-30', 'MALE', true, NOW(), NOW()),
('user-008', 'customer8', 'customer8@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '0123456796', 'Bùi', 'Thị H', 'https://example.com/avatar8.jpg', '1994-04-18', 'FEMALE', true, NOW(), NOW()),
('user-009', 'customer9', 'customer9@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '0123456797', 'Đinh', 'Văn I', 'https://example.com/avatar9.jpg', '1990-08-22', 'MALE', true, NOW(), NOW()),
('user-010', 'customer10', 'customer10@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '0123456798', 'Ngô', 'Thị J', 'https://example.com/avatar10.jpg', '1992-06-14', 'FEMALE', true, NOW(), NOW());

-- Insert Admin and Staff users
INSERT INTO users (id, username, email, password, phone, first_name, last_name, image, dob, gender, active, create_at, update_at) VALUES
('admin-001', 'admin', 'admin@orientalfashion.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '0987654321', 'Admin', 'System', 'https://example.com/admin.jpg', '1985-01-01', 'MALE', true, NOW(), NOW()),
('staff-001', 'staff1', 'staff1@orientalfashion.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '0987654322', 'Staff', 'Member', 'https://example.com/staff.jpg', '1990-01-01', 'FEMALE', true, NOW(), NOW());

-- Link Users to Roles
INSERT INTO user_role (user_id, role_id) VALUES
-- Admin role
('admin-001', 1),
-- Staff role
('staff-001', 2),
-- Customer roles
('user-001', 3), ('user-002', 3), ('user-003', 3), ('user-004', 3), ('user-005', 3),
('user-006', 3), ('user-007', 3), ('user-008', 3), ('user-009', 3), ('user-010', 3);

-- Insert Customers
INSERT INTO customers (user_id) VALUES
('user-001'), ('user-002'), ('user-003'), ('user-004'), ('user-005'),
('user-006'), ('user-007'), ('user-008'), ('user-009'), ('user-010');

-- ==============================================
-- 3. BRANDS (15 brands)
-- ==============================================

INSERT INTO brands (id, name, description, logo_url, active) VALUES
(1, 'Uniqlo', 'Japanese casual wear brand', 'https://example.com/logos/uniqlo.jpg', true),
(2, 'Zara', 'Spanish fast fashion brand', 'https://example.com/logos/zara.jpg', true),
(3, 'H&M', 'Swedish multinational clothing retailer', 'https://example.com/logos/hm.jpg', true),
(4, 'Nike', 'American multinational sportswear brand', 'https://example.com/logos/nike.jpg', true),
(5, 'Adidas', 'German multinational sportswear brand', 'https://example.com/logos/adidas.jpg', true),
(6, 'Gucci', 'Italian luxury fashion brand', 'https://example.com/logos/gucci.jpg', true),
(7, 'Louis Vuitton', 'French luxury fashion brand', 'https://example.com/logos/lv.jpg', true),
(8, 'Chanel', 'French luxury fashion brand', 'https://example.com/logos/chanel.jpg', true),
(9, 'Prada', 'Italian luxury fashion brand', 'https://example.com/logos/prada.jpg', true),
(10, 'Versace', 'Italian luxury fashion brand', 'https://example.com/logos/versace.jpg', true),
(11, 'Tommy Hilfiger', 'American lifestyle brand', 'https://example.com/logos/tommy.jpg', true),
(12, 'Calvin Klein', 'American fashion brand', 'https://example.com/logos/ck.jpg', true),
(13, 'Ralph Lauren', 'American luxury fashion brand', 'https://example.com/logos/rl.jpg', true),
(14, 'Burberry', 'British luxury fashion brand', 'https://example.com/logos/burberry.jpg', true),
(15, 'Hermès', 'French luxury fashion brand', 'https://example.com/logos/hermes.jpg', true);

-- ==============================================
-- 4. CATEGORIES (20 categories with hierarchy)
-- ==============================================

-- Main Categories
INSERT INTO categories (id, name, is_active, parent_id) VALUES
(1, 'Áo Nam', true, NULL),
(2, 'Áo Nữ', true, NULL),
(3, 'Quần Nam', true, NULL),
(4, 'Quần Nữ', true, NULL),
(5, 'Giày Nam', true, NULL),
(6, 'Giày Nữ', true, NULL),
(7, 'Phụ Kiện', true, NULL),
(8, 'Túi Xách', true, NULL);

-- Sub Categories for Áo Nam
INSERT INTO categories (id, name, is_active, parent_id) VALUES
(9, 'Áo Sơ Mi', true, 1),
(10, 'Áo Thun', true, 1),
(11, 'Áo Khoác', true, 1),
(12, 'Áo Vest', true, 1);

-- Sub Categories for Áo Nữ
INSERT INTO categories (id, name, is_active, parent_id) VALUES
(13, 'Áo Sơ Mi', true, 2),
(14, 'Áo Thun', true, 2),
(15, 'Áo Khoác', true, 2),
(16, 'Đầm', true, 2);

-- Sub Categories for Quần Nam
INSERT INTO categories (id, name, is_active, parent_id) VALUES
(17, 'Quần Jeans', true, 3),
(18, 'Quần Kaki', true, 3),
(19, 'Quần Short', true, 3);

-- Sub Categories for Quần Nữ
INSERT INTO categories (id, name, is_active, parent_id) VALUES
(20, 'Quần Jeans', true, 4),
(21, 'Quần Legging', true, 4),
(22, 'Quần Short', true, 4);

-- ==============================================
-- 5. PRODUCTS (50 products)
-- ==============================================

INSERT INTO products (id, name, brand_id, category_id, description, active, create_at, update_at) VALUES
-- Áo Nam (Products 1-15)
(1, 'Áo Sơ Mi Trắng Nam', 1, 9, 'Áo sơ mi trắng chất liệu cotton cao cấp', true, NOW(), NOW()),
(2, 'Áo Sơ Mi Xanh Nam', 1, 9, 'Áo sơ mi xanh dương chất liệu cotton', true, NOW(), NOW()),
(3, 'Áo Thun Basic Nam', 2, 10, 'Áo thun basic màu đen chất liệu cotton', true, NOW(), NOW()),
(4, 'Áo Thun Polo Nam', 2, 10, 'Áo polo nam màu xanh chất liệu cotton', true, NOW(), NOW()),
(5, 'Áo Khoác Bomber Nam', 3, 11, 'Áo khoác bomber nam màu đen', true, NOW(), NOW()),
(6, 'Áo Khoác Hoodie Nam', 3, 11, 'Áo hoodie nam màu xám chất liệu cotton', true, NOW(), NOW()),
(7, 'Áo Vest Nam', 4, 12, 'Áo vest nam màu xanh đen chất liệu wool', true, NOW(), NOW()),
(8, 'Áo Vest Kẻ Sọc Nam', 4, 12, 'Áo vest kẻ sọc nam màu xám', true, NOW(), NOW()),
(9, 'Áo Sơ Mi Kẻ Sọc Nam', 5, 9, 'Áo sơ mi kẻ sọc nam màu xanh trắng', true, NOW(), NOW()),
(10, 'Áo Thun Graphic Nam', 5, 10, 'Áo thun graphic nam với thiết kế độc đáo', true, NOW(), NOW()),
(11, 'Áo Khoác Denim Nam', 6, 11, 'Áo khoác denim nam màu xanh', true, NOW(), NOW()),
(12, 'Áo Khoác Blazer Nam', 6, 11, 'Áo khoác blazer nam màu đen', true, NOW(), NOW()),
(13, 'Áo Sơ Mi Flannel Nam', 7, 9, 'Áo sơ mi flannel nam màu đỏ', true, NOW(), NOW()),
(14, 'Áo Thun Tank Top Nam', 7, 10, 'Áo tank top nam màu trắng', true, NOW(), NOW()),
(15, 'Áo Vest 3 Mảnh Nam', 8, 12, 'Áo vest 3 mảnh nam màu xanh navy', true, NOW(), NOW()),

-- Áo Nữ (Products 16-30)
(16, 'Áo Sơ Mi Trắng Nữ', 9, 13, 'Áo sơ mi trắng nữ chất liệu cotton', true, NOW(), NOW()),
(17, 'Áo Sơ Mi Hoa Nữ', 9, 13, 'Áo sơ mi hoa nữ thiết kế nữ tính', true, NOW(), NOW()),
(18, 'Áo Thun Basic Nữ', 10, 14, 'Áo thun basic nữ màu hồng', true, NOW(), NOW()),
(19, 'Áo Thun Crop Top Nữ', 10, 14, 'Áo crop top nữ màu đen', true, NOW(), NOW()),
(20, 'Áo Khoác Cardigan Nữ', 11, 15, 'Áo khoác cardigan nữ màu be', true, NOW(), NOW()),
(21, 'Áo Khoác Blazer Nữ', 11, 15, 'Áo khoác blazer nữ màu xanh', true, NOW(), NOW()),
(22, 'Đầm Dài Nữ', 12, 16, 'Đầm dài nữ màu đỏ thiết kế sang trọng', true, NOW(), NOW()),
(23, 'Đầm Ngắn Nữ', 12, 16, 'Đầm ngắn nữ màu xanh', true, NOW(), NOW()),
(24, 'Áo Sơ Mi Oversize Nữ', 13, 13, 'Áo sơ mi oversize nữ màu trắng', true, NOW(), NOW()),
(25, 'Áo Thun Long Sleeve Nữ', 13, 14, 'Áo thun dài tay nữ màu xám', true, NOW(), NOW()),
(26, 'Áo Khoác Hoodie Nữ', 14, 15, 'Áo hoodie nữ màu hồng', true, NOW(), NOW()),
(27, 'Áo Khoác Bomber Nữ', 14, 15, 'Áo khoác bomber nữ màu đen', true, NOW(), NOW()),
(28, 'Đầm Maxi Nữ', 15, 16, 'Đầm maxi nữ màu tím', true, NOW(), NOW()),
(29, 'Đầm Mini Nữ', 15, 16, 'Đầm mini nữ màu vàng', true, NOW(), NOW()),
(30, 'Áo Sơ Mi Silk Nữ', 1, 13, 'Áo sơ mi silk nữ màu kem', true, NOW(), NOW()),

-- Quần Nam (Products 31-40)
(31, 'Quần Jeans Nam', 2, 17, 'Quần jeans nam màu xanh đậm', true, NOW(), NOW()),
(32, 'Quần Jeans Đen Nam', 2, 17, 'Quần jeans đen nam', true, NOW(), NOW()),
(33, 'Quần Kaki Nam', 3, 18, 'Quần kaki nam màu be', true, NOW(), NOW()),
(34, 'Quần Kaki Xanh Nam', 3, 18, 'Quần kaki xanh nam', true, NOW(), NOW()),
(35, 'Quần Short Nam', 4, 19, 'Quần short nam màu đen', true, NOW(), NOW()),
(36, 'Quần Short Thể Thao Nam', 4, 19, 'Quần short thể thao nam màu xanh', true, NOW(), NOW()),
(37, 'Quần Jeans Skinny Nam', 5, 17, 'Quần jeans skinny nam màu xanh nhạt', true, NOW(), NOW()),
(38, 'Quần Kaki Cargo Nam', 5, 18, 'Quần kaki cargo nam màu xanh rừng', true, NOW(), NOW()),
(39, 'Quần Short Bermuda Nam', 6, 19, 'Quần short bermuda nam màu trắng', true, NOW(), NOW()),
(40, 'Quần Jeans Ripped Nam', 6, 17, 'Quần jeans ripped nam màu xanh', true, NOW(), NOW()),

-- Quần Nữ (Products 41-50)
(41, 'Quần Jeans Nữ', 7, 20, 'Quần jeans nữ màu xanh', true, NOW(), NOW()),
(42, 'Quần Jeans Đen Nữ', 7, 20, 'Quần jeans đen nữ', true, NOW(), NOW()),
(43, 'Quần Legging Nữ', 8, 21, 'Quần legging nữ màu đen', true, NOW(), NOW()),
(44, 'Quần Legging Thể Thao Nữ', 8, 21, 'Quần legging thể thao nữ màu xám', true, NOW(), NOW()),
(45, 'Quần Short Nữ', 9, 22, 'Quần short nữ màu trắng', true, NOW(), NOW()),
(46, 'Quần Short Denim Nữ', 9, 22, 'Quần short denim nữ màu xanh', true, NOW(), NOW()),
(47, 'Quần Jeans Skinny Nữ', 10, 20, 'Quần jeans skinny nữ màu xanh đậm', true, NOW(), NOW()),
(48, 'Quần Legging Yoga Nữ', 10, 21, 'Quần legging yoga nữ màu hồng', true, NOW(), NOW()),
(49, 'Quần Short High Waist Nữ', 11, 22, 'Quần short high waist nữ màu đen', true, NOW(), NOW()),
(50, 'Quần Jeans Mom Fit Nữ', 11, 20, 'Quần jeans mom fit nữ màu xanh', true, NOW(), NOW());

-- ==============================================
-- 6. PRODUCT VARIANTS (200 variants - 4 per product: S, M, L, XL)
-- ==============================================

-- Product 1 variants
INSERT INTO product_variants (id, product_id, size, color, price, discount, quantity, additional_price, create_at) VALUES
(1, 1, 'S', 'Trắng', 299000, 0, 50, 0, NOW()),
(2, 1, 'M', 'Trắng', 299000, 0, 75, 0, NOW()),
(3, 1, 'L', 'Trắng', 299000, 0, 60, 0, NOW()),
(4, 1, 'XL', 'Trắng', 299000, 0, 40, 0, NOW());

-- Product 2 variants
INSERT INTO product_variants (id, product_id, size, color, price, discount, quantity, additional_price, create_at) VALUES
(5, 2, 'S', 'Xanh', 299000, 0, 45, 0, NOW()),
(6, 2, 'M', 'Xanh', 299000, 0, 80, 0, NOW()),
(7, 2, 'L', 'Xanh', 299000, 0, 65, 0, NOW()),
(8, 2, 'XL', 'Xanh', 299000, 0, 35, 0, NOW());

-- Product 3 variants
INSERT INTO product_variants (id, product_id, size, color, price, discount, quantity, additional_price, create_at) VALUES
(9, 3, 'S', 'Đen', 199000, 0, 100, 0, NOW()),
(10, 3, 'M', 'Đen', 199000, 0, 120, 0, NOW()),
(11, 3, 'L', 'Đen', 199000, 0, 90, 0, NOW()),
(12, 3, 'XL', 'Đen', 199000, 0, 70, 0, NOW());

-- Product 4 variants
INSERT INTO product_variants (id, product_id, size, color, price, discount, quantity, additional_price, create_at) VALUES
(13, 4, 'S', 'Xanh', 249000, 0, 60, 0, NOW()),
(14, 4, 'M', 'Xanh', 249000, 0, 85, 0, NOW()),
(15, 4, 'L', 'Xanh', 249000, 0, 70, 0, NOW()),
(16, 4, 'XL', 'Xanh', 249000, 0, 50, 0, NOW());

-- Product 5 variants
INSERT INTO product_variants (id, product_id, size, color, price, discount, quantity, additional_price, create_at) VALUES
(17, 5, 'S', 'Đen', 599000, 0, 30, 0, NOW()),
(18, 5, 'M', 'Đen', 599000, 0, 40, 0, NOW()),
(19, 5, 'L', 'Đen', 599000, 0, 35, 0, NOW()),
(20, 5, 'XL', 'Đen', 599000, 0, 25, 0, NOW());

-- Product 6 variants
INSERT INTO product_variants (id, product_id, size, color, price, discount, quantity, additional_price, create_at) VALUES
(21, 6, 'S', 'Xám', 399000, 0, 40, 0, NOW()),
(22, 6, 'M', 'Xám', 399000, 0, 55, 0, NOW()),
(23, 6, 'L', 'Xám', 399000, 0, 45, 0, NOW()),
(24, 6, 'XL', 'Xám', 399000, 0, 30, 0, NOW());

-- Product 7 variants
INSERT INTO product_variants (id, product_id, size, color, price, discount, quantity, additional_price, create_at) VALUES
(25, 7, 'S', 'Xanh Đen', 1299000, 0, 20, 0, NOW()),
(26, 7, 'M', 'Xanh Đen', 1299000, 0, 25, 0, NOW()),
(27, 7, 'L', 'Xanh Đen', 1299000, 0, 20, 0, NOW()),
(28, 7, 'XL', 'Xanh Đen', 1299000, 0, 15, 0, NOW());

-- Product 8 variants
INSERT INTO product_variants (id, product_id, size, color, price, discount, quantity, additional_price, create_at) VALUES
(29, 8, 'S', 'Xám', 1199000, 0, 18, 0, NOW()),
(30, 8, 'M', 'Xám', 1199000, 0, 22, 0, NOW()),
(31, 8, 'L', 'Xám', 1199000, 0, 18, 0, NOW()),
(32, 8, 'XL', 'Xám', 1199000, 0, 12, 0, NOW());

-- Product 9 variants
INSERT INTO product_variants (id, product_id, size, color, price, discount, quantity, additional_price, create_at) VALUES
(33, 9, 'S', 'Xanh Trắng', 349000, 0, 35, 0, NOW()),
(34, 9, 'M', 'Xanh Trắng', 349000, 0, 50, 0, NOW()),
(35, 9, 'L', 'Xanh Trắng', 349000, 0, 40, 0, NOW()),
(36, 9, 'XL', 'Xanh Trắng', 349000, 0, 25, 0, NOW());

-- Product 10 variants
INSERT INTO product_variants (id, product_id, size, color, price, discount, quantity, additional_price, create_at) VALUES
(37, 10, 'S', 'Đen', 229000, 0, 50, 0, NOW()),
(38, 10, 'M', 'Đen', 229000, 0, 70, 0, NOW()),
(39, 10, 'L', 'Đen', 229000, 0, 55, 0, NOW()),
(40, 10, 'XL', 'Đen', 229000, 0, 35, 0, NOW());

-- Continue with remaining products (Products 11-50) - I'll create a pattern for the rest
-- For brevity, I'll show the pattern and you can extend it

-- Products 11-15 variants (continuing the pattern)
INSERT INTO product_variants (id, product_id, size, color, price, discount, quantity, additional_price, create_at) VALUES
-- Product 11
(41, 11, 'S', 'Xanh', 799000, 0, 25, 0, NOW()),
(42, 11, 'M', 'Xanh', 799000, 0, 30, 0, NOW()),
(43, 11, 'L', 'Xanh', 799000, 0, 25, 0, NOW()),
(44, 11, 'XL', 'Xanh', 799000, 0, 20, 0, NOW()),
-- Product 12
(45, 12, 'S', 'Đen', 899000, 0, 20, 0, NOW()),
(46, 12, 'M', 'Đen', 899000, 0, 25, 0, NOW()),
(47, 12, 'L', 'Đen', 899000, 0, 20, 0, NOW()),
(48, 12, 'XL', 'Đen', 899000, 0, 15, 0, NOW()),
-- Product 13
(49, 13, 'S', 'Đỏ', 279000, 0, 40, 0, NOW()),
(50, 13, 'M', 'Đỏ', 279000, 0, 55, 0, NOW()),
(51, 13, 'L', 'Đỏ', 279000, 0, 45, 0, NOW()),
(52, 13, 'XL', 'Đỏ', 279000, 0, 30, 0, NOW()),
-- Product 14
(53, 14, 'S', 'Trắng', 179000, 0, 60, 0, NOW()),
(54, 14, 'M', 'Trắng', 179000, 0, 80, 0, NOW()),
(55, 14, 'L', 'Trắng', 179000, 0, 65, 0, NOW()),
(56, 14, 'XL', 'Trắng', 179000, 0, 45, 0, NOW()),
-- Product 15
(57, 15, 'S', 'Xanh Navy', 1399000, 0, 15, 0, NOW()),
(58, 15, 'M', 'Xanh Navy', 1399000, 0, 20, 0, NOW()),
(59, 15, 'L', 'Xanh Navy', 1399000, 0, 18, 0, NOW()),
(60, 15, 'XL', 'Xanh Navy', 1399000, 0, 12, 0, NOW());

-- Products 16-20 variants (Áo Nữ)
INSERT INTO product_variants (id, product_id, size, color, price, discount, quantity, additional_price, create_at) VALUES
-- Product 16
(61, 16, 'S', 'Trắng', 249000, 0, 45, 0, NOW()),
(62, 16, 'M', 'Trắng', 249000, 0, 60, 0, NOW()),
(63, 16, 'L', 'Trắng', 249000, 0, 50, 0, NOW()),
(64, 16, 'XL', 'Trắng', 249000, 0, 35, 0, NOW()),
-- Product 17
(65, 17, 'S', 'Hoa', 279000, 0, 35, 0, NOW()),
(66, 17, 'M', 'Hoa', 279000, 0, 50, 0, NOW()),
(67, 17, 'L', 'Hoa', 279000, 0, 40, 0, NOW()),
(68, 17, 'XL', 'Hoa', 279000, 0, 25, 0, NOW()),
-- Product 18
(69, 18, 'S', 'Hồng', 179000, 0, 70, 0, NOW()),
(70, 18, 'M', 'Hồng', 179000, 0, 90, 0, NOW()),
(71, 18, 'L', 'Hồng', 179000, 0, 75, 0, NOW()),
(72, 18, 'XL', 'Hồng', 179000, 0, 55, 0, NOW()),
-- Product 19
(73, 19, 'S', 'Đen', 199000, 0, 50, 0, NOW()),
(74, 19, 'M', 'Đen', 199000, 0, 70, 0, NOW()),
(75, 19, 'L', 'Đen', 199000, 0, 60, 0, NOW()),
(76, 19, 'XL', 'Đen', 199000, 0, 40, 0, NOW()),
-- Product 20
(77, 20, 'S', 'Be', 399000, 0, 30, 0, NOW()),
(78, 20, 'M', 'Be', 399000, 0, 40, 0, NOW()),
(79, 20, 'L', 'Be', 399000, 0, 35, 0, NOW()),
(80, 20, 'XL', 'Be', 399000, 0, 25, 0, NOW());

-- Continue with remaining products (Products 21-50) following the same pattern
-- For brevity, I'll create a few more examples and then provide the complete pattern

-- Products 21-25 variants
INSERT INTO product_variants (id, product_id, size, color, price, discount, quantity, additional_price, create_at) VALUES
-- Product 21
(81, 21, 'S', 'Xanh', 499000, 0, 25, 0, NOW()),
(82, 21, 'M', 'Xanh', 499000, 0, 35, 0, NOW()),
(83, 21, 'L', 'Xanh', 499000, 0, 30, 0, NOW()),
(84, 21, 'XL', 'Xanh', 499000, 0, 20, 0, NOW()),
-- Product 22
(85, 22, 'S', 'Đỏ', 699000, 0, 20, 0, NOW()),
(86, 22, 'M', 'Đỏ', 699000, 0, 25, 0, NOW()),
(87, 22, 'L', 'Đỏ', 699000, 0, 20, 0, NOW()),
(88, 22, 'XL', 'Đỏ', 699000, 0, 15, 0, NOW()),
-- Product 23
(89, 23, 'S', 'Xanh', 599000, 0, 30, 0, NOW()),
(90, 23, 'M', 'Xanh', 599000, 0, 40, 0, NOW()),
(91, 23, 'L', 'Xanh', 599000, 0, 35, 0, NOW()),
(92, 23, 'XL', 'Xanh', 599000, 0, 25, 0, NOW()),
-- Product 24
(93, 24, 'S', 'Trắng', 229000, 0, 40, 0, NOW()),
(94, 24, 'M', 'Trắng', 229000, 0, 55, 0, NOW()),
(95, 24, 'L', 'Trắng', 229000, 0, 45, 0, NOW()),
(96, 24, 'XL', 'Trắng', 229000, 0, 30, 0, NOW()),
-- Product 25
(97, 25, 'S', 'Xám', 199000, 0, 50, 0, NOW()),
(98, 25, 'M', 'Xám', 199000, 0, 70, 0, NOW()),
(99, 25, 'L', 'Xám', 199000, 0, 60, 0, NOW()),
(100, 25, 'XL', 'Xám', 199000, 0, 40, 0, NOW());

-- Products 26-30 variants
INSERT INTO product_variants (id, product_id, size, color, price, discount, quantity, additional_price, create_at) VALUES
-- Product 26
(101, 26, 'S', 'Hồng', 349000, 0, 35, 0, NOW()),
(102, 26, 'M', 'Hồng', 349000, 0, 50, 0, NOW()),
(103, 26, 'L', 'Hồng', 349000, 0, 40, 0, NOW()),
(104, 26, 'XL', 'Hồng', 349000, 0, 25, 0, NOW()),
-- Product 27
(105, 27, 'S', 'Đen', 399000, 0, 30, 0, NOW()),
(106, 27, 'M', 'Đen', 399000, 0, 40, 0, NOW()),
(107, 27, 'L', 'Đen', 399000, 0, 35, 0, NOW()),
(108, 27, 'XL', 'Đen', 399000, 0, 25, 0, NOW()),
-- Product 28
(109, 28, 'S', 'Tím', 799000, 0, 20, 0, NOW()),
(110, 28, 'M', 'Tím', 799000, 0, 25, 0, NOW()),
(111, 28, 'L', 'Tím', 799000, 0, 20, 0, NOW()),
(112, 28, 'XL', 'Tím', 799000, 0, 15, 0, NOW()),
-- Product 29
(113, 29, 'S', 'Vàng', 499000, 0, 25, 0, NOW()),
(114, 29, 'M', 'Vàng', 499000, 0, 35, 0, NOW()),
(115, 29, 'L', 'Vàng', 499000, 0, 30, 0, NOW()),
(116, 29, 'XL', 'Vàng', 499000, 0, 20, 0, NOW()),
-- Product 30
(117, 30, 'S', 'Kem', 329000, 0, 30, 0, NOW()),
(118, 30, 'M', 'Kem', 329000, 0, 45, 0, NOW()),
(119, 30, 'L', 'Kem', 329000, 0, 35, 0, NOW()),
(120, 30, 'XL', 'Kem', 329000, 0, 25, 0, NOW());

-- Products 31-40 variants (Quần Nam)
INSERT INTO product_variants (id, product_id, size, color, price, discount, quantity, additional_price, create_at) VALUES
-- Product 31
(121, 31, 'S', 'Xanh Đậm', 399000, 0, 40, 0, NOW()),
(122, 31, 'M', 'Xanh Đậm', 399000, 0, 55, 0, NOW()),
(123, 31, 'L', 'Xanh Đậm', 399000, 0, 45, 0, NOW()),
(124, 31, 'XL', 'Xanh Đậm', 399000, 0, 30, 0, NOW()),
-- Product 32
(125, 32, 'S', 'Đen', 399000, 0, 35, 0, NOW()),
(126, 32, 'M', 'Đen', 399000, 0, 50, 0, NOW()),
(127, 32, 'L', 'Đen', 399000, 0, 40, 0, NOW()),
(128, 32, 'XL', 'Đen', 399000, 0, 25, 0, NOW()),
-- Product 33
(129, 33, 'S', 'Be', 299000, 0, 50, 0, NOW()),
(130, 33, 'M', 'Be', 299000, 0, 70, 0, NOW()),
(131, 33, 'L', 'Be', 299000, 0, 60, 0, NOW()),
(132, 33, 'XL', 'Be', 299000, 0, 40, 0, NOW()),
-- Product 34
(133, 34, 'S', 'Xanh', 299000, 0, 45, 0, NOW()),
(134, 34, 'M', 'Xanh', 299000, 0, 65, 0, NOW()),
(135, 34, 'L', 'Xanh', 299000, 0, 55, 0, NOW()),
(136, 34, 'XL', 'Xanh', 299000, 0, 35, 0, NOW()),
-- Product 35
(137, 35, 'S', 'Đen', 199000, 0, 60, 0, NOW()),
(138, 35, 'M', 'Đen', 199000, 0, 80, 0, NOW()),
(139, 35, 'L', 'Đen', 199000, 0, 70, 0, NOW()),
(140, 35, 'XL', 'Đen', 199000, 0, 50, 0, NOW()),
-- Product 36
(141, 36, 'S', 'Xanh', 229000, 0, 50, 0, NOW()),
(142, 36, 'M', 'Xanh', 229000, 0, 70, 0, NOW()),
(143, 36, 'L', 'Xanh', 229000, 0, 60, 0, NOW()),
(144, 36, 'XL', 'Xanh', 229000, 0, 40, 0, NOW()),
-- Product 37
(145, 37, 'S', 'Xanh Nhạt', 349000, 0, 35, 0, NOW()),
(146, 37, 'M', 'Xanh Nhạt', 349000, 0, 50, 0, NOW()),
(147, 37, 'L', 'Xanh Nhạt', 349000, 0, 40, 0, NOW()),
(148, 37, 'XL', 'Xanh Nhạt', 349000, 0, 25, 0, NOW()),
-- Product 38
(149, 38, 'S', 'Xanh Rừng', 329000, 0, 30, 0, NOW()),
(150, 38, 'M', 'Xanh Rừng', 329000, 0, 45, 0, NOW()),
(151, 38, 'L', 'Xanh Rừng', 329000, 0, 35, 0, NOW()),
(152, 38, 'XL', 'Xanh Rừng', 329000, 0, 20, 0, NOW()),
-- Product 39
(153, 39, 'S', 'Trắng', 249000, 0, 40, 0, NOW()),
(154, 39, 'M', 'Trắng', 249000, 0, 55, 0, NOW()),
(155, 39, 'L', 'Trắng', 249000, 0, 45, 0, NOW()),
(156, 39, 'XL', 'Trắng', 249000, 0, 30, 0, NOW()),
-- Product 40
(157, 40, 'S', 'Xanh', 379000, 0, 30, 0, NOW()),
(158, 40, 'M', 'Xanh', 379000, 0, 40, 0, NOW()),
(159, 40, 'L', 'Xanh', 379000, 0, 35, 0, NOW()),
(160, 40, 'XL', 'Xanh', 379000, 0, 25, 0, NOW());

-- Products 41-50 variants (Quần Nữ)
INSERT INTO product_variants (id, product_id, size, color, price, discount, quantity, additional_price, create_at) VALUES
-- Product 41
(161, 41, 'S', 'Xanh', 349000, 0, 45, 0, NOW()),
(162, 41, 'M', 'Xanh', 349000, 0, 60, 0, NOW()),
(163, 41, 'L', 'Xanh', 349000, 0, 50, 0, NOW()),
(164, 41, 'XL', 'Xanh', 349000, 0, 35, 0, NOW()),
-- Product 42
(165, 42, 'S', 'Đen', 349000, 0, 40, 0, NOW()),
(166, 42, 'M', 'Đen', 349000, 0, 55, 0, NOW()),
(167, 42, 'L', 'Đen', 349000, 0, 45, 0, NOW()),
(168, 42, 'XL', 'Đen', 349000, 0, 30, 0, NOW()),
-- Product 43
(169, 43, 'S', 'Đen', 199000, 0, 70, 0, NOW()),
(170, 43, 'M', 'Đen', 199000, 0, 90, 0, NOW()),
(171, 43, 'L', 'Đen', 199000, 0, 75, 0, NOW()),
(172, 43, 'XL', 'Đen', 199000, 0, 55, 0, NOW()),
-- Product 44
(173, 44, 'S', 'Xám', 229000, 0, 60, 0, NOW()),
(174, 44, 'M', 'Xám', 229000, 0, 80, 0, NOW()),
(175, 44, 'L', 'Xám', 229000, 0, 70, 0, NOW()),
(176, 44, 'XL', 'Xám', 229000, 0, 50, 0, NOW()),
-- Product 45
(177, 45, 'S', 'Trắng', 179000, 0, 50, 0, NOW()),
(178, 45, 'M', 'Trắng', 179000, 0, 70, 0, NOW()),
(179, 45, 'L', 'Trắng', 179000, 0, 60, 0, NOW()),
(180, 45, 'XL', 'Trắng', 179000, 0, 40, 0, NOW()),
-- Product 46
(181, 46, 'S', 'Xanh', 199000, 0, 45, 0, NOW()),
(182, 46, 'M', 'Xanh', 199000, 0, 65, 0, NOW()),
(183, 46, 'L', 'Xanh', 199000, 0, 55, 0, NOW()),
(184, 46, 'XL', 'Xanh', 199000, 0, 35, 0, NOW()),
-- Product 47
(185, 47, 'S', 'Xanh Đậm', 379000, 0, 35, 0, NOW()),
(186, 47, 'M', 'Xanh Đậm', 379000, 0, 50, 0, NOW()),
(187, 47, 'L', 'Xanh Đậm', 379000, 0, 40, 0, NOW()),
(188, 47, 'XL', 'Xanh Đậm', 379000, 0, 25, 0, NOW()),
-- Product 48
(189, 48, 'S', 'Hồng', 249000, 0, 40, 0, NOW()),
(190, 48, 'M', 'Hồng', 249000, 0, 55, 0, NOW()),
(191, 48, 'L', 'Hồng', 249000, 0, 45, 0, NOW()),
(192, 48, 'XL', 'Hồng', 249000, 0, 30, 0, NOW()),
-- Product 49
(193, 49, 'S', 'Đen', 219000, 0, 45, 0, NOW()),
(194, 49, 'M', 'Đen', 219000, 0, 60, 0, NOW()),
(195, 49, 'L', 'Đen', 219000, 0, 50, 0, NOW()),
(196, 49, 'XL', 'Đen', 219000, 0, 35, 0, NOW()),
-- Product 50
(197, 50, 'S', 'Xanh', 329000, 0, 35, 0, NOW()),
(198, 50, 'M', 'Xanh', 329000, 0, 50, 0, NOW()),
(199, 50, 'L', 'Xanh', 329000, 0, 40, 0, NOW()),
(200, 50, 'XL', 'Xanh', 329000, 0, 25, 0, NOW());

-- ==============================================
-- 7. PRODUCT IMAGES (Sample images for first 10 products)
-- ==============================================

INSERT INTO product_image (id, product_id, variant_id, url) VALUES
-- Product 1 images
(1, 1, 1, 'https://example.com/products/1/s-1.jpg'),
(2, 1, 2, 'https://example.com/products/1/m-1.jpg'),
(3, 1, 3, 'https://example.com/products/1/l-1.jpg'),
(4, 1, 4, 'https://example.com/products/1/xl-1.jpg'),
-- Product 2 images
(5, 2, 5, 'https://example.com/products/2/s-1.jpg'),
(6, 2, 6, 'https://example.com/products/2/m-1.jpg'),
(7, 2, 7, 'https://example.com/products/2/l-1.jpg'),
(8, 2, 8, 'https://example.com/products/2/xl-1.jpg'),
-- Product 3 images
(9, 3, 9, 'https://example.com/products/3/s-1.jpg'),
(10, 3, 10, 'https://example.com/products/3/m-1.jpg'),
(11, 3, 11, 'https://example.com/products/3/l-1.jpg'),
(12, 3, 12, 'https://example.com/products/3/xl-1.jpg'),
-- Product 4 images
(13, 4, 13, 'https://example.com/products/4/s-1.jpg'),
(14, 4, 14, 'https://example.com/products/4/m-1.jpg'),
(15, 4, 15, 'https://example.com/products/4/l-1.jpg'),
(16, 4, 16, 'https://example.com/products/4/xl-1.jpg'),
-- Product 5 images
(17, 5, 17, 'https://example.com/products/5/s-1.jpg'),
(18, 5, 18, 'https://example.com/products/5/m-1.jpg'),
(19, 5, 19, 'https://example.com/products/5/l-1.jpg'),
(20, 5, 20, 'https://example.com/products/5/xl-1.jpg');

-- ==============================================
-- 8. ADDRESSES (Sample addresses for customers)
-- ==============================================

INSERT INTO addresses (id, city, ward, address_detail, customer_id) VALUES
(1, 'Hà Nội', 'Phường Cầu Giấy', 'Số 123 Đường Cầu Giấy', 'user-001'),
(2, 'Hà Nội', 'Phường Đống Đa', 'Số 456 Đường Đống Đa', 'user-001'),
(3, 'TP. Hồ Chí Minh', 'Phường Bến Nghé', 'Số 789 Đường Nguyễn Huệ', 'user-002'),
(4, 'TP. Hồ Chí Minh', 'Phường Tân Bình', 'Số 321 Đường Cộng Hòa', 'user-002'),
(5, 'Đà Nẵng', 'Phường Hải Châu', 'Số 654 Đường Lê Duẩn', 'user-003'),
(6, 'Hải Phòng', 'Phường Lê Chân', 'Số 987 Đường Lê Lợi', 'user-004'),
(7, 'Cần Thơ', 'Phường Ninh Kiều', 'Số 147 Đường Nguyễn Văn Cừ', 'user-005'),
(8, 'Huế', 'Phường Phú Hội', 'Số 258 Đường Lê Lợi', 'user-006'),
(9, 'Nha Trang', 'Phường Lộc Thọ', 'Số 369 Đường Trần Phú', 'user-007'),
(10, 'Vũng Tàu', 'Phường Thắng Tam', 'Số 741 Đường Thùy Vân', 'user-008');

-- ==============================================
-- 9. CARTS (Sample carts for customers)
-- ==============================================

INSERT INTO cart (id, customer_id, session_id, update_at, create_at) VALUES
(1, 'user-001', 'session-001', NOW(), NOW()),
(2, 'user-002', 'session-002', NOW(), NOW()),
(3, 'user-003', 'session-003', NOW(), NOW()),
(4, 'user-004', 'session-004', NOW(), NOW()),
(5, 'user-005', 'session-005', NOW(), NOW()),
(6, 'user-006', 'session-006', NOW(), NOW()),
(7, 'user-007', 'session-007', NOW(), NOW()),
(8, 'user-008', 'session-008', NOW(), NOW()),
(9, 'user-009', 'session-009', NOW(), NOW()),
(10, 'user-010', 'session-010', NOW(), NOW());

-- ==============================================
-- 10. CART ITEMS (Sample cart items)
-- ==============================================

INSERT INTO cart_item (id, cart_id, variant_id, quantity) VALUES
(1, 1, 1, 2),  -- Customer 1 has 2x Product 1 Size S
(2, 1, 5, 1),  -- Customer 1 has 1x Product 2 Size S
(3, 2, 9, 3),  -- Customer 2 has 3x Product 3 Size S
(4, 2, 13, 1), -- Customer 2 has 1x Product 4 Size S
(5, 3, 17, 1), -- Customer 3 has 1x Product 5 Size S
(6, 3, 21, 2), -- Customer 3 has 2x Product 6 Size S
(7, 4, 25, 1), -- Customer 4 has 1x Product 7 Size S
(8, 4, 29, 1), -- Customer 4 has 1x Product 8 Size S
(9, 5, 33, 2), -- Customer 5 has 2x Product 9 Size S
(10, 5, 37, 1); -- Customer 5 has 1x Product 10 Size S

-- ==============================================
-- 11. PROMOTIONS (Sample promotions)
-- ==============================================

INSERT INTO promotions (id, code, value, min_order_value, start_at, end_at, quantity, is_active, create_at) VALUES
(1, 'WELCOME10', '10', 500000, '2024-01-01 00:00:00', '2024-12-31 23:59:59', 1000, true, NOW()),
(2, 'SUMMER20', '20', 1000000, '2024-06-01 00:00:00', '2024-08-31 23:59:59', 500, true, NOW()),
(3, 'NEWYEAR50', '50000', 2000000, '2024-01-01 00:00:00', '2024-01-31 23:59:59', 200, true, NOW()),
(4, 'BLACKFRIDAY30', '30', 1500000, '2024-11-24 00:00:00', '2024-11-30 23:59:59', 300, true, NOW()),
(5, 'STUDENT15', '15', 300000, '2024-01-01 00:00:00', '2024-12-31 23:59:59', 2000, true, NOW());

-- ==============================================
-- 12. TYPE PROMOTIONS (Promotion types)
-- ==============================================

INSERT INTO type_promotions (id, promotion_id, type) VALUES
(1, 1, 'PERCENT'),    -- WELCOME10: 10% discount
(2, 2, 'PERCENT'),    -- SUMMER20: 20% discount
(3, 3, 'AMOUNT'),     -- NEWYEAR50: 50,000 VND discount
(4, 4, 'PERCENT'),    -- BLACKFRIDAY30: 30% discount
(5, 5, 'PERCENT');    -- STUDENT15: 15% discount

-- ==============================================
-- 13. ORDERS (Sample orders)
-- ==============================================

INSERT INTO orders (id, order_code, customer_id, total_amount, sub_total, promotion_id, promotion_value, order_status, payment_method, shipping_address_id, phone, update_at, create_at) VALUES
(1, 'ORD-001', 'user-001', 598000, 548000, 1, 50000, 'DELIVERED', 'CASH_ON_DELIVERY', 1, '0123456789', NOW(), NOW()),
(2, 'ORD-002', 'user-002', 897000, 867000, 2, 30000, 'SHIPPED', 'BANK_TRANSFER', 3, '0123456790', NOW(), NOW()),
(3, 'ORD-003', 'user-003', 998000, 968000, NULL, 0, 'PENDING', 'E_WALLET', 5, '0123456791', NOW(), NOW()),
(4, 'ORD-004', 'user-004', 2198000, 2148000, 3, 50000, 'PREPARING', 'BANK_TRANSFER', 6, '0123456792', NOW(), NOW()),
(5, 'ORD-005', 'user-005', 598000, 568000, 1, 30000, 'DELIVERED', 'CASH_ON_DELIVERY', 7, '0123456793', NOW(), NOW());

-- ==============================================
-- 14. ORDER ITEMS (Sample order items)
-- ==============================================

INSERT INTO order_item (id, order_id, product_variant_id, unit_price, quantity) VALUES
-- Order 1 items
(1, 1, 1, 299000, 2),  -- 2x Product 1 Size S
-- Order 2 items
(2, 2, 9, 199000, 3),  -- 3x Product 3 Size S
(3, 2, 13, 249000, 1), -- 1x Product 4 Size S
-- Order 3 items
(4, 3, 17, 599000, 1), -- 1x Product 5 Size S
(5, 3, 21, 399000, 1), -- 1x Product 6 Size S
-- Order 4 items
(6, 4, 25, 1299000, 1), -- 1x Product 7 Size S
(7, 4, 29, 1199000, 1), -- 1x Product 8 Size S
-- Order 5 items
(8, 5, 33, 349000, 1), -- 1x Product 9 Size S
(9, 5, 37, 229000, 1); -- 1x Product 10 Size S

-- ==============================================
-- 15. ORDER STATUS HISTORY (Sample status history)
-- ==============================================

INSERT INTO order_status_history (id, order_id, order_status, message, created_at) VALUES
-- Order 1 history
(1, 1, 'PENDING', 'Đơn hàng được tạo', NOW()),
(2, 1, 'PREPARING', 'Đơn hàng đang được chuẩn bị', NOW()),
(3, 1, 'SHIPPED', 'Đơn hàng đã được gửi', NOW()),
(4, 1, 'DELIVERED', 'Đơn hàng đã giao thành công', NOW()),
-- Order 2 history
(5, 2, 'PENDING', 'Đơn hàng được tạo', NOW()),
(6, 2, 'PREPARING', 'Đơn hàng đang được chuẩn bị', NOW()),
(7, 2, 'SHIPPED', 'Đơn hàng đã được gửi', NOW()),
-- Order 3 history
(8, 3, 'PENDING', 'Đơn hàng được tạo', NOW()),
-- Order 4 history
(9, 4, 'PENDING', 'Đơn hàng được tạo', NOW()),
(10, 4, 'PREPARING', 'Đơn hàng đang được chuẩn bị', NOW()),
-- Order 5 history
(11, 5, 'PENDING', 'Đơn hàng được tạo', NOW()),
(12, 5, 'PREPARING', 'Đơn hàng đang được chuẩn bị', NOW()),
(13, 5, 'SHIPPED', 'Đơn hàng đã được gửi', NOW()),
(14, 5, 'DELIVERED', 'Đơn hàng đã giao thành công', NOW());

-- ==============================================
-- 16. PAYMENTS (Sample payments)
-- ==============================================

INSERT INTO payments (id, order_id, payment_method, amount, payment_status, transaction_id, payload, create_at) VALUES
(1, 1, 'CASH_ON_DELIVERY', 598000, 'PAID', 'TXN-001', '{"method": "COD", "delivery_fee": 30000}', NOW()),
(2, 2, 'BANK_TRANSFER', 897000, 'PAID', 'TXN-002', '{"method": "BANK_TRANSFER", "bank": "Vietcombank"}', NOW()),
(3, 3, 'E_WALLET', 998000, 'PENDING', 'TXN-003', '{"method": "E_WALLET", "wallet": "MoMo"}', NOW()),
(4, 4, 'BANK_TRANSFER', 2198000, 'PAID', 'TXN-004', '{"method": "BANK_TRANSFER", "bank": "BIDV"}', NOW()),
(5, 5, 'CASH_ON_DELIVERY', 598000, 'PAID', 'TXN-005', '{"method": "COD", "delivery_fee": 30000}', NOW());

-- ==============================================
-- 17. SHIPMENTS (Sample shipments)
-- ==============================================

INSERT INTO shipments (id, order_id, carrier, tracking_number, status, shipped_at, expected_delivery_at, create_at) VALUES
(1, 1, 'Viettel Post', 'VT001234567', 'DELIVERED', NOW(), NOW(), NOW()),
(2, 2, 'Giao Hàng Nhanh', 'GHN001234567', 'IN_TRANSIT', NOW(), NOW() + INTERVAL 2 DAY, NOW()),
(3, 3, 'J&T Express', 'JT001234567', 'PENDING', NULL, NOW() + INTERVAL 3 DAY, NOW()),
(4, 4, 'Viettel Post', 'VT001234568', 'PICKED_UP', NOW(), NOW() + INTERVAL 2 DAY, NOW()),
(5, 5, 'Giao Hàng Nhanh', 'GHN001234568', 'DELIVERED', NOW(), NOW(), NOW());

-- ==============================================
-- 18. REVIEWS (Sample reviews)
-- ==============================================

INSERT INTO reviews (id, product_id, customer_id, order_item_id, rating, comment, create_at) VALUES
(1, 1, 'user-001', 1, 5, 'Sản phẩm chất lượng tốt, giao hàng nhanh', NOW()),
(2, 3, 'user-002', 2, 4, 'Áo thun đẹp, chất liệu mềm mại', NOW()),
(3, 5, 'user-003', 4, 5, 'Áo khoác bomber rất đẹp và ấm', NOW()),
(4, 7, 'user-004', 6, 5, 'Áo vest chất lượng cao, đáng giá', NOW()),
(5, 9, 'user-005', 8, 4, 'Áo sơ mi kẻ sọc đẹp, form chuẩn', NOW());

-- ==============================================
-- 19. CONVERSATIONS (Sample conversations)
-- ==============================================

INSERT INTO conversations (id, is_active, user_id) VALUES
(1, true, 'user-001'),
(2, true, 'user-002'),
(3, true, 'user-003'),
(4, true, 'user-004'),
(5, true, 'user-005');

-- ==============================================
-- 20. MESSAGES (Sample messages)
-- ==============================================

INSERT INTO messages (id, content, timestamp, sender_type, conversation_id) VALUES
(1, 'Xin chào, tôi muốn hỏi về sản phẩm áo sơ mi', NOW(), 'CUSTOMER', 1),
(2, 'Chào bạn! Tôi có thể giúp gì cho bạn về sản phẩm áo sơ mi?', NOW(), 'STAFF', 1),
(3, 'Sản phẩm này có size nào?', NOW(), 'CUSTOMER', 1),
(4, 'Sản phẩm có các size S, M, L, XL bạn nhé', NOW(), 'STAFF', 1),
(5, 'Cảm ơn bạn!', NOW(), 'CUSTOMER', 1),
(6, 'Tôi muốn đổi size sản phẩm', NOW(), 'CUSTOMER', 2),
(7, 'Bạn có thể đổi size trong vòng 7 ngày kể từ khi nhận hàng', NOW(), 'STAFF', 2),
(8, 'Làm sao để theo dõi đơn hàng?', NOW(), 'CUSTOMER', 3),
(9, 'Bạn có thể theo dõi đơn hàng qua mã tracking number', NOW(), 'STAFF', 3),
(10, 'Sản phẩm có được bảo hành không?', NOW(), 'CUSTOMER', 4),
(11, 'Sản phẩm được bảo hành 1 năm từ ngày mua', NOW(), 'STAFF', 4),
(12, 'Tôi muốn hủy đơn hàng', NOW(), 'CUSTOMER', 5),
(13, 'Bạn có thể hủy đơn hàng nếu chưa được xử lý', NOW(), 'STAFF', 5);

-- ==============================================
-- END OF SAMPLE DATA
-- ==============================================
