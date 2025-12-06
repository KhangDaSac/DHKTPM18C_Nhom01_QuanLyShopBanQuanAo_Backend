# Fix: Foreign Key Constraint Error khi thêm vào giỏ hàng

## Vấn đề gặp phải

Khi đăng nhập và thêm sản phẩm vào giỏ hàng, gặp lỗi:
```
Cannot add or update a child row: a foreign key constraint fails 
(`modamint_db`.`cart`, CONSTRAINT `FKioh3c0mo0al2kswtnk5r09y7f` 
FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`))
```

## Nguyên nhân

1. **Bảng Cart có foreign key constraint yêu cầu `customer_id` phải tồn tại trong bảng `customers`**
2. **Khi user đăng nhập (đặc biệt qua Google OAuth), chỉ tạo record trong bảng `users` mà không tạo record tương ứng trong bảng `customers`**
3. **Entity Customer có `@GeneratedValue(strategy = GenerationType.UUID)` sẽ tự động sinh UUID mới thay vì dùng ID từ User**

## Giải pháp đã áp dụng

### 1. Cập nhật Entity Customer (`Customer.java`)
**Thay đổi**: Xóa `@GeneratedValue` để có thể set `customerId` thủ công bằng User ID

```java
@Id
String customerId;  // Removed @GeneratedValue(strategy = GenerationType.UUID)
```

### 2. Cập nhật AuthenticationService 
**File**: `AuthenticationService.java`

#### a. Thêm CustomerRepository dependency
```java
CustomerRepository customerRepository;
```

#### b. Cập nhật phương thức `authenticateWithGoogle()`
- Khi tạo User mới qua Google OAuth → tự động tạo Customer record
- Khi User đã tồn tại → kiểm tra và tạo Customer record nếu chưa có

```java
// Tự động tạo Customer record cho user mới từ Google OAuth
Customer customer = Customer.builder()
        .customerId(user.getId())
        .user(user)
        .email(googleEmail)
        .name(googleName)
        .build();
customerRepository.save(customer);
```

#### c. Cập nhật phương thức `authenticate()` (đăng nhập thông thường)
- Kiểm tra Customer record khi đăng nhập
- Tạo Customer record nếu chưa tồn tại

```java
if (!customerRepository.existsByCustomerId(user.getId())) {
    Customer customer = Customer.builder()
            .customerId(user.getId())
            .user(user)
            .email(user.getEmail())
            .name((user.getFirstName() != null ? user.getFirstName() : "") + " " + 
                  (user.getLastName() != null ? user.getLastName() : "").trim())
            .phone(user.getPhone())
            .build();
    customerRepository.save(customer);
}
```

### 3. Cập nhật UserService (`UserService.java`)
**File**: `UserService.java`

Cập nhật phương thức `createRequest()` để tạo Customer với đúng customerId:

```java
if (hasCustomerRole) {
    Customer customer = Customer.builder()
            .customerId(savedUser.getId())  // Set customerId = user.id
            .user(savedUser)
            .email(savedUser.getEmail())
            .name((savedUser.getFirstName() != null ? savedUser.getFirstName() : "") + " " + 
                  (savedUser.getLastName() != null ? savedUser.getLastName() : "").trim())
            .phone(savedUser.getPhone())
            .build();
    customerRepository.save(customer);
}
```

### 4. Cập nhật CartServiceImpl (`CartServiceImpl.java`)
**File**: `CartServiceImpl.java`

Cải thiện phương thức `ensureCustomerExists()` để tạo Customer với đầy đủ thông tin:

```java
Customer customer = Customer.builder()
        .customerId(customerId)
        .user(user)
        .email(user.getEmail())
        .name((user.getFirstName() != null ? user.getFirstName() : "") + " " + 
              (user.getLastName() != null ? user.getLastName() : "").trim())
        .phone(user.getPhone())
        .build();
customerRepository.save(customer);
entityManager.flush();
```

## Script SQL để fix dữ liệu hiện có

Nếu database đã có users mà chưa có customer records, chạy script sau:

```sql
-- File: fix_missing_customers.sql

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
```

## Kết quả

Sau khi áp dụng các fix trên:

1. ✅ User đăng nhập qua Google OAuth sẽ tự động có Customer record
2. ✅ User đăng nhập thông thường sẽ tự động có Customer record nếu chưa có
3. ✅ User tạo mới qua API `/users` sẽ có Customer record với đúng customerId
4. ✅ Có thể thêm sản phẩm vào giỏ hàng mà không bị lỗi foreign key constraint
5. ✅ Cart được tạo tự động với customer_id hợp lệ

## Testing

### Test case 1: Đăng nhập bằng Google OAuth
1. Đăng nhập qua Google lần đầu
2. Kiểm tra database: `SELECT * FROM customers WHERE customer_id = '<user_id>'`
3. Thêm sản phẩm vào giỏ hàng
4. Kiểm tra giỏ hàng: `SELECT * FROM cart WHERE customer_id = '<user_id>'`

### Test case 2: Đăng nhập thông thường
1. Đăng nhập bằng username/password
2. Kiểm tra Customer record được tạo
3. Thêm sản phẩm vào giỏ hàng thành công

### Test case 3: User hiện có (đã tồn tại trước khi fix)
1. Chạy script SQL `fix_missing_customers.sql`
2. Đăng nhập với user cũ
3. Thêm sản phẩm vào giỏ hàng thành công

## Files đã thay đổi

1. `Customer.java` - Xóa @GeneratedValue
2. `AuthenticationService.java` - Thêm logic tạo Customer khi login
3. `UserService.java` - Cập nhật logic tạo Customer với customerId
4. `CartServiceImpl.java` - Cải thiện ensureCustomerExists()
5. `fix_missing_customers.sql` - Script fix dữ liệu cũ

## Lưu ý

- **Quan trọng**: Luôn đảm bảo `customer_id = user_id` để duy trì tính nhất quán
- Khi tạo Customer mới, không dùng `@GeneratedValue`, phải set customerId thủ công
- Luôn kiểm tra Customer tồn tại trước khi thao tác với Cart
- Sử dụng `entityManager.flush()` để đảm bảo Customer được commit vào DB trước khi tạo Cart

## Ngày fix
06/12/2025
