# Email Configuration Guide for ModaMint Backend

## 1. Tạo App Password cho Gmail

Để sử dụng Gmail SMTP server, bạn cần tạo App Password:

### Bước 1: Bật 2-Step Verification
  1. Đăng nhập vào Google Account: 
2. Vào **Security** → **2-Step Verification**
3. Bật tính năng này nếu chưa bật

### Bước 2: Tạo App Password
1. Vào **Security** → **App passwords** (https://myaccount.google.com/apppasswords)
2. Chọn **Select app** → **Mail**
3. Chọn **Select device** → **Other (Custom name)**
4. Nhập tên: "ModaMint Backend"
5. Click **Generate**
6. Copy mã 16 ký tự được tạo ra

## 2. Cấu hình Backend

### Thêm vào file `.env` (tạo mới nếu chưa có):

```env
# Email Configuration
EMAIL_USERNAME=your-email@gmail.com
EMAIL_PASSWORD=your-16-char-app-password
```

**Lưu ý**: 
- `EMAIL_USERNAME`: Email Gmail của bạn
- `EMAIL_PASSWORD`: App Password 16 ký tự (KHÔNG phải mật khẩu Gmail thông thường)

### File `.env` mẫu hoàn chỉnh:

```env
# Database
DB_URL=jdbc:mariadb://localhost:3306/modamint_db
DB_USERNAME=root
DB_PASSWORD=root

# JWT
JWT_SECRET=wDhbSSpxZfV5HKciZOEUlvBExqAuzHmHhLDAuO6PALLAqTP163lNPxLYsUxs3UUI

# Email
EMAIL_USERNAME=modamint.shop@gmail.com
EMAIL_PASSWORD=abcd efgh ijkl mnop

# VNPay
VNPAY_TMN_CODE=your_tmn_code
VNPAY_HASH_SECRET=your_hash_secret
VNPAY_URL=https://sandbox.vnpayment.vn/paymentv2/vpcpay.html
VNPAY_RETURN_URL=http://localhost:5173/payment/return

# Gemini AI
GEMINI_API_KEY=your_gemini_api_key
```

## 3. Test Email Service

### Test với Postman hoặc curl:

```bash
POST http://localhost:8080/api/v1/checkout
Content-Type: application/json

{
  "customerId": "guest_1234567890",
  "shippingAddressId": 1,
  "paymentMethod": "CASH_ON_DELIVERY",
  "phone": "0123456789",
  "isGuest": true,
  "guestName": "Nguyen Van A",
  "guestEmail": "test@example.com",
  "guestCartItems": [
    {
      "variantId": 1,
      "quantity": 2,
      "unitPrice": 100000
    }
  ]
}
```

## 4. Troubleshooting

### Lỗi: "Authentication failed"
- Kiểm tra lại Email và App Password
- Đảm bảo 2-Step Verification đã bật
- Tạo lại App Password mới

### Lỗi: "Connection timeout"
- Kiểm tra firewall/antivirus có block port 587
- Thử đổi port thành 465 và sử dụng SSL:
  ```yaml
  spring:
    mail:
      port: 465
      properties:
        mail:
          smtp:
            ssl:
              enable: true
  ```

### Lỗi: "Could not connect to SMTP host"
- Kiểm tra kết nối internet
- Thử ping smtp.gmail.com
- Kiểm tra proxy settings nếu có

### Email không gửi được nhưng không báo lỗi
- Kiểm tra logs: `log.info` và `log.error` trong CheckoutService
- Email được gửi async nên không ảnh hưởng đến checkout
- Kiểm tra spam folder của recipient

## 5. Email Template Customization

File template: `src/main/resources/templates/order-confirmation.html`

Bạn có thể chỉnh sửa:
- Logo và màu sắc (thay đổi `#4CAF50` thành màu brand của bạn)
- Nội dung text
- Layout và styling

## 6. Production Settings

Khi deploy production, đảm bảo:

1. **Sử dụng email domain riêng** (không dùng Gmail)
2. **Cấu hình SMTP server riêng** (SendGrid, Amazon SES, Mailgun, etc.)
3. **Không commit `.env` file** vào Git
4. **Sử dụng environment variables** trên server

### Ví dụ với SendGrid:

```yaml
spring:
  mail:
    host: smtp.sendgrid.net
    port: 587
    username: apikey
    password: ${SENDGRID_API_KEY}
```

## 7. Monitoring

Kiểm tra logs để theo dõi email sending:

```bash
# Windows PowerShell
Get-Content -Path "logs/application.log" -Wait | Select-String "email"

# Hoặc trong code
log.info("Order confirmation email queued for: {}", recipientEmail);
log.error("Failed to queue order confirmation email", e);
```

## 8. Features

- ✅ Gửi email tự động sau khi đặt hàng thành công
- ✅ Hỗ trợ cả khách hàng đã đăng ký và khách vãng lai
- ✅ Template email đẹp với Thymeleaf
- ✅ Async sending (không làm chậm checkout)
- ✅ Error handling (lỗi email không ảnh hưởng đơn hàng)
- ✅ Hiển thị đầy đủ thông tin: sản phẩm, địa chỉ, giá, khuyến mãi

## Support

Nếu gặp vấn đề, kiểm tra:
1. Console logs khi start application
2. Email configuration trong application.yaml
3. .env file có đúng format không
4. Gmail App Password còn hợp lệ không
