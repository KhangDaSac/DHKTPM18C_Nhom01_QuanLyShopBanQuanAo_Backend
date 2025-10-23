# 📋 API ENDPOINTS DOCUMENTATION

## 🔐 1. AUTHENTICATION CONTROLLER (`/auth`)

| Endpoint | Method | Mô tả | Ai được phép? |
|----------|--------|-------|---------------|
| `/auth/login` | POST | Đăng nhập, lấy token | **PUBLIC** - Ai cũng có thể đăng nhập |
| `/auth/introspect` | POST | Kiểm tra token có hợp lệ không | **PUBLIC** - Kiểm tra token |
| `/auth/refresh` | POST | Làm mới access token | **AUTHENTICATED** - Cần có refresh token |
| `/auth/logout` | POST | Đăng xuất | **PUBLIC** - Có thể logout mà không cần token |
| `/auth/me` | GET | Lấy thông tin user hiện tại | **AUTHENTICATED** - CUSTOMER hoặc ADMIN |

---

## 👥 2. USER CONTROLLER (`/users`)

| Endpoint | Method | Mô tả | Ai được phép? |
|----------|--------|-------|---------------|
| `/users` | POST | Tạo user mới (đăng ký) | **PUBLIC** - Ai cũng có thể đăng ký |
| `/users` | GET | Lấy danh sách tất cả users | **ADMIN ONLY** - Chỉ admin xem được |
| `/users/{userId}` | GET | Lấy thông tin user theo ID | **ADMIN ONLY** - Chỉ admin xem được |
| `/users/{userId}` | PUT | Cập nhật thông tin user | **ADMIN ONLY** - Chỉ admin sửa được |
| `/users/{userId}` | DELETE | Xóa user | **ADMIN ONLY** - Chỉ admin xóa được |

---

## 🛍️ 3. PRODUCT CONTROLLER (`/products`)

| Endpoint | Method | Mô tả | Ai được phép? |
|----------|--------|-------|---------------|
| `/products` | GET | Lấy tất cả sản phẩm | **PUBLIC** - Ai cũng có thể xem |
| `/products/{id}` | GET | Lấy chi tiết sản phẩm | **PUBLIC** - Ai cũng có thể xem |
| `/products/search` | GET | Tìm kiếm sản phẩm | **PUBLIC** - Ai cũng có thể tìm |
| `/products/paginated` | GET | Phân trang sản phẩm | **PUBLIC** - Ai cũng có thể xem |
| `/products/brand/{brandId}` | GET | Sản phẩm theo thương hiệu | **PUBLIC** - Ai cũng có thể xem |
| `/products/category/{categoryId}` | GET | Sản phẩm theo danh mục | **PUBLIC** - Ai cũng có thể xem |
| `/products/active` | GET | Sản phẩm đang hoạt động | **PUBLIC** - Ai cũng có thể xem |
| `/products/count` | GET | Đếm tổng sản phẩm | **PUBLIC** - Ai cũng có thể xem |
| `/products/count/active` | GET | Đếm sản phẩm hoạt động | **PUBLIC** - Ai cũng có thể xem |
| `/products` | POST | Tạo sản phẩm mới | **ADMIN ONLY** - Chỉ admin tạo được |
| `/products/{id}` | PUT | Cập nhật sản phẩm | **ADMIN ONLY** - Chỉ admin sửa được |
| `/products/{id}` | DELETE | Xóa sản phẩm (soft delete) | **ADMIN ONLY** - Chỉ admin xóa được |
| `/products/{id}/restore` | PUT | Khôi phục sản phẩm | **ADMIN ONLY** - Chỉ admin khôi phục được |
| `/products/{id}/permanent` | DELETE | Xóa vĩnh viễn sản phẩm | **ADMIN ONLY** - Chỉ admin xóa vĩnh viễn |

---

## 📂 4. CATEGORY CONTROLLER (`/categories`)

| Endpoint | Method | Mô tả | Ai được phép? |
|----------|--------|-------|---------------|
| `/categories` | GET | Lấy tất cả danh mục | **PUBLIC** - Ai cũng có thể xem |
| `/categories/{id}` | GET | Lấy chi tiết danh mục | **PUBLIC** - Ai cũng có thể xem |
| `/categories/paginated` | GET | Phân trang danh mục | **PUBLIC** - Ai cũng có thể xem |
| `/categories/active` | GET | Danh mục đang hoạt động | **PUBLIC** - Ai cũng có thể xem |
| `/categories/search` | GET | Tìm kiếm danh mục | **PUBLIC** - Ai cũng có thể tìm |
| `/categories/count` | GET | Đếm tổng danh mục | **PUBLIC** - Ai cũng có thể xem |
| `/categories/count/active` | GET | Đếm danh mục hoạt động | **PUBLIC** - Ai cũng có thể xem |
| `/categories` | POST | Tạo danh mục mới | **ADMIN ONLY** - Chỉ admin tạo được |
| `/categories/{id}` | PUT | Cập nhật danh mục | **ADMIN ONLY** - Chỉ admin sửa được |
| `/categories/{id}` | DELETE | Xóa danh mục (soft delete) | **ADMIN ONLY** - Chỉ admin xóa được |
| `/categories/{id}/restore` | PUT | Khôi phục danh mục | **ADMIN ONLY** - Chỉ admin khôi phục được |
| `/categories/{id}/permanent` | DELETE | Xóa vĩnh viễn danh mục | **ADMIN ONLY** - Chỉ admin xóa vĩnh viễn |

---

## 🏷️ 5. BRAND CONTROLLER (`/brands`)

| Endpoint | Method | Mô tả | Ai được phép? |
|----------|--------|-------|---------------|
| `/brands` | GET | Lấy tất cả thương hiệu | **PUBLIC** - Ai cũng có thể xem |
| `/brands/{id}` | GET | Lấy chi tiết thương hiệu | **PUBLIC** - Ai cũng có thể xem |
| `/brands/paginated` | GET | Phân trang thương hiệu | **PUBLIC** - Ai cũng có thể xem |
| `/brands/search` | GET | Tìm kiếm thương hiệu | **PUBLIC** - Ai cũng có thể tìm |
| `/brands/active` | GET | Thương hiệu đang hoạt động | **PUBLIC** - Ai cũng có thể xem |
| `/brands/count` | GET | Đếm tổng thương hiệu | **PUBLIC** - Ai cũng có thể xem |
| `/brands/count/active` | GET | Đếm thương hiệu hoạt động | **PUBLIC** - Ai cũng có thể xem |
| `/brands` | POST | Tạo thương hiệu mới | **ADMIN ONLY** - Chỉ admin tạo được |
| `/brands/{id}` | PUT | Cập nhật thương hiệu | **ADMIN ONLY** - Chỉ admin sửa được |
| `/brands/{id}` | DELETE | Xóa thương hiệu (soft delete) | **ADMIN ONLY** - Chỉ admin xóa được |
| `/brands/{id}/restore` | PUT | Khôi phục thương hiệu | **ADMIN ONLY** - Chỉ admin khôi phục được |
| `/brands/{id}/permanent` | DELETE | Xóa vĩnh viễn thương hiệu | **ADMIN ONLY** - Chỉ admin xóa vĩnh viễn |

---

## 👤 6. CUSTOMER CONTROLLER (`/api/v1/customers`)

| Endpoint | Method | Mô tả | Ai được phép? |
|----------|--------|-------|---------------|
| `/api/v1/customers` | POST | Tạo profile customer | **CUSTOMER/ADMIN** - Customer tự tạo profile của mình |
| `/api/v1/customers/{userId}` | GET | Lấy thông tin customer theo ID | **CUSTOMER/ADMIN** - Customer xem profile của chính họ, Admin xem tất cả |
| `/api/v1/customers` | GET | Lấy tất cả customers | **ADMIN ONLY** - Chỉ admin xem được |
| `/api/v1/customers/active` | GET | Lấy customers đang hoạt động | **ADMIN ONLY** - Chỉ admin xem được |
| `/api/v1/customers/{userId}` | PUT | Cập nhật thông tin customer | **CUSTOMER/ADMIN** - Customer sửa profile của chính họ, Admin sửa tất cả |
| `/api/v1/customers/{userId}` | DELETE | Xóa customer | **ADMIN ONLY** - Chỉ admin xóa được |

---

## 🔒 SPRING SECURITY CONFIGURATION

### **Các mức bảo mật:**

#### **PUBLIC (Không cần authentication):**
- Ai cũng có thể truy cập
- Không cần gửi token
- Ví dụ: Đăng nhập, đăng ký, xem sản phẩm

#### **AUTHENTICATED (Cần authentication):**
- Phải có token hợp lệ
- Token được gửi trong header: `Authorization: Bearer <token>`
- Ví dụ: Lấy thông tin user hiện tại

#### **ROLE-BASED (Phân quyền theo vai trò):**
- **CUSTOMER**: Khách hàng thông thường
- **ADMIN**: Quản trị viên

### **SecurityConfig đơn giản:**

```java
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                // === PUBLIC ENDPOINTS ===
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/categories/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/brands/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                
                // === TẤT CẢ ENDPOINTS KHÁC CẦN AUTHENTICATION ===
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
            );

        return http.build();
    }
}
```

### **Sử dụng @PreAuthorize trong Controller:**

```java
// PUBLIC - không cần annotation
@GetMapping
public ApiResponse<List<ProductResponse>> getAllProducts() {
    // Ai cũng có thể xem
}

// ADMIN ONLY
@PostMapping
@PreAuthorize("hasRole('ADMIN')")
public ApiResponse<ProductResponse> createProduct(@RequestBody ProductRequest request) {
    // Chỉ ADMIN mới có thể tạo
}

// CUSTOMER hoặc ADMIN
@GetMapping("/{userId}")
@PreAuthorize("hasRole('ADMIN') or (#userId == authentication.name)")
public ApiResponse<CustomerResponse> getCustomerById(@PathVariable String userId) {
    // ADMIN xem được tất cả, CUSTOMER chỉ xem được của chính họ
}
```

---

## 📝 FLOW ĐĂNG KÝ VÀ SỬ DỤNG

### **Customer đăng ký:**
```
1. POST /users (PUBLIC) → Tạo tài khoản User
2. POST /auth/login (PUBLIC) → Đăng nhập lấy token
3. POST /api/v1/customers (AUTHENTICATED) → Tạo profile Customer
4. GET /api/v1/customers/{userId} (AUTHENTICATED) → Xem profile của chính họ
```

### **Admin quản lý:**
```
1. POST /users (ADMIN) → Tạo tài khoản cho nhân viên mới
2. GET /users (ADMIN) → Xem danh sách tất cả users
3. GET /api/v1/customers (ADMIN) → Xem danh sách tất cả customers
4. PUT /api/v1/customers/{userId} (ADMIN) → Sửa thông tin customer
```

---

## 🚀 CÁCH SỬ DỤNG API

### **Headers cần thiết:**
```
Content-Type: application/json
Authorization: Bearer <access_token>  // Cho các endpoint cần authentication
```

### **Response format:**
```json
{
  "code": 2000,
  "message": "Success",
  "result": { ... }
}
```

### **Error format:**
```json
{
  "code": 4000,
  "message": "Error message",
  "result": null
}
```
