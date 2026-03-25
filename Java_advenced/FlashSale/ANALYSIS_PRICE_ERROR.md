# 📊 PHÂN TÍCH LỖI: PRICE = 0 TRONG ORDER_DETAILS

**Vấn đề**: Lưu `Order_Details.price = 0` thay vì lưu giá thực tế  
**Nguy hiểm**: Dữ liệu lịch sử bị mất → Báo cáo doanh thu sai lệch  
**Loại lỗi**: Data Integrity & Business Logic Error  
**Mức độ**: 🔴 **CỰC CAO - CRITICAL**

---

## 🔴 VẤN ĐỀ CHI TIẾT

### **Code hiện tại (LỖI)**:
```java
// OrderDetailDAO.java - dòng 19
ps.setDouble(4, 0); // ❌ HARDCODE GIÁ = 0
```

**Khi chạy**:
```
Order_Details:
  order_id | product_id | quantity | price
     1     |     1      |    1     |   0   ← ❌ SAI
     1     |     2      |    2     |   0   ← ❌ SAI
```

---

## 📋 TẠI SAO ĐÂY LÀ LỖI?

### **1. Mất thông tin giá lịch sử**

```
Thực tế:
  Ngày 25/3/2026: Product A có giá 100.000 VND
  User mua 1 cái → Order_Details.price phải = 100.000

Hiện tại (LỖI):
  Order_Details.price = 0 ← Mất thông tin 100.000 VND
```

**Hậu quả**:
- ❌ Không thể tính doanh thu đúng = SUM(price × quantity)
- ❌ Không thể kiểm toán lịch sử giao dịch
- ❌ Báo cáo tài chính sai 100%

---

### **2. Vấn đề với giá Products thay đổi theo thời gian**

**Kịch bản**:
```
Thời điểm 1 (25/3/2026):
  Products.price = 100.000 VND
  User A mua → Order_Details phải lưu 100.000
  
Thời điểm 2 (26/3/2026):
  Products.price = 80.000 VND (sale)
  User B mua → Order_Details phải lưu 80.000
  
Nếu giá = 0:
  ❌ Cả 2 orders đều có price = 0
  ❌ Báo cáo doanh thu = 0 (SAIDATA)
  
Nếu JOIN từ Products:
  SELECT SUM(p.price * od.quantity)
  = 80.000 × (A's qty + B's qty) ← ❌ SAI
  (Dùng giá hiện tại, không phải giá lúc mua)
```

---

### **3. Tính toàn vẹn dữ liệu (Data Integrity)**

**Nguyên tắc ACID - Atomicity & Integrity**:
- Order_Details là bản ghi **lịch sử giao dịch**
- Mỗi record phải **không thay đổi** (immutable)
- Phải **chứa đầy đủ thông tin** tại thời điểm mua

**Hiện tại vi phạm**:
- ❌ Dữ liệu **thiếu** (price = 0)
- ❌ Dữ liệu **phụ thuộc** vào bảng khác (phải JOIN)
- ❌ Dữ liệu **có thể bị thay đổi gián tiếp** (nếu Products.price đổi)

---

## ✅ GIẢI PHÁP ĐÚNG

### **Cách 1: Lấy giá từ Products khi tạo Order_Details (TỐT NHẤT)**

```java
// OrderDetailDAO.java
public void insertBatch(Connection conn, int orderId, List<OrderItem> items) throws Exception {
    String sql = "INSERT INTO Order_Details(order_id, product_id, quantity, price) " +
                 "VALUES (?, ?, ?, ?)";
    
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        
        for (OrderItem item : items) {
            // ✅ Lấy giá hiện tại từ Products
            double productPrice = getProductPrice(conn, item.productId);
            
            ps.setInt(1, orderId);
            ps.setInt(2, item.productId);
            ps.setInt(3, item.quantity);
            ps.setDouble(4, productPrice);  // ✅ Lưu giá thực tế
            
            ps.addBatch();
        }
        
        ps.executeBatch();
    }
}

// Helper method
private double getProductPrice(Connection conn, int productId) throws Exception {
    String sql = "SELECT price FROM Products WHERE product_id = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, productId);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            return rs.getDouble("price");
        }
        throw new Exception("Sản phẩm không tồn tại: " + productId);
    }
}
```

**Kết quả**:
```
Order_Details:
  order_id | product_id | quantity | price
     1     |     1      |    1     | 100.000  ✅ ĐÚNG
     1     |     2      |    2     | 80.000   ✅ ĐÚNG
```

---

### **Cách 2: OrderItem chứa price (LỖI 3 ở báo cáo)**

```java
// entity/OrderItem.java
public class OrderItem {
    public int productId;
    public int quantity;
    public double price;  // ✅ Thêm price
    
    public OrderItem(int productId, int quantity, double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }
}

// OrderDetailDAO.java
public void insertBatch(Connection conn, int orderId, List<OrderItem> items) throws Exception {
    String sql = "INSERT INTO Order_Details(order_id, product_id, quantity, price) " +
                 "VALUES (?, ?, ?, ?)";
    
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        
        for (OrderItem item : items) {
            ps.setInt(1, orderId);
            ps.setInt(2, item.productId);
            ps.setInt(3, item.quantity);
            ps.setDouble(4, item.price);  // ✅ Lấy từ OrderItem
            
            ps.addBatch();
        }
        
        ps.executeBatch();
    }
}
```

---

## 📊 SO SÁNH 3 CÁCH LÀM

| Cách | Mô tả | Ưu điểm | Nhược điểm | Khuyến cáo |
|------|-------|---------|-----------|-----------|
| **Hiện tại (SAI)** | price = 0 | Nhanh | ❌ Mất dữ liệu, báo cáo sai | ❌ KHÔNG DÙNG |
| **Cách 1** | Lấy từ Products | ✅ Lưu đúng giá, logic trong DAO | ❌ Thêm 1 query/item | ✅ TỐTHAT |
| **Cách 2** | OrderItem có price | ✅ Logic trong Service, hiệu quả | ❌ OrderItem phức tạp | ✅ CŨNG TỐT |
| **Cách 3 (SAI)** | JOIN từ Products | Nhanh | ❌ Báo cáo sai khi giá thay đổi | ❌ KHÔNG DÙNG |

**Khuyến cáo**: Dùng **Cách 1** (lấy từ Products) là tốt nhất

---

## 🚨 HẬU QUẢ CỦA LỖI

### **Trên Báo Cáo Doanh Thu**

**Hiện tại (SAI)**:
```sql
CALL GetRevenueByCategory();

Kết quả:
  Category | Revenue
  ---------|----------
  Test     |    0     ← ❌ SAI (phải > 0)
  
SELECT SUM(price * quantity) FROM Order_Details;
-- Result: 0 ❌ (phải > 0)
```

**Sau khi sửa (ĐÚNG)**:
```sql
CALL GetRevenueByCategory();

Kết quả:
  Category | Revenue
  ---------|----------
  Test     | 500.000  ← ✅ ĐÚNG
  
SELECT SUM(price * quantity) FROM Order_Details;
-- Result: 500.000 ✅
```

### **Tác động doanh sở**

```
Tình huống:
  Flash sale 1000 orders, tổng doanh thu = 10 tỷ VND
  
Hiện tại (SAI):
  Báo cáo: 0 VND ← Sai lệch 10 tỷ VND!
  Quyết định sai (không biết bán được bao nhiêu)
  
Sau sửa (ĐÚNG):
  Báo cáo: 10 tỷ VND ← Chính xác
  Quyết định đúng
```

---

## ✅ KẾT LUẬN

**Vâng, đây CHÍNH XÁC LÀ LỖI CỦA BÀI**

- ✅ **Lỗi được báo cáo ở**: `ERROR_REPORT_ORDER_AND_REPORTING.md` - **Lỗi 4**
- ✅ **Mức độ**: 🔴 **CỰC CAO - CRITICAL**
- ✅ **Loại lỗi**: Data Integrity + Business Logic
- ✅ **Gây sai**: Báo cáo doanh thu 100%

**Bạn nhận ra chính xác vấn đề**:
- ✅ Giá Products thay đổi theo thời gian
- ✅ Order_Details phải lưu giá tại thời điểm mua
- ✅ Price = 0 mất thông tin → báo cáo sai

**Giải pháp**:
1. **Cách 1 (Khuyến cáo)**: Lấy price từ Products khi insert Order_Details
2. **Cách 2**: Thêm price field vào OrderItem (sửa Lỗi 3)
3. **TRÁNH Cách 3**: Dùng JOIN sau (dữ liệu sẽ sai nếu giá thay đổi)

Đây là một trong các lỗi **CRITICAL** của bài mà cần sửa ngay!

