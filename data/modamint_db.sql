INSERT INTO brands (id, name, description, logo_url, active)
VALUES
    (1, 'Nike', 'Thương hiệu thể thao hàng đầu thế giới', 'https://logo.com/nike.png', true),
    (2, 'Adidas', 'Thương hiệu thời trang thể thao nổi tiếng', 'https://logo.com/adidas.png', true);

INSERT INTO categories (id, name, is_active)
VALUES
    (1, 'Giày thể thao', true),
    (2, 'Áo thun', true);
INSERT INTO products (id, name, brand_id, category_id, description, active, create_at, update_at)
VALUES
    (1, 'Giày Nike Air Zoom', 1, 1, 'Giày chạy bộ cao cấp', true, NOW(), NOW()),
    (2, 'Áo Adidas Originals', 2, 2, 'Áo thun cotton 100%', true, NOW(), NOW());

INSERT INTO product_variants (id, product_id, size, color, price, discount, quantity, additional_price, create_at)
VALUES
    (1, 1, '42', 'Trắng', 2500000, 0, 50, 0, NOW()),
    (2, 1, '43', 'Đen', 2500000, 200000, 30, 0, NOW()),
    (3, 2, 'L', 'Trắng', 800000, 0, 100, 0, NOW());

INSERT INTO product_image (id, product_id, variant_id, url)
VALUES
    (1, 1, NULL, 'https://example.com/airzoom1.jpg'),
    (2, 1, NULL, 'https://example.com/airzoom2.jpg'),
    (3, 2, NULL, 'https://example.com/adidas1.jpg');



INSERT INTO users (id, username, email, password, active)
VALUES
    ('u001', 'john_doe', 'john@example.com', '123456', true),
    ('u002', 'anna_smith', 'anna@example.com', '123456', true);

INSERT INTO customers (user_id, loyalty_points)
VALUES
    ('u001', 120),
    ('u002', 80);

INSERT INTO  addresses(id, city, ward, address_detail, customer_id)
VALUES
    (1, 'Hà Nội', 'Cầu Giấy', '123 Đường Trần Duy Hưng', 'u001'),
    (2, 'TP.HCM', 'Quận 1', '45 Nguyễn Huệ', 'u002');


INSERT INTO cart (id, customer_id, session_id, update_at, create_at)
VALUES
    (1, 'u001', 'session123', NOW(), NOW()),
    (2, 'u002', 'session456', NOW(), NOW());

INSERT INTO cart_item (id, cart_id, variant_id, quantity)
VALUES
    (1, 1, 1, 2),
    (2, 1, 3, 1),
    (3, 2, 2, 1);

INSERT INTO promotions (id, code, value, min_order_value, start_at, end_at, quantity, is_active, create_at)
VALUES
    (1, 'WELCOME10', '10%', 500000, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY), 100, true, NOW());

INSERT INTO type_promotions (id, promotion_id, type)
VALUES
    (1, 1, 'PERCENTAGE');

INSERT INTO orders (id, order_code, customer_id, total_amount, sub_total, promotion_id, promotion_value, order_status, payment_status, shipping_address_id, phone, update_at, create_at)
VALUES
    (1, 'ORD001', 'u001', 2700000, 2500000, 1, 200000, 'SHIPPED', 'BANK_TRANSFER', 1, '0987654321', NOW(), NOW());

INSERT INTO order_item (id, order_id, product_variant_id, unit_price, quantity)
VALUES
    (1, 1, 1, 2500000, 1);
INSERT INTO payments (id, order_id, method, amount, status, transaction_id, payload, create_at)
VALUES
    (1, 1, 'COD', 2500000, 'PAID', 'TRANS123', 'Thanh toán khi nhận hàng', NOW());
INSERT INTO shipments (id, order_id, carrier, tracking_number, status, shipped_at, expected_delivery_at, create_at)
VALUES
    (1, 1, 'Giao Hàng Nhanh', 'GHN001', 'DELIVERED', NOW(), DATE_ADD(NOW(), INTERVAL 2 DAY), NOW());
INSERT INTO reviews (id, product_id, customer_id, order_item_id, rating, comment, create_at)
VALUES
    (1, 1, 'u001', 1, 5, 'Giày chạy cực êm và nhẹ!', NOW());

select * from products