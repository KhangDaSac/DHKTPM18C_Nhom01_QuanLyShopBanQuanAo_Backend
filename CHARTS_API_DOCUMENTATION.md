# Charts API Documentation

## API Endpoints cho Dashboard Analytics

Base URL: `/api/charts`

---

## 1. Doanh Số Theo Ngày
**GET** `/api/charts/sales/daily`

Lấy dữ liệu doanh số theo ngày (30 ngày gần nhất mặc định)

### Query Parameters
| Tham số | Kiểu | Bắt buộc | Mô tả |
|---------|------|----------|-------|
| `startDate` | LocalDate | Không | Ngày bắt đầu (format: YYYY-MM-DD). Mặc định: 30 ngày trước |
| `endDate` | LocalDate | Không | Ngày kết thúc (format: YYYY-MM-DD). Mặc định: hôm nay |

### Response Example
```json
[
  {
    "date": "2025-12-01",
    "revenue": 12500000,
    "orders": 45
  },
  {
    "date": "2025-12-02",
    "revenue": 15800000,
    "orders": 52
  }
]
```

### Ví dụ sử dụng
```javascript
// Lấy 30 ngày gần nhất
fetch('/api/charts/sales/daily')

// Lấy từ ngày cụ thể
fetch('/api/charts/sales/daily?startDate=2025-11-01&endDate=2025-11-30')
```

---

## 2. Doanh Số Theo Tháng
**GET** `/api/charts/sales/monthly`

Lấy dữ liệu doanh số theo tháng (12 tháng gần nhất mặc định)

### Query Parameters
| Tham số | Kiểu | Bắt buộc | Mô tả |
|---------|------|----------|-------|
| `months` | Integer | Không | Số tháng muốn lấy. Mặc định: 12 |

### Response Example
```json
[
  {
    "month": "2025-11",
    "revenue": 250000000,
    "orders": 680
  },
  {
    "month": "2025-12",
    "revenue": 320000000,
    "orders": 892
  }
]
```

### Ví dụ sử dụng
```javascript
// Lấy 12 tháng gần nhất
fetch('/api/charts/sales/monthly')

// Lấy 6 tháng gần nhất
fetch('/api/charts/sales/monthly?months=6')
```

---

## 3. Top Sản Phẩm Bán Chạy
**GET** `/api/charts/top-products`

Lấy danh sách sản phẩm bán chạy nhất (từ đơn hàng PENDING)

### Query Parameters
| Tham số | Kiểu | Bắt buộc | Mô tả |
|---------|------|----------|-------|
| `limit` | Integer | Không | Số lượng sản phẩm muốn lấy. Mặc định: 10 |

### Response Example
```json
[
  {
    "productId": 1,
    "productName": "Áo Thun Nam Basic",
    "sold": 150,
    "revenue": 45000000,
    "image": "https://cloudinary.com/image1.jpg"
  },
  {
    "productId": 5,
    "productName": "Quần Jean Slim Fit",
    "sold": 128,
    "revenue": 64000000,
    "image": "https://cloudinary.com/image5.jpg"
  }
]
```

### Ví dụ sử dụng
```javascript
// Lấy top 10 sản phẩm
fetch('/api/charts/top-products')

// Lấy top 20 sản phẩm
fetch('/api/charts/top-products?limit=20')
```

---

## 4. Phân Tích Tồn Kho
**GET** `/api/charts/inventory`

Phân tích tồn kho theo danh mục và sản phẩm sắp hết hàng

### Response Example
```json
{
  "byCategory": [
    {
      "categoryName": "Áo",
      "totalStock": 500,
      "totalValue": 150000000
    },
    {
      "categoryName": "Quần",
      "totalStock": 350,
      "totalValue": 120000000
    }
  ],
  "lowStock": [
    {
      "productId": 12,
      "productName": "Áo Sơ Mi Trắng",
      "variantId": 45,
      "size": "M",
      "color": "Trắng",
      "stock": 5
    }
  ],
  "totalVariants": 1250,
  "totalStock": 5420
}
```

### Ví dụ sử dụng
```javascript
fetch('/api/charts/inventory')
```

---

## 5. Trạng Thái Đơn Hàng
**GET** `/api/charts/order-status`

Phân tích đơn hàng theo trạng thái

### Response Example
```json
[
  {
    "status": "PENDING",
    "statusName": "Chờ xử lý",
    "count": 25,
    "totalValue": 75000000
  },
  {
    "status": "PREPARING",
    "statusName": "Đang chuẩn bị",
    "count": 18,
    "totalValue": 54000000
  },
  {
    "status": "SHIPPED",
    "statusName": "Đang giao",
    "count": 32,
    "totalValue": 96000000
  },
  {
    "status": "DELIVERED",
    "statusName": "Đã giao",
    "count": 120,
    "totalValue": 360000000
  },
  {
    "status": "CANCELLED",
    "statusName": "Đã hủy",
    "count": 5,
    "totalValue": 15000000
  }
]
```

### Ví dụ sử dụng
```javascript
fetch('/api/charts/order-status')
```

---

## 6. Phân Tích Khách Hàng
**GET** `/api/charts/customers`

Phân tích khách hàng mới và top khách hàng chi tiêu nhiều

### Response Example
```json
{
  "totalCustomers": 350,
  "newCustomersDaily": [
    {
      "date": "2025-12-01",
      "count": 5
    },
    {
      "date": "2025-12-02",
      "count": 8
    }
  ],
  "topSpenders": [
    {
      "customerId": "user-123",
      "customerName": "Nguyễn Văn A",
      "email": "nguyenvana@gmail.com",
      "totalSpent": 25000000
    },
    {
      "customerId": "user-456",
      "customerName": "Trần Thị B",
      "email": "tranthib@gmail.com",
      "totalSpent": 18000000
    }
  ]
}
```

### Ví dụ sử dụng
```javascript
fetch('/api/charts/customers')
```

---

## 7. Phân Tích Khuyến Mãi
**GET** `/api/charts/promotions`

Phân tích hiệu quả khuyến mãi

### Response Example
```json
{
  "totalOrders": 500,
  "ordersWithPromotion": 180,
  "ordersWithoutPromotion": 320,
  "totalPromotionValue": 45000000
}
```

### Ví dụ sử dụng
```javascript
fetch('/api/charts/promotions')
```

---

## 8. Ma Trận Biến Thể
**GET** `/api/charts/variant-matrix`

Lấy ma trận biến thể sản phẩm (Color x Size) để hiển thị heatmap

### Response Example
```json
{
  "colors": ["Đỏ", "Xanh", "Vàng", "Trắng"],
  "sizes": ["S", "M", "L", "XL"],
  "data": [
    {
      "color": "Đỏ",
      "S": 10,
      "M": 15,
      "L": 20,
      "XL": 8
    },
    {
      "color": "Xanh",
      "S": 12,
      "M": 18,
      "L": 25,
      "XL": 10
    },
    {
      "color": "Vàng",
      "S": 8,
      "M": 12,
      "L": 15,
      "XL": 6
    }
  ]
}
```

### Ví dụ sử dụng
```javascript
fetch('/api/charts/variant-matrix')
```

---

## Logic Nghiệp Vụ

### 📊 Doanh Số
- **Nguồn dữ liệu**: Đơn hàng có trạng thái `PENDING` (Chờ xử lý)
- **Giá trị**: Lấy từ `subTotal` (tổng tiền sau khuyến mãi)
- **Lý do**: Chỉ tính doanh số từ đơn hàng đã xác nhận thanh toán

### 🏆 Sản Phẩm Bán Chạy
- **Nguồn dữ liệu**: `OrderItem` từ đơn hàng `PENDING`
- **Tính toán**: 
  - `sold`: Tổng `quantity` từ tất cả order items
  - `revenue`: Tổng `lineTotal` (unitPrice × quantity)
- **Sắp xếp**: Theo số lượng bán được (sold) giảm dần

### 📦 Tồn Kho
- **Nguồn dữ liệu**: `ProductVariant.quantity`
- **Sản phẩm sắp hết**: Variant có `quantity < 10`
- **Tổng giá trị**: `quantity × price` của tất cả variants
- **Phân loại**: Theo `category` của sản phẩm

---

## Error Handling

Tất cả endpoints đều trả về error response theo format:

```json
{
  "error": "Mô tả lỗi",
  "message": "Chi tiết lỗi kỹ thuật"
}
```

### HTTP Status Codes
- `200 OK`: Thành công
- `400 Bad Request`: Lỗi tham số hoặc logic nghiệp vụ
- `500 Internal Server Error`: Lỗi server

---

## Frontend Integration

### TypeScript Interface Example

```typescript
// src/types/charts.ts
export interface DailySalesData {
  date: string;
  revenue: number;
  orders: number;
}

export interface MonthlySalesData {
  month: string;
  revenue: number;
  orders: number;
}

export interface TopProductData {
  productId: number;
  productName: string;
  sold: number;
  revenue: number;
  image: string;
}

export interface InventoryData {
  byCategory: Array<{
    categoryName: string;
    totalStock: number;
    totalValue: number;
  }>;
  lowStock: Array<{
    productId: number;
    productName: string;
    variantId: number;
    size: string;
    color: string;
    stock: number;
  }>;
  totalVariants: number;
  totalStock: number;
}

export interface OrderStatusData {
  status: string;
  statusName: string;
  count: number;
  totalValue: number;
}
```

### Service Example

```typescript
// src/services/analytics/charts.ts
import { apiClient } from '@/api/client';
import type { DailySalesData, MonthlySalesData, TopProductData } from '@/types/charts';

export const chartsService = {
  getDailySales: async (startDate?: string, endDate?: string) => {
    const params = new URLSearchParams();
    if (startDate) params.append('startDate', startDate);
    if (endDate) params.append('endDate', endDate);
    
    const { data } = await apiClient.get<DailySalesData[]>(
      `/api/charts/sales/daily?${params.toString()}`
    );
    return data;
  },

  getMonthlySales: async (months: number = 12) => {
    const { data } = await apiClient.get<MonthlySalesData[]>(
      `/api/charts/sales/monthly?months=${months}`
    );
    return data;
  },

  getTopProducts: async (limit: number = 10) => {
    const { data } = await apiClient.get<TopProductData[]>(
      `/api/charts/top-products?limit=${limit}`
    );
    return data;
  },

  getInventory: async () => {
    const { data } = await apiClient.get('/api/charts/inventory');
    return data;
  },

  getOrderStatus: async () => {
    const { data } = await apiClient.get('/api/charts/order-status');
    return data;
  }
};
```

---

## Testing với Postman

### Collection Setup
1. Import file: `Image_Upload_API.postman_collection.json`
2. Thêm folder mới: `Charts Analytics`
3. Thêm 8 requests tương ứng 8 endpoints

### Environment Variables
```json
{
  "baseUrl": "http://localhost:8080",
  "apiPrefix": "/api/charts"
}
```

### Test Requests
```
GET {{baseUrl}}{{apiPrefix}}/sales/daily
GET {{baseUrl}}{{apiPrefix}}/sales/monthly?months=6
GET {{baseUrl}}{{apiPrefix}}/top-products?limit=20
GET {{baseUrl}}{{apiPrefix}}/inventory
GET {{baseUrl}}{{apiPrefix}}/order-status
GET {{baseUrl}}{{apiPrefix}}/customers
GET {{baseUrl}}{{apiPrefix}}/promotions
GET {{baseUrl}}{{apiPrefix}}/variant-matrix
```

---

## Performance Optimization

### Backend
- Sử dụng `@Transactional(readOnly = true)` cho các query SELECT
- Implement caching với Redis cho dữ liệu không thay đổi thường xuyên
- Tối ưu query với JOIN FETCH để tránh N+1 problem

### Frontend
- Implement debouncing cho date range picker
- Cache API responses với React Query hoặc SWR
- Lazy load charts (chỉ fetch khi tab được active)
- Giới hạn số điểm dữ liệu hiển thị (MAX_DATA_POINTS = 100)

---

## Changelog

### Version 1.0.0 (2025-12-07)
- ✅ Initial release
- ✅ 8 endpoints cho dashboard analytics
- ✅ Hỗ trợ query parameters
- ✅ Error handling
- ✅ Documentation đầy đủ

---

## Support

Nếu gặp vấn đề, hãy kiểm tra:
1. Backend server đang chạy (`localhost:8080`)
2. Database connection OK
3. Có dữ liệu trong bảng `orders`, `order_item`, `product_variants`
4. CORS configuration cho phép frontend domain

**Liên hệ**: GitHub Issues hoặc team support
