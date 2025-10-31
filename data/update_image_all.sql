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
UPDATE products SET images = 'https://picsum.photos/seed/1010/800/1000|https://picsum.photos/seed/1011/800/1000|https://picsum.photos/seed/1012/800/1000|https://picsum.photos/seed/1013/800/1000' WHERE id = 1;

-- Áo Sơ Mi Xanh Nam
UPDATE products SET images = 'https://picsum.photos/seed/1020/800/1000|https://picsum.photos/seed/1021/800/1000|https://picsum.photos/seed/1022/800/1000|https://picsum.photos/seed/1023/800/1000' WHERE id = 2;

-- Áo Thun Basic Nam
UPDATE products SET images = 'https://picsum.photos/seed/2030/800/1000|https://picsum.photos/seed/2031/800/1000|https://picsum.photos/seed/2032/800/1000|https://picsum.photos/seed/2033/800/1000' WHERE id = 3;

-- Áo Thun Polo Nam
UPDATE products SET images = 'https://picsum.photos/seed/2040/800/1000|https://picsum.photos/seed/2041/800/1000|https://picsum.photos/seed/2042/800/1000|https://picsum.photos/seed/2043/800/1000' WHERE id = 4;

-- Áo Khoác Bomber Nam
UPDATE products SET images = 'https://picsum.photos/seed/3050/800/1000|https://picsum.photos/seed/3051/800/1000|https://picsum.photos/seed/3052/800/1000|https://picsum.photos/seed/3053/800/1000' WHERE id = 5;

-- Áo Khoác Hoodie Nam
UPDATE products SET images = 'https://picsum.photos/seed/3060/800/1000|https://picsum.photos/seed/3061/800/1000|https://picsum.photos/seed/3062/800/1000|https://picsum.photos/seed/3063/800/1000' WHERE id = 6;

-- Áo Vest Nam
UPDATE products SET images = 'https://picsum.photos/seed/4070/800/1000|https://picsum.photos/seed/4071/800/1000|https://picsum.photos/seed/4072/800/1000|https://picsum.photos/seed/4073/800/1000' WHERE id = 7;

-- Áo Vest Kẻ Sọc Nam
UPDATE products SET images = 'https://picsum.photos/seed/4080/800/1000|https://picsum.photos/seed/4081/800/1000|https://picsum.photos/seed/4082/800/1000|https://picsum.photos/seed/4083/800/1000' WHERE id = 8;

-- Áo Sơ Mi Kẻ Sọc Nam
UPDATE products SET images = 'https://picsum.photos/seed/1090/800/1000|https://picsum.photos/seed/1091/800/1000|https://picsum.photos/seed/1092/800/1000|https://picsum.photos/seed/1093/800/1000' WHERE id = 9;

-- Áo Thun Graphic Nam
UPDATE products SET images = 'https://picsum.photos/seed/2100/800/1000|https://picsum.photos/seed/2101/800/1000|https://picsum.photos/seed/2102/800/1000|https://picsum.photos/seed/2103/800/1000' WHERE id = 10;

-- Áo Khoác Denim Nam
UPDATE products SET images = 'https://picsum.photos/seed/3110/800/1000|https://picsum.photos/seed/3111/800/1000|https://picsum.photos/seed/3112/800/1000|https://picsum.photos/seed/3113/800/1000' WHERE id = 11;

-- Áo Khoác Blazer Nam
UPDATE products SET images = 'https://picsum.photos/seed/3120/800/1000|https://picsum.photos/seed/3121/800/1000|https://picsum.photos/seed/3122/800/1000|https://picsum.photos/seed/3123/800/1000' WHERE id = 12;

-- Áo Sơ Mi Flannel Nam
UPDATE products SET images = 'https://picsum.photos/seed/1130/800/1000|https://picsum.photos/seed/1131/800/1000|https://picsum.photos/seed/1132/800/1000|https://picsum.photos/seed/1133/800/1000' WHERE id = 13;

-- Áo Thun Tank Top Nam
UPDATE products SET images = 'https://picsum.photos/seed/2140/800/1000|https://picsum.photos/seed/2141/800/1000|https://picsum.photos/seed/2142/800/1000|https://picsum.photos/seed/2143/800/1000' WHERE id = 14;

-- Áo Vest 3 Mảnh Nam
UPDATE products SET images = 'https://picsum.photos/seed/4150/800/1000|https://picsum.photos/seed/4151/800/1000|https://picsum.photos/seed/4152/800/1000|https://picsum.photos/seed/4153/800/1000' WHERE id = 15;

-- Áo Sơ Mi Trắng Nữ
UPDATE products SET images = 'https://picsum.photos/seed/5160/800/1000|https://picsum.photos/seed/5161/800/1000|https://picsum.photos/seed/5162/800/1000|https://picsum.photos/seed/5163/800/1000' WHERE id = 16;

-- Áo Sơ Mi Hoa Nữ
UPDATE products SET images = 'https://picsum.photos/seed/5170/800/1000|https://picsum.photos/seed/5171/800/1000|https://picsum.photos/seed/5172/800/1000|https://picsum.photos/seed/5173/800/1000' WHERE id = 17;

-- Áo Thun Basic Nữ
UPDATE products SET images = 'https://picsum.photos/seed/6180/800/1000|https://picsum.photos/seed/6181/800/1000|https://picsum.photos/seed/6182/800/1000|https://picsum.photos/seed/6183/800/1000' WHERE id = 18;

-- Áo Thun Crop Top Nữ
UPDATE products SET images = 'https://picsum.photos/seed/6190/800/1000|https://picsum.photos/seed/6191/800/1000|https://picsum.photos/seed/6192/800/1000|https://picsum.photos/seed/6193/800/1000' WHERE id = 19;

-- Áo Khoác Cardigan Nữ
UPDATE products SET images = 'https://picsum.photos/seed/7200/800/1000|https://picsum.photos/seed/7201/800/1000|https://picsum.photos/seed/7202/800/1000|https://picsum.photos/seed/7203/800/1000' WHERE id = 20;

-- Áo Khoác Blazer Nữ
UPDATE products SET images = 'https://picsum.photos/seed/7210/800/1000|https://picsum.photos/seed/7211/800/1000|https://picsum.photos/seed/7212/800/1000|https://picsum.photos/seed/7213/800/1000' WHERE id = 21;

-- Đầm Dài Nữ
UPDATE products SET images = 'https://picsum.photos/seed/8220/800/1000|https://picsum.photos/seed/8221/800/1000|https://picsum.photos/seed/8222/800/1000|https://picsum.photos/seed/8223/800/1000' WHERE id = 22;

-- Đầm Ngắn Nữ
UPDATE products SET images = 'https://picsum.photos/seed/8230/800/1000|https://picsum.photos/seed/8231/800/1000|https://picsum.photos/seed/8232/800/1000|https://picsum.photos/seed/8233/800/1000' WHERE id = 23;

-- Áo Sơ Mi Oversize Nữ
UPDATE products SET images = 'https://picsum.photos/seed/5240/800/1000|https://picsum.photos/seed/5241/800/1000|https://picsum.photos/seed/5242/800/1000|https://picsum.photos/seed/5243/800/1000' WHERE id = 24;

-- Áo Thun Long Sleeve Nữ
UPDATE products SET images = 'https://picsum.photos/seed/6250/800/1000|https://picsum.photos/seed/6251/800/1000|https://picsum.photos/seed/6252/800/1000|https://picsum.photos/seed/6253/800/1000' WHERE id = 25;

-- Áo Khoác Hoodie Nữ
UPDATE products SET images = 'https://picsum.photos/seed/7260/800/1000|https://picsum.photos/seed/7261/800/1000|https://picsum.photos/seed/7262/800/1000|https://picsum.photos/seed/7263/800/1000' WHERE id = 26;

-- Áo Khoác Bomber Nữ
UPDATE products SET images = 'https://picsum.photos/seed/7270/800/1000|https://picsum.photos/seed/7271/800/1000|https://picsum.photos/seed/7272/800/1000|https://picsum.photos/seed/7273/800/1000' WHERE id = 27;

-- Đầm Maxi Nữ
UPDATE products SET images = 'https://picsum.photos/seed/8280/800/1000|https://picsum.photos/seed/8281/800/1000|https://picsum.photos/seed/8282/800/1000|https://picsum.photos/seed/8283/800/1000' WHERE id = 28;

-- Đầm Mini Nữ
UPDATE products SET images = 'https://picsum.photos/seed/8290/800/1000|https://picsum.photos/seed/8291/800/1000|https://picsum.photos/seed/8292/800/1000|https://picsum.photos/seed/8293/800/1000' WHERE id = 29;

-- Áo Sơ Mi Silk Nữ
UPDATE products SET images = 'https://picsum.photos/seed/5300/800/1000|https://picsum.photos/seed/5301/800/1000|https://picsum.photos/seed/5302/800/1000|https://picsum.photos/seed/5303/800/1000' WHERE id = 30;

-- Quần Jeans Nam
UPDATE products SET images = 'https://picsum.photos/seed/9310/800/1000|https://picsum.photos/seed/9311/800/1000|https://picsum.photos/seed/9312/800/1000|https://picsum.photos/seed/9313/800/1000' WHERE id = 31;

-- Quần Jeans Đen Nam
UPDATE products SET images = 'https://picsum.photos/seed/9320/800/1000|https://picsum.photos/seed/9321/800/1000|https://picsum.photos/seed/9322/800/1000|https://picsum.photos/seed/9323/800/1000' WHERE id = 32;

-- Quần Kaki Nam
UPDATE products SET images = 'https://picsum.photos/seed/10330/800/1000|https://picsum.photos/seed/10331/800/1000|https://picsum.photos/seed/10332/800/1000|https://picsum.photos/seed/10333/800/1000' WHERE id = 33;

-- Quần Kaki Xanh Nam
UPDATE products SET images = 'https://picsum.photos/seed/10340/800/1000|https://picsum.photos/seed/10341/800/1000|https://picsum.photos/seed/10342/800/1000|https://picsum.photos/seed/10343/800/1000' WHERE id = 34;

-- Quần Short Nam
UPDATE products SET images = 'https://picsum.photos/seed/11350/800/1000|https://picsum.photos/seed/11351/800/1000|https://picsum.photos/seed/11352/800/1000|https://picsum.photos/seed/11353/800/1000' WHERE id = 35;

-- Quần Short Thể Thao Nam
UPDATE products SET images = 'https://picsum.photos/seed/11360/800/1000|https://picsum.photos/seed/11361/800/1000|https://picsum.photos/seed/11362/800/1000|https://picsum.photos/seed/11363/800/1000' WHERE id = 36;

-- Quần Jeans Skinny Nam
UPDATE products SET images = 'https://picsum.photos/seed/9370/800/1000|https://picsum.photos/seed/9371/800/1000|https://picsum.photos/seed/9372/800/1000|https://picsum.photos/seed/9373/800/1000' WHERE id = 37;

-- Quần Kaki Cargo Nam
UPDATE products SET images = 'https://picsum.photos/seed/10380/800/1000|https://picsum.photos/seed/10381/800/1000|https://picsum.photos/seed/10382/800/1000|https://picsum.photos/seed/10383/800/1000' WHERE id = 38;

-- Quần Short Bermuda Nam
UPDATE products SET images = 'https://picsum.photos/seed/11390/800/1000|https://picsum.photos/seed/11391/800/1000|https://picsum.photos/seed/11392/800/1000|https://picsum.photos/seed/11393/800/1000' WHERE id = 39;

-- Quần Jeans Ripped Nam
UPDATE products SET images = 'https://picsum.photos/seed/9400/800/1000|https://picsum.photos/seed/9401/800/1000|https://picsum.photos/seed/9402/800/1000|https://picsum.photos/seed/9403/800/1000' WHERE id = 40;

-- Quần Jeans Nữ
UPDATE products SET images = 'https://picsum.photos/seed/12410/800/1000|https://picsum.photos/seed/12411/800/1000|https://picsum.photos/seed/12412/800/1000|https://picsum.photos/seed/12413/800/1000' WHERE id = 41;

-- Quần Jeans Đen Nữ
UPDATE products SET images = 'https://picsum.photos/seed/12420/800/1000|https://picsum.photos/seed/12421/800/1000|https://picsum.photos/seed/12422/800/1000|https://picsum.photos/seed/12423/800/1000' WHERE id = 42;

-- Quần Legging Nữ
UPDATE products SET images = 'https://picsum.photos/seed/13430/800/1000|https://picsum.photos/seed/13431/800/1000|https://picsum.photos/seed/13432/800/1000|https://picsum.photos/seed/13433/800/1000' WHERE id = 43;

-- Quần Legging Thể Thao Nữ
UPDATE products SET images = 'https://picsum.photos/seed/13440/800/1000|https://picsum.photos/seed/13441/800/1000|https://picsum.photos/seed/13442/800/1000|https://picsum.photos/seed/13443/800/1000' WHERE id = 44;

-- Quần Short Nữ
UPDATE products SET images = 'https://picsum.photos/seed/14450/800/1000|https://picsum.photos/seed/14451/800/1000|https://picsum.photos/seed/14452/800/1000|https://picsum.photos/seed/14453/800/1000' WHERE id = 45;

-- Quần Short Denim Nữ
UPDATE products SET images = 'https://picsum.photos/seed/14460/800/1000|https://picsum.photos/seed/14461/800/1000|https://picsum.photos/seed/14462/800/1000|https://picsum.photos/seed/14463/800/1000' WHERE id = 46;

-- Quần Jeans Skinny Nữ
UPDATE products SET images = 'https://picsum.photos/seed/12470/800/1000|https://picsum.photos/seed/12471/800/1000|https://picsum.photos/seed/12472/800/1000|https://picsum.photos/seed/12473/800/1000' WHERE id = 47;

-- Quần Legging Yoga Nữ
UPDATE products SET images = 'https://picsum.photos/seed/13480/800/1000|https://picsum.photos/seed/13481/800/1000|https://picsum.photos/seed/13482/800/1000|https://picsum.photos/seed/13483/800/1000' WHERE id = 48;

-- Quần Short High Waist Nữ
UPDATE products SET images = 'https://picsum.photos/seed/14490/800/1000|https://picsum.photos/seed/14491/800/1000|https://picsum.photos/seed/14492/800/1000|https://picsum.photos/seed/14493/800/1000' WHERE id = 49;

-- Quần Jeans Mom Fit Nữ
UPDATE products SET images = 'https://picsum.photos/seed/12500/800/1000|https://picsum.photos/seed/12501/800/1000|https://picsum.photos/seed/12502/800/1000|https://picsum.photos/seed/12503/800/1000' WHERE id = 50;
-- END product images

-- ========== PRODUCT VARIANTS (Picsum 600x600) ==========
-- Begin: product variant images
-- (Generated previously by fetch_product_images.py)

-- START variant images
-- Script to update product_variants images from Picsum Photos
-- Generated automatically by fetch_product_images.py
-- Image size: 600x600px

UPDATE product_variants SET image = 'https://picsum.photos/seed/15102/600/600' WHERE id = 2;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15103/600/600' WHERE id = 3;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15104/600/600' WHERE id = 4;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15206/600/600' WHERE id = 6;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15207/600/600' WHERE id = 7;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15208/600/600' WHERE id = 8;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15310/600/600' WHERE id = 10;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15311/600/600' WHERE id = 11;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15312/600/600' WHERE id = 12;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15414/600/600' WHERE id = 14;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15415/600/600' WHERE id = 15;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15416/600/600' WHERE id = 16;
UPDATE product_variants SET image = 'https://picsum.photos/seed/3518/600/600' WHERE id = 18;
UPDATE product_variants SET image = 'https://picsum.photos/seed/3519/600/600' WHERE id = 19;
UPDATE product_variants SET image = 'https://picsum.photos/seed/3520/600/600' WHERE id = 20;
UPDATE product_variants SET image = 'https://picsum.photos/seed/3622/600/600' WHERE id = 22;
UPDATE product_variants SET image = 'https://picsum.photos/seed/3623/600/600' WHERE id = 23;
UPDATE product_variants SET image = 'https://picsum.photos/seed/3624/600/600' WHERE id = 24;
UPDATE product_variants SET image = 'https://picsum.photos/seed/4726/600/600' WHERE id = 26;
UPDATE product_variants SET image = 'https://picsum.photos/seed/4727/600/600' WHERE id = 27;
UPDATE product_variants SET image = 'https://picsum.photos/seed/4728/600/600' WHERE id = 28;
UPDATE product_variants SET image = 'https://picsum.photos/seed/4830/600/600' WHERE id = 30;
UPDATE product_variants SET image = 'https://picsum.photos/seed/4831/600/600' WHERE id = 31;
UPDATE product_variants SET image = 'https://picsum.photos/seed/4832/600/600' WHERE id = 32;
UPDATE product_variants SET image = 'https://picsum.photos/seed/1934/600/600' WHERE id = 34;
UPDATE product_variants SET image = 'https://picsum.photos/seed/1935/600/600' WHERE id = 35;
UPDATE product_variants SET image = 'https://picsum.photos/seed/1936/600/600' WHERE id = 36;
UPDATE product_variants SET image = 'https://picsum.photos/seed/3038/600/600' WHERE id = 38;
UPDATE product_variants SET image = 'https://picsum.photos/seed/3039/600/600' WHERE id = 39;
UPDATE product_variants SET image = 'https://picsum.photos/seed/3040/600/600' WHERE id = 40;
UPDATE product_variants SET image = 'https://picsum.photos/seed/4141/600/600' WHERE id = 41;
UPDATE product_variants SET image = 'https://picsum.photos/seed/4142/600/600' WHERE id = 42;
UPDATE product_variants SET image = 'https://picsum.photos/seed/4143/600/600' WHERE id = 43;
UPDATE product_variants SET image = 'https://picsum.photos/seed/4144/600/600' WHERE id = 44;
UPDATE product_variants SET image = 'https://picsum.photos/seed/4245/600/600' WHERE id = 45;
UPDATE product_variants SET image = 'https://picsum.photos/seed/4246/600/600' WHERE id = 46;
UPDATE product_variants SET image = 'https://picsum.photos/seed/4247/600/600' WHERE id = 47;
UPDATE product_variants SET image = 'https://picsum.photos/seed/4248/600/600' WHERE id = 48;
UPDATE product_variants SET image = 'https://picsum.photos/seed/2349/600/600' WHERE id = 49;
UPDATE product_variants SET image = 'https://picsum.photos/seed/2350/600/600' WHERE id = 50;
UPDATE product_variants SET image = 'https://picsum.photos/seed/2351/600/600' WHERE id = 51;
UPDATE product_variants SET image = 'https://picsum.photos/seed/2352/600/600' WHERE id = 52;
UPDATE product_variants SET image = 'https://picsum.photos/seed/3453/600/600' WHERE id = 53;
UPDATE product_variants SET image = 'https://picsum.photos/seed/3454/600/600' WHERE id = 54;
UPDATE product_variants SET image = 'https://picsum.photos/seed/3455/600/600' WHERE id = 55;
UPDATE product_variants SET image = 'https://picsum.photos/seed/3456/600/600' WHERE id = 56;
UPDATE product_variants SET image = 'https://picsum.photos/seed/5557/600/600' WHERE id = 57;
UPDATE product_variants SET image = 'https://picsum.photos/seed/5558/600/600' WHERE id = 58;
UPDATE product_variants SET image = 'https://picsum.photos/seed/5559/600/600' WHERE id = 59;
UPDATE product_variants SET image = 'https://picsum.photos/seed/5560/600/600' WHERE id = 60;
UPDATE product_variants SET image = 'https://picsum.photos/seed/6661/600/600' WHERE id = 61;
UPDATE product_variants SET image = 'https://picsum.photos/seed/6662/600/600' WHERE id = 62;
UPDATE product_variants SET image = 'https://picsum.photos/seed/6663/600/600' WHERE id = 63;
UPDATE product_variants SET image = 'https://picsum.photos/seed/6664/600/600' WHERE id = 64;
UPDATE product_variants SET image = 'https://picsum.photos/seed/6765/600/600' WHERE id = 65;
UPDATE product_variants SET image = 'https://picsum.photos/seed/6766/600/600' WHERE id = 66;
UPDATE product_variants SET image = 'https://picsum.photos/seed/6767/600/600' WHERE id = 67;
UPDATE product_variants SET image = 'https://picsum.photos/seed/6768/600/600' WHERE id = 68;
UPDATE product_variants SET image = 'https://picsum.photos/seed/7869/600/600' WHERE id = 69;
UPDATE product_variants SET image = 'https://picsum.photos/seed/7870/600/600' WHERE id = 70;
UPDATE product_variants SET image = 'https://picsum.photos/seed/7871/600/600' WHERE id = 71;
UPDATE product_variants SET image = 'https://picsum.photos/seed/7872/600/600' WHERE id = 72;
UPDATE product_variants SET image = 'https://picsum.photos/seed/7973/600/600' WHERE id = 73;
UPDATE product_variants SET image = 'https://picsum.photos/seed/7974/600/600' WHERE id = 74;
UPDATE product_variants SET image = 'https://picsum.photos/seed/7975/600/600' WHERE id = 75;
UPDATE product_variants SET image = 'https://picsum.photos/seed/7976/600/600' WHERE id = 76;
UPDATE product_variants SET image = 'https://picsum.photos/seed/9077/600/600' WHERE id = 77;
UPDATE product_variants SET image = 'https://picsum.photos/seed/9078/600/600' WHERE id = 78;
UPDATE product_variants SET image = 'https://picsum.photos/seed/9079/600/600' WHERE id = 79;
UPDATE product_variants SET image = 'https://picsum.photos/seed/9080/600/600' WHERE id = 80;
UPDATE product_variants SET image = 'https://picsum.photos/seed/9181/600/600' WHERE id = 81;
UPDATE product_variants SET image = 'https://picsum.photos/seed/9182/600/600' WHERE id = 82;
UPDATE product_variants SET image = 'https://picsum.photos/seed/9183/600/600' WHERE id = 83;
UPDATE product_variants SET image = 'https://picsum.photos/seed/9184/600/600' WHERE id = 84;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10285/600/600' WHERE id = 85;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10286/600/600' WHERE id = 86;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10287/600/600' WHERE id = 87;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10288/600/600' WHERE id = 88;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10389/600/600' WHERE id = 89;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10390/600/600' WHERE id = 90;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10391/600/600' WHERE id = 91;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10392/600/600' WHERE id = 92;
UPDATE product_variants SET image = 'https://picsum.photos/seed/7493/600/600' WHERE id = 93;
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
UPDATE product_variants SET image = 'https://picsum.photos/seed/9805/600/600' WHERE id = 105;
UPDATE product_variants SET image = 'https://picsum.photos/seed/9806/600/600' WHERE id = 106;
UPDATE product_variants SET image = 'https://picsum.photos/seed/9807/600/600' WHERE id = 107;
UPDATE product_variants SET image = 'https://picsum.photos/seed/9808/600/600' WHERE id = 108;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10909/600/600' WHERE id = 109;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10910/600/600' WHERE id = 110;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10911/600/600' WHERE id = 111;
UPDATE product_variants SET image = 'https://picsum.photos/seed/10912/600/600' WHERE id = 112;
UPDATE product_variants SET image = 'https://picsum.photos/seed/11013/600/600' WHERE id = 113;
UPDATE product_variants SET image = 'https://picsum.photos/seed/11014/600/600' WHERE id = 114;
UPDATE product_variants SET image = 'https://picsum.photos/seed/11015/600/600' WHERE id = 115;
UPDATE product_variants SET image = 'https://picsum.photos/seed/11016/600/600' WHERE id = 116;
UPDATE product_variants SET image = 'https://picsum.photos/seed/8117/600/600' WHERE id = 117;
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
UPDATE product_variants SET image = 'https://picsum.photos/seed/14637/600/600' WHERE id = 137;
UPDATE product_variants SET image = 'https://picsum.photos/seed/14638/600/600' WHERE id = 138;
UPDATE product_variants SET image = 'https://picsum.photos/seed/14639/600/600' WHERE id = 139;
UPDATE product_variants SET image = 'https://picsum.photos/seed/14640/600/600' WHERE id = 140;
UPDATE product_variants SET image = 'https://picsum.photos/seed/14741/600/600' WHERE id = 141;
UPDATE product_variants SET image = 'https://picsum.photos/seed/14742/600/600' WHERE id = 142;
UPDATE product_variants SET image = 'https://picsum.photos/seed/14743/600/600' WHERE id = 143;
UPDATE product_variants SET image = 'https://picsum.photos/seed/14744/600/600' WHERE id = 144;
UPDATE product_variants SET image = 'https://picsum.photos/seed/12845/600/600' WHERE id = 145;
UPDATE product_variants SET image = 'https://picsum.photos/seed/12846/600/600' WHERE id = 146;
UPDATE product_variants SET image = 'https://picsum.photos/seed/12847/600/600' WHERE id = 147;
UPDATE product_variants SET image = 'https://picsum.photos/seed/12848/600/600' WHERE id = 148;
UPDATE product_variants SET image = 'https://picsum.photos/seed/13949/600/600' WHERE id = 149;
UPDATE product_variants SET image = 'https://picsum.photos/seed/13950/600/600' WHERE id = 150;
UPDATE product_variants SET image = 'https://picsum.photos/seed/13951/600/600' WHERE id = 151;
UPDATE product_variants SET image = 'https://picsum.photos/seed/13952/600/600' WHERE id = 152;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15053/600/600' WHERE id = 153;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15054/600/600' WHERE id = 154;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15055/600/600' WHERE id = 155;
UPDATE product_variants SET image = 'https://picsum.photos/seed/15056/600/600' WHERE id = 156;
UPDATE product_variants SET image = 'https://picsum.photos/seed/13157/600/600' WHERE id = 157;
UPDATE product_variants SET image = 'https://picsum.photos/seed/13158/600/600' WHERE id = 158;
UPDATE product_variants SET image = 'https://picsum.photos/seed/13159/600/600' WHERE id = 159;
UPDATE product_variants SET image = 'https://picsum.photos/seed/13160/600/600' WHERE id = 160;
UPDATE product_variants SET image = 'https://picsum.photos/seed/16261/600/600' WHERE id = 161;
UPDATE product_variants SET image = 'https://picsum.photos/seed/16262/600/600' WHERE id = 162;
UPDATE product_variants SET image = 'https://picsum.photos/seed/16263/600/600' WHERE id = 163;
UPDATE product_variants SET image = 'https://picsum.photos/seed/16264/600/600' WHERE id = 164;
UPDATE product_variants SET image = 'https://picsum.photos/seed/16365/600/600' WHERE id = 165;
UPDATE product_variants SET image = 'https://picsum.photos/seed/16366/600/600' WHERE id = 166;
UPDATE product_variants SET image = 'https://picsum.photos/seed/16367/600/600' WHERE id = 167;
UPDATE product_variants SET image = 'https://picsum.photos/seed/16368/600/600' WHERE id = 168;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17469/600/600' WHERE id = 169;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17470/600/600' WHERE id = 170;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17471/600/600' WHERE id = 171;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17472/600/600' WHERE id = 172;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17573/600/600' WHERE id = 173;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17574/600/600' WHERE id = 174;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17575/600/600' WHERE id = 175;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17576/600/600' WHERE id = 176;
UPDATE product_variants SET image = 'https://picsum.photos/seed/18677/600/600' WHERE id = 177;
UPDATE product_variants SET image = 'https://picsum.photos/seed/18678/600/600' WHERE id = 178;
UPDATE product_variants SET image = 'https://picsum.photos/seed/18679/600/600' WHERE id = 179;
UPDATE product_variants SET image = 'https://picsum.photos/seed/18680/600/600' WHERE id = 180;
UPDATE product_variants SET image = 'https://picsum.photos/seed/18781/600/600' WHERE id = 181;
UPDATE product_variants SET image = 'https://picsum.photos/seed/18782/600/600' WHERE id = 182;
UPDATE product_variants SET image = 'https://picsum.photos/seed/18783/600/600' WHERE id = 183;
UPDATE product_variants SET image = 'https://picsum.photos/seed/18784/600/600' WHERE id = 184;
UPDATE product_variants SET image = 'https://picsum.photos/seed/16885/600/600' WHERE id = 185;
UPDATE product_variants SET image = 'https://picsum.photos/seed/16886/600/600' WHERE id = 186;
UPDATE product_variants SET image = 'https://picsum.photos/seed/16887/600/600' WHERE id = 187;
UPDATE product_variants SET image = 'https://picsum.photos/seed/16888/600/600' WHERE id = 188;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17989/600/600' WHERE id = 189;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17990/600/600' WHERE id = 190;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17991/600/600' WHERE id = 191;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17992/600/600' WHERE id = 192;
UPDATE product_variants SET image = 'https://picsum.photos/seed/19093/600/600' WHERE id = 193;
UPDATE product_variants SET image = 'https://picsum.photos/seed/19094/600/600' WHERE id = 194;
UPDATE product_variants SET image = 'https://picsum.photos/seed/19095/600/600' WHERE id = 195;
UPDATE product_variants SET image = 'https://picsum.photos/seed/19096/600/600' WHERE id = 196;
UPDATE product_variants SET image = 'https://picsum.photos/seed/17197/600/600' WHERE id = 197;
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
