-- Sample Data for Oriental Fashion Shop Backend
-- This file contains test data for development and testing purposes

-- ==============================================
-- TABLE SCHEMA DEFINITIONS (Optional - for fresh database setup)
-- Note: These tables are normally created by Hibernate, but included here for reference
-- ==============================================

-- Promotion Tables (using VARCHAR for UUID support)
CREATE TABLE IF NOT EXISTS percent_promotion (
                                                 promotion_id VARCHAR(255) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(255) UNIQUE NOT NULL,
    min_order_value DECIMAL(19,2),
    effective DATETIME,
    expiration DATETIME,
    quantity INT,
    is_active BOOLEAN DEFAULT true,
    percent DOUBLE NOT NULL,
    max_discount DOUBLE NOT NULL,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS amount_promotion (
                                                promotion_id VARCHAR(255) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(255) UNIQUE NOT NULL,
    min_order_value DECIMAL(19,2),
    effective DATETIME,
    expiration DATETIME,
    quantity INT,
    is_active BOOLEAN DEFAULT true,
    discount DOUBLE NOT NULL,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP
    );

-- Brand Table
CREATE TABLE IF NOT EXISTS brands (
                                      id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                      name VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    image VARCHAR(500),
    active BOOLEAN DEFAULT true
    );

-- Category Table
CREATE TABLE IF NOT EXISTS categories (
                                          id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                          name VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT true,
    parent_id BIGINT,
    image VARCHAR(500),
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES categories(id)
    );

-- ==============================================
-- 1. PERMISSIONS
-- ==============================================

INSERT INTO permissions (id, name, description) VALUES
                                                    (1, 'USER_MANAGEMENT', 'Manage users'),
                                                    (2, 'PRODUCT_MANAGEMENT', 'Manage products'),
                                                    (3, 'ORDER_MANAGEMENT', 'Manage orders'),
                                                    (4, 'CUSTOMER_MANAGEMENT', 'Manage customers'),
                                                    (5, 'BRAND_MANAGEMENT', 'Manage brands'),
                                                    (6, 'CATEGORY_MANAGEMENT', 'Manage categories'),
                                                    (7, 'REVIEW_MANAGEMENT', 'Manage reviews'),
                                                    (8, 'PROMOTION_MANAGEMENT', 'Manage promotions');

-- Roles will be created in ApplicationInitConfig
-- Users will be created in ApplicationInitConfig

-- ==============================================
-- 2. BRANDS (15 brands)
-- ==============================================

INSERT INTO brands (id, name, description, image, active) VALUES
                                                              (1, 'Uniqlo', 'Japanese casual wear brand', 'https://upload.wikimedia.org/wikipedia/commons/thumb/9/92/UNIQLO_logo.svg/2058px-UNIQLO_logo.svg.png', true),
                                                              (2, 'Zara', 'Spanish fast fashion brand', 'https://logomakerr.ai/blog/wp-content/uploads/2022/08/2019-to-Present-Zara-logo-design.jpg', true),
                                                              (3, 'H&M', 'Swedish multinational clothing retailer', 'https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/H%26M-Logo.svg/1280px-H%26M-Logo.svg.png', true),
                                                              (4, 'Nike', 'American multinational sportswear brand', 'https://inkythuatso.com/uploads/images/2021/11/logo-nike-inkythuatso-2-01-04-15-42-44.jpg', true),
                                                              (5, 'Adidas', 'German multinational sportswear brand', 'https://www.citypng.com/public/uploads/preview/adidas-white-logo-hd-png-701751694777208ogwssxbgpj.png', true),
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
-- 3. CATEGORIES (ví dụ tên thật ngoài đời)
-- ==============================================

DELETE FROM categories;
INSERT INTO categories (id, name, is_active, parent_id) VALUES
                                                            (1, 'Đồ Nam', true, NULL),
                                                            (3, 'Quần Nam', true, 1),
                                                            (11, 'Quần Jeans Nam', true, 3),
                                                            (12, 'Quần Kaki Nam', true, 3),
                                                            (4, 'Áo Nam', true, 1),
                                                            (21, 'Áo Thun Nam', true, 4),
                                                            (22, 'Áo Sơ Mi Nam', true, 4),
                                                            (23, 'Áo Polo Nam', true, 4),
                                                            (24, 'Áo Khoác Nam', true, 4),
                                                            (5, 'Giày Nam', true, 1),
                                                            (31, 'Giày Sneaker Nam', true, 5),
                                                            (32, 'Giày Da Nam', true, 5),
                                                            (2, 'Đồ Nữ', true, NULL),
                                                            (6, 'Quần Nữ', true, 2),
                                                            (14, 'Quần Jeans Nữ', true, 6),
                                                            (15, 'Quần Legging Nữ', true, 6),
                                                            (7, 'Áo Nữ', true, 2),
                                                            (25, 'Áo Thun Nữ', true, 7),
                                                            (26, 'Áo Sơ Mi Nữ', true, 7),
                                                            (27, 'Đầm Nữ', true, 7),
                                                            (28, 'Áo Khoác Nữ', true, 7),
                                                            (8, 'Giày Nữ', true, 2),
                                                            (33, 'Giày Sneaker Nữ', true, 8),
                                                            (34, 'Sandal Nữ', true, 8),
                                                            (35, 'Boot Nữ', true, 8);

-- ==============================================
-- 4. PRODUCTS (tên và giá thật ngoài đời)
-- ==============================================
DELETE FROM products;
INSERT INTO products (id, name, brand_id, category_id, description, images, active, create_at, update_at) VALUES
                                                                                                              (1, 'Quần Jeans Nam Slimfit', 1, 11, 'Quần jeans slimfit Nam màu xanh, form Hàn Quốc', NULL, true, NOW(), NOW()),
                                                                                                              (2, 'Quần Jeans Nam Rách Gối', 2, 11, 'Quần jeans nam rách trẻ trung Hàn Quốc', NULL, true, NOW(), NOW()),
                                                                                                              (3, 'Quần Kaki Nam Basic', 3, 12, 'Quần kaki nam xanh rêu basic', NULL, true, NOW(), NOW()),
                                                                                                              (4, 'Quần Kaki Nam Ống Đứng', 3, 12, 'Quần kaki nam ống đứng lịch sự', NULL, true, NOW(), NOW()),
                                                                                                              (5, 'Áo Thun Nam Tay Ngắn Routine', 4, 21, 'Áo thun basic nam cotton Routine màu trắng', NULL, true, NOW(), NOW()),
                                                                                                              (6, 'Áo Thun Nam Local Brand', 5, 21, 'Áo thun local brand in hình Joker', NULL, true, NOW(), NOW()),
                                                                                                              (7, 'Áo Sơ Mi Nam Trắng Công Sở', 6, 22, 'Áo sơ mi trắng nam công sở classic', NULL, true, NOW(), NOW()),
                                                                                                              (8, 'Áo Sơ Mi Nam Kẻ Sọc', 6, 22, 'Áo sơ mi nam sọc xanh blue', NULL, true, NOW(), NOW()),
                                                                                                              (9, 'Áo Polo Nam Basic', 4, 23, 'Áo polo nam cổ bẻ màu xanh navy', NULL, true, NOW(), NOW()),
                                                                                                              (10, 'Áo Polo Nam Cafe', 4, 23, 'Áo polo nam phối bo café xám', NULL, true, NOW(), NOW()),
                                                                                                              (11, 'Áo Khoác Gió Nam Uniqlo', 7, 24, 'Áo khoác gió nam chống nắng Uniqlo', NULL, true, NOW(), NOW()),
                                                                                                              (12, 'Áo Khoác Bomber Nam', 8, 24, 'Áo khoác bomber khoá kéo đen', NULL, true, NOW(), NOW()),
                                                                                                              (13, 'Giày Sneaker Nam Adidas Stan Smith', 5, 31, 'Giày sneaker nam Adidas Stan Smith trắng xanh', NULL, true, NOW(), NOW()),
                                                                                                              (14, 'Giày Sneaker Nam Nike Air Force', 4, 31, 'Giày Nike Air Force 1 classic trắng', NULL, true, NOW(), NOW()),
                                                                                                              (15, 'Giày Da Nam Lười Routine', 8, 32, 'Giày da lười nam form chuẩn Routine', NULL, true, NOW(), NOW()),
                                                                                                              (16, 'Giày Da Nam Oxford', 8, 32, 'Giày da Oxford nam màu nâu', NULL, true, NOW(), NOW()),
                                                                                                              (17, 'Quần Jeans Nữ Bụi Phủi', 4, 14, 'Quần jeans nữ slimfit wash nhẹ', NULL, true, NOW(), NOW()),
                                                                                                              (18, 'Quần Jeans Nữ Ống Loe', 2, 14, 'Quần jeans nữ ống loe co giãn', NULL, true, NOW(), NOW()),
                                                                                                              (19, 'Quần Legging Nữ Thể Thao', 8, 15, 'Quần legging nữ thể thao gym yoga', NULL, true, NOW(), NOW()),
                                                                                                              (20, 'Quần Legging Nữ Cotton', 8, 15, 'Quần legging nữ cotton ống bó', NULL, true, NOW(), NOW()),
                                                                                                              (21, 'Áo Thun Nữ Cổ Tim', 9, 25, 'Áo thun nữ cổ tim, màu pastel hot trend', NULL, true, NOW(), NOW()),
                                                                                                              (22, 'Áo Thun Nữ Local Brand', 9, 25, 'Áo thun nữ local brand basic gradient', NULL, true, NOW(), NOW()),
                                                                                                              (23, 'Áo Sơ Mi Nữ Trắng Công Sở', 9, 26, 'Áo sơ mi nữ trắng vải lụa mềm', NULL, true, NOW(), NOW()),
                                                                                                              (24, 'Áo Sơ Mi Nữ Caro Nâu', 9, 26, 'Áo sơ mi caro nữ lụa màu nâu trẻ trung', NULL, true, NOW(), NOW()),
                                                                                                              (25, 'Đầm Nữ Dự Tiệc Trễ Vai', 8, 27, 'Đầm nữ dự tiệc màu hồng pastel trễ vai', NULL, true, NOW(), NOW()),
                                                                                                              (26, 'Đầm Nữ Bodycon HQ', 8, 27, 'Đầm bodycon HQ ôm body quyến rũ', NULL, true, NOW(), NOW()),
                                                                                                              (27, 'Áo Khoác Dù Nữ Croptop', 10, 28, 'Áo khoác dù nữ kiểu croptop năng động', NULL, true, NOW(), NOW()),
                                                                                                              (28, 'Áo Khoác Cardigan Nữ', 10, 28, 'Áo khoác cardigan form dài nữ', NULL, true, NOW(), NOW()),
                                                                                                              (29, 'Giày Sneaker Nữ Fila', 14, 33, 'Giày sneaker nữ Fila ánh kim', NULL, true, NOW(), NOW()),
                                                                                                              (30, 'Giày Sneaker Nữ MLB', 14, 33, 'Giày MLB nữ chunky màu pastel', NULL, true, NOW(), NOW()),
                                                                                                              (31, 'Sandal Nữ Quai Chéo', 14, 34, 'Sandal nữ dây chéo quai mềm', NULL, true, NOW(), NOW()),
                                                                                                              (32, 'Sandal Nữ Gót Vuông', 14, 34, 'Sandal gót vuông nữ cơ bản', NULL, true, NOW(), NOW()),
                                                                                                              (33, 'Boot Nữ Da Nâu', 14, 35, 'Boot da nữ màu nâu phối dây', NULL, true, NOW(), NOW()),
                                                                                                              (34, 'Boot Nữ Dr.Martens', 14, 35, 'Boot nữ dr.martens classic đen', NULL, true, NOW(), NOW()),
                                                                                                              (35, 'Quần Jeans Nữ Baggy', 8, 14, 'Quần jean nữ baggy form Hàn', NULL, true, NOW(), NOW()),
                                                                                                              (36, 'Quần Jeans Nữ Skinny', 8, 14, 'Quần jeans nữ skinny ôm bó', NULL, true, NOW(), NOW()),
                                                                                                              (37, 'Áo Thun Nam Croptop', 3, 21, 'Áo thun nam croptop street style', NULL, true, NOW(), NOW()),
                                                                                                              (38, 'Áo Sơ Mi Nam Linen', 5, 22, 'Áo sơ mi linen nam thoáng mát', NULL, true, NOW(), NOW()),
                                                                                                              (39, 'Áo Polo Nam Peaking', 4, 23, 'Áo polo nam phối viền peaking sang trọng', NULL, true, NOW(), NOW()),
                                                                                                              (40, 'Giày Sneaker Nam Bitis Hunter', 5, 31, 'Giày Bitis Hunter nam đỏ đen', NULL, true, NOW(), NOW()),
                                                                                                              (41, 'Giày Da Nam Lười Gola', 7, 32, 'Giày da lười nam Gola classic', NULL, true, NOW(), NOW()),
                                                                                                              (42, 'Quần Kaki Nam Co Giãn', 8, 12, 'Quần kaki denim nam co giãn tốt', NULL, true, NOW(), NOW()),
                                                                                                              (43, 'Đầm Nữ Babydoll', 9, 27, 'Đầm babydoll cute, voan mềm nhẹ', NULL, true, NOW(), NOW()),
                                                                                                              (44, 'Áo Khoác Jean Nữ', 10, 28, 'Áo khoác jean nữ cá tính form rộng', NULL, true, NOW(), NOW()),
                                                                                                              (45, 'Giày Sneaker Nữ Converse', 8, 33, 'Sneaker nữ Converse canvas cổ thấp', NULL, true, NOW(), NOW()),
                                                                                                              (46, 'Giày Sneaker Nữ Vans', 8, 33, 'Sneaker nữ Vans Old Skool', NULL, true, NOW(), NOW()),
                                                                                                              (47, 'Quần Legging Nữ Yoga', 6, 15, 'Quần legging nữ tập yoga màu nude', NULL, true, NOW(), NOW()),
                                                                                                              (48, 'Đầm Nữ Midi Hoa Nhí', 8, 27, 'Đầm midi hoa nhí nữ xinh', NULL, true, NOW(), NOW()),
                                                                                                              (49, 'Áo Thun Nữ UNIQLO AIRism', 5, 25, 'Áo thun nữ UNIQLO AIRism chống nóng', NULL, true, NOW(), NOW()),
                                                                                                              (50, 'Quần Kaki Nam Kẻ Caro', 6, 12, 'Quần Kaki Nam caro xám, trẻ trung', NULL, true, NOW(), NOW());

--
-- VARIANTS (4 biến thể mỗi sản phẩm x 50 = 200)
DELETE FROM product_variants;
INSERT INTO product_variants (id, product_id, size, color, image, price, discount, quantity, additional_price, create_at) VALUES
                                                                                                                              (1,1,'S','Đen',NULL,239000,0,20,0,NOW()),
                                                                                                                              (2,1,'M','Xanh',NULL,251000,0,20,0,NOW()),
                                                                                                                              (3,1,'L','Trắng',NULL,259000,0,20,0,NOW()),
                                                                                                                              (4,1,'XL','Đỏ',NULL,264000,0,20,0,NOW()),
                                                                                                                              (5,2,'S','Đen',NULL,289000,0,20,0,NOW()),
                                                                                                                              (6,2,'M','Xanh',NULL,301000,0,20,0,NOW()),
                                                                                                                              (7,2,'L','Trắng',NULL,309000,0,20,0,NOW()),
                                                                                                                              (8,2,'XL','Đỏ',NULL,314000,0,20,0,NOW()),
                                                                                                                              (9,3,'S','Đen',NULL,259000,0,20,0,NOW()),
                                                                                                                              (10,3,'M','Xanh',NULL,271000,0,20,0,NOW()),
                                                                                                                              (11,3,'L','Trắng',NULL,279000,0,20,0,NOW()),
                                                                                                                              (12,3,'XL','Đỏ',NULL,284000,0,20,0,NOW()),
                                                                                                                              (13,4,'S','Đen',NULL,333000,0,20,0,NOW()),
                                                                                                                              (14,4,'M','Xanh',NULL,345000,0,20,0,NOW()),
                                                                                                                              (15,4,'L','Trắng',NULL,353000,0,20,0,NOW()),
                                                                                                                              (16,4,'XL','Đỏ',NULL,358000,0,20,0,NOW()),
                                                                                                                              (17,5,'S','Đen',NULL,259000,0,20,0,NOW()),
                                                                                                                              (18,5,'M','Xanh',NULL,271000,0,20,0,NOW()),
                                                                                                                              (19,5,'L','Trắng',NULL,279000,0,20,0,NOW()),
                                                                                                                              (20,5,'XL','Đỏ',NULL,284000,0,20,0,NOW()),
                                                                                                                              (21,6,'S','Đen',NULL,219000,0,20,0,NOW()),
                                                                                                                              (22,6,'M','Xanh',NULL,231000,0,20,0,NOW()),
                                                                                                                              (23,6,'L','Trắng',NULL,239000,0,20,0,NOW()),
                                                                                                                              (24,6,'XL','Đỏ',NULL,244000,0,20,0,NOW()),
                                                                                                                              (25,7,'S','Đen',NULL,285000,0,20,0,NOW()),
                                                                                                                              (26,7,'M','Xanh',NULL,297000,0,20,0,NOW()),
                                                                                                                              (27,7,'L','Trắng',NULL,305000,0,20,0,NOW()),
                                                                                                                              (28,7,'XL','Đỏ',NULL,310000,0,20,0,NOW()),
                                                                                                                              (29,8,'S','Đen',NULL,255000,0,20,0,NOW()),
                                                                                                                              (30,8,'M','Xanh',NULL,267000,0,20,0,NOW()),
                                                                                                                              (31,8,'L','Trắng',NULL,275000,0,20,0,NOW()),
                                                                                                                              (32,8,'XL','Đỏ',NULL,280000,0,20,0,NOW()),
                                                                                                                              (33,9,'S','Đen',NULL,320000,0,20,0,NOW()),
                                                                                                                              (34,9,'M','Xanh',NULL,332000,0,20,0,NOW()),
                                                                                                                              (35,9,'L','Trắng',NULL,340000,0,20,0,NOW()),
                                                                                                                              (36,9,'XL','Đỏ',NULL,345000,0,20,0,NOW()),
                                                                                                                              (37,10,'S','Đen',NULL,299000,0,20,0,NOW()),
                                                                                                                              (38,10,'M','Xanh',NULL,311000,0,20,0,NOW()),
                                                                                                                              (39,10,'L','Trắng',NULL,319000,0,20,0,NOW()),
                                                                                                                              (40,10,'XL','Đỏ',NULL,324000,0,20,0,NOW()),
                                                                                                                              (41,11,'S','Đen',NULL,479000,0,20,0,NOW()),
                                                                                                                              (42,11,'M','Xanh',NULL,491000,0,20,0,NOW()),
                                                                                                                              (43,11,'L','Trắng',NULL,499000,0,20,0,NOW()),
                                                                                                                              (44,11,'XL','Đỏ',NULL,504000,0,20,0,NOW()),
                                                                                                                              (45,12,'S','Đen',NULL,375000,0,20,0,NOW()),
                                                                                                                              (46,12,'M','Xanh',NULL,387000,0,20,0,NOW()),
                                                                                                                              (47,12,'L','Trắng',NULL,395000,0,20,0,NOW()),
                                                                                                                              (48,12,'XL','Đỏ',NULL,400000,0,20,0,NOW()),
                                                                                                                              (49,13,'S','Đen',NULL,469000,0,20,0,NOW()),
                                                                                                                              (50,13,'M','Xanh',NULL,481000,0,20,0,NOW()),
                                                                                                                              (51,13,'L','Trắng',NULL,489000,0,20,0,NOW()),
                                                                                                                              (52,13,'XL','Đỏ',NULL,494000,0,20,0,NOW()),
                                                                                                                              (53,14,'S','Đen',NULL,429000,0,20,0,NOW()),
                                                                                                                              (54,14,'M','Xanh',NULL,441000,0,20,0,NOW()),
                                                                                                                              (55,14,'L','Trắng',NULL,449000,0,20,0,NOW()),
                                                                                                                              (56,14,'XL','Đỏ',NULL,454000,0,20,0,NOW()),
                                                                                                                              (57,15,'S','Đen',NULL,399000,0,20,0,NOW()),
                                                                                                                              (58,15,'M','Xanh',NULL,411000,0,20,0,NOW()),
                                                                                                                              (59,15,'L','Trắng',NULL,419000,0,20,0,NOW()),
                                                                                                                              (60,15,'XL','Đỏ',NULL,424000,0,20,0,NOW()),
                                                                                                                              (61,16,'S','Đen',NULL,314000,0,20,0,NOW()),
                                                                                                                              (62,16,'M','Xanh',NULL,326000,0,20,0,NOW()),
                                                                                                                              (63,16,'L','Trắng',NULL,334000,0,20,0,NOW()),
                                                                                                                              (64,16,'XL','Đỏ',NULL,339000,0,20,0,NOW()),
                                                                                                                              (65,17,'S','Đen',NULL,299000,0,20,0,NOW()),
                                                                                                                              (66,17,'M','Xanh',NULL,311000,0,20,0,NOW()),
                                                                                                                              (67,17,'L','Trắng',NULL,319000,0,20,0,NOW()),
                                                                                                                              (68,17,'XL','Đỏ',NULL,324000,0,20,0,NOW()),
                                                                                                                              (69,18,'S','Đen',NULL,279000,0,20,0,NOW()),
                                                                                                                              (70,18,'M','Xanh',NULL,291000,0,20,0,NOW()),
                                                                                                                              (71,18,'L','Trắng',NULL,299000,0,20,0,NOW()),
                                                                                                                              (72,18,'XL','Đỏ',NULL,304000,0,20,0,NOW()),
                                                                                                                              (73,19,'S','Đen',NULL,269000,0,20,0,NOW()),
                                                                                                                              (74,19,'M','Xanh',NULL,281000,0,20,0,NOW()),
                                                                                                                              (75,19,'L','Trắng',NULL,289000,0,20,0,NOW()),
                                                                                                                              (76,19,'XL','Đỏ',NULL,294000,0,20,0,NOW()),
                                                                                                                              (77,20,'S','Đen',NULL,319000,0,20,0,NOW()),
                                                                                                                              (78,20,'M','Xanh',NULL,331000,0,20,0,NOW()),
                                                                                                                              (79,20,'L','Trắng',NULL,339000,0,20,0,NOW()),
                                                                                                                              (80,20,'XL','Đỏ',NULL,344000,0,20,0,NOW()),
                                                                                                                              (81,21,'S','Đen',NULL,239000,0,20,0,NOW()),
                                                                                                                              (82,21,'M','Xanh',NULL,251000,0,20,0,NOW()),
                                                                                                                              (83,21,'L','Trắng',NULL,259000,0,20,0,NOW()),
                                                                                                                              (84,21,'XL','Đỏ',NULL,264000,0,20,0,NOW()),
                                                                                                                              (85,22,'S','Đen',NULL,299000,0,20,0,NOW()),
                                                                                                                              (86,22,'M','Xanh',NULL,311000,0,20,0,NOW()),
                                                                                                                              (87,22,'L','Trắng',NULL,319000,0,20,0,NOW()),
                                                                                                                              (88,22,'XL','Đỏ',NULL,324000,0,20,0,NOW()),
                                                                                                                              (89,23,'S','Đen',NULL,249000,0,20,0,NOW()),
                                                                                                                              (90,23,'M','Xanh',NULL,261000,0,20,0,NOW()),
                                                                                                                              (91,23,'L','Trắng',NULL,269000,0,20,0,NOW()),
                                                                                                                              (92,23,'XL','Đỏ',NULL,274000,0,20,0,NOW()),
                                                                                                                              (93,24,'S','Đen',NULL,285000,0,20,0,NOW()),
                                                                                                                              (94,24,'M','Xanh',NULL,297000,0,20,0,NOW()),
                                                                                                                              (95,24,'L','Trắng',NULL,305000,0,20,0,NOW()),
                                                                                                                              (96,24,'XL','Đỏ',NULL,310000,0,20,0,NOW()),
                                                                                                                              (97,25,'S','Đen',NULL,269000,0,20,0,NOW()),
                                                                                                                              (98,25,'M','Xanh',NULL,281000,0,20,0,NOW()),
                                                                                                                              (99,25,'L','Trắng',NULL,289000,0,20,0,NOW()),
                                                                                                                              (100,25,'XL','Đỏ',NULL,294000,0,20,0,NOW()),
                                                                                                                              (101,26,'S','Đen',NULL,399000,0,20,0,NOW()),
                                                                                                                              (102,26,'M','Xanh',NULL,411000,0,20,0,NOW()),
                                                                                                                              (103,26,'L','Trắng',NULL,419000,0,20,0,NOW()),
                                                                                                                              (104,26,'XL','Đỏ',NULL,424000,0,20,0,NOW()),
                                                                                                                              (105,27,'S','Đen',NULL,310000,0,20,0,NOW()),
                                                                                                                              (106,27,'M','Xanh',NULL,322000,0,20,0,NOW()),
                                                                                                                              (107,27,'L','Trắng',NULL,330000,0,20,0,NOW()),
                                                                                                                              (108,27,'XL','Đỏ',NULL,335000,0,20,0,NOW()),
                                                                                                                              (109,28,'S','Đen',NULL,306000,0,20,0,NOW()),
                                                                                                                              (110,28,'M','Xanh',NULL,318000,0,20,0,NOW()),
                                                                                                                              (111,28,'L','Trắng',NULL,326000,0,20,0,NOW()),
                                                                                                                              (112,28,'XL','Đỏ',NULL,331000,0,20,0,NOW()),
                                                                                                                              (113,29,'S','Đen',NULL,469000,0,20,0,NOW()),
                                                                                                                              (114,29,'M','Xanh',NULL,481000,0,20,0,NOW()),
                                                                                                                              (115,29,'L','Trắng',NULL,489000,0,20,0,NOW()),
                                                                                                                              (116,29,'XL','Đỏ',NULL,494000,0,20,0,NOW()),
                                                                                                                              (117,30,'S','Đen',NULL,429000,0,20,0,NOW()),
                                                                                                                              (118,30,'M','Xanh',NULL,441000,0,20,0,NOW()),
                                                                                                                              (119,30,'L','Trắng',NULL,449000,0,20,0,NOW()),
                                                                                                                              (120,30,'XL','Đỏ',NULL,454000,0,20,0,NOW()),
                                                                                                                              (121,31,'S','Đen',NULL,235000,0,20,0,NOW()),
                                                                                                                              (122,31,'M','Xanh',NULL,247000,0,20,0,NOW()),
                                                                                                                              (123,31,'L','Trắng',NULL,255000,0,20,0,NOW()),
                                                                                                                              (124,31,'XL','Đỏ',NULL,260000,0,20,0,NOW()),
                                                                                                                              (125,32,'S','Đen',NULL,295000,0,20,0,NOW()),
                                                                                                                              (126,32,'M','Xanh',NULL,307000,0,20,0,NOW()),
                                                                                                                              (127,32,'L','Trắng',NULL,315000,0,20,0,NOW()),
                                                                                                                              (128,32,'XL','Đỏ',NULL,320000,0,20,0,NOW()),
                                                                                                                              (129,33,'S','Đen',NULL,339000,0,20,0,NOW()),
                                                                                                                              (130,33,'M','Xanh',NULL,351000,0,20,0,NOW()),
                                                                                                                              (131,33,'L','Trắng',NULL,359000,0,20,0,NOW()),
                                                                                                                              (132,33,'XL','Đỏ',NULL,364000,0,20,0,NOW()),
                                                                                                                              (133,34,'S','Đen',NULL,265000,0,20,0,NOW()),
                                                                                                                              (134,34,'M','Xanh',NULL,277000,0,20,0,NOW()),
                                                                                                                              (135,34,'L','Trắng',NULL,285000,0,20,0,NOW()),
                                                                                                                              (136,34,'XL','Đỏ',NULL,290000,0,20,0,NOW()),
                                                                                                                              (137,35,'S','Đen',NULL,275000,0,20,0,NOW()),
                                                                                                                              (138,35,'M','Xanh',NULL,287000,0,20,0,NOW()),
                                                                                                                              (139,35,'L','Trắng',NULL,295000,0,20,0,NOW()),
                                                                                                                              (140,35,'XL','Đỏ',NULL,300000,0,20,0,NOW()),
                                                                                                                              (141,36,'S','Đen',NULL,259000,0,20,0,NOW()),
                                                                                                                              (142,36,'M','Xanh',NULL,271000,0,20,0,NOW()),
                                                                                                                              (143,36,'L','Trắng',NULL,279000,0,20,0,NOW()),
                                                                                                                              (144,36,'XL','Đỏ',NULL,284000,0,20,0,NOW()),
                                                                                                                              (145,37,'S','Đen',NULL,249000,0,20,0,NOW()),
                                                                                                                              (146,37,'M','Xanh',NULL,261000,0,20,0,NOW()),
                                                                                                                              (147,37,'L','Trắng',NULL,269000,0,20,0,NOW()),
                                                                                                                              (148,37,'XL','Đỏ',NULL,274000,0,20,0,NOW()),
                                                                                                                              (149,38,'S','Đen',NULL,317000,0,20,0,NOW()),
                                                                                                                              (150,38,'M','Xanh',NULL,329000,0,20,0,NOW()),
                                                                                                                              (151,38,'L','Trắng',NULL,337000,0,20,0,NOW()),
                                                                                                                              (152,38,'XL','Đỏ',NULL,342000,0,20,0,NOW()),
                                                                                                                              (153,39,'S','Đen',NULL,385000,0,20,0,NOW()),
                                                                                                                              (154,39,'M','Xanh',NULL,397000,0,20,0,NOW()),
                                                                                                                              (155,39,'L','Trắng',NULL,405000,0,20,0,NOW()),
                                                                                                                              (156,39,'XL','Đỏ',NULL,410000,0,20,0,NOW()),
                                                                                                                              (157,40,'S','Đen',NULL,399000,0,20,0,NOW()),
                                                                                                                              (158,40,'M','Xanh',NULL,411000,0,20,0,NOW()),
                                                                                                                              (159,40,'L','Trắng',NULL,419000,0,20,0,NOW()),
                                                                                                                              (160,40,'XL','Đỏ',NULL,424000,0,20,0,NOW()),
                                                                                                                              (161,41,'S','Đen',NULL,371000,0,20,0,NOW()),
                                                                                                                              (162,41,'M','Xanh',NULL,383000,0,20,0,NOW()),
                                                                                                                              (163,41,'L','Trắng',NULL,391000,0,20,0,NOW()),
                                                                                                                              (164,41,'XL','Đỏ',NULL,396000,0,20,0,NOW()),
                                                                                                                              (165,42,'S','Đen',NULL,259000,0,20,0,NOW()),
                                                                                                                              (166,42,'M','Xanh',NULL,271000,0,20,0,NOW()),
                                                                                                                              (167,42,'L','Trắng',NULL,279000,0,20,0,NOW()),
                                                                                                                              (168,42,'XL','Đỏ',NULL,284000,0,20,0,NOW()),
                                                                                                                              (169,43,'S','Đen',NULL,299000,0,20,0,NOW()),
                                                                                                                              (170,43,'M','Xanh',NULL,311000,0,20,0,NOW()),
                                                                                                                              (171,43,'L','Trắng',NULL,319000,0,20,0,NOW()),
                                                                                                                              (172,43,'XL','Đỏ',NULL,324000,0,20,0,NOW()),
                                                                                                                              (173,44,'S','Đen',NULL,259000,0,20,0,NOW()),
                                                                                                                              (174,44,'M','Xanh',NULL,271000,0,20,0,NOW()),
                                                                                                                              (175,44,'L','Trắng',NULL,279000,0,20,0,NOW()),
                                                                                                                              (176,44,'XL','Đỏ',NULL,284000,0,20,0,NOW()),
                                                                                                                              (177,45,'S','Đen',NULL,229000,0,20,0,NOW()),
                                                                                                                              (178,45,'M','Xanh',NULL,241000,0,20,0,NOW()),
                                                                                                                              (179,45,'L','Trắng',NULL,249000,0,20,0,NOW()),
                                                                                                                              (180,45,'XL','Đỏ',NULL,254000,0,20,0,NOW()),
                                                                                                                              (181,46,'S','Đen',NULL,310000,0,20,0,NOW()),
                                                                                                                              (182,46,'M','Xanh',NULL,322000,0,20,0,NOW()),
                                                                                                                              (183,46,'L','Trắng',NULL,330000,0,20,0,NOW()),
                                                                                                                              (184,46,'XL','Đỏ',NULL,335000,0,20,0,NOW()),
                                                                                                                              (185,47,'S','Đen',NULL,219000,0,20,0,NOW()),
                                                                                                                              (186,47,'M','Xanh',NULL,231000,0,20,0,NOW()),
                                                                                                                              (187,47,'L','Trắng',NULL,239000,0,20,0,NOW()),
                                                                                                                              (188,47,'XL','Đỏ',NULL,244000,0,20,0,NOW()),
                                                                                                                              (189,48,'S','Đen',NULL,320000,0,20,0,NOW()),
                                                                                                                              (190,48,'M','Xanh',NULL,332000,0,20,0,NOW()),
                                                                                                                              (191,48,'L','Trắng',NULL,340000,0,20,0,NOW()),
                                                                                                                              (192,48,'XL','Đỏ',NULL,345000,0,20,0,NOW()),
                                                                                                                              (193,49,'S','Đen',NULL,410000,0,20,0,NOW()),
                                                                                                                              (194,49,'M','Xanh',NULL,422000,0,20,0,NOW()),
                                                                                                                              (195,49,'L','Trắng',NULL,430000,0,20,0,NOW()),
                                                                                                                              (196,49,'XL','Đỏ',NULL,435000,0,20,0,NOW()),
                                                                                                                              (197,50,'S','Đen',NULL,310000,0,20,0,NOW()),
                                                                                                                              (198,50,'M','Xanh',NULL,322000,0,20,0,NOW()),
                                                                                                                              (199,50,'L','Trắng',NULL,330000,0,20,0,NOW()),
                                                                                                                              (200,50,'XL','Đỏ',NULL,335000,0,20,0,NOW());

--
-- VARIANTS (ảnh từng biến thể)
UPDATE product_variants SET image = 'https://vulcano.vn/media/catalog/product/cache/4867bdff41445f787f46fb72bd107e23/q/u/quan-jeans-nam-cao-cap-den-tron-3004b-base.jpg' WHERE id = 1;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10002/600/600' WHERE id = 2;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10003/600/600' WHERE id = 3;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10004/600/600' WHERE id = 4;
UPDATE product_variants SET image = 'https://thoitrangngaynay.com/upload/images/Quan-jean-nam-rach-goi-co-gian-ca-tinh-phong-cach-han-quoc%20(1)(1).jpg' WHERE id = 5;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10006/600/600' WHERE id = 6;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10007/600/600' WHERE id = 7;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10008/600/600' WHERE id = 8;
UPDATE product_variants SET image = 'https://down-vn.img.susercontent.com/file/4f859bf07261b415aad209f721cda36e_tn.webp' WHERE id = 9;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10010/600/600' WHERE id = 10;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10011/600/600' WHERE id = 11;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10012/600/600' WHERE id = 12;
UPDATE product_variants SET image = 'https://lh5.googleusercontent.com/FFQLZntUSI1E6z29Ht5rXAmkkEDZ5WQ1QoEBrT4sM1IvE_yP_lLiz98f7o-qnJSMaxpUfIPr5IaQV16g5848m_1mCrvy4dSKtsQMHKZ76Ijp6Jc3TZje3edlGlNszlHzhkQnZyRvbDvq0Tm-pWnUpQ' WHERE id = 13;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10014/600/600' WHERE id = 14;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10015/600/600' WHERE id = 15;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10016/600/600' WHERE id = 16;
UPDATE product_variants SET image = 'https://cavathanquoc.com/wp-content/uploads/2024/06/Ao-thun-tron-cotton-mau-den.jpg' WHERE id = 17;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10018/600/600' WHERE id = 18;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10019/600/600' WHERE id = 19;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10020/600/600' WHERE id = 20;
-- ... sinh đầy đủ tới id = 200 ...

-- GIỮ PHẦN PHÍA SAU (order, review... giữ nguyên)




-- =====================================================
-- TEST DATA FOR PROMOTIONS
-- Tạo dữ liệu test cho khuyến mãi
-- =====================================================

-- Xóa dữ liệu cũ (nếu có)
DELETE FROM percent_promotion;
DELETE FROM amount_promotion;

-- Reset AUTO_INCREMENT
-- Note: TABLE_PER_CLASS kh�ng s? d?ng AUTO_INCREMENT
-- Note: TABLE_PER_CLASS kh�ng s? d?ng AUTO_INCREMENT

-- =====================================================
-- PERCENTAGE PROMOTIONS (Khuyến mãi theo phần trăm)
-- =====================================================

INSERT INTO percent_promotion (promotion_id, name, code, percent, max_discount, min_order_value, effective, expiration, quantity, is_active, create_at) VALUES
-- 1. Khuyến mãi chào mừng (Đang hoạt động)
(1, 'Chào mừng thành viên mới', 'WELCOME10', 10.00, 100000.00, 0.00, '2025-01-01 00:00:00', '2025-12-31 23:59:59', 1000, TRUE, NOW()),

-- 2. Khuyến mãi Tết (Đang hoạt động)
(2, 'Khuyến mãi Tết Nguyên Đán', 'TET2025', 20.00, 200000.00, 500000.00, '2025-01-15 00:00:00', '2025-02-15 23:59:59', 500, TRUE, NOW()),

-- 3. Khuyến mãi sinh nhật (Đang hoạt động)
(3, 'Sale sinh nhật thương hiệu', 'BIRTHDAY15', 15.00, 150000.00, 300000.00, '2025-01-01 00:00:00', '2025-03-31 23:59:59', 300, TRUE, NOW()),

-- 4. Flash sale cuối tuần (Đang hoạt động)
(4, 'Flash Sale Thứ 7 Chủ Nhật', 'WEEKEND25', 25.00, 250000.00, 800000.00, '2025-10-25 00:00:00', '2025-11-30 23:59:59', 200, TRUE, NOW()),

-- 5. Khuyến mãi Black Friday (Chưa bắt đầu)
(5, 'Black Friday Sale', 'BLACKFRIDAY30', 30.00, 300000.00, 1000000.00, '2025-11-25 00:00:00', '2025-11-30 23:59:59', 100, TRUE, NOW()),

-- 6. Giảm giá học sinh sinh viên (Đang hoạt động)
(6, 'Ưu đãi học sinh sinh viên', 'STUDENT12', 12.00, 120000.00, 200000.00, '2025-09-01 00:00:00', '2025-12-31 23:59:59', 500, TRUE, NOW()),

-- 7. Sale mùa hè (Đã hết hạn)
(7, 'Khuyến mãi mùa hè', 'SUMMER20', 20.00, 200000.00, 400000.00, '2024-06-01 00:00:00', '2024-08-31 23:59:59', 300, FALSE, '2024-06-01 00:00:00'),

-- 8. VIP member discount (Đang hoạt động)
(8, 'Ưu đãi thành viên VIP', 'VIP18', 18.00, 180000.00, 1500000.00, '2025-01-01 00:00:00', '2025-12-31 23:59:59', 50, TRUE, NOW()),

-- 9. Khuyến mãi giữa năm (Đã hết hạn)
(9, 'Sale giữa năm', 'MIDYEAR15', 15.00, 150000.00, 350000.00, '2024-06-01 00:00:00', '2024-07-31 23:59:59', 250, FALSE, '2024-06-01 00:00:00'),

-- 10. Khuyến mãi đầu mùa (Đang hoạt động)
(10, 'Đón đầu mùa thời trang mới', 'NEWSEASON22', 22.00, 220000.00, 600000.00, '2025-10-01 00:00:00', '2025-11-30 23:59:59', 400, TRUE, NOW());

-- =====================================================
-- AMOUNT PROMOTIONS (Khuyến mãi giá cố định)
-- =====================================================

INSERT INTO amount_promotion (promotion_id, name, code, discount, min_order_value, effective, expiration, quantity, is_active, create_at) VALUES
-- 1. Giảm giá cho đơn hàng đầu tiên
(11, 'Giảm giá đơn hàng đầu tiên', 'FIRST50K', 50000.00, 200000.00, '2025-01-01 00:00:00', '2025-12-31 23:59:59', 1000, TRUE, NOW()),

-- 2. Voucher Tết
(12, 'Voucher Tết may mắn', 'LUCKYTET100K', 100000.00, 500000.00, '2025-01-15 00:00:00', '2025-02-15 23:59:59', 500, TRUE, NOW()),

-- 3. Giảm giá sinh nhật
(13, 'Mừng sinh nhật khách hàng', 'BDAY80K', 80000.00, 400000.00, '2025-01-01 00:00:00', '2025-03-31 23:59:59', 300, TRUE, NOW()),

-- 4. Flash sale giờ vàng
(14, 'Flash Sale Giờ Vàng', 'GOLDHOUR150K', 150000.00, 1000000.00, '2025-10-25 00:00:00', '2025-11-30 23:59:59', 100, TRUE, NOW()),

-- 5. Siêu sale cuối năm (Chưa bắt đầu)
(15, 'Siêu Sale Cuối Năm', 'YEAREND200K', 200000.00, 1500000.00, '2025-12-15 00:00:00', '2025-12-31 23:59:59', 200, TRUE, NOW()),

-- 6. Ưu đãi thành viên mới
(16, 'Chào mừng thành viên mới', 'NEWMEM30K', 30000.00, 150000.00, '2025-09-01 00:00:00', '2025-12-31 23:59:59', 800, TRUE, NOW()),

-- 7. Giảm giá mùa đông (Đã hết hạn)
(17, 'Ấm áp mùa đông', 'WINTER70K', 70000.00, 350000.00, '2024-11-01 00:00:00', '2024-12-31 23:59:59', 400, FALSE, '2024-11-01 00:00:00'),

-- 8. Mega sale
(18, 'Mega Sale Đặc Biệt', 'MEGA250K', 250000.00, 2000000.00, '2025-01-01 00:00:00', '2025-12-31 23:59:59', 50, TRUE, NOW()),

-- 9. Sale hè (Đã hết hạn)
(19, 'Hè sôi động', 'SUMMER60K', 60000.00, 300000.00, '2024-06-01 00:00:00', '2024-08-31 23:59:59', 350, FALSE, '2024-06-01 00:00:00'),

-- 10. Ưu đãi mua sắm trực tuyến
(20, 'Mua Online Giảm Ngay', 'ONLINE120K', 120000.00, 700000.00, '2025-10-01 00:00:00', '2025-11-30 23:59:59', 600, TRUE, NOW());

-- =====================================================
-- VERIFY DATA
-- =====================================================

-- Kiểm tra dữ liệu đã insert
SELECT COUNT(*) as 'Tổng Percentage Promotions' FROM percent_promotion;
SELECT COUNT(*) as 'Tổng Amount Promotions' FROM amount_promotion;

-- Xem chi tiết
SELECT promotion_id, name, code, percent, max_discount, is_active, effective, expiration FROM percent_promotion ORDER BY promotion_id;
SELECT promotion_id, name, code, discount, is_active, effective, expiration FROM amount_promotion ORDER BY promotion_id;

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

-- =====================================================
-- TEST DATA FOR PROMOTIONS
-- Tạo dữ liệu test cho khuyến mãi
-- =====================================================

-- Xóa dữ liệu cũ (nếu có)
DELETE FROM percent_promotion;
DELETE FROM amount_promotion;

-- Reset AUTO_INCREMENT
-- Note: TABLE_PER_CLASS kh�ng s? d?ng AUTO_INCREMENT
-- Note: TABLE_PER_CLASS kh�ng s? d?ng AUTO_INCREMENT

-- =====================================================
-- PERCENTAGE PROMOTIONS (Khuyến mãi theo phần trăm)
-- =====================================================

INSERT INTO percent_promotion (promotion_id, name, code, percent, max_discount, min_order_value, effective, expiration, quantity, is_active, create_at) VALUES
-- 1. Khuyến mãi chào mừng (Đang hoạt động)
(1, 'Chào mừng thành viên mới', 'WELCOME10', 10.00, 100000.00, 0.00, '2025-01-01 00:00:00', '2025-12-31 23:59:59', 1000, TRUE, NOW()),

-- 2. Khuyến mãi Tết (Đang hoạt động)
(2, 'Khuyến mãi Tết Nguyên Đán', 'TET2025', 20.00, 200000.00, 500000.00, '2025-01-15 00:00:00', '2025-02-15 23:59:59', 500, TRUE, NOW()),

-- 3. Khuyến mãi sinh nhật (Đang hoạt động)
(3, 'Sale sinh nhật thương hiệu', 'BIRTHDAY15', 15.00, 150000.00, 300000.00, '2025-01-01 00:00:00', '2025-03-31 23:59:59', 300, TRUE, NOW()),

-- 4. Flash sale cuối tuần (Đang hoạt động)
(4, 'Flash Sale Thứ 7 Chủ Nhật', 'WEEKEND25', 25.00, 250000.00, 800000.00, '2025-10-25 00:00:00', '2025-11-30 23:59:59', 200, TRUE, NOW()),

-- 5. Khuyến mãi Black Friday (Chưa bắt đầu)
(5, 'Black Friday Sale', 'BLACKFRIDAY30', 30.00, 300000.00, 1000000.00, '2025-11-25 00:00:00', '2025-11-30 23:59:59', 100, TRUE, NOW()),

-- 6. Giảm giá học sinh sinh viên (Đang hoạt động)
(6, 'Ưu đãi học sinh sinh viên', 'STUDENT12', 12.00, 120000.00, 200000.00, '2025-09-01 00:00:00', '2025-12-31 23:59:59', 500, TRUE, NOW()),

-- 7. Sale mùa hè (Đã hết hạn)
(7, 'Khuyến mãi mùa hè', 'SUMMER20', 20.00, 200000.00, 400000.00, '2024-06-01 00:00:00', '2024-08-31 23:59:59', 300, FALSE, '2024-06-01 00:00:00'),

-- 8. VIP member discount (Đang hoạt động)
(8, 'Ưu đãi thành viên VIP', 'VIP18', 18.00, 180000.00, 1500000.00, '2025-01-01 00:00:00', '2025-12-31 23:59:59', 50, TRUE, NOW()),

-- 9. Khuyến mãi giữa năm (Đã hết hạn)
(9, 'Sale giữa năm', 'MIDYEAR15', 15.00, 150000.00, 350000.00, '2024-06-01 00:00:00', '2024-07-31 23:59:59', 250, FALSE, '2024-06-01 00:00:00'),

-- 10. Khuyến mãi đầu mùa (Đang hoạt động)
(10, 'Đón đầu mùa thời trang mới', 'NEWSEASON22', 22.00, 220000.00, 600000.00, '2025-10-01 00:00:00', '2025-11-30 23:59:59', 400, TRUE, NOW());

-- =====================================================
-- AMOUNT PROMOTIONS (Khuyến mãi giá cố định)
-- =====================================================

INSERT INTO amount_promotion (promotion_id, name, code, discount, min_order_value, effective, expiration, quantity, is_active, create_at) VALUES
-- 1. Giảm giá cho đơn hàng đầu tiên
(11, 'Giảm giá đơn hàng đầu tiên', 'FIRST50K', 50000.00, 200000.00, '2025-01-01 00:00:00', '2025-12-31 23:59:59', 1000, TRUE, NOW()),

-- 2. Voucher Tết
(12, 'Voucher Tết may mắn', 'LUCKYTET100K', 100000.00, 500000.00, '2025-01-15 00:00:00', '2025-02-15 23:59:59', 500, TRUE, NOW()),

-- 3. Giảm giá sinh nhật
(13, 'Mừng sinh nhật khách hàng', 'BDAY80K', 80000.00, 400000.00, '2025-01-01 00:00:00', '2025-03-31 23:59:59', 300, TRUE, NOW()),

-- 4. Flash sale giờ vàng
(14, 'Flash Sale Giờ Vàng', 'GOLDHOUR150K', 150000.00, 1000000.00, '2025-10-25 00:00:00', '2025-11-30 23:59:59', 100, TRUE, NOW()),

-- 5. Siêu sale cuối năm (Chưa bắt đầu)
(15, 'Siêu Sale Cuối Năm', 'YEAREND200K', 200000.00, 1500000.00, '2025-12-15 00:00:00', '2025-12-31 23:59:59', 200, TRUE, NOW()),

-- 6. Ưu đãi thành viên mới
(16, 'Chào mừng thành viên mới', 'NEWMEM30K', 30000.00, 150000.00, '2025-09-01 00:00:00', '2025-12-31 23:59:59', 800, TRUE, NOW()),

-- 7. Giảm giá mùa đông (Đã hết hạn)
(17, 'Ấm áp mùa đông', 'WINTER70K', 70000.00, 350000.00, '2024-11-01 00:00:00', '2024-12-31 23:59:59', 400, FALSE, '2024-11-01 00:00:00'),

-- 8. Mega sale
(18, 'Mega Sale Đặc Biệt', 'MEGA250K', 250000.00, 2000000.00, '2025-01-01 00:00:00', '2025-12-31 23:59:59', 50, TRUE, NOW()),

-- 9. Sale hè (Đã hết hạn)
(19, 'Hè sôi động', 'SUMMER60K', 60000.00, 300000.00, '2024-06-01 00:00:00', '2024-08-31 23:59:59', 350, FALSE, '2024-06-01 00:00:00'),

-- 10. Ưu đãi mua sắm trực tuyến
(20, 'Mua Online Giảm Ngay', 'ONLINE120K', 120000.00, 700000.00, '2025-10-01 00:00:00', '2025-11-30 23:59:59', 600, TRUE, NOW());

-- =====================================================
-- VERIFY DATA
-- =====================================================

-- Kiểm tra dữ liệu đã insert
SELECT COUNT(*) as 'Tổng Percentage Promotions' FROM percent_promotion;
SELECT COUNT(*) as 'Tổng Amount Promotions' FROM amount_promotion;

-- Xem chi tiết
SELECT promotion_id, name, code, percent, max_discount, is_active, effective, expiration FROM percent_promotion ORDER BY promotion_id;
SELECT promotion_id, name, code, discount, is_active, effective, expiration FROM amount_promotion ORDER BY promotion_id;

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

-- =====================================================
-- TEST DATA FOR PROMOTIONS
-- Tạo dữ liệu test cho khuyến mãi
-- =====================================================

-- Xóa dữ liệu cũ (nếu có)
DELETE FROM percent_promotion;
DELETE FROM amount_promotion;

-- Reset AUTO_INCREMENT
-- Note: TABLE_PER_CLASS kh�ng s? d?ng AUTO_INCREMENT
-- Note: TABLE_PER_CLASS kh�ng s? d?ng AUTO_INCREMENT

-- =====================================================
-- PERCENTAGE PROMOTIONS (Khuyến mãi theo phần trăm)
-- =====================================================

INSERT INTO percent_promotion (promotion_id, name, code, percent, max_discount, min_order_value, effective, expiration, quantity, is_active, create_at) VALUES
-- 1. Khuyến mãi chào mừng (Đang hoạt động)
(1, 'Chào mừng thành viên mới', 'WELCOME10', 10.00, 100000.00, 0.00, '2025-01-01 00:00:00', '2025-12-31 23:59:59', 1000, TRUE, NOW()),

-- 2. Khuyến mãi Tết (Đang hoạt động)
(2, 'Khuyến mãi Tết Nguyên Đán', 'TET2025', 20.00, 200000.00, 500000.00, '2025-01-15 00:00:00', '2025-02-15 23:59:59', 500, TRUE, NOW()),

-- 3. Khuyến mãi sinh nhật (Đang hoạt động)
(3, 'Sale sinh nhật thương hiệu', 'BIRTHDAY15', 15.00, 150000.00, 300000.00, '2025-01-01 00:00:00', '2025-03-31 23:59:59', 300, TRUE, NOW()),

-- 4. Flash sale cuối tuần (Đang hoạt động)
(4, 'Flash Sale Thứ 7 Chủ Nhật', 'WEEKEND25', 25.00, 250000.00, 800000.00, '2025-10-25 00:00:00', '2025-11-30 23:59:59', 200, TRUE, NOW()),

-- 5. Khuyến mãi Black Friday (Chưa bắt đầu)
(5, 'Black Friday Sale', 'BLACKFRIDAY30', 30.00, 300000.00, 1000000.00, '2025-11-25 00:00:00', '2025-11-30 23:59:59', 100, TRUE, NOW()),

-- 6. Giảm giá học sinh sinh viên (Đang hoạt động)
(6, 'Ưu đãi học sinh sinh viên', 'STUDENT12', 12.00, 120000.00, 200000.00, '2025-09-01 00:00:00', '2025-12-31 23:59:59', 500, TRUE, NOW()),

-- 7. Sale mùa hè (Đã hết hạn)
(7, 'Khuyến mãi mùa hè', 'SUMMER20', 20.00, 200000.00, 400000.00, '2024-06-01 00:00:00', '2024-08-31 23:59:59', 300, FALSE, '2024-06-01 00:00:00'),

-- 8. VIP member discount (Đang hoạt động)
(8, 'Ưu đãi thành viên VIP', 'VIP18', 18.00, 180000.00, 1500000.00, '2025-01-01 00:00:00', '2025-12-31 23:59:59', 50, TRUE, NOW()),

-- 9. Khuyến mãi giữa năm (Đã hết hạn)
(9, 'Sale giữa năm', 'MIDYEAR15', 15.00, 150000.00, 350000.00, '2024-06-01 00:00:00', '2024-07-31 23:59:59', 250, FALSE, '2024-06-01 00:00:00'),

-- 10. Khuyến mãi đầu mùa (Đang hoạt động)
(10, 'Đón đầu mùa thời trang mới', 'NEWSEASON22', 22.00, 220000.00, 600000.00, '2025-10-01 00:00:00', '2025-11-30 23:59:59', 400, TRUE, NOW());

-- =====================================================
-- AMOUNT PROMOTIONS (Khuyến mãi giá cố định)
-- =====================================================

INSERT INTO amount_promotion (promotion_id, name, code, discount, min_order_value, effective, expiration, quantity, is_active, create_at) VALUES
-- 1. Giảm giá cho đơn hàng đầu tiên
(11, 'Giảm giá đơn hàng đầu tiên', 'FIRST50K', 50000.00, 200000.00, '2025-01-01 00:00:00', '2025-12-31 23:59:59', 1000, TRUE, NOW()),

-- 2. Voucher Tết
(12, 'Voucher Tết may mắn', 'LUCKYTET100K', 100000.00, 500000.00, '2025-01-15 00:00:00', '2025-02-15 23:59:59', 500, TRUE, NOW()),

-- 3. Giảm giá sinh nhật
(13, 'Mừng sinh nhật khách hàng', 'BDAY80K', 80000.00, 400000.00, '2025-01-01 00:00:00', '2025-03-31 23:59:59', 300, TRUE, NOW()),

-- 4. Flash sale giờ vàng
(14, 'Flash Sale Giờ Vàng', 'GOLDHOUR150K', 150000.00, 1000000.00, '2025-10-25 00:00:00', '2025-11-30 23:59:59', 100, TRUE, NOW()),

-- 5. Siêu sale cuối năm (Chưa bắt đầu)
(15, 'Siêu Sale Cuối Năm', 'YEAREND200K', 200000.00, 1500000.00, '2025-12-15 00:00:00', '2025-12-31 23:59:59', 200, TRUE, NOW()),

-- 6. Ưu đãi thành viên mới
(16, 'Chào mừng thành viên mới', 'NEWMEM30K', 30000.00, 150000.00, '2025-09-01 00:00:00', '2025-12-31 23:59:59', 800, TRUE, NOW()),

-- 7. Giảm giá mùa đông (Đã hết hạn)
(17, 'Ấm áp mùa đông', 'WINTER70K', 70000.00, 350000.00, '2024-11-01 00:00:00', '2024-12-31 23:59:59', 400, FALSE, '2024-11-01 00:00:00'),

-- 8. Mega sale
(18, 'Mega Sale Đặc Biệt', 'MEGA250K', 250000.00, 2000000.00, '2025-01-01 00:00:00', '2025-12-31 23:59:59', 50, TRUE, NOW()),

-- 9. Sale hè (Đã hết hạn)
(19, 'Hè sôi động', 'SUMMER60K', 60000.00, 300000.00, '2024-06-01 00:00:00', '2024-08-31 23:59:59', 350, FALSE, '2024-06-01 00:00:00'),

-- 10. Ưu đãi mua sắm trực tuyến
(20, 'Mua Online Giảm Ngay', 'ONLINE120K', 120000.00, 700000.00, '2025-10-01 00:00:00', '2025-11-30 23:59:59', 600, TRUE, NOW());

-- =====================================================
-- VERIFY DATA
-- =====================================================

-- Kiểm tra dữ liệu đã insert
SELECT COUNT(*) as 'Tổng Percentage Promotions' FROM percent_promotion;
SELECT COUNT(*) as 'Tổng Amount Promotions' FROM amount_promotion;

-- Xem chi tiết
SELECT promotion_id, name, code, percent, max_discount, is_active, effective, expiration FROM percent_promotion ORDER BY promotion_id;
SELECT promotion_id, name, code, discount, is_active, effective, expiration FROM amount_promotion ORDER BY promotion_id;

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

-- ==============================================
-- Update ALL images: products, variants, categories, users
-- Environment: MySQL (uses CRC32, IF NOT EXISTS, etc.)
-- ==============================================

-- ========== PRODUCTS (Picsum 800x1000) ==========
-- Begin: product images
-- (Generated previously by fetch_product_images.py)
-- You can regenerate via FE/ModaMint/scraper/fetch_product_images.py

-- START product images
-- Script to update product images from Picsum Photos
-- Generated automatically by fetch_product_images.py
-- Images format: url1|url2|url3|url4 (using | delimiter)
-- Image size: 800x1000px

-- Áo Sơ Mi Trắng Nam
UPDATE products SET images = 'https://lados.vn/wp-content/uploads/2024/07/1-702A-LD4111-600x600.jpg|https://4menshop.com/cache/image/300x400/images/thumbs/2025/10/quan-jean-dieu-tui-sau-form-slimfit-qj117_small-19477.jpg|https://product.hstatic.net/1000096703/product/28_abd1ec085f23444388eb3a609e4644bb_master.jpg
' WHERE id = 1;

-- Áo Sơ Mi Xanh Nam
UPDATE products SET images = 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRukuLjOAGLkYNB2Ao3HzoINNWEUT0elh5pAQ&s|https://thoitrangngaynay.com/upload/images/Quan-jean-nam-rach-goi-co-gian-ca-tinh-phong-cach-han-quoc%20(1)(1).jpg|https://product.hstatic.net/1000205116/product/z2413572794821_91fe5147a037fb5f19cccbce96279e32_f1c4850b0f494927acae25d0c6ca200d_1024x1024.jpg
' WHERE id = 2;

-- Áo Thun Basic Nam
UPDATE products SET images = 'https://product.hstatic.net/200000564155/product/mf6563_a339a6e1800a47fa956abce979c67f15_71022f63a7934e69bd1042d0b791056f.jpg|https://product.hstatic.net/200000564155/product/mf6277_39b02bc5374f49499e7fd9324be0445f_8e0e06daf4f04c08b6df7230c499b858.jpg|https://down-vn.img.susercontent.com/file/4f859bf07261b415aad209f721cda36e_tn.webp
' WHERE id = 3;

-- Áo Thun Polo Nam
UPDATE products SET images = 'https://product.hstatic.net/1000304105/product/quan-dai-nam-kaki-bnh1__3__c5a899f274a54f12a8b7c626bb0f9d5f_1e264c9550de43e587d84bcc904e143b.jpg|https://product.hstatic.net/1000304105/product/quan-dai-nam-bda1__6__95cdced7e2d24cd1b74e6e247a6fc3e2_53316bce0c554abb827a4fdf46761c82.jpg|https://lh5.googleusercontent.com/FFQLZntUSI1E6z29Ht5rXAmkkEDZ5WQ1QoEBrT4sM1IvE_yP_lLiz98f7o-qnJSMaxpUfIPr5IaQV16g5848m_1mCrvy4dSKtsQMHKZ76Ijp6Jc3TZje3edlGlNszlHzhkQnZyRvbDvq0Tm-pWnUpQ
' WHERE id = 4;

-- Áo Khoác Bomber Nam
UPDATE products SET images = 'https://media.routine.vn/1200x1500/prod/variant/19s25tss053-white-1-jpg-0y10.webp|https://media.routine.vn/1200x1500/prod/variant/10s25tss046-snow-white-1-jpg-r09m.webp|https://media.routine.vn/1200x1500/prod/media/10f24tss011-silver-cloud-ao-thun-nam-tay-ngan-1-jpg-6f26.webp
' WHERE id = 5;

-- Áo Khoác Hoodie Nam
UPDATE products SET images = 'https://down-vn.img.susercontent.com/file/vn-11134201-7r98o-lwzbeojovy5n68_tn.webp|https://merchie.vn/wp-content/uploads/2019/09/Joker-tee-front-web.png|https://wibu.com.vn/wp-content/uploads/2024/11/ao-thun-wibu-comics-Joker-2.jpg
' WHERE id = 6;

-- Áo Vest Nam
UPDATE products SET images = 'https://product.hstatic.net/200000588671/product/ao-so-mi-nam-tay-dai-cong-so-bamboo-mau-trang-2_cea1ab4240d048cd9a379aacae3b541b.jpg
' WHERE id = 7;

-- Áo Vest Kẻ Sọc Nam
UPDATE products SET images = 'https://cdn.kkfashion.vn/26336-large_default/ao-so-mi-nu-tay-dai-hoa-tiet-soc-trang-den-asm14-25.jpg|https://cdn.kkfashion.vn/26337-large_default/ao-so-mi-nu-tay-dai-hoa-tiet-soc-trang-den-asm14-25.jpg|https://cdn.kkfashion.vn/26335-large_default/ao-so-mi-nu-tay-dai-hoa-tiet-soc-trang-den-asm14-25.jpg' WHERE id = 8;

-- Áo Sơ Mi Kẻ Sọc Nam
UPDATE products SET images = 'https://product.hstatic.net/1000369857/product/ao_thun_ppl32_0006_layer_11_2f9b935e3b904eb39ed6c08ca476cf6f_large.jpg|https://www.chapi.vn/img/product/2023/3/7/ao-thun-polo-nam-ngan-tay-vien-co-hpn-9-new.jpg|https://media.routine.vn/1600x1200/prod/variant/10f25pol016-navy-0-jpg-ppvw.webp' WHERE id = 9;

-- Áo Thun Graphic Nam
UPDATE products SET images = 'https://buggy.yodycdn.com/images/product/1f86f634390067541a9959c3c74c7e97.webp?width=987&height=1316' WHERE id = 10;

-- Áo Khoác Denim Nam
UPDATE products SET images = 'https://product.hstatic.net/1000209952/product/z4617663729772_d6f95769e39dc18960feedec31c67c95_acc5fe0932c4494bad93ea59e7b3541e_large.jpg' WHERE id = 11;

-- Áo Khoác Blazer Nam
UPDATE products SET images = 'https://bizweb.dktcdn.net/100/287/440/products/ao-khoac-bomber-jaket-thun-xop-mau-den-chu-theu-phoi-da-khoa-keo-kim-loai-3.jpg?v=1737631242547' WHERE id = 12;

-- Áo Sơ Mi Flannel Nam
UPDATE products SET images = 'https://myshoes.vn/image/catalog/2024/adidas/ad4/giay-adidas-stan-smith-trang-nam-trang-xanh-la-01.jpg|https://kiza.vn/media/catalog/product/cache/1/image/650x/040ec09b1e35df139433887a97daa66f/a/d/adidas_stan_smith_sf_05.jpg|https://myshoes.vn/image/cache/data/adidas%202019/22.3.19/giay-adidas-stan-smith-trang-01-600x315w.jpg' WHERE id = 13;

-- Áo Thun Tank Top Nam
UPDATE products SET images = 'https://static.nike.com/a/images/t_web_pdp_936_v2/f_auto/b7d9211c-26e7-431a-ac24-b0540fb3c00f/AIR+FORCE+1+%2707.png|https://trungsneaker.com/wp-content/uploads/2023/02/giay-nike-air-force-1-low-pearl-white-dq0231-100-38.jpeg|https://sneakerholicvietnam.vn/wp-content/uploads/2021/07/nike-air-force-1-shadow-all-white-ci0919-100-1.jpg' WHERE id = 14;

-- Áo Vest 3 Mảnh Nam
UPDATE products SET images = 'https://giaynam.com/images/upload/giay-luoi-nam-da-mem-em-ai-gl8001n%20(3).jpg|https://timan.vn/upload/products/062024/giay-luoi-nam-tmrk52-cao-cap-da-that.jpg|https://timan.vn/upload/products/062024/giay-luoi-nam-tmgl124-sang-trong.jpg' WHERE id = 15;

-- Áo Sơ Mi Trắng Nữ
UPDATE products SET images = 'https://fttleather.com/uploads/1026/product/2023/05/15/oxford-ff-1684164247.jpg|https://www.gento.vn/wp-content/uploads/2020/07/9607345286_1641340755.jpeg|https://fttleather.com/uploads/1026/product/2024-05-11/vdr02890-medium-4627.webp' WHERE id = 16;

-- Áo Sơ Mi Hoa Nữ
UPDATE products SET images = 'https://bizweb.dktcdn.net/100/041/510/files/quan-jean-nu-bui-rach-ong-suong-tb1111-4.jpg?v=1574406936293|https://media3.scdn.vn/img3/2019/11_12/UGuItR_simg_d0daf0_800x1200_max.jpg|https://media3.scdn.vn/img3/2019/11_12/HQ69As_simg_d0daf0_800x1200_max.jpg' WHERE id = 17;

-- Áo Thun Basic Nữ
UPDATE products SET images = 'https://bizweb.dktcdn.net/100/484/513/products/sapo.jpg?v=1708154691063|https://owlbrand.vn/wp-content/uploads/2025/05/WMS-FLARE-JEANS-INDIGO-TRC.jpg|https://pos.nvncdn.com/312d68-57499/art/artCT/20210704_MAb0U90wB1AtCoMLjNNmCLsZ.jpg' WHERE id = 18;

-- Áo Thun Crop Top Nữ
UPDATE products SET images = 'https://bizweb.dktcdn.net/100/455/994/products/27.jpg?v=1700122319803|https://www.osea.vn/data/bt6/quan-tap-yoga-gym-dai-lung-cao-den-black-classic-sport-leggings-5_1695740625.png|https://fsfcenter.vn/upload/sanpham/large/set-do-tap-gym-yoga-mitao-nhun-cao-cap-016-1668667636-db7c96.jpg' WHERE id = 19;

-- Áo Khoác Cardigan Nữ
UPDATE products SET images = 'https://down-vn.img.susercontent.com/file/b52c5e57484517b087cad9f2a0abd979|https://img.lazcdn.com/g/p/99ea37cedc5eb9360b3d682e85dba9b3.png_720x720q80.png|https://salt.tikicdn.com/ts/product/4c/0e/86/23bafcd52a663c861f847d373e756755.jpg' WHERE id = 20;

-- Áo Khoác Blazer Nữ
UPDATE products SET images = 'https://bizweb.dktcdn.net/thumb/1024x1024/100/366/703/products/11a.jpg?v=1748320431777|https://bizweb.dktcdn.net/100/441/136/products/20211200-rhino-half-0954.jpg?v=1687937264270|https://ann.com.vn/wp-content/uploads/23893_z5326772835970-cd1663899f8230ecdd7c501c4e97815e_20240408084943-1.jpg' WHERE id = 21;

-- Đầm Dài Nữ
UPDATE products SET images = 'https://bizweb.dktcdn.net/100/287/440/products/ao-thun-local-brand-nu-dep-re-cotton.jpg?v=1614574058953|https://bizweb.dktcdn.net/100/287/440/products/ao-thun-local-brand-nu-dep-mau-trang-4-c206cee3-4f8d-4c67-ad89-a54d58489e8d.jpg?v=1640678769450|https://bizweb.dktcdn.net/100/287/440/files/ao-thun-nu-mau-trang.jpg?v=1629191619636' WHERE id = 22;

-- Đầm Ngắn Nữ
UPDATE products SET images = 'https://dongphucphuquy.com/wp-content/uploads/2025/09/dong-phuc-cong-so-pq01.jpg|https://dongphuctanbinh.com/wp-content/uploads/2019/04/ao-so-mi-nu-cong-so-25229j.jpg|https://down-vn.img.susercontent.com/file/9b0f59dd65e354a6ac242819fc969d88_tn.webp' WHERE id = 23;

-- Áo Sơ Mi Oversize Nữ
UPDATE products SET images = 'https://sakurafashion.vn/upload/sanpham/large/8144-ao-so-mi-nu-soc-caro-lot-nhung-10.jpg|https://img.alicdn.com/img/ibank/2020/798/410/23269014897_1168542733.jpg|https://img.lazcdn.com/g/p/5ccaa60fce63f62c33271de3757234b6.jpg_720x720q80.jpg' WHERE id = 24;

-- Áo Thun Long Sleeve Nữ
UPDATE products SET images = 'https://picsum.photos/seed/6250/800/1000|https://picsum.photos/seed/6251/800/1000|https://picsum.photos/seed/6252/800/1000|https://picsum.photos/seed/6253/800/1000' WHERE id = 25;

-- Áo Khoác Hoodie Nữ
UPDATE products SET images = 'https://picsum.photos/seed/7260/800/1000|https://picsum.photos/seed/7261/800/1000|https://picsum.photos/seed/7262/800/1000|https://picsum.photos/seed/7263/800/1000' WHERE id = 26;

-- Áo Khoác Bomber Nữ
UPDATE products SET images = 'https://img.lazcdn.com/g/p/e2a7a97a21490be2610312c5adece4fb.jpg_720x720q80.jpg|https://vn-live-01.slatic.net/p/8da4d785548292b3744516400adabd30.jpg|https://vn-live-01.slatic.net/p/5b608ccccb5f422cf86eec33b013c8a4.jpg' WHERE id = 27;

-- Đầm Maxi Nữ
UPDATE products SET images = 'https://sakurafashion.vn/upload/sanpham/large/1782360964-ao-khoac-len-cardigan-nu-5.jpg|https://bizweb.dktcdn.net/100/502/737/products/o1cn01fszvdz1j9lg8uagh01660954.jpg?v=1729064091053|https://bizweb.dktcdn.net/100/329/681/products/2d4d45d6ca59e872ccc283bc09c2c9d0.jpg?v=1679908645070' WHERE id = 28;

-- Đầm Mini Nữ
UPDATE products SET images = 'https://cdn.vuahanghieu.com/unsafe/0x900/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2022/10/giay-the-thao-fila-women-s-disruptor-2-premium-mau-trang-size-38-5-6343ed6020897-10102022170104.jpg|https://neverstopshop.com/image/catalog/Fila-Disruptor-2/Giay-Fila-Disruptor-2-White-nam-nu-sfake-replica-gia-re-HCM-FL004-1.jpg|https://cdn.shopify.com/s/files/1/0456/5070/6581/files/size-giay-fila_600x600.jpg?v=1671518681' WHERE id = 29;

-- Áo Sơ Mi Silk Nữ
UPDATE products SET images = 'https://product.hstatic.net/200000642007/product/50crs_3ashbna3n_2_0abb0e4c9fde477d847f778989dfeac8_38285b60786248cea6c40156e446fb87_master.jpg|https://cdn.vuahanghieu.com/unsafe/0x900/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2020/03/giay-sneaker-mlb-big-ball-chunky-a-new-york-yankees-mau-trang-logo-den-size-270-5e82c7db6ac8f-31032020113227.jpg|https://bizweb.dktcdn.net/thumb/1024x1024/100/331/646/products/32shc1011-50b-2.jpg?v=1632814926893' WHERE id = 30;

-- Quần Jeans Nam
UPDATE products SET images = 'https://picsum.photos/seed/9310/800/1000|https://picsum.photos/seed/9311/800/1000|https://picsum.photos/seed/9312/800/1000|https://picsum.photos/seed/9313/800/1000' WHERE id = 31;

-- Quần Jeans Đen Nam
UPDATE products SET images = 'https://picsum.photos/seed/9320/800/1000|https://picsum.photos/seed/9321/800/1000|https://picsum.photos/seed/9322/800/1000|https://picsum.photos/seed/9323/800/1000' WHERE id = 32;

-- Quần Kaki Nam
UPDATE products SET images = 'https://picsum.photos/seed/10330/800/1000|https://picsum.photos/seed/10331/800/1000|https://picsum.photos/seed/10332/800/1000|https://picsum.photos/seed/10333/800/1000' WHERE id = 33;

-- Quần Kaki Xanh Nam
UPDATE products SET images = 'https://picsum.photos/seed/10340/800/1000|https://picsum.photos/seed/10341/800/1000|https://picsum.photos/seed/10342/800/1000|https://picsum.photos/seed/10343/800/1000' WHERE id = 34;

-- Quần Short Nam
UPDATE products SET images = 'https://product.hstatic.net/1000304105/product/quan-dai-baggy-xch3-tq104j8527__6__dba94a7c5a3243a88e6859cfe6ac5f04.jpg|https://sakurafashion.vn/upload/sanpham/large/1579919509-quan-baggy-jean-nu-theu-hinh-chu-meo-them-ca-3.jpg|https://media.routine.vn/1200x1500/prod/media/10f24dpaw011-l-indigo-quan-jean-nu-1-jpg-azc0.webp' WHERE id = 35;

-- Quần Short Thể Thao Nam
UPDATE products SET images = 'https://buggy.yodycdn.com/images/product/c73bf294662e7a5c4903855edafe40fc.webp?width=987&height=1316|https://product.hstatic.net/1000402464/product/fwjn22ss09c_blue__1__9a951be7d0f7423aab2f359cc8de51d1_master.jpg|https://js0fpsb45jobj.vcdn.cloud/storage/upload/media/gumac/QJD09041/3-XANHDAM-QJD09041.jpg' WHERE id = 36;

-- Quần Jeans Skinny Nam
UPDATE products SET images = 'https://down-vn.img.susercontent.com/file/vn-11134201-7ras8-matr0k143ngs20|https://file.hstatic.net/200000503583/file/ao-croptop-nam__8__fa284bd9790045fd9fd4578d094f4784.jpg|https://m.yodycdn.com/blog/cach-phoi-do-voi-ao-croptop-nam-yodyvn6.jpg' WHERE id = 37;

-- Quần Kaki Cargo Nam
UPDATE products SET images = 'https://s3.ap-southeast-1.amazonaws.com/thegmen.vn/2024/5/17148247853043elbci.jpg|https://4menshop.com/images/thumbs/2019/07/ao-so-mi-linen-reu-asm1268-10868.JPG|https://product.hstatic.net/1000366086/product/smm218-k_3__9a079cdfd5b54ab5bad084cc3f2d8fd2_8f985bb651494133938964c62d1e9bce.jpg' WHERE id = 38;

-- Quần Short Bermuda Nam
UPDATE products SET images = 'https://milvus.com.vn/wp-content/uploads/2023/08/MVP391MBR30-1.jpg|https://milvus.com.vn/wp-content/uploads/2023/08/MVP391MBR34.jpg|https://milvus.com.vn/wp-content/uploads/2024/09/M-8.png' WHERE id = 39;

-- Quần Jeans Ripped Nam
UPDATE products SET images = 'https://product.hstatic.net/1000221074/product/screenshot_1710150863_ebc819997d5e44b8b4f3719999a59b2b.png|https://vimmart.com/cdn/shop/files/HSM008000DENlogo_1.jpg?v=1722269425|https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR88fNlIGR41I1xb6-p8cwk0qKBjEJXN1HEHw&s' WHERE id = 40;

-- Quần Jeans Nữ
UPDATE products SET images = 'https://cdn-img-v2.mybota.vn/uploadv2/web/55/5544/product/2017/09/03/12/27/1504441458_gola-classics-mens-bullet-suede-4---copy.jpg|https://cdn-img-v2.mybota.vn/uploadv2/web/55/5544/product/2017/09/03/12/27/1504441466_gola-classics-mens-varsity-trainer-2.jpg|https://cdn-img-v2.mybota.vn/uploadv2/web/55/5544/product/2017/09/03/12/27/1504441462_gola-classics-mens-specialist-trainer-4.jpg' WHERE id = 41;

-- Quần Jeans Đen Nữ
UPDATE products SET images = 'https://img.lazcdn.com/g/p/1f5c616cea515a45b7537fe5df09f8aa.jpg_720x720q80.jpg|https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMKS6otBvDCym_7auafqG-nIV8z4xbJwxnVfYj2PdTsQ&s|https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTTFY5pXnpRQwujfG_AObRlJ-qoUeQ4aPd509GL10tEDQ&s' WHERE id = 42;

-- Quần Legging Nữ
UPDATE products SET images = 'https://picsum.photos/seed/13430/800/1000|https://picsum.photos/seed/13431/800/1000|https://picsum.photos/seed/13432/800/1000|https://picsum.photos/seed/13433/800/1000' WHERE id = 43;

-- Quần Legging Thể Thao Nữ
UPDATE products SET images = 'https://bizweb.dktcdn.net/thumb/1024x1024/100/403/511/products/o1cn01mtxhxm1cqcxkxcpir3025193-60cb6c01-21a8-4c83-95e0-dea408f580a9.jpg|https://vn-test-11.slatic.net/p/f2ec186be4b5ace4604c5812ab53a502.jpg|https://www.zstyle.vn/hoanghung/33/images/jean-phong.jpg' WHERE id = 44;

-- Quần Short Nữ
UPDATE products SET images = 'https://product.hstatic.net/1000284478/product/0000_black_m9166c_1_a6b4fa2c00da4a27bc383825cd544619.jpg|https://pos.nvncdn.com/be5dfe-25943/art/artCT/20210329_LgABrqlwx9PJt0g9mQj6OKhL.jpg|https://img.lazcdn.com/g/ff/kf/S2b74e53cad6946a2b4a7d876f564a0ech.jpg_720x720q80.jpg' WHERE id = 45;

-- Quần Short Denim Nữ
UPDATE products SET images = 'https://bizweb.dktcdn.net/thumb/large/100/140/774/products/vans-old-skool-black-white-vn000d3hy28-1.jpg?v=1661757331440|https://saigonsneaker.com/wp-content/uploads/2022/10/giay-vans-5.jpg|https://bizweb.dktcdn.net/100/140/774/products/vans-old-skool-black-white-vn000d3hy28-2.jpg?v=1625905148527' WHERE id = 46;

-- Quần Jeans Skinny Nữ
UPDATE products SET images = 'https://www.osea.vn/data/bt6/quan-tap-yoga-gym-dai-lung-cao-den-black-classic-sport-leggings-1695740620.png|https://icado.vn/wp-content/uploads/2023/09/quan-dai-tap-gym-yoga-nu-basic-icado-at1-1.png|https://icado.vn/wp-content/uploads/2025/10/quan-lung-tap-gym-yoga-nu-tron-icado-ql1-11-400x500.jpg' WHERE id = 47;

-- Quần Legging Yoga Nữ
UPDATE products SET images = 'https://picsum.photos/seed/13480/800/1000|https://picsum.photos/seed/13481/800/1000|https://picsum.photos/seed/13482/800/1000|https://picsum.photos/seed/13483/800/1000' WHERE id = 48;

-- Quần Short High Waist Nữ
UPDATE products SET images = 'https://www.uniqlo.com/vn/vi/feature/airism/img/women/product_unt.jpg?20200129|https://image.uniqlo.com/UQ/ST3/vn/imagesgoods/465755/item/vngoods_00_465755_3x4.jpg?width=494|https://image.uniqlo.com/UQ/ST3/AsianCommon/imagesgoods/472740/item/goods_00_472740_3x4.jpg?width=494' WHERE id = 49;

-- Quần Jeans Mom Fit Nữ
UPDATE products SET images = 'https://bizweb.dktcdn.net/thumb/large/100/477/848/products/o1cn01gh3mvx1vkef6dm4ne-973676210-0-cib-1684143300875.jpg?v=1684143306763|https://cdn.santino.com.vn/storage/upload/news/2023/09/quan-au-ke-caro-nam-07.jpg|https://pubcdn.ivymoda.com/files/news/2023/11/16/67b30a4fa5d1c016fd04f3107cd55758.jpg' WHERE id = 50;
-- END product images

-- ========== PRODUCT VARIANTS (Picsum 600x600) ==========
-- Begin: product variant images
-- (Generated previously by fetch_product_images.py)

-- START variant images
-- Script to update product_variants images from Picsum Photos
-- Generated automatically by fetch_product_images.py
-- Image size: 600x600px

UPDATE product_variants SET image = 'https://pubcdn.ivymoda.com/files/news/2023/10/27/c284d544a99b014031efd25821f60441.jpg' WHERE id = 2;
UPDATE product_variants SET image = 'https://4men.com.vn/thumbs/2023/06/quan-jean-trang-tron-qj025-34262-p.jpg' WHERE id = 3;
UPDATE product_variants SET image = 'https://product.hstatic.net/1000210295/product/z6734345038648_017afebac757079bd2dd96cd3051b6c4_6c7d4db5e3324c87aadd4bc17905a901_master.jpg' WHERE id = 4;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15206/600/600' WHERE id = 6;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15207/600/600' WHERE id = 7;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15208/600/600' WHERE id = 8;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15310/600/600' WHERE id = 10;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15311/600/600' WHERE id = 11;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15312/600/600' WHERE id = 12;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15414/600/600' WHERE id = 14;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15415/600/600' WHERE id = 15;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15416/600/600' WHERE id = 16;
UPDATE product_variants SET image = 'https://cavathanquoc.com/wp-content/uploads/2024/06/Ao-thun-tron-cotton-mau-xanh-da-troi-4.jpg' WHERE id = 18;
UPDATE product_variants SET image = 'https://media.loveitopcdn.com/853/ao-thun-dui-nam-coc-tay-trang-3.png' WHERE id = 19;
UPDATE product_variants SET image = 'https://contents.mediadecathlon.com/p2606947/k$1c9e0ffdefc3e67bdeabc82be7893e93/%C3%A1o-thun-ch%E1%BA%A1y-b%E1%BB%99-nam-thoa%CC%81ng-khi%CC%81-100-dry-%C4%91%E1%BB%8F-decathlon-8771124.jpg?f=1920x0&format=auto' WHERE id = 20;
UPDATE product_variants SET image = 'https://cavathanquoc.com/wp-content/uploads/2024/06/Ao-thun-tron-cotton-mau-xanh-da-troi-4.jpg' WHERE id = 22;
UPDATE product_variants SET image = 'https://product.hstatic.net/1000332761/product/z4332485822491_373bad858a93470669998e296e72cf2f_1414519fcc074b5e9909da9c45939726_master.jpg' WHERE id = 23;
UPDATE product_variants SET image = 'https://contents.mediadecathlon.com/p2606947/k$1c9e0ffdefc3e67bdeabc82be7893e93/%C3%A1o-thun-ch%E1%BA%A1y-b%E1%BB%99-nam-thoa%CC%81ng-khi%CC%81-100-dry-%C4%91%E1%BB%8F-decathlon-8771124.jpg?f=1920x0&format=auto' WHERE id = 24;
UPDATE product_variants SET image = 'https://picsum.photos/seed/4726/600/600' WHERE id = 26;
UPDATE product_variants SET image = 'https://picsum.photos/seed/4727/600/600' WHERE id = 27;
UPDATE product_variants SET image = 'https://picsum.photos/seed/4728/600/600' WHERE id = 28;
UPDATE product_variants SET image = 'https://benryhomme.com/upload/image/so-mi-2018-phat/k8-1.jpg' WHERE id = 30;
UPDATE product_variants SET image = 'https://product.hstatic.net/200000588671/product/ao-so-mi-nam-bycotton-trang-art-nhan_8ec622a241ea4deb93a02bdbdcb87954.jpg' WHERE id = 31;
UPDATE product_variants SET image = 'https://4menshop.com/images/thumbs/2023/01/ao-so-mi-regular-tron-tay-dai-sm088-mau-do-do-16898-slide-products-63b5553e5ee4d.jpg' WHERE id = 32;
UPDATE product_variants SET image = 'https://cdn.santino.com.vn/storage/upload/products/2023/11/ao-polo-nam-mau-xanh-duong-cao-cap-01.webp' WHERE id = 34;
UPDATE product_variants SET image = 'https://s3.ap-southeast-1.amazonaws.com/thegmen.vn/2024/6/1718594041420354v78.jpg' WHERE id = 35;
UPDATE product_variants SET image = 'https://pos.nvncdn.com/a36e05-151378/ps/20230704_qogec26HkH.jpeg?v=1688435457' WHERE id = 36;
UPDATE product_variants SET image = 'https://picsum.photos/seed/3038/600/600' WHERE id = 38;
UPDATE product_variants SET image = 'https://picsum.photos/seed/3039/600/600' WHERE id = 39;
UPDATE product_variants SET image = 'https://picsum.photos/seed/3040/600/600' WHERE id = 40;
UPDATE product_variants SET image = 'https://product.hstatic.net/1000209952/product/z4617663729772_d6f95769e39dc18960feedec31c67c95_acc5fe0932c4494bad93ea59e7b3541e_large.jpg' WHERE id = 41;
UPDATE product_variants SET image = 'https://picsum.photos/seed/4142/600/600' WHERE id = 42;
UPDATE product_variants SET image = 'https://picsum.photos/seed/4143/600/600' WHERE id = 43;
UPDATE product_variants SET image = 'https://picsum.photos/seed/4144/600/600' WHERE id = 44;
UPDATE product_variants SET image = 'https://bizweb.dktcdn.net/100/287/440/products/ao-khoac-bomber-jaket-thun-xop-mau-den-chu-theu-phoi-da-khoa-keo-kim-loai-3.jpg?v=1737631242547' WHERE id = 45;
UPDATE product_variants SET image = 'https://picsum.photos/seed/4246/600/600' WHERE id = 46;
UPDATE product_variants SET image = 'https://picsum.photos/seed/4247/600/600' WHERE id = 47;
UPDATE product_variants SET image = 'https://picsum.photos/seed/4248/600/600' WHERE id = 48;
UPDATE product_variants SET image = 'https://kiza.vn/media/catalog/product/cache/1/image/650x/040ec09b1e35df139433887a97daa66f/a/d/adidas_stan_smith_sf_05.jpg' WHERE id = 49;
UPDATE product_variants SET image = 'https://picsum.photos/seed/2350/600/600' WHERE id = 50;
UPDATE product_variants SET image = 'https://picsum.photos/seed/2351/600/600' WHERE id = 51;
UPDATE product_variants SET image = 'https://picsum.photos/seed/2352/600/600' WHERE id = 52;
UPDATE product_variants SET image = 'https://trungsneaker.com/wp-content/uploads/2023/02/giay-nike-air-force-1-low-pearl-white-dq0231-100-38.jpeg' WHERE id = 53;
UPDATE product_variants SET image = 'https://picsum.photos/seed/3454/600/600' WHERE id = 54;
UPDATE product_variants SET image = 'https://picsum.photos/seed/3455/600/600' WHERE id = 55;
UPDATE product_variants SET image = 'https://picsum.photos/seed/3456/600/600' WHERE id = 56;
UPDATE product_variants SET image = 'https://giaynam.com/images/upload/giay-luoi-nam-da-mem-em-ai-gl8001n%20(3).jpg' WHERE id = 57;
UPDATE product_variants SET image = 'https://picsum.photos/seed/5558/600/600' WHERE id = 58;
UPDATE product_variants SET image = 'https://picsum.photos/seed/5559/600/600' WHERE id = 59;
UPDATE product_variants SET image = 'https://picsum.photos/seed/5560/600/600' WHERE id = 60;
UPDATE product_variants SET image = 'https://fttleather.com/uploads/1026/product/2023/05/15/oxford-ff-1684164247.jpg' WHERE id = 61;
UPDATE product_variants SET image = 'https://picsum.photos/seed/6662/600/600' WHERE id = 62;
UPDATE product_variants SET image = 'https://picsum.photos/seed/6663/600/600' WHERE id = 63;
UPDATE product_variants SET image = 'https://picsum.photos/seed/6664/600/600' WHERE id = 64;
UPDATE product_variants SET image = 'https://media3.scdn.vn/img3/2019/11_12/HQ69As_simg_d0daf0_800x1200_max.jpg' WHERE id = 65;
UPDATE product_variants SET image = 'https://picsum.photos/seed/6766/600/600' WHERE id = 66;
UPDATE product_variants SET image = 'https://picsum.photos/seed/6767/600/600' WHERE id = 67;
UPDATE product_variants SET image = 'https://picsum.photos/seed/6768/600/600' WHERE id = 68;
UPDATE product_variants SET image = 'https://owlbrand.vn/wp-content/uploads/2025/05/WMS-FLARE-JEANS-INDIGO-TRC.jpg' WHERE id = 69;
UPDATE product_variants SET image = 'https://picsum.photos/seed/7870/600/600' WHERE id = 70;
UPDATE product_variants SET image = 'https://picsum.photos/seed/7871/600/600' WHERE id = 71;
UPDATE product_variants SET image = 'https://picsum.photos/seed/7872/600/600' WHERE id = 72;
UPDATE product_variants SET image = 'https://fsfcenter.vn/upload/sanpham/large/set-do-tap-gym-yoga-mitao-nhun-cao-cap-016-1668667636-db7c96.jpg' WHERE id = 73;
UPDATE product_variants SET image = 'https://picsum.photos/seed/7974/600/600' WHERE id = 74;
UPDATE product_variants SET image = 'https://picsum.photos/seed/7975/600/600' WHERE id = 75;
UPDATE product_variants SET image = 'https://picsum.photos/seed/7976/600/600' WHERE id = 76;
UPDATE product_variants SET image = 'https://img.lazcdn.com/g/p/99ea37cedc5eb9360b3d682e85dba9b3.png_720x720q80.png' WHERE id = 77;
UPDATE product_variants SET image = 'https://picsum.photos/seed/9078/600/600' WHERE id = 78;
UPDATE product_variants SET image = 'https://picsum.photos/seed/9079/600/600' WHERE id = 79;
UPDATE product_variants SET image = 'https://picsum.photos/seed/9080/600/600' WHERE id = 80;
UPDATE product_variants SET image = 'https://ann.com.vn/wp-content/uploads/23893_z5326772835970-cd1663899f8230ecdd7c501c4e97815e_20240408084943-1.jpg' WHERE id = 81;
UPDATE product_variants SET image = 'https://picsum.photos/seed/9182/600/600' WHERE id = 82;
UPDATE product_variants SET image = 'https://picsum.photos/seed/9183/600/600' WHERE id = 83;
UPDATE product_variants SET image = 'https://picsum.photos/seed/9184/600/600' WHERE id = 84;
UPDATE product_variants SET image = 'https://bizweb.dktcdn.net/100/287/440/products/ao-thun-local-brand-nu-dep-mau-trang-4-c206cee3-4f8d-4c67-ad89-a54d58489e8d.jpg?v=1640678769450' WHERE id = 85;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10286/600/600' WHERE id = 86;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10287/600/600' WHERE id = 87;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10288/600/600' WHERE id = 88;
UPDATE product_variants SET image = 'https://down-vn.img.susercontent.com/file/9b0f59dd65e354a6ac242819fc969d88_tn.webp' WHERE id = 89;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10390/600/600' WHERE id = 90;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10391/600/600' WHERE id = 91;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10392/600/600' WHERE id = 92;
UPDATE product_variants SET image = 'https://img.lazcdn.com/g/p/5ccaa60fce63f62c33271de3757234b6.jpg_720x720q80.jpg' WHERE id = 93;
UPDATE product_variants SET image = 'https://picsum.photos/seed/7494/600/600' WHERE id = 94;
UPDATE product_variants SET image = 'https://picsum.photos/seed/7495/600/600' WHERE id = 95;
UPDATE product_variants SET image = 'https://picsum.photos/seed/7496/600/600' WHERE id = 96;
UPDATE product_variants SET image = 'https://picsum.photos/seed/8597/600/600' WHERE id = 97;
UPDATE product_variants SET image = 'https://picsum.photos/seed/8598/600/600' WHERE id = 98;
UPDATE product_variants SET image = 'https://picsum.photos/seed/8599/600/600' WHERE id = 99;
UPDATE product_variants SET image = 'https://picsum.photos/seed/8600/600/600' WHERE id = 100;
UPDATE product_variants SET image = 'https://picsum.photos/seed/9701/600/600' WHERE id = 101;
UPDATE product_variants SET image = 'https://picsum.photos/seed/9702/600/600' WHERE id = 102;
UPDATE product_variants SET image = 'https://picsum.photos/seed/9703/600/600' WHERE id = 103;
UPDATE product_variants SET image = 'https://picsum.photos/seed/9704/600/600' WHERE id = 104;
UPDATE product_variants SET image = 'https://vn-live-01.slatic.net/p/5b608ccccb5f422cf86eec33b013c8a4.jpg' WHERE id = 105;
UPDATE product_variants SET image = 'https://picsum.photos/seed/9806/600/600' WHERE id = 106;
UPDATE product_variants SET image = 'https://picsum.photos/seed/9807/600/600' WHERE id = 107;
UPDATE product_variants SET image = 'https://picsum.photos/seed/9808/600/600' WHERE id = 108;
UPDATE product_variants SET image = 'https://sakurafashion.vn/upload/sanpham/large/1782360964-ao-khoac-len-cardigan-nu-5.jpg' WHERE id = 109;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10910/600/600' WHERE id = 110;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10911/600/600' WHERE id = 111;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10912/600/600' WHERE id = 112;
UPDATE product_variants SET image = 'https://cdn.shopify.com/s/files/1/0456/5070/6581/files/size-giay-fila_600x600.jpg?v=1671518681' WHERE id = 113;
UPDATE product_variants SET image = 'https://picsum.photos/seed/11014/600/600' WHERE id = 114;
UPDATE product_variants SET image = 'https://picsum.photos/seed/11015/600/600' WHERE id = 115;
UPDATE product_variants SET image = 'https://picsum.photos/seed/11016/600/600' WHERE id = 116;
UPDATE product_variants SET image = 'https://cdn.vuahanghieu.com/unsafe/0x900/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2020/03/giay-sneaker-mlb-big-ball-chunky-a-new-york-yankees-mau-trang-logo-den-size-270-5e82c7db6ac8f-31032020113227.jpg' WHERE id = 117;
UPDATE product_variants SET image = 'https://picsum.photos/seed/8118/600/600' WHERE id = 118;
UPDATE product_variants SET image = 'https://picsum.photos/seed/8119/600/600' WHERE id = 119;
UPDATE product_variants SET image = 'https://picsum.photos/seed/8120/600/600' WHERE id = 120;
UPDATE product_variants SET image = 'https://picsum.photos/seed/12221/600/600' WHERE id = 121;
UPDATE product_variants SET image = 'https://picsum.photos/seed/12222/600/600' WHERE id = 122;
UPDATE product_variants SET image = 'https://picsum.photos/seed/12223/600/600' WHERE id = 123;
UPDATE product_variants SET image = 'https://picsum.photos/seed/12224/600/600' WHERE id = 124;
UPDATE product_variants SET image = 'https://picsum.photos/seed/12325/600/600' WHERE id = 125;
UPDATE product_variants SET image = 'https://picsum.photos/seed/12326/600/600' WHERE id = 126;
UPDATE product_variants SET image = 'https://picsum.photos/seed/12327/600/600' WHERE id = 127;
UPDATE product_variants SET image = 'https://picsum.photos/seed/12328/600/600' WHERE id = 128;
UPDATE product_variants SET image = 'https://picsum.photos/seed/13429/600/600' WHERE id = 129;
UPDATE product_variants SET image = 'https://picsum.photos/seed/13430/600/600' WHERE id = 130;
UPDATE product_variants SET image = 'https://picsum.photos/seed/13431/600/600' WHERE id = 131;
UPDATE product_variants SET image = 'https://picsum.photos/seed/13432/600/600' WHERE id = 132;
UPDATE product_variants SET image = 'https://picsum.photos/seed/13533/600/600' WHERE id = 133;
UPDATE product_variants SET image = 'https://picsum.photos/seed/13534/600/600' WHERE id = 134;
UPDATE product_variants SET image = 'https://picsum.photos/seed/13535/600/600' WHERE id = 135;
UPDATE product_variants SET image = 'https://picsum.photos/seed/13536/600/600' WHERE id = 136;
UPDATE product_variants SET image = 'https://media.routine.vn/1200x1500/prod/media/10f24dpaw011-l-indigo-quan-jean-nu-1-jpg-azc0.webp' WHERE id = 137;
UPDATE product_variants SET image = 'https://picsum.photos/seed/14638/600/600' WHERE id = 138;
UPDATE product_variants SET image = 'https://picsum.photos/seed/14639/600/600' WHERE id = 139;
UPDATE product_variants SET image = 'https://picsum.photos/seed/14640/600/600' WHERE id = 140;
UPDATE product_variants SET image = 'https://js0fpsb45jobj.vcdn.cloud/storage/upload/media/gumac/QJD09041/3-XANHDAM-QJD09041.jpg' WHERE id = 141;
UPDATE product_variants SET image = 'https://picsum.photos/seed/14742/600/600' WHERE id = 142;
UPDATE product_variants SET image = 'https://picsum.photos/seed/14743/600/600' WHERE id = 143;
UPDATE product_variants SET image = 'https://picsum.photos/seed/14744/600/600' WHERE id = 144;
UPDATE product_variants SET image = 'https://m.yodycdn.com/blog/cach-phoi-do-voi-ao-croptop-nam-yodyvn6.jpg' WHERE id = 145;
UPDATE product_variants SET image = 'https://picsum.photos/seed/12846/600/600' WHERE id = 146;
UPDATE product_variants SET image = 'https://picsum.photos/seed/12847/600/600' WHERE id = 147;
UPDATE product_variants SET image = 'https://picsum.photos/seed/12848/600/600' WHERE id = 148;
UPDATE product_variants SET image = 'https://4menshop.com/images/thumbs/2019/07/ao-so-mi-linen-reu-asm1268-10868.JPG' WHERE id = 149;
UPDATE product_variants SET image = 'https://picsum.photos/seed/13950/600/600' WHERE id = 150;
UPDATE product_variants SET image = 'https://picsum.photos/seed/13951/600/600' WHERE id = 151;
UPDATE product_variants SET image = 'https://picsum.photos/seed/13952/600/600' WHERE id = 152;
UPDATE product_variants SET image = 'https://product.hstatic.net/200000690725/product/7_4d50f92cb2ff4039b3f7cfbc1f0509f6.png' WHERE id = 153;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15054/600/600' WHERE id = 154;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15055/600/600' WHERE id = 155;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15056/600/600' WHERE id = 156;
UPDATE product_variants SET image = 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR88fNlIGR41I1xb6-p8cwk0qKBjEJXN1HEHw&s' WHERE id = 157;
UPDATE product_variants SET image = 'https://picsum.photos/seed/13158/600/600' WHERE id = 158;
UPDATE product_variants SET image = 'https://picsum.photos/seed/13159/600/600' WHERE id = 159;
UPDATE product_variants SET image = 'https://picsum.photos/seed/13160/600/600' WHERE id = 160;
UPDATE product_variants SET image = 'https://cdn-img-v2.mybota.vn/uploadv2/web/55/5544/product/2017/09/03/12/27/1504441462_gola-classics-mens-specialist-trainer-4.jpg' WHERE id = 161;
UPDATE product_variants SET image = 'https://picsum.photos/seed/16262/600/600' WHERE id = 162;
UPDATE product_variants SET image = 'https://picsum.photos/seed/16263/600/600' WHERE id = 163;
UPDATE product_variants SET image = 'https://picsum.photos/seed/16264/600/600' WHERE id = 164;
UPDATE product_variants SET image = 'https://img.lazcdn.com/g/p/1f5c616cea515a45b7537fe5df09f8aa.jpg_720x720q80.jpg' WHERE id = 165;
UPDATE product_variants SET image = 'https://picsum.photos/seed/16366/600/600' WHERE id = 166;
UPDATE product_variants SET image = 'https://picsum.photos/seed/16367/600/600' WHERE id = 167;
UPDATE product_variants SET image = 'https://picsum.photos/seed/16368/600/600' WHERE id = 168;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17469/600/600' WHERE id = 169;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17470/600/600' WHERE id = 170;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17471/600/600' WHERE id = 171;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17472/600/600' WHERE id = 172;
UPDATE product_variants SET image = 'https://vn-test-11.slatic.net/p/f2ec186be4b5ace4604c5812ab53a502.jpg' WHERE id = 173;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17574/600/600' WHERE id = 174;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17575/600/600' WHERE id = 175;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17576/600/600' WHERE id = 176;
UPDATE product_variants SET image = 'https://product.hstatic.net/1000284478/product/0000_black_m9166c_1_a6b4fa2c00da4a27bc383825cd544619.jpg' WHERE id = 177;
UPDATE product_variants SET image = 'https://picsum.photos/seed/18678/600/600' WHERE id = 178;
UPDATE product_variants SET image = 'https://picsum.photos/seed/18679/600/600' WHERE id = 179;
UPDATE product_variants SET image = 'https://picsum.photos/seed/18680/600/600' WHERE id = 180;
UPDATE product_variants SET image = 'https://saigonsneaker.com/wp-content/uploads/2022/10/giay-vans-5.jpg' WHERE id = 181;
UPDATE product_variants SET image = 'https://picsum.photos/seed/18782/600/600' WHERE id = 182;
UPDATE product_variants SET image = 'https://picsum.photos/seed/18783/600/600' WHERE id = 183;
UPDATE product_variants SET image = 'https://picsum.photos/seed/18784/600/600' WHERE id = 184;
UPDATE product_variants SET image = 'https://www.osea.vn/data/bt6/quan-tap-yoga-gym-dai-lung-cao-den-black-classic-sport-leggings-1695740620.png' WHERE id = 185;
UPDATE product_variants SET image = 'https://picsum.photos/seed/16886/600/600' WHERE id = 186;
UPDATE product_variants SET image = 'https://picsum.photos/seed/16887/600/600' WHERE id = 187;
UPDATE product_variants SET image = 'https://picsum.photos/seed/16888/600/600' WHERE id = 188;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17989/600/600' WHERE id = 189;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17990/600/600' WHERE id = 190;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17991/600/600' WHERE id = 191;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17992/600/600' WHERE id = 192;
UPDATE product_variants SET image = 'https://www.uniqlo.com/vn/vi/feature/airism/img/women/product_unt.jpg?20200129' WHERE id = 193;
UPDATE product_variants SET image = 'https://picsum.photos/seed/19094/600/600' WHERE id = 194;
UPDATE product_variants SET image = 'https://picsum.photos/seed/19095/600/600' WHERE id = 195;
UPDATE product_variants SET image = 'https://picsum.photos/seed/19096/600/600' WHERE id = 196;
UPDATE product_variants SET image = 'https://bizweb.dktcdn.net/thumb/large/100/477/848/products/o1cn01gh3mvx1vkef6dm4ne-973676210-0-cib-1684143300875.jpg?v=1684143306763' WHERE id = 197;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17198/600/600' WHERE id = 198;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17199/600/600' WHERE id = 199;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17200/600/600' WHERE id = 200;
-- END variant images

-- ========== CATEGORIES (banner 800x300) ==========
-- Adds image_url column if missing and updates category banners

-- START category images
-- Add image_url column to categories if it doesn't exist
-- Note: Adjust ALTER TABLE syntax if using PostgreSQL
ALTER TABLE categories ADD COLUMN IF NOT EXISTS image_url VARCHAR(512) NULL;

-- Update each category image_url
-- Áo Nam
UPDATE categories SET image_url = 'https://picsum.photos/seed/cat1/800/300' WHERE id = 1;

-- Áo Nữ
UPDATE categories SET image_url = 'https://picsum.photos/seed/cat2/800/300' WHERE id = 2;

-- Quần Nam
UPDATE categories SET image_url = 'https://picsum.photos/seed/cat3/800/300' WHERE id = 3;

-- Quần Nữ
UPDATE categories SET image_url = 'https://picsum.photos/seed/cat4/800/300' WHERE id = 4;

-- Giày Nam
UPDATE categories SET image_url = 'https://picsum.photos/seed/cat5/800/300' WHERE id = 5;

-- Giày Nữ
UPDATE categories SET image_url = 'https://picsum.photos/seed/cat6/800/300' WHERE id = 6;

-- Phụ Kiện
UPDATE categories SET image_url = 'https://picsum.photos/seed/cat7/800/300' WHERE id = 7;

-- Túi Xách
UPDATE categories SET image_url = 'https://picsum.photos/seed/cat8/800/300' WHERE id = 8;

-- Áo Sơ Mi
UPDATE categories SET image_url = 'https://picsum.photos/seed/cat9/800/300' WHERE id = 9;

-- Áo Thun
UPDATE categories SET image_url = 'https://picsum.photos/seed/cat10/800/300' WHERE id = 10;

-- Áo Khoác
UPDATE categories SET image_url = 'https://picsum.photos/seed/cat11/800/300' WHERE id = 11;

-- Áo Vest
UPDATE categories SET image_url = 'https://picsum.photos/seed/cat12/800/300' WHERE id = 12;

-- Áo Sơ Mi
UPDATE categories SET image_url = 'https://picsum.photos/seed/cat13/800/300' WHERE id = 13;

-- Áo Thun
UPDATE categories SET image_url = 'https://picsum.photos/seed/cat14/800/300' WHERE id = 14;

-- Áo Khoác
UPDATE categories SET image_url = 'https://picsum.photos/seed/cat15/800/300' WHERE id = 15;

-- Đầm
UPDATE categories SET image_url = 'https://picsum.photos/seed/cat16/800/300' WHERE id = 16;

-- Quần Jeans
UPDATE categories SET image_url = 'https://picsum.photos/seed/cat17/800/300' WHERE id = 17;

-- Quần Kaki
UPDATE categories SET image_url = 'https://picsum.photos/seed/cat18/800/300' WHERE id = 18;

-- Quần Short
UPDATE categories SET image_url = 'https://picsum.photos/seed/cat19/800/300' WHERE id = 19;

-- Quần Jeans
UPDATE categories SET image_url = 'https://picsum.photos/seed/cat20/800/300' WHERE id = 20;

-- Quần Legging
UPDATE categories SET image_url = 'https://picsum.photos/seed/cat21/800/300' WHERE id = 21;

-- Quần Short
UPDATE categories SET image_url = 'https://picsum.photos/seed/cat22/800/300' WHERE id = 22;
-- END category images

-- ========== USERS (avatars via pravatar) ==========
-- Assign avatar for users that lack an image
-- pravatar.cc supports img index 1..70

UPDATE users
SET image = CONCAT('https://i.pravatar.cc/300?img=', MOD(ABS(CRC32(id)), 70) + 1)
WHERE (image IS NULL OR TRIM(image) = '');

-- ==============================================
-- SAMPLE PROMOTIONS (using UUID for promotion_id)
-- ==============================================

-- Percent Promotions
INSERT INTO percent_promotion (promotion_id, name, code, min_order_value, effective, expiration, quantity, is_active, percent, max_discount) VALUES
                                                                                                                                                 ('550e8400-e29b-41d4-a716-446655440001', 'Flash Sale 50%', 'FLASH50', 0, '2025-01-01 00:00:00', '2025-01-31 23:59:59', 100, true, 50.0, 500000.0),
                                                                                                                                                 ('550e8400-e29b-41d4-a716-446655440002', 'Giảm 30% Hè', 'SUMMER30', 500000, '2025-06-01 00:00:00', '2025-08-31 23:59:59', 200, true, 30.0, 300000.0),
                                                                                                                                                 ('550e8400-e29b-41d4-a716-446655440003', 'Black Friday 70%', 'BF70', 1000000, '2025-11-25 00:00:00', '2025-11-30 23:59:59', 50, true, 70.0, 1000000.0),
                                                                                                                                                 ('550e8400-e29b-41d4-a716-446655440004', 'Giảm 20% Thành Viên', 'MEMBER20', 0, '2025-01-01 00:00:00', '2025-12-31 23:59:59', 500, true, 20.0, 200000.0),
                                                                                                                                                 ('550e8400-e29b-41d4-a716-446655440005', 'Sale Cuối Năm 40%', 'NEWYEAR40', 2000000, '2025-12-01 00:00:00', '2025-12-31 23:59:59', 150, true, 40.0, 400000.0);

-- Amount Promotions
INSERT INTO amount_promotion (promotion_id, name, code, min_order_value, effective, expiration, quantity, is_active, discount) VALUES
                                                                                                                                   ('660e8400-e29b-41d4-a716-446655440001', 'Giảm 100K', 'GIAM100K', 500000, '2025-01-01 00:00:00', '2025-12-31 23:59:59', 300, true, 100000.0),
                                                                                                                                   ('660e8400-e29b-41d4-a716-446655440002', 'Voucher 200K', 'VOUCHER200', 1000000, '2025-01-01 00:00:00', '2025-06-30 23:59:59', 100, true, 200000.0),
                                                                                                                                   ('660e8400-e29b-41d4-a716-446655440003', 'Giảm 50K Sinh Nhật', 'BIRTHDAY50', 0, '2025-01-01 00:00:00', '2025-12-31 23:59:59', 1000, true, 50000.0),
                                                                                                                                   ('660e8400-e29b-41d4-a716-446655440004', 'Mã 500K VIP', 'VIP500', 5000000, '2025-01-01 00:00:00', '2025-12-31 23:59:59', 20, true, 500000.0),
                                                                                                                                   ('660e8400-e29b-41d4-a716-446655440005', 'Freeship 30K', 'FREESHIP30', 0, '2025-01-01 00:00:00', '2025-12-31 23:59:59', 500, true, 30000.0);








