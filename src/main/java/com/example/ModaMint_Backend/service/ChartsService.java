package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.entity.*;
import com.example.ModaMint_Backend.enums.OrderStatus;
import com.example.ModaMint_Backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ChartsService
 * Service layer cho dashboard analytics
 * 
 * Logic nghiệp vụ:
 * - Doanh số: Lấy từ đơn hàng PENDING (chờ xử lý)
 * - Sản phẩm bán chạy: Lấy từ OrderItem của đơn hàng PENDING
 * - Tồn kho: Lấy quantity từ ProductVariant
 */
@Service
@RequiredArgsConstructor
public class ChartsService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;

    /**
     * Lấy doanh số theo ngày
     * 
     * @param startDate Ngày bắt đầu
     * @param endDate Ngày kết thúc
     * @return List doanh số theo ngày
     */
    public List<Map<String, Object>> getDailySales(LocalDate startDate, LocalDate endDate) {
        // Lấy tất cả đơn hàng PENDING trong khoảng thời gian
        List<Order> orders = orderRepository.findByOrderStatus(OrderStatus.PENDING)
                .stream()
                .filter(order -> {
                    LocalDate orderDate = order.getCreateAt().toLocalDate();
                    return !orderDate.isBefore(startDate) && !orderDate.isAfter(endDate);
                })
                .toList();

        // Group by date và tính tổng
        Map<LocalDate, List<Order>> ordersByDate = orders.stream()
                .collect(Collectors.groupingBy(order -> order.getCreateAt().toLocalDate()));

        // Tạo list kết quả
        List<Map<String, Object>> result = new ArrayList<>();
        
        // Duyệt qua từng ngày trong khoảng thời gian
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            Map<String, Object> dayData = new HashMap<>();
            dayData.put("date", currentDate.toString());
            
            List<Order> dayOrders = ordersByDate.getOrDefault(currentDate, new ArrayList<>());
            
            // Tính tổng doanh thu
            BigDecimal totalRevenue = dayOrders.stream()
                    .map(Order::getSubTotal)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            dayData.put("revenue", totalRevenue);
            dayData.put("orders", dayOrders.size());
            
            result.add(dayData);
            currentDate = currentDate.plusDays(1);
        }

        return result;
    }

    /**
     * Lấy doanh số theo tháng
     * 
     * @param months Số tháng muốn lấy (tính từ hiện tại)
     * @return List doanh số theo tháng
     */
    public List<Map<String, Object>> getMonthlySales(int months) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(months);

        // Lấy tất cả đơn hàng PENDING
        List<Order> orders = orderRepository.findByOrderStatus(OrderStatus.PENDING)
                .stream()
                .filter(order -> {
                    LocalDate orderDate = order.getCreateAt().toLocalDate();
                    return !orderDate.isBefore(startDate) && !orderDate.isAfter(endDate);
                })
                .toList();

        // Group by month-year
        Map<String, List<Order>> ordersByMonth = orders.stream()
                .collect(Collectors.groupingBy(order -> {
                    LocalDate date = order.getCreateAt().toLocalDate();
                    return String.format("%04d-%02d", date.getYear(), date.getMonthValue());
                }));

        // Tạo list kết quả
        List<Map<String, Object>> result = new ArrayList<>();
        
        LocalDate currentMonth = startDate.withDayOfMonth(1);
        while (!currentMonth.isAfter(endDate)) {
            String monthKey = String.format("%04d-%02d", 
                    currentMonth.getYear(), 
                    currentMonth.getMonthValue());
            
            Map<String, Object> monthData = new HashMap<>();
            monthData.put("month", monthKey);
            
            List<Order> monthOrders = ordersByMonth.getOrDefault(monthKey, new ArrayList<>());
            
            BigDecimal totalRevenue = monthOrders.stream()
                    .map(Order::getSubTotal)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            monthData.put("revenue", totalRevenue);
            monthData.put("orders", monthOrders.size());
            
            result.add(monthData);
            currentMonth = currentMonth.plusMonths(1);
        }

        return result;
    }

    /**
     * Lấy top sản phẩm bán chạy
     * 
     * @param limit Số lượng sản phẩm muốn lấy
     * @return List top sản phẩm
     */
    public List<Map<String, Object>> getTopProducts(int limit) {
        // Lấy tất cả order items từ đơn hàng PENDING
        List<Order> pendingOrders = orderRepository.findByOrderStatus(OrderStatus.PENDING);
        Set<Long> pendingOrderIds = pendingOrders.stream()
                .map(Order::getId)
                .collect(Collectors.toSet());

        List<OrderItem> orderItems = orderItemRepository.findAll()
                .stream()
                .filter(item -> pendingOrderIds.contains(item.getOrderId()))
                .toList();

        // Group by product variant và tính tổng
        Map<Long, ProductSalesData> productSales = new HashMap<>();
        
        for (OrderItem item : orderItems) {
            Long variantId = item.getProductVariantId();
            ProductVariant variant = productVariantRepository.findById(variantId).orElse(null);
            
            if (variant != null && variant.getProduct() != null) {
                Long productId = variant.getProductId();
                Product product = variant.getProduct();
                
                ProductSalesData data = productSales.getOrDefault(productId, 
                        new ProductSalesData(productId, product.getName()));
                
                data.addSale(item.getQuantity(), item.getLineTotal());
                
                // Lưu hình ảnh đầu tiên
                if (data.getImage() == null && product.getImages() != null && !product.getImages().isEmpty()) {
                    data.setImage(product.getImages().iterator().next());
                }
                
                productSales.put(productId, data);
            }
        }

        // Sort và lấy top
        return productSales.values().stream()
                .sorted((a, b) -> Integer.compare(b.getSold(), a.getSold()))
                .limit(limit)
                .map(ProductSalesData::toMap)
                .collect(Collectors.toList());
    }

    /**
     * Lấy phân tích tồn kho
     * 
     * @return Map chứa thông tin tồn kho
     */
    public Map<String, Object> getInventoryAnalytics() {
        List<ProductVariant> variants = productVariantRepository.findAll();
        
        // Tồn kho theo danh mục
        Map<String, CategoryInventory> inventoryByCategory = new HashMap<>();
        
        // Danh sách sản phẩm sắp hết hàng
        List<Map<String, Object>> lowStock = new ArrayList<>();
        
        for (ProductVariant variant : variants) {
            Product product = variant.getProduct();
            if (product != null && product.getCategory() != null) {
                String categoryName = product.getCategory().getName();
                
                CategoryInventory catInventory = inventoryByCategory.getOrDefault(
                        categoryName, new CategoryInventory(categoryName));
                
                catInventory.addStock(variant.getQuantity(), variant.getPrice());
                inventoryByCategory.put(categoryName, catInventory);
                
                // Sản phẩm sắp hết hàng (quantity < 10)
                if (variant.getQuantity() != null && variant.getQuantity() < 10) {
                    Map<String, Object> lowStockItem = new HashMap<>();
                    lowStockItem.put("productId", product.getId());
                    lowStockItem.put("productName", product.getName());
                    lowStockItem.put("variantId", variant.getId());
                    lowStockItem.put("size", variant.getSize());
                    lowStockItem.put("color", variant.getColor());
                    lowStockItem.put("stock", variant.getQuantity());
                    lowStock.add(lowStockItem);
                }
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("byCategory", inventoryByCategory.values().stream()
                .map(CategoryInventory::toMap)
                .collect(Collectors.toList()));
        result.put("lowStock", lowStock);
        result.put("totalVariants", variants.size());
        
        int totalStock = variants.stream()
                .map(ProductVariant::getQuantity)
                .filter(Objects::nonNull)
                .reduce(0, Integer::sum);
        result.put("totalStock", totalStock);

        return result;
    }

    /**
     * Lấy phân tích trạng thái đơn hàng
     * 
     * @return List trạng thái đơn hàng
     */
    public List<Map<String, Object>> getOrderStatusAnalytics() {
        List<Order> orders = orderRepository.findAll();
        
        Map<OrderStatus, StatusData> statusMap = new HashMap<>();
        
        for (Order order : orders) {
            OrderStatus status = order.getOrderStatus();
            StatusData data = statusMap.getOrDefault(status, new StatusData(status));
            data.addOrder(order.getSubTotal());
            statusMap.put(status, data);
        }

        return statusMap.values().stream()
                .map(StatusData::toMap)
                .collect(Collectors.toList());
    }

    /**
     * Lấy phân tích khách hàng
     * 
     * @return Map thông tin khách hàng
     */
    public Map<String, Object> getCustomerAnalytics() {
        List<Customer> customers = customerRepository.findAll();
        List<Order> orders = orderRepository.findAll();

        // Khách hàng mới theo ngày (30 ngày gần nhất)
        LocalDate startDate = LocalDate.now().minusDays(30);
        Map<LocalDate, Long> newCustomersDaily = customers.stream()
                .filter(c -> c.getUser() != null && c.getUser().getCreateAt() != null)
                .filter(c -> !c.getUser().getCreateAt().toLocalDate().isBefore(startDate))
                .collect(Collectors.groupingBy(
                        c -> c.getUser().getCreateAt().toLocalDate(),
                        Collectors.counting()
                ));

        List<Map<String, Object>> newCustomersList = newCustomersDaily.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("date", entry.getKey().toString());
                    map.put("count", entry.getValue());
                    return map;
                })
                .sorted((a, b) -> ((String) a.get("date")).compareTo((String) b.get("date")))
                .collect(Collectors.toList());

        // Top khách hàng chi tiêu nhiều
        Map<String, BigDecimal> customerSpending = new HashMap<>();
        for (Order order : orders) {
            String customerId = order.getCustomerId();
            if (customerId != null) {
                BigDecimal current = customerSpending.getOrDefault(customerId, BigDecimal.ZERO);
                customerSpending.put(customerId, current.add(order.getSubTotal()));
            }
        }

        List<Map<String, Object>> topSpenders = customerSpending.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(10)
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("customerId", entry.getKey());
                    map.put("totalSpent", entry.getValue());
                    
                    // Tìm thông tin khách hàng
                    customers.stream()
                            .filter(c -> c.getCustomerId().equals(entry.getKey()))
                            .findFirst()
                            .ifPresent(c -> {
                                if (c.getUser() != null) {
                                    String fullName = (c.getUser().getFirstName() != null ? c.getUser().getFirstName() : "") +
                                            " " + (c.getUser().getLastName() != null ? c.getUser().getLastName() : "");
                                    map.put("customerName", fullName.trim());
                                    map.put("email", c.getUser().getEmail());
                                }
                            });
                    
                    return map;
                })
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("totalCustomers", customers.size());
        result.put("newCustomersDaily", newCustomersList);
        result.put("topSpenders", topSpenders);

        return result;
    }

    /**
     * Lấy phân tích khuyến mãi
     */
    public Map<String, Object> getPromotionAnalytics() {
        List<Order> orders = orderRepository.findAll();
        
        long ordersWithPromotion = orders.stream()
                .filter(o -> o.getPromotionValue() != null && 
                        o.getPromotionValue().compareTo(BigDecimal.ZERO) > 0)
                .count();
        
        long ordersWithoutPromotion = orders.size() - ordersWithPromotion;
        
        BigDecimal totalPromotionValue = orders.stream()
                .map(Order::getPromotionValue)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> result = new HashMap<>();
        result.put("totalOrders", orders.size());
        result.put("ordersWithPromotion", ordersWithPromotion);
        result.put("ordersWithoutPromotion", ordersWithoutPromotion);
        result.put("totalPromotionValue", totalPromotionValue);

        return result;
    }

    /**
     * Lấy ma trận biến thể (Color x Size)
     */
    public Map<String, Object> getVariantMatrix() {
        List<ProductVariant> variants = productVariantRepository.findAll();
        
        // Lấy danh sách colors và sizes
        Set<String> colors = variants.stream()
                .map(ProductVariant::getColor)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        
        Set<String> sizes = variants.stream()
                .map(ProductVariant::getSize)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // Tạo ma trận
        Map<String, Map<String, Integer>> matrix = new HashMap<>();
        
        for (ProductVariant variant : variants) {
            String color = variant.getColor();
            String size = variant.getSize();
            
            if (color != null && size != null) {
                matrix.putIfAbsent(color, new HashMap<>());
                Map<String, Integer> sizeMap = matrix.get(color);
                sizeMap.put(size, sizeMap.getOrDefault(size, 0) + variant.getQuantity());
            }
        }

        // Chuyển sang format cho chart
        List<Map<String, Object>> data = matrix.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> row = new HashMap<>();
                    row.put("color", entry.getKey());
                    row.putAll(entry.getValue());
                    return row;
                })
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("colors", new ArrayList<>(colors));
        result.put("sizes", new ArrayList<>(sizes));
        result.put("data", data);

        return result;
    }

    // ============== Helper Classes ==============

    /**
     * Class lưu trữ dữ liệu bán hàng của sản phẩm
     */
    private static class ProductSalesData {
        private final Long productId;
        private final String productName;
        private int sold = 0;
        private BigDecimal revenue = BigDecimal.ZERO;
        private String image;

        public ProductSalesData(Long productId, String productName) {
            this.productId = productId;
            this.productName = productName;
        }

        public void addSale(int quantity, BigDecimal lineTotal) {
            this.sold += quantity;
            this.revenue = this.revenue.add(lineTotal);
        }

        public int getSold() {
            return sold;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Map<String, Object> toMap() {
            Map<String, Object> map = new HashMap<>();
            map.put("productId", productId);
            map.put("productName", productName);
            map.put("sold", sold);
            map.put("revenue", revenue);
            map.put("image", image);
            return map;
        }
    }

    /**
     * Class lưu trữ tồn kho theo danh mục
     */
    private static class CategoryInventory {
        private final String categoryName;
        private int totalStock = 0;
        private BigDecimal totalValue = BigDecimal.ZERO;

        public CategoryInventory(String categoryName) {
            this.categoryName = categoryName;
        }

        public void addStock(Integer quantity, BigDecimal price) {
            if (quantity != null) {
                this.totalStock += quantity;
            }
            if (quantity != null && price != null) {
                this.totalValue = this.totalValue.add(
                        price.multiply(BigDecimal.valueOf(quantity))
                );
            }
        }

        public Map<String, Object> toMap() {
            Map<String, Object> map = new HashMap<>();
            map.put("categoryName", categoryName);
            map.put("totalStock", totalStock);
            map.put("totalValue", totalValue);
            return map;
        }
    }

    /**
     * Class lưu trữ dữ liệu trạng thái đơn hàng
     */
    private static class StatusData {
        private final OrderStatus status;
        private int count = 0;
        private BigDecimal totalValue = BigDecimal.ZERO;

        public StatusData(OrderStatus status) {
            this.status = status;
        }

        public void addOrder(BigDecimal subTotal) {
            this.count++;
            if (subTotal != null) {
                this.totalValue = this.totalValue.add(subTotal);
            }
        }

        public Map<String, Object> toMap() {
            Map<String, Object> map = new HashMap<>();
            map.put("status", status.name());
            map.put("statusName", getStatusName(status));
            map.put("count", count);
            map.put("totalValue", totalValue);
            return map;
        }

        private String getStatusName(OrderStatus status) {
            switch (status) {
                case PENDING:
                    return "Chờ xử lý";
                case PREPARING:
                    return "Đang chuẩn bị";
                case ARRIVED_AT_LOCATION:
                    return "Đã đến kho";
                case SHIPPED:
                    return "Đang giao";
                case DELIVERED:
                    return "Đã giao";
                case CANCELLED:
                    return "Đã hủy";
                case RETURNED:
                    return "Đã trả hàng";
                default:
                    return status.name();
            }
        }
    }
}
