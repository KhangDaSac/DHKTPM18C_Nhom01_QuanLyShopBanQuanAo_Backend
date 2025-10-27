    -- Sample Data for Oriental Fashion Shop Backend
    -- This file contains test data for development and testing purposes

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
    -- 3. CATEGORIES (20 categories with hierarchy)
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
    -- 4. PRODUCTS (50 products)
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
    -- 5. PRODUCT IMAGES (now stored in products.images column)
        -- Each product has 4 images stored as pipe-delimited string: "url1|url2|url3|url4"
    -- ==============================================

    -- Áo Nam (Products 1-15)
    UPDATE products SET images = 'https://example.com/products/1/img-1.jpg|https://example.com/products/1/img-2.jpg|https://example.com/products/1/img-3.jpg|https://example.com/products/1/img-4.jpg' WHERE id = 1;
    UPDATE products SET images = 'https://example.com/products/2/img-1.jpg|https://example.com/products/2/img-2.jpg|https://example.com/products/2/img-3.jpg|https://example.com/products/2/img-4.jpg' WHERE id = 2;
    UPDATE products SET images = 'https://example.com/products/3/img-1.jpg|https://example.com/products/3/img-2.jpg|https://example.com/products/3/img-3.jpg|https://example.com/products/3/img-4.jpg' WHERE id = 3;
    UPDATE products SET images = 'https://example.com/products/4/img-1.jpg|https://example.com/products/4/img-2.jpg|https://example.com/products/4/img-3.jpg|https://example.com/products/4/img-4.jpg' WHERE id = 4;
    UPDATE products SET images = 'https://example.com/products/5/img-1.jpg|https://example.com/products/5/img-2.jpg|https://example.com/products/5/img-3.jpg|https://example.com/products/5/img-4.jpg' WHERE id = 5;
    UPDATE products SET images = 'https://example.com/products/6/img-1.jpg|https://example.com/products/6/img-2.jpg|https://example.com/products/6/img-3.jpg|https://example.com/products/6/img-4.jpg' WHERE id = 6;
    UPDATE products SET images = 'https://example.com/products/7/img-1.jpg|https://example.com/products/7/img-2.jpg|https://example.com/products/7/img-3.jpg|https://example.com/products/7/img-4.jpg' WHERE id = 7;
    UPDATE products SET images = 'https://example.com/products/8/img-1.jpg|https://example.com/products/8/img-2.jpg|https://example.com/products/8/img-3.jpg|https://example.com/products/8/img-4.jpg' WHERE id = 8;
    UPDATE products SET images = 'https://example.com/products/9/img-1.jpg|https://example.com/products/9/img-2.jpg|https://example.com/products/9/img-3.jpg|https://example.com/products/9/img-4.jpg' WHERE id = 9;
    UPDATE products SET images = 'https://example.com/products/10/img-1.jpg|https://example.com/products/10/img-2.jpg|https://example.com/products/10/img-3.jpg|https://example.com/products/10/img-4.jpg' WHERE id = 10;
    UPDATE products SET images = 'https://example.com/products/11/img-1.jpg|https://example.com/products/11/img-2.jpg|https://example.com/products/11/img-3.jpg|https://example.com/products/11/img-4.jpg' WHERE id = 11;
    UPDATE products SET images = 'https://example.com/products/12/img-1.jpg|https://example.com/products/12/img-2.jpg|https://example.com/products/12/img-3.jpg|https://example.com/products/12/img-4.jpg' WHERE id = 12;
    UPDATE products SET images = 'https://example.com/products/13/img-1.jpg|https://example.com/products/13/img-2.jpg|https://example.com/products/13/img-3.jpg|https://example.com/products/13/img-4.jpg' WHERE id = 13;
    UPDATE products SET images = 'https://example.com/products/14/img-1.jpg|https://example.com/products/14/img-2.jpg|https://example.com/products/14/img-3.jpg|https://example.com/products/14/img-4.jpg' WHERE id = 14;
    UPDATE products SET images = 'https://example.com/products/15/img-1.jpg|https://example.com/products/15/img-2.jpg|https://example.com/products/15/img-3.jpg|https://example.com/products/15/img-4.jpg' WHERE id = 15;

    -- Áo Nữ (Products 16-30)
    UPDATE products SET images = 'https://example.com/products/16/img-1.jpg|https://example.com/products/16/img-2.jpg|https://example.com/products/16/img-3.jpg|https://example.com/products/16/img-4.jpg' WHERE id = 16;
    UPDATE products SET images = 'https://example.com/products/17/img-1.jpg|https://example.com/products/17/img-2.jpg|https://example.com/products/17/img-3.jpg|https://example.com/products/17/img-4.jpg' WHERE id = 17;
    UPDATE products SET images = 'https://example.com/products/18/img-1.jpg|https://example.com/products/18/img-2.jpg|https://example.com/products/18/img-3.jpg|https://example.com/products/18/img-4.jpg' WHERE id = 18;
    UPDATE products SET images = 'https://example.com/products/19/img-1.jpg|https://example.com/products/19/img-2.jpg|https://example.com/products/19/img-3.jpg|https://example.com/products/19/img-4.jpg' WHERE id = 19;
    UPDATE products SET images = 'https://example.com/products/20/img-1.jpg|https://example.com/products/20/img-2.jpg|https://example.com/products/20/img-3.jpg|https://example.com/products/20/img-4.jpg' WHERE id = 20;
    UPDATE products SET images = 'https://example.com/products/21/img-1.jpg|https://example.com/products/21/img-2.jpg|https://example.com/products/21/img-3.jpg|https://example.com/products/21/img-4.jpg' WHERE id = 21;
    UPDATE products SET images = 'https://example.com/products/22/img-1.jpg|https://example.com/products/22/img-2.jpg|https://example.com/products/22/img-3.jpg|https://example.com/products/22/img-4.jpg' WHERE id = 22;
    UPDATE products SET images = 'https://example.com/products/23/img-1.jpg|https://example.com/products/23/img-2.jpg|https://example.com/products/23/img-3.jpg|https://example.com/products/23/img-4.jpg' WHERE id = 23;
    UPDATE products SET images = 'https://example.com/products/24/img-1.jpg|https://example.com/products/24/img-2.jpg|https://example.com/products/24/img-3.jpg|https://example.com/products/24/img-4.jpg' WHERE id = 24;
    UPDATE products SET images = 'https://example.com/products/25/img-1.jpg|https://example.com/products/25/img-2.jpg|https://example.com/products/25/img-3.jpg|https://example.com/products/25/img-4.jpg' WHERE id = 25;
    UPDATE products SET images = 'https://example.com/products/26/img-1.jpg|https://example.com/products/26/img-2.jpg|https://example.com/products/26/img-3.jpg|https://example.com/products/26/img-4.jpg' WHERE id = 26;
    UPDATE products SET images = 'https://example.com/products/27/img-1.jpg|https://example.com/products/27/img-2.jpg|https://example.com/products/27/img-3.jpg|https://example.com/products/27/img-4.jpg' WHERE id = 27;
    UPDATE products SET images = 'https://example.com/products/28/img-1.jpg|https://example.com/products/28/img-2.jpg|https://example.com/products/28/img-3.jpg|https://example.com/products/28/img-4.jpg' WHERE id = 28;
    UPDATE products SET images = 'https://example.com/products/29/img-1.jpg|https://example.com/products/29/img-2.jpg|https://example.com/products/29/img-3.jpg|https://example.com/products/29/img-4.jpg' WHERE id = 29;
    UPDATE products SET images = 'https://example.com/products/30/img-1.jpg|https://example.com/products/30/img-2.jpg|https://example.com/products/30/img-3.jpg|https://example.com/products/30/img-4.jpg' WHERE id = 30;

    -- Quần Nam (Products 31-40)
    UPDATE products SET images = 'https://example.com/products/31/img-1.jpg|https://example.com/products/31/img-2.jpg|https://example.com/products/31/img-3.jpg|https://example.com/products/31/img-4.jpg' WHERE id = 31;
    UPDATE products SET images = 'https://example.com/products/32/img-1.jpg|https://example.com/products/32/img-2.jpg|https://example.com/products/32/img-3.jpg|https://example.com/products/32/img-4.jpg' WHERE id = 32;
    UPDATE products SET images = 'https://example.com/products/33/img-1.jpg|https://example.com/products/33/img-2.jpg|https://example.com/products/33/img-3.jpg|https://example.com/products/33/img-4.jpg' WHERE id = 33;
    UPDATE products SET images = 'https://example.com/products/34/img-1.jpg|https://example.com/products/34/img-2.jpg|https://example.com/products/34/img-3.jpg|https://example.com/products/34/img-4.jpg' WHERE id = 34;
    UPDATE products SET images = 'https://example.com/products/35/img-1.jpg|https://example.com/products/35/img-2.jpg|https://example.com/products/35/img-3.jpg|https://example.com/products/35/img-4.jpg' WHERE id = 35;
    UPDATE products SET images = 'https://example.com/products/36/img-1.jpg|https://example.com/products/36/img-2.jpg|https://example.com/products/36/img-3.jpg|https://example.com/products/36/img-4.jpg' WHERE id = 36;
    UPDATE products SET images = 'https://example.com/products/37/img-1.jpg|https://example.com/products/37/img-2.jpg|https://example.com/products/37/img-3.jpg|https://example.com/products/37/img-4.jpg' WHERE id = 37;
    UPDATE products SET images = 'https://example.com/products/38/img-1.jpg|https://example.com/products/38/img-2.jpg|https://example.com/products/38/img-3.jpg|https://example.com/products/38/img-4.jpg' WHERE id = 38;
    UPDATE products SET images = 'https://example.com/products/39/img-1.jpg|https://example.com/products/39/img-2.jpg|https://example.com/products/39/img-3.jpg|https://example.com/products/39/img-4.jpg' WHERE id = 39;
    UPDATE products SET images = 'https://example.com/products/40/img-1.jpg|https://example.com/products/40/img-2.jpg|https://example.com/products/40/img-3.jpg|https://example.com/products/40/img-4.jpg' WHERE id = 40;

    -- Quần Nữ (Products 41-50)
    UPDATE products SET images = 'https://example.com/products/41/img-1.jpg|https://example.com/products/41/img-2.jpg|https://example.com/products/41/img-3.jpg|https://example.com/products/41/img-4.jpg' WHERE id = 41;
    UPDATE products SET images = 'https://example.com/products/42/img-1.jpg|https://example.com/products/42/img-2.jpg|https://example.com/products/42/img-3.jpg|https://example.com/products/42/img-4.jpg' WHERE id = 42;
    UPDATE products SET images = 'https://example.com/products/43/img-1.jpg|https://example.com/products/43/img-2.jpg|https://example.com/products/43/img-3.jpg|https://example.com/products/43/img-4.jpg' WHERE id = 43;
    UPDATE products SET images = 'https://example.com/products/44/img-1.jpg|https://example.com/products/44/img-2.jpg|https://example.com/products/44/img-3.jpg|https://example.com/products/44/img-4.jpg' WHERE id = 44;
    UPDATE products SET images = 'https://example.com/products/45/img-1.jpg|https://example.com/products/45/img-2.jpg|https://example.com/products/45/img-3.jpg|https://example.com/products/45/img-4.jpg' WHERE id = 45;
    UPDATE products SET images = 'https://example.com/products/46/img-1.jpg|https://example.com/products/46/img-2.jpg|https://example.com/products/46/img-3.jpg|https://example.com/products/46/img-4.jpg' WHERE id = 46;
    UPDATE products SET images = 'https://example.com/products/47/img-1.jpg|https://example.com/products/47/img-2.jpg|https://example.com/products/47/img-3.jpg|https://example.com/products/47/img-4.jpg' WHERE id = 47;
    UPDATE products SET images = 'https://example.com/products/48/img-1.jpg|https://example.com/products/48/img-2.jpg|https://example.com/products/48/img-3.jpg|https://example.com/products/48/img-4.jpg' WHERE id = 48;
    UPDATE products SET images = 'https://example.com/products/49/img-1.jpg|https://example.com/products/49/img-2.jpg|https://example.com/products/49/img-3.jpg|https://example.com/products/49/img-4.jpg' WHERE id = 49;
    UPDATE products SET images = 'https://example.com/products/50/img-1.jpg|https://example.com/products/50/img-2.jpg|https://example.com/products/50/img-3.jpg|https://example.com/products/50/img-4.jpg' WHERE id = 50;

    -- ==============================================
    -- 6. PRODUCT VARIANTS (200 variants - 4 per product: S, M, L, XL)
    -- ==============================================

    INSERT INTO product_variants (id, product_id, size, color, image, price, discount, quantity, additional_price, create_at) VALUES
    -- Product 1 variants
    (1, 1, 'S', 'Trắng', 'https://example.com/products/1/s-trang.jpg', 299000, 0, 50, 0, NOW()),
    (2, 1, 'M', 'Trắng', 'https://example.com/products/1/m-trang.jpg', 299000, 0, 75, 0, NOW()),
    (3, 1, 'L', 'Trắng', 'https://example.com/products/1/l-trang.jpg', 299000, 0, 60, 0, NOW()),
    (4, 1, 'XL', 'Trắng', 'https://example.com/products/1/xl-trang.jpg', 299000, 0, 40, 0, NOW()),

    -- Product 2 variants
    (5, 2, 'S', 'Xanh', 'https://example.com/products/2/s-xanh.jpg', 299000, 0, 45, 0, NOW()),
    (6, 2, 'M', 'Xanh', 'https://example.com/products/2/m-xanh.jpg', 299000, 0, 80, 0, NOW()),
    (7, 2, 'L', 'Xanh', 'https://example.com/products/2/l-xanh.jpg', 299000, 0, 65, 0, NOW()),
    (8, 2, 'XL', 'Xanh', 'https://example.com/products/2/xl-xanh.jpg', 299000, 0, 35, 0, NOW()),

    -- Product 3 variants
    (9, 3, 'S', 'Đen', 'https://example.com/products/3/s-den.jpg', 199000, 0, 100, 0, NOW()),
    (10, 3, 'M', 'Đen', 'https://example.com/products/3/m-den.jpg', 199000, 0, 120, 0, NOW()),
    (11, 3, 'L', 'Đen', 'https://example.com/products/3/l-den.jpg', 199000, 0, 90, 0, NOW()),
    (12, 3, 'XL', 'Đen', 'https://example.com/products/3/xl-den.jpg', 199000, 0, 70, 0, NOW()),

    -- Product 4 variants
    (13, 4, 'S', 'Xanh', 'https://example.com/products/4/s-xanh.jpg', 249000, 0, 60, 0, NOW()),
    (14, 4, 'M', 'Xanh', 'https://example.com/products/4/m-xanh.jpg', 249000, 0, 85, 0, NOW()),
    (15, 4, 'L', 'Xanh', 'https://example.com/products/4/l-xanh.jpg', 249000, 0, 70, 0, NOW()),
    (16, 4, 'XL', 'Xanh', 'https://example.com/products/4/xl-xanh.jpg', 249000, 0, 50, 0, NOW()),

    -- Product 5 variants
    (17, 5, 'S', 'Đen', 'https://example.com/products/5/s-den.jpg', 599000, 0, 30, 0, NOW()),
    (18, 5, 'M', 'Đen', 'https://example.com/products/5/m-den.jpg', 599000, 0, 40, 0, NOW()),
    (19, 5, 'L', 'Đen', 'https://example.com/products/5/l-den.jpg', 599000, 0, 35, 0, NOW()),
    (20, 5, 'XL', 'Đen', 'https://example.com/products/5/xl-den.jpg', 599000, 0, 25, 0, NOW()),

    -- Product 6 variants
    (21, 6, 'S', 'Xám', 'https://example.com/products/6/s-xam.jpg', 399000, 0, 40, 0, NOW()),
    (22, 6, 'M', 'Xám', 'https://example.com/products/6/m-xam.jpg', 399000, 0, 55, 0, NOW()),
    (23, 6, 'L', 'Xám', 'https://example.com/products/6/l-xam.jpg', 399000, 0, 45, 0, NOW()),
    (24, 6, 'XL', 'Xám', 'https://example.com/products/6/xl-xam.jpg', 399000, 0, 30, 0, NOW()),

    -- Product 7 variants
    (25, 7, 'S', 'Xanh Đen', 'https://example.com/products/7/s-xanh-den.jpg', 1299000, 0, 20, 0, NOW()),
    (26, 7, 'M', 'Xanh Đen', 'https://example.com/products/7/m-xanh-den.jpg', 1299000, 0, 25, 0, NOW()),
    (27, 7, 'L', 'Xanh Đen', 'https://example.com/products/7/l-xanh-den.jpg', 1299000, 0, 20, 0, NOW()),
    (28, 7, 'XL', 'Xanh Đen', 'https://example.com/products/7/xl-xanh-den.jpg', 1299000, 0, 15, 0, NOW()),

    -- Product 8 variants
    (29, 8, 'S', 'Xám', 'https://example.com/products/8/s-xam.jpg', 1199000, 0, 18, 0, NOW()),
    (30, 8, 'M', 'Xám', 'https://example.com/products/8/m-xam.jpg', 1199000, 0, 22, 0, NOW()),
    (31, 8, 'L', 'Xám', 'https://example.com/products/8/l-xam.jpg', 1199000, 0, 18, 0, NOW()),
    (32, 8, 'XL', 'Xám', 'https://example.com/products/8/xl-xam.jpg', 1199000, 0, 12, 0, NOW()),

    -- Product 9 variants
    (33, 9, 'S', 'Xanh Trắng', 'https://example.com/products/9/s-xanh-trang.jpg', 349000, 0, 35, 0, NOW()),
    (34, 9, 'M', 'Xanh Trắng', 'https://example.com/products/9/m-xanh-trang.jpg', 349000, 0, 50, 0, NOW()),
    (35, 9, 'L', 'Xanh Trắng', 'https://example.com/products/9/l-xanh-trang.jpg', 349000, 0, 40, 0, NOW()),
    (36, 9, 'XL', 'Xanh Trắng', 'https://example.com/products/9/xl-xanh-trang.jpg', 349000, 0, 25, 0, NOW()),

    -- Product 10 variants
    (37, 10, 'S', 'Đen', 'https://example.com/products/10/s-den.jpg', 229000, 0, 50, 0, NOW()),
    (38, 10, 'M', 'Đen', 'https://example.com/products/10/m-den.jpg', 229000, 0, 70, 0, NOW()),
    (39, 10, 'L', 'Đen', 'https://example.com/products/10/l-den.jpg', 229000, 0, 55, 0, NOW()),
    (40, 10, 'XL', 'Đen', 'https://example.com/products/10/xl-den.jpg', 229000, 0, 35, 0, NOW());

    -- Continue with more variants...
    INSERT INTO product_variants (id, product_id, size, color, image, price, discount, quantity, additional_price, create_at) VALUES
    (41, 11, 'S', 'Xanh', 'https://example.com/variants/11/s.jpg', 799000, 0, 25, 0, NOW()),
    (42, 11, 'M', 'Xanh', 'https://example.com/variants/11/m.jpg', 799000, 0, 30, 0, NOW()),
    (43, 11, 'L', 'Xanh', 'https://example.com/variants/11/l.jpg', 799000, 0, 25, 0, NOW()),
    (44, 11, 'XL', 'Xanh', 'https://example.com/variants/11/xl.jpg', 799000, 0, 20, 0, NOW()),
    (45, 12, 'S', 'Đen', 'https://example.com/variants/12/s.jpg', 899000, 0, 20, 0, NOW()),
    (46, 12, 'M', 'Đen', 'https://example.com/variants/12/m.jpg', 899000, 0, 25, 0, NOW()),
    (47, 12, 'L', 'Đen', 'https://example.com/variants/12/l.jpg', 899000, 0, 20, 0, NOW()),
    (48, 12, 'XL', 'Đen', 'https://example.com/variants/12/xl.jpg', 899000, 0, 15, 0, NOW()),
    (49, 13, 'S', 'Đỏ', 'https://example.com/variants/13/s.jpg', 279000, 0, 40, 0, NOW()),
    (50, 13, 'M', 'Đỏ', 'https://example.com/variants/13/m.jpg', 279000, 0, 55, 0, NOW()),
    (51, 13, 'L', 'Đỏ', 'https://example.com/variants/13/l.jpg', 279000, 0, 45, 0, NOW()),
    (52, 13, 'XL', 'Đỏ', 'https://example.com/variants/13/xl.jpg', 279000, 0, 30, 0, NOW()),
    (53, 14, 'S', 'Trắng', 'https://example.com/variants/14/s.jpg', 179000, 0, 60, 0, NOW()),
    (54, 14, 'M', 'Trắng', 'https://example.com/variants/14/m.jpg', 179000, 0, 80, 0, NOW()),
    (55, 14, 'L', 'Trắng', 'https://example.com/variants/14/l.jpg', 179000, 0, 65, 0, NOW()),
    (56, 14, 'XL', 'Trắng', 'https://example.com/variants/14/xl.jpg', 179000, 0, 45, 0, NOW()),
    (57, 15, 'S', 'Xanh Navy', 'https://example.com/variants/15/s.jpg', 1399000, 0, 15, 0, NOW()),
    (58, 15, 'M', 'Xanh Navy', 'https://example.com/variants/15/m.jpg', 1399000, 0, 20, 0, NOW()),
    (59, 15, 'L', 'Xanh Navy', 'https://example.com/variants/15/l.jpg', 1399000, 0, 18, 0, NOW()),
    (60, 15, 'XL', 'Xanh Navy', 'https://example.com/variants/15/xl.jpg', 1399000, 0, 12, 0, NOW());

    -- Product 16-30 variants (Áo Nữ)
    INSERT INTO product_variants (id, product_id, size, color, image, price, discount, quantity, additional_price, create_at) VALUES
    (61, 16, 'S', 'Trắng', 'https://example.com/variants/16/s.jpg', 329000, 0, 40, 0, NOW()),
    (62, 16, 'M', 'Trắng', 'https://example.com/variants/16/m.jpg', 329000, 0, 55, 0, NOW()),
    (63, 16, 'L', 'Trắng', 'https://example.com/variants/16/l.jpg', 329000, 0, 50, 0, NOW()),
    (64, 16, 'XL', 'Trắng', 'https://example.com/variants/16/xl.jpg', 329000, 0, 35, 0, NOW()),
    (65, 17, 'S', 'Hoa', 'https://example.com/variants/17/s.jpg', 359000, 0, 45, 0, NOW()),
    (66, 17, 'M', 'Hoa', 'https://example.com/variants/17/m.jpg', 359000, 0, 60, 0, NOW()),
    (67, 17, 'L', 'Hoa', 'https://example.com/variants/17/l.jpg', 359000, 0, 55, 0, NOW()),
    (68, 17, 'XL', 'Hoa', 'https://example.com/variants/17/xl.jpg', 359000, 0, 40, 0, NOW()),
    (69, 18, 'S', 'Hồng', 'https://example.com/variants/18/s.jpg', 249000, 0, 60, 0, NOW()),
    (70, 18, 'M', 'Hồng', 'https://example.com/variants/18/m.jpg', 249000, 0, 80, 0, NOW()),
    (71, 18, 'L', 'Hồng', 'https://example.com/variants/18/l.jpg', 249000, 0, 65, 0, NOW()),
    (72, 18, 'XL', 'Hồng', 'https://example.com/variants/18/xl.jpg', 249000, 0, 45, 0, NOW()),
    (73, 19, 'S', 'Đen', 'https://example.com/variants/19/s.jpg', 279000, 0, 55, 0, NOW()),
    (74, 19, 'M', 'Đen', 'https://example.com/variants/19/m.jpg', 279000, 0, 75, 0, NOW()),
    (75, 19, 'L', 'Đen', 'https://example.com/variants/19/l.jpg', 279000, 0, 60, 0, NOW()),
    (76, 19, 'XL', 'Đen', 'https://example.com/variants/19/xl.jpg', 279000, 0, 40, 0, NOW()),
    (77, 20, 'S', 'Be', 'https://example.com/variants/20/s.jpg', 399000, 0, 50, 0, NOW()),
    (78, 20, 'M', 'Be', 'https://example.com/variants/20/m.jpg', 399000, 0, 70, 0, NOW()),
    (79, 20, 'L', 'Be', 'https://example.com/variants/20/l.jpg', 399000, 0, 55, 0, NOW()),
    (80, 20, 'XL', 'Be', 'https://example.com/variants/20/xl.jpg', 399000, 0, 45, 0, NOW()),
    (81, 21, 'S', 'Xanh', 'https://example.com/variants/21/s.jpg', 429000, 0, 45, 0, NOW()),
    (82, 21, 'M', 'Xanh', 'https://example.com/variants/21/m.jpg', 429000, 0, 65, 0, NOW()),
    (83, 21, 'L', 'Xanh', 'https://example.com/variants/21/l.jpg', 429000, 0, 50, 0, NOW()),
    (84, 21, 'XL', 'Xanh', 'https://example.com/variants/21/xl.jpg', 429000, 0, 40, 0, NOW()),
    (85, 22, 'S', 'Đỏ', 'https://example.com/variants/22/s.jpg', 899000, 0, 30, 0, NOW()),
    (86, 22, 'M', 'Đỏ', 'https://example.com/variants/22/m.jpg', 899000, 0, 40, 0, NOW()),
    (87, 22, 'L', 'Đỏ', 'https://example.com/variants/22/l.jpg', 899000, 0, 35, 0, NOW()),
    (88, 22, 'XL', 'Đỏ', 'https://example.com/variants/22/xl.jpg', 899000, 0, 25, 0, NOW()),
    (89, 23, 'S', 'Xanh', 'https://example.com/variants/23/s.jpg', 759000, 0, 35, 0, NOW()),
    (90, 23, 'M', 'Xanh', 'https://example.com/variants/23/m.jpg', 759000, 0, 45, 0, NOW()),
    (91, 23, 'L', 'Xanh', 'https://example.com/variants/23/l.jpg', 759000, 0, 40, 0, NOW()),
    (92, 23, 'XL', 'Xanh', 'https://example.com/variants/23/xl.jpg', 759000, 0, 30, 0, NOW()),
    (93, 24, 'S', 'Trắng', 'https://example.com/variants/24/s.jpg', 379000, 0, 50, 0, NOW()),
    (94, 24, 'M', 'Trắng', 'https://example.com/variants/24/m.jpg', 379000, 0, 70, 0, NOW()),
    (95, 24, 'L', 'Trắng', 'https://example.com/variants/24/l.jpg', 379000, 0, 55, 0, NOW()),
    (96, 24, 'XL', 'Trắng', 'https://example.com/variants/24/xl.jpg', 379000, 0, 45, 0, NOW()),
    (97, 25, 'S', 'Xám', 'https://example.com/variants/25/s.jpg', 299000, 0, 60, 0, NOW()),
    (98, 25, 'M', 'Xám', 'https://example.com/variants/25/m.jpg', 299000, 0, 80, 0, NOW()),
    (99, 25, 'L', 'Xám', 'https://example.com/variants/25/l.jpg', 299000, 0, 65, 0, NOW()),
    (100, 25, 'XL', 'Xám', 'https://example.com/variants/25/xl.jpg', 299000, 0, 45, 0, NOW()),
    (101, 26, 'S', 'Hồng', 'https://example.com/variants/26/s.jpg', 449000, 0, 50, 0, NOW()),
    (102, 26, 'M', 'Hồng', 'https://example.com/variants/26/m.jpg', 449000, 0, 70, 0, NOW()),
    (103, 26, 'L', 'Hồng', 'https://example.com/variants/26/l.jpg', 449000, 0, 55, 0, NOW()),
    (104, 26, 'XL', 'Hồng', 'https://example.com/variants/26/xl.jpg', 449000, 0, 45, 0, NOW()),
    (105, 27, 'S', 'Đen', 'https://example.com/variants/27/s.jpg', 479000, 0, 45, 0, NOW()),
    (106, 27, 'M', 'Đen', 'https://example.com/variants/27/m.jpg', 479000, 0, 65, 0, NOW()),
    (107, 27, 'L', 'Đen', 'https://example.com/variants/27/l.jpg', 479000, 0, 50, 0, NOW()),
    (108, 27, 'XL', 'Đen', 'https://example.com/variants/27/xl.jpg', 479000, 0, 40, 0, NOW()),
    (109, 28, 'S', 'Tím', 'https://example.com/variants/28/s.jpg', 999000, 0, 30, 0, NOW()),
    (110, 28, 'M', 'Tím', 'https://example.com/variants/28/m.jpg', 999000, 0, 40, 0, NOW()),
    (111, 28, 'L', 'Tím', 'https://example.com/variants/28/l.jpg', 999000, 0, 35, 0, NOW()),
    (112, 28, 'XL', 'Tím', 'https://example.com/variants/28/xl.jpg', 999000, 0, 25, 0, NOW()),
    (113, 29, 'S', 'Vàng', 'https://example.com/variants/29/s.jpg', 829000, 0, 35, 0, NOW()),
    (114, 29, 'M', 'Vàng', 'https://example.com/variants/29/m.jpg', 829000, 0, 45, 0, NOW()),
    (115, 29, 'L', 'Vàng', 'https://example.com/variants/29/l.jpg', 829000, 0, 40, 0, NOW()),
    (116, 29, 'XL', 'Vàng', 'https://example.com/variants/29/xl.jpg', 829000, 0, 30, 0, NOW()),
    (117, 30, 'S', 'Kem', 'https://example.com/variants/30/s.jpg', 459000, 0, 50, 0, NOW()),
    (118, 30, 'M', 'Kem', 'https://example.com/variants/30/m.jpg', 459000, 0, 70, 0, NOW()),
    (119, 30, 'L', 'Kem', 'https://example.com/variants/30/l.jpg', 459000, 0, 55, 0, NOW()),
    (120, 30, 'XL', 'Kem', 'https://example.com/variants/30/xl.jpg', 459000, 0, 45, 0, NOW());

    -- Product 31-50 variants (Quần Nam & Quần Nữ)
    INSERT INTO product_variants (id, product_id, size, color, image, price, discount, quantity, additional_price, create_at) VALUES
    (121, 31, 'S', 'Xanh', 'https://example.com/variants/31/s.jpg', 699000, 0, 40, 0, NOW()),
    (122, 31, 'M', 'Xanh', 'https://example.com/variants/31/m.jpg', 699000, 0, 55, 0, NOW()),
    (123, 31, 'L', 'Xanh', 'https://example.com/variants/31/l.jpg', 699000, 0, 45, 0, NOW()),
    (124, 31, 'XL', 'Xanh', 'https://example.com/variants/31/xl.jpg', 699000, 0, 35, 0, NOW()),
    (125, 32, 'S', 'Đen', 'https://example.com/variants/32/s.jpg', 699000, 0, 45, 0, NOW()),
    (126, 32, 'M', 'Đen', 'https://example.com/variants/32/m.jpg', 699000, 0, 60, 0, NOW()),
    (127, 32, 'L', 'Đen', 'https://example.com/variants/32/l.jpg', 699000, 0, 50, 0, NOW()),
    (128, 32, 'XL', 'Đen', 'https://example.com/variants/32/xl.jpg', 699000, 0, 40, 0, NOW()),
    (129, 33, 'S', 'Be', 'https://example.com/variants/33/s.jpg', 549000, 0, 55, 0, NOW()),
    (130, 33, 'M', 'Be', 'https://example.com/variants/33/m.jpg', 549000, 0, 70, 0, NOW()),
    (131, 33, 'L', 'Be', 'https://example.com/variants/33/l.jpg', 549000, 0, 60, 0, NOW()),
    (132, 33, 'XL', 'Be', 'https://example.com/variants/33/xl.jpg', 549000, 0, 50, 0, NOW()),
    (133, 34, 'S', 'Xanh', 'https://example.com/variants/34/s.jpg', 549000, 0, 50, 0, NOW()),
    (134, 34, 'M', 'Xanh', 'https://example.com/variants/34/m.jpg', 549000, 0, 65, 0, NOW()),
    (135, 34, 'L', 'Xanh', 'https://example.com/variants/34/l.jpg', 549000, 0, 55, 0, NOW()),
    (136, 34, 'XL', 'Xanh', 'https://example.com/variants/34/xl.jpg', 549000, 0, 45, 0, NOW()),
    (137, 35, 'S', 'Đen', 'https://example.com/variants/35/s.jpg', 299000, 0, 70, 0, NOW()),
    (138, 35, 'M', 'Đen', 'https://example.com/variants/35/m.jpg', 299000, 0, 90, 0, NOW()),
    (139, 35, 'L', 'Đen', 'https://example.com/variants/35/l.jpg', 299000, 0, 75, 0, NOW()),
    (140, 35, 'XL', 'Đen', 'https://example.com/variants/35/xl.jpg', 299000, 0, 60, 0, NOW()),
    (141, 36, 'S', 'Xanh', 'https://example.com/variants/36/s.jpg', 329000, 0, 65, 0, NOW()),
    (142, 36, 'M', 'Xanh', 'https://example.com/variants/36/m.jpg', 329000, 0, 85, 0, NOW()),
    (143, 36, 'L', 'Xanh', 'https://example.com/variants/36/l.jpg', 329000, 0, 70, 0, NOW()),
    (144, 36, 'XL', 'Xanh', 'https://example.com/variants/36/xl.jpg', 329000, 0, 55, 0, NOW()),
    (145, 37, 'S', 'Xanh', 'https://example.com/variants/37/s.jpg', 749000, 0, 45, 0, NOW()),
    (146, 37, 'M', 'Xanh', 'https://example.com/variants/37/m.jpg', 749000, 0, 60, 0, NOW()),
    (147, 37, 'L', 'Xanh', 'https://example.com/variants/37/l.jpg', 749000, 0, 50, 0, NOW()),
    (148, 37, 'XL', 'Xanh', 'https://example.com/variants/37/xl.jpg', 749000, 0, 40, 0, NOW()),
    (149, 38, 'S', 'Xanh', 'https://example.com/variants/38/s.jpg', 599000, 0, 50, 0, NOW()),
    (150, 38, 'M', 'Xanh', 'https://example.com/variants/38/m.jpg', 599000, 0, 65, 0, NOW()),
    (151, 38, 'L', 'Xanh', 'https://example.com/variants/38/l.jpg', 599000, 0, 55, 0, NOW()),
    (152, 38, 'XL', 'Xanh', 'https://example.com/variants/38/xl.jpg', 599000, 0, 45, 0, NOW()),
    (153, 39, 'S', 'Trắng', 'https://example.com/variants/39/s.jpg', 349000, 0, 60, 0, NOW()),
    (154, 39, 'M', 'Trắng', 'https://example.com/variants/39/m.jpg', 349000, 0, 80, 0, NOW()),
    (155, 39, 'L', 'Trắng', 'https://example.com/variants/39/l.jpg', 349000, 0, 65, 0, NOW()),
    (156, 39, 'XL', 'Trắng', 'https://example.com/variants/39/xl.jpg', 349000, 0, 55, 0, NOW()),
    (157, 40, 'S', 'Xanh', 'https://example.com/variants/40/s.jpg', 799000, 0, 40, 0, NOW()),
    (158, 40, 'M', 'Xanh', 'https://example.com/variants/40/m.jpg', 799000, 0, 55, 0, NOW()),
    (159, 40, 'L', 'Xanh', 'https://example.com/variants/40/l.jpg', 799000, 0, 45, 0, NOW()),
    (160, 40, 'XL', 'Xanh', 'https://example.com/variants/40/xl.jpg', 799000, 0, 35, 0, NOW()),
    (161, 41, 'S', 'Xanh', 'https://example.com/variants/41/s.jpg', 779000, 0, 45, 0, NOW()),
    (162, 41, 'M', 'Xanh', 'https://example.com/variants/41/m.jpg', 779000, 0, 60, 0, NOW()),
    (163, 41, 'L', 'Xanh', 'https://example.com/variants/41/l.jpg', 779000, 0, 50, 0, NOW()),
    (164, 41, 'XL', 'Xanh', 'https://example.com/variants/41/xl.jpg', 779000, 0, 40, 0, NOW()),
    (165, 42, 'S', 'Đen', 'https://example.com/variants/42/s.jpg', 779000, 0, 50, 0, NOW()),
    (166, 42, 'M', 'Đen', 'https://example.com/variants/42/m.jpg', 779000, 0, 65, 0, NOW()),
    (167, 42, 'L', 'Đen', 'https://example.com/variants/42/l.jpg', 779000, 0, 55, 0, NOW()),
    (168, 42, 'XL', 'Đen', 'https://example.com/variants/42/xl.jpg', 779000, 0, 45, 0, NOW()),
    (169, 43, 'S', 'Đen', 'https://example.com/variants/43/s.jpg', 429000, 0, 60, 0, NOW()),
    (170, 43, 'M', 'Đen', 'https://example.com/variants/43/m.jpg', 429000, 0, 80, 0, NOW()),
    (171, 43, 'L', 'Đen', 'https://example.com/variants/43/l.jpg', 429000, 0, 65, 0, NOW()),
    (172, 43, 'XL', 'Đen', 'https://example.com/variants/43/xl.jpg', 429000, 0, 55, 0, NOW()),
    (173, 44, 'S', 'Xám', 'https://example.com/variants/44/s.jpg', 449000, 0, 55, 0, NOW()),
    (174, 44, 'M', 'Xám', 'https://example.com/variants/44/m.jpg', 449000, 0, 75, 0, NOW()),
    (175, 44, 'L', 'Xám', 'https://example.com/variants/44/l.jpg', 449000, 0, 60, 0, NOW()),
    (176, 44, 'XL', 'Xám', 'https://example.com/variants/44/xl.jpg', 449000, 0, 50, 0, NOW()),
    (177, 45, 'S', 'Trắng', 'https://example.com/variants/45/s.jpg', 299000, 0, 70, 0, NOW()),
    (178, 45, 'M', 'Trắng', 'https://example.com/variants/45/m.jpg', 299000, 0, 90, 0, NOW()),
    (179, 45, 'L', 'Trắng', 'https://example.com/variants/45/l.jpg', 299000, 0, 75, 0, NOW()),
    (180, 45, 'XL', 'Trắng', 'https://example.com/variants/45/xl.jpg', 299000, 0, 65, 0, NOW()),
    (181, 46, 'S', 'Xanh', 'https://example.com/variants/46/s.jpg', 349000, 0, 65, 0, NOW()),
    (182, 46, 'M', 'Xanh', 'https://example.com/variants/46/m.jpg', 349000, 0, 85, 0, NOW()),
    (183, 46, 'L', 'Xanh', 'https://example.com/variants/46/l.jpg', 349000, 0, 70, 0, NOW()),
    (184, 46, 'XL', 'Xanh', 'https://example.com/variants/46/xl.jpg', 349000, 0, 60, 0, NOW()),
    (185, 47, 'S', 'Xanh', 'https://example.com/variants/47/s.jpg', 829000, 0, 50, 0, NOW()),
    (186, 47, 'M', 'Xanh', 'https://example.com/variants/47/m.jpg', 829000, 0, 65, 0, NOW()),
    (187, 47, 'L', 'Xanh', 'https://example.com/variants/47/l.jpg', 829000, 0, 55, 0, NOW()),
    (188, 47, 'XL', 'Xanh', 'https://example.com/variants/47/xl.jpg', 829000, 0, 45, 0, NOW()),
    (189, 48, 'S', 'Hồng', 'https://example.com/variants/48/s.jpg', 379000, 0, 55, 0, NOW()),
    (190, 48, 'M', 'Hồng', 'https://example.com/variants/48/m.jpg', 379000, 0, 70, 0, NOW()),
    (191, 48, 'L', 'Hồng', 'https://example.com/variants/48/l.jpg', 379000, 0, 55, 0, NOW()),
    (192, 48, 'XL', 'Hồng', 'https://example.com/variants/48/xl.jpg', 379000, 0, 50, 0, NOW()),
    (193, 49, 'S', 'Đen', 'https://example.com/variants/49/s.jpg', 349000, 0, 60, 0, NOW()),
    (194, 49, 'M', 'Đen', 'https://example.com/variants/49/m.jpg', 349000, 0, 80, 0, NOW()),
    (195, 49, 'L', 'Đen', 'https://example.com/variants/49/l.jpg', 349000, 0, 65, 0, NOW()),
    (196, 49, 'XL', 'Đen', 'https://example.com/variants/49/xl.jpg', 349000, 0, 55, 0, NOW()),
    (197, 50, 'S', 'Xanh', 'https://example.com/variants/50/s.jpg', 849000, 0, 50, 0, NOW()),
    (198, 50, 'M', 'Xanh', 'https://example.com/variants/50/m.jpg', 849000, 0, 65, 0, NOW()),
    (199, 50, 'L', 'Xanh', 'https://example.com/variants/50/l.jpg', 849000, 0, 55, 0, NOW()),
    (200, 50, 'XL', 'Xanh', 'https://example.com/variants/50/xl.jpg', 849000, 0, 45, 0, NOW());

    -- ==============================================
    -- 7. PERCENTAGE PROMOTIONS
    -- ==============================================

    INSERT INTO percentage_promotions (id, code, discount_percent, min_order_value, start_at, end_at, quantity, is_active, create_at) VALUES
    (1, 'WELCOME10', 10, 500000, '2024-01-01 00:00:00', '2024-12-31 23:59:59', 1000, true, NOW()),
    (2, 'SUMMER20', 20, 1000000, '2024-06-01 00:00:00', '2024-08-31 23:59:59', 500, true, NOW()),
    (3, 'STUDENT15', 15, 300000, '2024-01-01 00:00:00', '2024-12-31 23:59:59', 2000, true, NOW()),
    (4, 'BLACKFRIDAY30', 30, 1500000, '2024-11-24 00:00:00', '2024-11-30 23:59:59', 300, true, NOW());

    -- ==============================================
    -- 8. AMOUNT PROMOTIONS
    -- ==============================================

    INSERT INTO amount_promotions (id, code, discount_amount, min_order_value, start_at, end_at, quantity, is_active, create_at) VALUES
    (1, 'NEWYEAR50', 50000, 2000000, '2024-01-01 00:00:00', '2024-01-31 23:59:59', 200, true, NOW()),
    (2, 'FREESHIP30', 30000, 500000, '2024-01-01 00:00:00', '2024-12-31 23:59:59', 5000, true, NOW());

    -- ==============================================
    -- 9. ORDERS (Sample orders for testing dashboard)
    -- ==============================================

    -- This will use customers created by ApplicationInitConfig
    INSERT INTO orders (id, order_code, customer_id, total_amount, sub_total, percentage_promotion_id, amount_promotion_id, promotion_value, order_status, payment_method, shipping_address_id, phone, create_at, update_at)
    SELECT 
        ROW_NUMBER() OVER (ORDER BY u.id) AS id,
        CONCAT('ORD', DATE_FORMAT(NOW(), '%Y%m%d'), LPAD(ROW_NUMBER() OVER (ORDER BY u.id), 4, '0')) AS order_code,
        c.user_id AS customer_id,
        CASE 
            WHEN ROW_NUMBER() OVER (ORDER BY u.id) % 4 = 1 THEN 1299000
            WHEN ROW_NUMBER() OVER (ORDER BY u.id) % 4 = 2 THEN 799000
            WHEN ROW_NUMBER() OVER (ORDER BY u.id) % 4 = 3 THEN 599000
            ELSE 399000
        END AS total_amount,
        CASE 
            WHEN ROW_NUMBER() OVER (ORDER BY u.id) % 4 = 1 THEN 1169100
            WHEN ROW_NUMBER() OVER (ORDER BY u.id) % 4 = 2 THEN 679150
            WHEN ROW_NUMBER() OVER (ORDER BY u.id) % 4 = 3 THEN 549100
            ELSE 349000
        END AS sub_total,
        CASE WHEN ROW_NUMBER() OVER (ORDER BY u.id) % 3 = 0 THEN 1 ELSE NULL END AS percentage_promotion_id,
        NULL AS amount_promotion_id,
        CASE WHEN ROW_NUMBER() OVER (ORDER BY u.id) % 3 = 0 THEN 100000 ELSE 0 END AS promotion_value,
        CASE 
            WHEN ROW_NUMBER() OVER (ORDER BY u.id) <= 3 THEN 'PENDING'
            WHEN ROW_NUMBER() OVER (ORDER BY u.id) <= 5 THEN 'PREPARING'
            WHEN ROW_NUMBER() OVER (ORDER BY u.id) <= 8 THEN 'SHIPPED'
            WHEN ROW_NUMBER() OVER (ORDER BY u.id) <= 12 THEN 'DELIVERED'
            ELSE 'CANCELLED'
        END AS order_status,
        CASE 
            WHEN ROW_NUMBER() OVER (ORDER BY u.id) % 3 = 1 THEN 'CASH_ON_DELIVERY'
            WHEN ROW_NUMBER() OVER (ORDER BY u.id) % 3 = 2 THEN 'BANK_TRANSFER'
            ELSE 'E_WALLET'
        END AS payment_method,
        NULL AS shipping_address_id,
        u.phone AS phone,
        DATE_SUB(NOW(), INTERVAL ROW_NUMBER() OVER (ORDER BY u.id) DAY) AS create_at,
        DATE_SUB(NOW(), INTERVAL ROW_NUMBER() OVER (ORDER BY u.id) DAY) AS update_at
    FROM users u
    INNER JOIN customers c ON c.user_id = u.id
    WHERE u.username LIKE 'customer%'
    LIMIT 15;

    -- ==============================================
    -- 10. ORDER ITEMS
    -- ==============================================

    INSERT INTO order_item (order_id, product_variant_id, unit_price, quantity)
    SELECT o.id, pv.id, pv.price, 1
    FROM orders o
    CROSS JOIN (
        SELECT id, price FROM product_variants ORDER BY RAND() LIMIT 15
    ) pv
    LIMIT 15;

    -- ==============================================
    -- 11. ORDER STATUS HISTORY
    -- ==============================================

    INSERT INTO order_status_history (order_id, order_status, message, created_at)
    SELECT 
        o.id,
        'PENDING' AS order_status,
        'Đơn hàng đã được đặt thành công' AS message,
        o.create_at AS created_at
    FROM orders o
    WHERE NOT EXISTS (
        SELECT 1 FROM order_status_history osh WHERE osh.order_id = o.id
    );

    -- ==============================================
    -- 12. PAYMENTS
    -- ==============================================

    INSERT INTO payments (order_id, payment_method, amount, payment_status, transaction_id, payload, create_at)
    SELECT 
        o.id AS order_id,
        o.payment_method,
        o.sub_total AS amount,
        CASE 
            WHEN o.order_status IN ('DELIVERED', 'SHIPPED') THEN 'PAID'
            WHEN o.order_status IN ('PENDING', 'PREPARING') THEN 'PENDING'
            ELSE 'CANCELLED'
        END AS payment_status,
        CONCAT('TXN', DATE_FORMAT(NOW(), '%Y%m%d'), LPAD(o.id, 3, '0')) AS transaction_id,
        CONCAT('{"method": "', LOWER(o.payment_method), '"}') AS payload,
        o.create_at AS create_at
    FROM orders o
    WHERE o.order_status IN ('DELIVERED', 'SHIPPED')
    LIMIT 7;

    -- ==============================================
    -- 13. SHIPMENTS
    -- ==============================================

    INSERT INTO shipments (order_id, carrier, tracking_number, status, shipped_at, expected_delivery_at, create_at)
    SELECT 
        o.id AS order_id,
        CASE (ROW_NUMBER() OVER (ORDER BY o.id) % 3)
            WHEN 0 THEN 'GHTK'
            WHEN 1 THEN 'Viettel Post'
            ELSE 'Grab Express'
        END AS carrier,
        CONCAT(
            CASE (ROW_NUMBER() OVER (ORDER BY o.id) % 3)
                WHEN 0 THEN 'GHTK'
                WHEN 1 THEN 'VT'
                ELSE 'GRB'
            END,
            DATE_FORMAT(o.create_at, '%y%m%d'),
            LPAD(ROW_NUMBER() OVER (ORDER BY o.id), 3, '0')
        ) AS tracking_number,
        CASE 
            WHEN o.order_status = 'DELIVERED' THEN 'DELIVERED'
            WHEN o.order_status = 'SHIPPED' THEN 'IN_TRANSIT'
            ELSE 'PENDING'
        END AS status,
        DATE_ADD(o.create_at, INTERVAL 1 DAY) AS shipped_at,
        DATE_ADD(o.create_at, INTERVAL 3 DAY) AS expected_delivery_at,
        o.create_at AS create_at
    FROM orders o
    WHERE o.order_status IN ('DELIVERED', 'SHIPPED')
    LIMIT 7;

    -- ==============================================
    -- 14. REVIEWS
    -- ==============================================

    INSERT INTO reviews (product_id, customer_id, order_item_id, rating, comment, create_at)
    SELECT 
        pv.product_id,
        o.customer_id,
        oi.id AS order_item_id,
        FLOOR(3 + RAND() * 3) AS rating,
        CASE FLOOR(1 + RAND() * 5)
            WHEN 1 THEN 'Sản phẩm rất đẹp, chất lượng tốt!'
            WHEN 2 THEN 'Giao hàng nhanh, đúng mô tả.'
            WHEN 3 THEN 'Chất lượng ổn, giá cả hợp lý.'
            WHEN 4 THEN 'Rất hài lòng với sản phẩm này!'
            ELSE 'Không như mong đợi, nhưng vẫn chấp nhận được.'
        END AS comment,
        DATE_ADD(o.update_at, INTERVAL FLOOR(RAND() * 5) DAY) AS create_at
    FROM order_item oi
    INNER JOIN orders o ON o.id = oi.order_id
    INNER JOIN product_variants pv ON pv.id = oi.product_variant_id
    WHERE o.order_status = 'DELIVERED'
    LIMIT 10;

    -- ==============================================
    -- END OF SAMPLE DATA
    -- ==============================================
