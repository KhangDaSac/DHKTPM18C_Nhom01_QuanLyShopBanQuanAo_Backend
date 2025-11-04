-- Fix: Cập nhật product_images với URLs ảnh thật
-- Sử dụng ảnh từ Unsplash (nguồn ảnh miễn phí chất lượng cao)

-- Xóa dữ liệu ảnh cũ (URLs giả)
DELETE FROM product_image;

-- Thêm ảnh thật cho các sản phẩm
INSERT INTO product_image (id, product_id, variant_id, url) VALUES
-- Product 1: Áo Thun Nam Basic (Trắng)
(1, 1, NULL, 'https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=500'),
(2, 1, NULL, 'https://images.unsplash.com/photo-1583743814966-8936f5b7be1a?w=500'),
(3, 1, NULL, 'https://images.unsplash.com/photo-1622445275463-afa2ab738c34?w=500'),

-- Product 2: Áo Thun Nam Basic (Đen)
(4, 2, NULL, 'https://images.unsplash.com/photo-1554568218-0f1715e72254?w=500'),
(5, 2, NULL, 'https://images.unsplash.com/photo-1618354691373-d851c5c3a990?w=500'),
(6, 2, NULL, 'https://images.unsplash.com/photo-1503342217505-b0a15ec3261c?w=500'),

-- Product 3: Áo Thun Nam Oversize (Xám)
(7, 3, NULL, 'https://images.unsplash.com/photo-1576566588028-4147f3842f27?w=500'),
(8, 3, NULL, 'https://images.unsplash.com/photo-1562157873-818bc0726f68?w=500'),
(9, 3, NULL, 'https://images.unsplash.com/photo-1529374255404-311a2a4f1fd9?w=500'),

-- Product 4: Áo Polo Nam (Xanh Navy)
(10, 4, NULL, 'https://images.unsplash.com/photo-1586790170083-2f9ceadc732d?w=500'),
(11, 4, NULL, 'https://images.unsplash.com/photo-1607345366928-199ea26cfe3e?w=500'),
(12, 4, NULL, 'https://images.unsplash.com/photo-1626497764746-6dc36546b388?w=500'),

-- Product 5: Áo Sơ Mi Nam Dài Tay (Trắng)
(13, 5, NULL, 'https://images.unsplash.com/photo-1602810318383-e386cc2a3ccf?w=500'),
(14, 5, NULL, 'https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=500'),
(15, 5, NULL, 'https://images.unsplash.com/photo-1603252109303-2751441dd157?w=500'),

-- Product 6: Áo Sơ Mi Nam Ngắn Tay (Xanh Dương)
(16, 6, NULL, 'https://images.unsplash.com/photo-1620012253295-c15cc3e65df4?w=500'),
(17, 6, NULL, 'https://images.unsplash.com/photo-1606346768481-9fe27a4c7e07?w=500'),
(18, 6, NULL, 'https://images.unsplash.com/photo-1584370848010-d7fe6bc767ec?w=500'),

-- Product 7: Quần Jean Nam Slim Fit (Xanh Đậm)
(19, 7, NULL, 'https://images.unsplash.com/photo-1542272604-787c3835535d?w=500'),
(20, 7, NULL, 'https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?w=500'),
(21, 7, NULL, 'https://images.unsplash.com/photo-1605518216938-7c31b7b14ad0?w=500'),

-- Product 8: Quần Jean Nam Baggy (Xanh Nhạt)
(22, 8, NULL, 'https://images.unsplash.com/photo-1541099649105-f69ad21f3246?w=500'),
(23, 8, NULL, 'https://images.unsplash.com/photo-1598522325074-042db73aa4e6?w=500'),
(24, 8, NULL, 'https://images.unsplash.com/photo-1604176354204-9268737828e4?w=500'),

-- Product 9: Quần Kaki Nam Slim (Be)
(25, 9, NULL, 'https://images.unsplash.com/photo-1473966968600-fa801b869a1a?w=500'),
(26, 9, NULL, 'https://images.unsplash.com/photo-1506629082955-511b1aa562c8?w=500'),
(27, 9, NULL, 'https://images.unsplash.com/photo-1624378440070-ada0d6f69a21?w=500'),

-- Product 10: Quần Kaki Nam Regular (Xanh Rêu)
(28, 10, NULL, 'https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?w=500'),
(29, 10, NULL, 'https://images.unsplash.com/photo-1617137968427-85924c800a22?w=500'),
(30, 10, NULL, 'https://images.unsplash.com/photo-1591047139829-d91aecb6caea?w=500'),

-- Product 11: Áo Khoác Bomber Nam (Đen)
(31, 11, NULL, 'https://images.unsplash.com/photo-1551028719-00167b16eac5?w=500'),
(32, 11, NULL, 'https://images.unsplash.com/photo-1591047139829-d91aecb6caea?w=500'),
(33, 11, NULL, 'https://images.unsplash.com/photo-1548126032-079166fcdc39?w=500'),

-- Product 12: Áo Khoác Jean Nam (Xanh Đậm)
(34, 12, NULL, 'https://images.unsplash.com/photo-1551537482-f2075a1d41f2?w=500'),
(35, 12, NULL, 'https://images.unsplash.com/photo-1594938298603-c8148c4dae35?w=500'),
(36, 12, NULL, 'https://images.unsplash.com/photo-1601333144130-8cbb312386b6?w=500'),

-- Product 13: Áo Hoodie Nam (Xám)
(37, 13, NULL, 'https://images.unsplash.com/photo-1556821840-3a63f95609a7?w=500'),
(38, 13, NULL, 'https://images.unsplash.com/photo-1578587018452-892bacefd3f2?w=500'),
(39, 13, NULL, 'https://images.unsplash.com/photo-1620799140408-edc6dcb6d633?w=500'),

-- Product 14: Áo Hoodie Nam (Đen)
(40, 14, NULL, 'https://images.unsplash.com/photo-1620799139834-6b8f844fbe61?w=500'),
(41, 14, NULL, 'https://images.unsplash.com/photo-1578681994506-b8f463449011?w=500'),
(42, 14, NULL, 'https://images.unsplash.com/photo-1556821840-3a63f95609a7?w=500'),

-- Product 15: Quần Short Jean Nam (Xanh Nhạt)
(43, 15, NULL, 'https://images.unsplash.com/photo-1591195853828-11db59a44f6b?w=500'),
(44, 15, NULL, 'https://images.unsplash.com/photo-1598554747436-c9293d6a588f?w=500'),
(45, 15, NULL, 'https://images.unsplash.com/photo-1624378441864-6eda7eac51cb?w=500');

-- Verify: Kiểm tra ảnh đã được thêm
SELECT p.id, p.name, COUNT(pi.id) as image_count, GROUP_CONCAT(pi.url SEPARATOR '\n') as urls
FROM products p
LEFT JOIN product_image pi ON p.id = pi.product_id
WHERE p.id <= 15
GROUP BY p.id, p.name
ORDER BY p.id;
