# 📋 TÓNG HỢP LỖI - QUY TRÌNH ĐẶT HÀNG VÀ BÁO CÁO

**Dự án**: FlashSale Order Management System  
**Phạm vi**: OrderService, OrderDAO, OrderDetailDAO, ReportDAO  
**Ngày**: 25/03/2026

---

## 🔴 DANH SÁCH LỖI TÓM TẮT

| # | Lỗi | File | Loại | Mức độ | Phát sinh | Ưu tiên |
|---|-----|------|------|-------|----------|---------|
| **1** | ResultSet Leak (check stock) | OrderService.java | Resource | 🔴 CAO | Lâu (~100 gọi) | 2️⃣ |
| **2** | PreparedStatement Leak (update stock) | OrderService.java | Resource | 🔴 CAO | Lâu | 3️⃣ |
| **3** | PreparedStatement Leak (insert details) | OrderDetailDAO.java | Resource | 🔴 CAO | **NGAY** | **1️⃣** |
| **4** | Price = 0 hardcode | OrderDetailDAO.java | Logic | 🔴 CỰC CAO | **NGAY (mỗi order)** | **1️⃣** |
| **5** | ResultSet không close | OrderDAO.java | Resource | 🟡 THẤP | Lâu | 4️⃣ |

---

## 🔍 CHI TIẾT TỪNG LỖI

### ⚠️ **LỖI 4: PRICE = 0 - CRITICAL (Data Integrity)** 🔴

**Vị trí**: `OrderDetailDAO.java`, dòng 19

**Code hiện tại (LỖI)**:
```java
public void insertBatch(Connection conn, int orderId, List<OrderItem> items) throws Exception {
    String sql = "INSERT INTO Order_Details(order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
    
    PreparedStatement ps = conn.prepareStatement(sql);
    
    for (OrderItem item : items) {
        ps.setInt(1, orderId);
        ps.setInt(2, item.productId);
        ps.setInt(3, item.quantity);
        ps.setDouble(4, 0);  // ❌ HARDCODE GIÁ = 0 - CRITICAL ERROR!
        ps.addBatch();
    }
    
    ps.executeBatch();
}
```

**Tại sao đây là LỖI CỰC CAO?**

#### **Vấn đề 1: Mất thông tin giá lịch sử**
```
Thực tế:
  Product A: 100.000 VND (ngày 25/3)
  User mua 1 cái
  
Hiện tại (SAI):
  Order_Details.price = 0 ← ❌ Mất 100.000 VND!

Hậu quả:
  - Không thể tính doanh thu = SUM(price × quantity)
  - Không thể kiểm toán lịch sử giao dịch
  - Báo cáo tài chính SAI 100%
```

#### **Vấn đề 2: Giá sản phẩm thay đổi theo thời gian**
```
Timeline:
  25/3 - 09:00: Product A = 100.000 VND
    User X mua → Order_Details phải lưu 100.000
    
  25/3 - 12:00: Product A = 80.000 VND (sale)
    User Y mua → Order_Details phải lưu 80.000

Nếu giá = 0 (hiện tại - SAI):
  Order 1: price = 0
  Order 2: price = 0
  ❌ Báo cáo doanh thu = 0 (SAIDATA)
  
Nếu JOIN từ Products (CŨNG SAI):
  SELECT SUM(p.price * od.quantity)
  = 80.000 × (qty1 + qty2)
  ❌ Dùng giá hiện tại (80k), không phải giá lúc mua (100k)
  ❌ Báo cáo doanh thu vẫn SAI
```

#### **Vấn đề 3: Vi phạm Data Integrity (ACID - Atomicity & Integrity)**

**Nguyên tắc**:
- Order_Details là bản ghi **lịch sử giao dịch** (immutable)
- Phải **chứa đầy đủ thông tin** tại thời điểm mua
- **Không được phụ thuộc** vào bảng khác

**Hiện tại vi phạm**:
```
❌ Dữ liệu THIẾU (price = 0)
❌ Dữ liệu PHỤ THUỘC vào Products table
❌ Dữ liệu CÓ THỂ BỊ THAY ĐỔI GIÁN TIẾP (nếu Products.price đổi)
```

---

### 📊 **Ví dụ thực tế - Hậu quả của Lỗi 4:**

```
FLASH SALE - 25/3/2026:
  1000 orders, mỗi order trung bình 10.000 VND
  Tổng doanh thu thực tế: 10 TỶ VND

HIỆN TẠI (SAI):
  ORDER_DETAILS:
    order_id | product_id | quantity | price
      1      |     1      |    1     |   0    ← ❌
      2      |     2      |    2     |   0    ← ❌
      ...
      1000   |    ...     |   ...    |   0    ← ❌

  BÁOW CÁO DOANH THU:
    SELECT SUM(price * quantity) FROM Order_Details
    = 0 VND  ← ❌❌ SAI LỆCH 10 TỶ VND!

  HỆ QUẢ:
    ❌ Không biết đã bán được bao nhiêu
    ❌ Quyết định kinh doanh sai
    ❌ Báo cáo tài chính không chính xác
    ❌ Kiểm toán không thể xác minh

SAU KHI SỬA (ĐÚNG):
  ORDER_DETAILS:
    order_id | product_id | quantity | price
      1      |     1      |    1     |  100.000  ← ✅ (giá tại thời điểm mua)
      2      |     2      |    2     |   80.000  ← ✅
      ...
      1000   |    ...     |   ...    | 10.000    ← ✅

  BÁOW CÁO DOANH THU:
    SELECT SUM(price * quantity) FROM Order_Details
    = 10.000.000.000 VND (10 tỷ)  ← ✅ ĐÚNG!

  HỆ QUẢ:
    ✅ Biết chính xác đã bán được 10 tỷ
    ✅ Quyết định kinh doanh CHÍNH XÁC
    ✅ Báo cáo tài chính chính xác
    ✅ Kiểm toán có thể xác minh
```

---

### **Giải pháp Lỗi 4:**

#### **Cách 1: Lấy price từ Products khi insert (KHUYẾN CÁO)**
```java
public void insertBatch(Connection conn, int orderId, List<OrderItem> items) throws Exception {
    String sql = "INSERT INTO Order_Details(order_id, product_id, quantity, price) " +
                 "VALUES (?, ?, ?, ?)";
    
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        
        for (OrderItem item : items) {
            // ✅ Lấy giá hiện tại từ Products table
            double productPrice = getProductPrice(conn, item.productId);
            
            ps.setInt(1, orderId);
            ps.setInt(2, item.productId);
            ps.setInt(3, item.quantity);
            ps.setDouble(4, productPrice);  // ✅ Lưu giá thực tế!
            
            ps.addBatch();
        }
        
        ps.executeBatch();
    }
}

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
     1     |     1      |    1     | 100.000  ✅
     2     |     2      |    2     |  80.000  ✅
```

#### **Cách 2: OrderItem chứa price (Sửa Lỗi 3)**
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
for (OrderItem item : items) {
    ps.setInt(1, orderId);
    ps.setInt(2, item.productId);
    ps.setInt(3, item.quantity);
    ps.setDouble(4, item.price);  // ✅ Lấy từ OrderItem
    ps.addBatch();
}
```

---

### **LỖI 3: PreparedStatement Leak trong OrderDetailDAO** 🔴

**Vị trí**: `OrderDetailDAO.java`, dòng 14-20  
**Phân loại**: Resource Leak (PreparedStatement)  
**Mức độ**: 🔴 CAO - **ĐỨ TIÊN CẦN SỬA (sau Lỗi 4)**

**Code gốc (LỖI)**:
```java
public void insertBatch(Connection conn, int orderId, List<OrderItem> items) throws Exception {
    String sql = "INSERT INTO Order_Details(order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
    
    PreparedStatement ps = conn.prepareStatement(sql);  // ❌ Tạo PS
    
    for (OrderItem item : items) {
        ps.setInt(1, orderId);
        ps.setInt(2, item.productId);
        ps.setInt(3, item.quantity);
        ps.setDouble(4, 0);
        ps.addBatch();
    }
    
    ps.executeBatch();
    // ❌ ps không được close!
}
```

**Nguyên nhân**:
- PreparedStatement tạo nhưng không close
- Mỗi lần `insertBatch()` gọi → 1 PS leak
- Trong test 50 orders → 50 PS không đóng

**Hậu quả**:
```
Khi chạy 50 threads placeOrder():
  Thread 1: insertBatch() → PS leak
  Thread 2: insertBatch() → PS leak
  ...
  Thread 50: insertBatch() → PS leak
  
  Kết quả: 50 PreparedStatements không đóng
  → Connection pool exhausted sau ~30-40 orders
  → Transaction ROLLBACK
  → Order detail không được tạo
```

**Giải pháp (Try-with-resources)**:
```java
public void insertBatch(Connection conn, int orderId, List<OrderItem> items) throws Exception {
    String sql = "INSERT INTO Order_Details(order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
    
    try (PreparedStatement ps = conn.prepareStatement(sql)) {  // ✅ Auto-close
        
        for (OrderItem item : items) {
            ps.setInt(1, orderId);
            ps.setInt(2, item.productId);
            ps.setInt(3, item.quantity);
            ps.setDouble(4, item.price);  // ✅ Cũng sửa lỗi 4
            ps.addBatch();
        }
        
        ps.executeBatch();
        
    } catch (SQLException e) {
        throw new Exception("Lỗi insert Order_Details: " + e.getMessage(), e);
    }
}
```

---

### **LỖI 1: ResultSet Leak trong OrderService** 🔴

**Vị trí**: `OrderService.java`, dòng 45-50  
**Phân loại**: Resource Leak (ResultSet)  
**Mức độ**: 🔴 CAO

**Code gốc (LỖI)**:
```java
for (OrderItem item : items) {
    if (item.quantity <= 0) {
        throw new Exception("Số lượng không hợp lệ: product_id=" + item.productId);
    }
    
    checkStmt.setInt(1, item.productId);
    ResultSet rs = checkStmt.executeQuery();  // ❌ Tạo RS
    
    if (rs.next()) {
        int stock = rs.getInt("stock");
        if (stock < item.quantity) {
            throw new Exception("Hết hàng: product_id=" + item.productId);
        }
    } else {
        throw new Exception("Không tìm thấy sản phẩm: product_id=" + item.productId);
    }
    // ❌ rs không được close!
}
```

**Hậu quả**:
- Mỗi loop tạo 1 ResultSet chưa close
- 50 items = 50 ResultSet leak
- Sau ~100-500 lần gọi: `SQLException: No more data sources available`

**Giải pháp**:
```java
for (OrderItem item : items) {
    if (item.quantity <= 0) {
        throw new Exception("Số lượng không hợp lệ: product_id=" + item.productId);
    }
    
    checkStmt.setInt(1, item.productId);
    try (ResultSet rs = checkStmt.executeQuery()) {  // ✅ Auto-close RS
        if (rs.next()) {
            int stock = rs.getInt("stock");
            if (stock < item.quantity) {
                throw new Exception("Hết hàng: product_id=" + item.productId);
            }
        } else {
            throw new Exception("Không tìm thấy sản phẩm: product_id=" + item.productId);
        }
    }
}
```

---

### **LỖI 2: PreparedStatement Leak trong OrderService** 🔴

**Vị trí**: `OrderService.java`, dòng 63-71  
**Phân loại**: Resource Leak (PreparedStatement)  
**Mức độ**: 🔴 CAO

**Code gốc (LỖI)**:
```java
String updateSql = "UPDATE Products SET stock = stock - ? WHERE product_id = ?";
PreparedStatement updateStmt = conn.prepareStatement(updateSql);

for (OrderItem item : items) {
    updateStmt.setInt(1, item.quantity);
    updateStmt.setInt(2, item.productId);
    updateStmt.addBatch();
}

updateStmt.executeBatch();
// ❌ updateStmt không được close!
```

**Giải pháp**:
```java
String updateSql = "UPDATE Products SET stock = stock - ? WHERE product_id = ?";

try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {  // ✅ Auto-close

    for (OrderItem item : items) {
        updateStmt.setInt(1, item.quantity);
        updateStmt.setInt(2, item.productId);
        updateStmt.addBatch();
    }
    
    updateStmt.executeBatch();
    
} catch (SQLException e) {
    throw new SQLException("Lỗi update stock: " + e.getMessage(), e);
}
```

---

### **LỖI 5: Minor ResultSet Leak trong OrderDAO** 🟡

**Vị trí**: `OrderDAO.java`, dòng 12  
**Phân loại**: Minor Resource Leak  
**Mức độ**: 🟡 THẤP

**Code gốc (LỖI)**:
```java
public int createOrder(Connection conn, int userId) throws Exception {
    String sql = "INSERT INTO Orders(user_id) VALUES (?)";
    PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    ps.setInt(1, userId);
    ps.executeUpdate();
    
    ResultSet rs = ps.getGeneratedKeys();  // ❌ RS không close (minor)
    if (rs.next()) return rs.getInt(1);
    
    throw new Exception("Không tạo được order");
}
```

**Giải pháp**:
```java
public int createOrder(Connection conn, int userId) throws Exception {
    String sql = "INSERT INTO Orders(user_id) VALUES (?)";
    
    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        ps.setInt(1, userId);
        ps.executeUpdate();
        
        try (ResultSet rs = ps.getGeneratedKeys()) {  // ✅ Auto-close RS
            if (rs.next()) return rs.getInt(1);
        }
    }
    
    throw new Exception("Không tạo được order");
}
```

---

## 📊 MA TRẬN TÓNG HỢP

```
Lỗi | Phát sinh | Mức độ | Ảnh hưởng | Sửa |
----|-----------|--------|----------|------|
4️⃣  | NGAY      | 🔴 CỰC CAO | Báo cáo sai 100% | TRƯ TIÊN 1 |
3️⃣  | NGAY      | 🔴 CAO     | Order fail      | TRƯ TIÊN 1 |
1️⃣  | Lâu (~100)| 🔴 CAO     | Crash app       | TRƯ TIÊN 2 |
2️⃣  | Lâu       | 🔴 CAO     | Crash app       | TRƯ TIÊN 2 |
5️⃣  | Lâu       | 🟡 THẤP    | Minimal         | TRƯ TIÊN 3 |
```

---

## 🎯 KẾT LUẬN

### **Lỗi CỰC CAO - PHẢI SỬA NGAY:**
1. ❌ **Lỗi 4 (price = 0)** - Báo cáo doanh thu SAI 100%
2. ❌ **Lỗi 3 (PS leak)** - Order chi tiết không insert được

### **Lỗi CAO - PHẢI SỬA:**
3. ❌ **Lỗi 1 (RS leak)** - Crash sau ~100 gọi
4. ❌ **Lỗi 2 (PS leak)** - Crash khi update stock

### **Lỗi THẤP - NÊN SỬA:**
5. ⚠️ **Lỗi 5 (RS minor leak)** - Minimal impact

**Khuyến cáo ưu tiên sửa**: 4 → 3 → 1 → 2 → 5

