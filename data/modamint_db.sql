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
UPDATE product_variants SET image = 'https://picsum.photos/seed/10001/600/600' WHERE id = 1;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10002/600/600' WHERE id = 2;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10003/600/600' WHERE id = 3;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10004/600/600' WHERE id = 4;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10005/600/600' WHERE id = 5;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10006/600/600' WHERE id = 6;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10007/600/600' WHERE id = 7;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10008/600/600' WHERE id = 8;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10009/600/600' WHERE id = 9;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10010/600/600' WHERE id = 10;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10011/600/600' WHERE id = 11;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10012/600/600' WHERE id = 12;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10013/600/600' WHERE id = 13;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10014/600/600' WHERE id = 14;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10015/600/600' WHERE id = 15;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10016/600/600' WHERE id = 16;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10017/600/600' WHERE id = 17;
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

