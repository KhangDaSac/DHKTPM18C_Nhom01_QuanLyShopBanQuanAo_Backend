# HƯỚNG DẪN FIX LỖI GIỎ HÀNG

## ⚠️ VẤN ĐỀ

Lỗi: `Cannot add or update a child row: a foreign key constraint fails (customer_id)`

## 🔍 NGUYÊN NHÂN

1. Database đang dùng `ddl-auto: create-drop` → **Mỗi lần restart sẽ XÓA TOÀN BỘ dữ liệu**
2. Các user cũ trong database không có Customer record
3. Khi thêm vào giỏ hàng, Cart yêu cầu customer_id phải tồn tại

## ✅ GIẢI PHÁP NHANH (3 BƯỚC)

### Bước 1: RESTART Backend
```bash
# Stop backend hiện tại (Ctrl+C)
# Khởi động lại backend
```

**LÝ DO**: Code đã được fix, cần restart để áp dụng thay đổi

### Bước 2: ĐĂNG XUẤT và ĐĂNG NHẬP LẠI
1. Mở trình duyệt
2. Đăng xuất khỏi hệ thống
3. **Đăng nhập lại** (Google OAuth hoặc username/password)

**LÝ DO**: Khi đăng nhập lại, code mới sẽ tự động tạo Customer record

### Bước 3: TEST - Thêm sản phẩm vào giỏ hàng
1. Chọn một sản phẩm
2. Nhấn "Thêm vào giỏ hàng"
3. ✅ **THÀNH CÔNG!** - Không còn lỗi

---

## 🗄️ NẾU CẦN GIỮ DỮ LIỆU (Không bị xóa khi restart)

### Option 1: Thay đổi application.yaml (KHUYÊN DÙNG cho PRODUCTION)

Sửa file `application.yaml`:

```yaml
jpa:
  hibernate:
    ddl-auto: update  # Thay vì create-drop
```

**Các giá trị có thể dùng:**
- `update`: Tự động cập nhật schema, **GIỮ dữ liệu**
- `validate`: Chỉ validate, không thay đổi DB
- `create-drop`: Xóa và tạo lại mỗi khi restart (ĐANG DÙNG)
- `create`: Xóa và tạo lại, nhưng không xóa khi shutdown
- `none`: Không làm gì

### Option 2: Chạy script SQL để tạo Customer cho users hiện có

**Nếu database đã có users nhưng thiếu Customer records:**

```sql
-- Chạy script này trong MySQL/MariaDB

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

-- Kiểm tra kết quả
SELECT COUNT(*) as total_customers FROM customers;
```

---

## 📝 TÓM TẮT CODE ĐÃ FIX

### Files đã sửa:
1. ✅ `Customer.java` - Xóa @GeneratedValue để dùng User ID
2. ✅ `AuthenticationService.java` - Tự động tạo Customer khi login
3. ✅ `UserService.java` - Tạo Customer khi register
4. ✅ `CartServiceImpl.java` - Đảm bảo Customer tồn tại trước khi tạo Cart

### Các trường hợp đã xử lý:
- ✅ Đăng nhập Google OAuth → Tạo Customer tự động
- ✅ Đăng nhập username/password → Tạo Customer tự động
- ✅ Đăng ký user mới → Tạo Customer tự động
- ✅ Thêm vào giỏ hàng → Kiểm tra và tạo Customer nếu cần

---

## 🐛 TROUBLESHOOTING

### Vẫn còn lỗi sau khi restart?

**Kiểm tra 1**: Đã đăng nhập lại chưa?
```
→ Phải ĐĂNG XUẤT và ĐĂNG NHẬP LẠI để tạo Customer record mới
```

**Kiểm tra 2**: Backend đã restart với code mới chưa?
```
→ Phải stop và start lại backend
```

**Kiểm tra 3**: Kiểm tra database
```sql
-- Kiểm tra user có Customer record không
SELECT u.id, u.username, u.email, c.customer_id
FROM users u
LEFT JOIN customers c ON c.customer_id = u.id
WHERE u.username = 'YOUR_USERNAME';

-- Nếu customer_id = NULL → Chưa có Customer record
-- → Cần đăng nhập lại
```

**Kiểm tra 4**: Xem log backend
```
→ Tìm dòng log: "Customer record created successfully for userId: ..."
→ Nếu không có → Code chưa được áp dụng
```

---

## 🎯 QUICK FIX CHECKLIST

- [ ] Backend đã được restart với code mới
- [ ] Đã đăng xuất khỏi frontend
- [ ] Đã đăng nhập lại
- [ ] Test thêm sản phẩm vào giỏ hàng
- [ ] ✅ THÀNH CÔNG!

---

## 📞 HỖ TRỢ

Nếu vẫn gặp lỗi, hãy kiểm tra:
1. Log backend có dòng "Customer record created successfully"?
2. Database có table `customers` không?
3. User có role CUSTOMER không?

**Database check:**
```sql
SELECT * FROM users WHERE username = 'YOUR_USERNAME';
SELECT * FROM user_role WHERE user_id = 'USER_ID_FROM_ABOVE';
SELECT * FROM customers WHERE customer_id = 'USER_ID_FROM_ABOVE';
```

---

**Ngày tạo**: 06/12/2025  
**Version**: 1.0
