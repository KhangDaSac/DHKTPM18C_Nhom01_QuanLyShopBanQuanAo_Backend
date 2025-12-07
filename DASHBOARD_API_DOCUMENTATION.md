# Dashboard Charts API Documentation

## Base URL
```
http://localhost:8080/api/charts
```

## Endpoints Overview

| Endpoint | Method | Description | Response Type |
|----------|--------|-------------|---------------|
| `/sales` | GET | Tổng quan doanh số | Object |
| `/best-products` | GET | Sản phẩm bán chạy nhất | Array |
| `/inventory` | GET | Tình trạng tồn kho | Array |
| `/variants` | GET | Tồn kho theo biến thể | Array |
| `/order-status` | GET | Phân bố trạng thái đơn hàng | Array |
| `/customers` | GET | Thống kê khách hàng | Object |
| `/promotions` | GET | Hiệu quả khuyến mãi | Array |

---

## 1. Sales Chart (Doanh số)

### Endpoint
```
GET /api/charts/sales
```

### Query Parameters
| Parameter | Type | Required | Description | Example |
|-----------|------|----------|-------------|---------|
| `dateFrom` | DateTime | No | Ngày bắt đầu (ISO 8601) | `2024-01-01T00:00:00` |
| `dateTo` | DateTime | No | Ngày kết thúc (ISO 8601) | `2024-03-01T23:59:59` |

### Response
```json
{
  "totalRevenue": 15000000.50,
  "totalOrders": 125,
  "avgOrderValue": 120000.00
}
```

### Frontend Integration
```typescript
// React/TypeScript example
const fetchSalesData = async (dateFrom?: string, dateTo?: string) => {
  const params = new URLSearchParams();
  if (dateFrom) params.append('dateFrom', dateFrom);
  if (dateTo) params.append('dateTo', dateTo);
  
  const response = await fetch(`/api/charts/sales?${params}`);
  return response.json();
};

// Usage: Dashboard KPI cards, Revenue line chart
```

---

## 2. Best Products Chart (Sản phẩm bán chạy)

### Endpoint
```
GET /api/charts/best-products
```

### Query Parameters
| Parameter | Type | Required | Default | Description |
|-----------|------|----------|---------|-------------|
| `dateFrom` | DateTime | No | - | Ngày bắt đầu |
| `dateTo` | DateTime | No | - | Ngày kết thúc |
| `limit` | Integer | No | 10 | Số lượng sản phẩm trả về |

### Response
```json
[
  {
    "productName": "Áo sơ mi nam",
    "qtySold": 150,
    "revenue": 4500000.00
  },
  {
    "productName": "Quần jean nữ",
    "qtySold": 120,
    "revenue": 3600000.00
  }
]
```

### Frontend Integration
```typescript
// Chart.js Bar Chart example
const fetchBestProducts = async (limit = 10) => {
  const response = await fetch(`/api/charts/best-products?limit=${limit}`);
  const data = await response.json();
  
  return {
    labels: data.map(item => item.productName),
    datasets: [{
      label: 'Doanh thu',
      data: data.map(item => item.revenue),
      backgroundColor: 'rgba(75, 192, 192, 0.6)'
    }]
  };
};

// Usage: Bar chart, Product ranking table
```

---

## 3. Inventory Chart (Tồn kho)

### Endpoint
```
GET /api/charts/inventory
```

### Query Parameters
| Parameter | Type | Required | Default | Description |
|-----------|------|----------|---------|-------------|
| `limit` | Integer | No | 50 | Số lượng sản phẩm |
| `lowStockOnly` | Boolean | No | false | Chỉ hiển thị SP tồn kho thấp |

### Response
```json
[
  {
    "productName": "Áo khoác da",
    "stockQty": 5,
    "minQty": 10
  },
  {
    "productName": "Giày thể thao",
    "stockQty": 8,
    "minQty": 10
  }
]
```

### Frontend Integration
```typescript
// Low stock alert
const fetchLowStockProducts = async () => {
  const response = await fetch('/api/charts/inventory?lowStockOnly=true&limit=20');
  return response.json();
};

// Usage: Alert badges, Stock monitoring table
```

---

## 4. Variants Chart (Biến thể SKU)

### Endpoint
```
GET /api/charts/variants
```

### Query Parameters
| Parameter | Type | Required | Default | Description |
|-----------|------|----------|---------|-------------|
| `limit` | Integer | No | 100 | Số lượng biến thể |
| `lowStockOnly` | Boolean | No | false | Chỉ hiển thị biến thể tồn < 5 |

### Response
```json
[
  {
    "productName": "Áo thun cotton",
    "variantName": "XL - Đỏ",
    "sku": "PROD12-VAR45",
    "stockQty": 3
  }
]
```

### Frontend Integration
```typescript
const fetchVariants = async () => {
  const response = await fetch('/api/charts/variants?lowStockOnly=true');
  return response.json();
};

// Usage: Variant management table, SKU tracking
```

---

## 5. Order Status Chart (Trạng thái đơn hàng)

### Endpoint
```
GET /api/charts/order-status
```

### Query Parameters
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `dateFrom` | DateTime | No | Ngày bắt đầu |
| `dateTo` | DateTime | No | Ngày kết thúc |

### Response
```json
[
  {
    "status": "DELIVERED",
    "total": 450
  },
  {
    "status": "PENDING",
    "total": 125
  },
  {
    "status": "SHIPPING",
    "total": 80
  },
  {
    "status": "CANCELLED",
    "total": 25
  }
]
```

### Frontend Integration
```typescript
// Pie Chart example
const fetchOrderStatus = async () => {
  const response = await fetch('/api/charts/order-status');
  const data = await response.json();
  
  return {
    labels: data.map(item => item.status),
    datasets: [{
      data: data.map(item => item.total),
      backgroundColor: [
        '#4CAF50', // DELIVERED - Green
        '#FFC107', // PENDING - Yellow
        '#2196F3', // SHIPPING - Blue
        '#F44336'  // CANCELLED - Red
      ]
    }]
  };
};

// Usage: Pie chart, Doughnut chart, Status distribution
```

---

## 6. Customers Chart (Khách hàng)

### Endpoint
```
GET /api/charts/customers
```

### Query Parameters
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `dateFrom` | DateTime | No | Ngày bắt đầu (cho khách hàng mới) |
| `dateTo` | DateTime | No | Ngày kết thúc (cho khách hàng mới) |

### Response
```json
{
  "totalCustomers": 1250,
  "newCustomers": 85
}
```

### Frontend Integration
```typescript
const fetchCustomerStats = async (dateFrom?: string, dateTo?: string) => {
  const params = new URLSearchParams();
  if (dateFrom) params.append('dateFrom', dateFrom);
  if (dateTo) params.append('dateTo', dateTo);
  
  const response = await fetch(`/api/charts/customers?${params}`);
  return response.json();
};

// Usage: Customer growth KPI, User acquisition metrics
```

---

## 7. Promotions Chart (Khuyến mãi)

### Endpoint
```
GET /api/charts/promotions
```

### Query Parameters
| Parameter | Type | Required | Default | Description |
|-----------|------|----------|---------|-------------|
| `dateFrom` | DateTime | No | - | Ngày bắt đầu |
| `dateTo` | DateTime | No | - | Ngày kết thúc |
| `limit` | Integer | No | 20 | Số lượng khuyến mãi |

### Response
```json
[
  {
    "promotionName": "Flash Sale 12.12",
    "discountPercent": 20.0,
    "ordersApplied": 230
  },
  {
    "promotionName": "Giảm 100K cho đơn 500K",
    "discountPercent": 100000.0,
    "ordersApplied": 180
  }
]
```

### Frontend Integration
```typescript
const fetchPromotionStats = async () => {
  const response = await fetch('/api/charts/promotions?limit=10');
  return response.json();
};

// Usage: Promotion effectiveness table, Marketing campaign chart
```

---

## Error Responses

### 400 Bad Request - Invalid Date Range
```json
{
  "timestamp": "2024-12-07T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "dateFrom cannot be after dateTo"
}
```

### 400 Bad Request - Invalid Date Format
```json
{
  "timestamp": "2024-12-07T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid parameter format. Please use ISO 8601 format for dates (e.g., 2024-01-01T00:00:00)",
  "parameter": "dateFrom"
}
```

### 500 Internal Server Error
```json
{
  "timestamp": "2024-12-07T10:30:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred. Please try again later."
}
```

---

## Testing with cURL

### Test Sales Chart
```bash
curl -X GET "http://localhost:8080/api/charts/sales?dateFrom=2024-01-01T00:00:00&dateTo=2024-12-31T23:59:59"
```

### Test Best Products
```bash
curl -X GET "http://localhost:8080/api/charts/best-products?limit=5"
```

### Test Low Stock Inventory
```bash
curl -X GET "http://localhost:8080/api/charts/inventory?lowStockOnly=true"
```

---

## Frontend Dashboard Integration Guide

### 1. Dashboard Overview Tab
```typescript
// Fetch multiple chart data
const loadDashboard = async () => {
  const [sales, orderStatus, customers] = await Promise.all([
    fetch('/api/charts/sales').then(r => r.json()),
    fetch('/api/charts/order-status').then(r => r.json()),
    fetch('/api/charts/customers').then(r => r.json())
  ]);
  
  return { sales, orderStatus, customers };
};
```

### 2. Product Analytics Tab
```typescript
const loadProductAnalytics = async () => {
  const [bestProducts, inventory] = await Promise.all([
    fetch('/api/charts/best-products?limit=10').then(r => r.json()),
    fetch('/api/charts/inventory?lowStockOnly=true').then(r => r.json())
  ]);
  
  return { bestProducts, inventory };
};
```

### 3. Marketing Tab
```typescript
const loadMarketingData = async () => {
  const promotions = await fetch('/api/charts/promotions').then(r => r.json());
  return promotions;
};
```

---

## Notes for Frontend Developers

1. **Date Format**: Always use ISO 8601 format `YYYY-MM-DDTHH:mm:ss`
2. **Caching**: Consider implementing client-side caching for better performance
3. **Error Handling**: Always handle error responses appropriately
4. **Loading States**: Show loading indicators while fetching data
5. **Refresh**: Implement auto-refresh or manual refresh button for real-time data
6. **Filters**: Use date range pickers for `dateFrom` and `dateTo` parameters
7. **Charts**: Recommended libraries: Chart.js, Recharts, ApexCharts, ECharts
8. **Responsive**: Ensure charts are responsive on mobile devices

---

## Performance Optimization

- All queries use indexed columns (createAt, orderStatus, active)
- Pagination is implemented for large datasets
- Read-only transactions for better performance
- Consider adding Redis caching for frequently accessed data
