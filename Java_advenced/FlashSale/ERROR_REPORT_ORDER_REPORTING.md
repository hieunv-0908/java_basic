# 🔴 BÁO CÁO LỖI - QUY TRÌNH ĐẶT HÀNG VÀ BÁO CÁO

**Ngày**: 25/03/2026  
**Dự án**: FlashSale Order Management System  
**Phạm vi kiểm tra**: OrderService, ReportDAO, OrderDAO, OrderDetailDAO  

---

## 📌 TÓM TẮT LỖI

| # | File | Loại Lỗi | Mức độ | Dòng | Phát sinh khi |
|---|------|----------|-------|------|---------------|
| 1 | OrderService.java | Resource Leak (ResultSet) | 🔴 CAO | 45-50 | Runtime (sau ~100 lần gọi) |
| 2 | OrderService.java | Resource Leak (PreparedStatement) | 🔴 CAO | 63 | Runtime (mỗi lần executeBatch) |
| 3 | OrderDetailDAO.java | Resource Leak (PreparedStatement) | 🔴 CAO | 14-20 | Runtime (mỗi Order chi tiết) |
| 4 | OrderDetailDAO.java | Logic Error (Giá = 0) | 🟡 TRUNG | 19 | Mỗi lần insert Order_Details |
| 5 | OrderItem.java | Design Issue (Thiếu giá) | 🟡 TRUNG | - | Mỗi lần sử dụng |
| 6 | OrderDAO.java | Minor Leak (ResultSet) | 🟡 THẤP | 12 | Minimal impact |
| 7 | ProductDAO.java | Connection Leak | 🔴 CỰC CAO | 15-16 | Mỗi lần gọi addProduct() |

---

## 🔍 CHI TIẾT TỪNG LỖI

### **LỖI 1: Resource Leak - ResultSet trong OrderService** 🔴

**Vị trí**: `OrderService.java`, dòng 45-50  
**Phân loại**: Resource Leak (Serious)  
**Mức độ**: 🔴 CAO - Sẽ hết kết nối sau nhiều gọi

**Code gốc**:
```java
for (OrderItem item : items) {
    if (item.quantity <= 0) {
        throw new Exception("Số lượng không hợp lệ: product_id=" + item.productId);
    }
    
    checkStmt.setInt(1, item.productId);
    ResultSet rs = checkStmt.executeQuery();  // ❌ Tạo ResultSet
    
    if (rs.next()) {
        int stock = rs.getInt("stock");
        if (stock < item.quantity) {
            throw new Exception("Hết hàng: product_id=" + item.productId);
        }
    } else {
        throw new Exception("Không tìm thấy sản phẩm: product_id=" + item.productId);
    }
    // ❌ ResultSet không được close!
}
```

**Nguyên nhân**: 
- Mỗi lần loop tạo một `ResultSet` mới
- Không gọi `rs.close()` sau khi sử dụng
- Khi có 50 items, tạo 50 ResultSet mà không đóng

**Hậu quả**:
- Memory leak - kết nối DB tích tụ không được giải phóng
- Sau ~100-500 lần gọi: `SQLException: No more data sources available`
- Trong test 50 threads, có thể xảy ra **ngay lần thứ 2-3**

**Cách phát hiện**: 
- Chạy TestFlashSale 2-3 lần liên tiếp → sẽ crash
- Monitor: Số lượng open connections tăng liên tục

---

### **LỖI 2: Resource Leak - PreparedStatement trong OrderService** 🔴

**Vị trí**: `OrderService.java`, dòng 63-71  
**Phân loại**: Resource Leak (Serious)  
**Mức độ**: 🔴 CAO

**Code gốc**:
```java
String updateSql = "UPDATE Products SET stock = stock - ? WHERE product_id = ?";
PreparedStatement updateStmt = conn.prepareStatement(updateSql);

for (OrderItem item : items) {
    updateStmt.setInt(1, item.quantity);
    updateStmt.setInt(2, item.productId);
    updateStmt.addBatch();
}

updateStmt.executeBatch();  // ✅ Thực thi
// ❌ updateStmt không được close!
```

**Nguyên nhân**: 
- PreparedStatement `updateStmt` tạo nhưng không close
- Mỗi gọi `placeOrder()` tạo thêm 1 PreparedStatement không đóng

**Hậu quả**:
- Tương tự lỗi 1
- Kết hợp với lỗi 1 & 3, có thể hết kết nối rất nhanh

**Cách phát hiện**:
- Chạy 50 threads → Một số thread sẽ fail do connection exhausted

---

### **LỖI 3: Resource Leak - PreparedStatement trong OrderDetailDAO** 🔴

**Vị trí**: `OrderDetailDAO.java`, dòng 14-20  
**Phân loại**: Resource Leak (Serious)  
**Mức độ**: 🔴 CAO

**Code gốc**:
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
- Mỗi lần `insertBatch()` được gọi, tạo PreparedStatement mới
- Không close sau dùng
- Trong test 50 lần → tạo 50 PreparedStatement không đóng

**Hậu quả**:
- **MỤC TIÊU CHÍNH** trong test flash sale
- Mỗi order = 1 PreparedStatement không đóng
- 50 orders = 50 PS leak → Connection hết rất nhanh

---

### **LỖI 4: Logic Error - Giá sản phẩm = 0 trong OrderDetailDAO** 🟡

**Vị trí**: `OrderDetailDAO.java`, dòng 19  
**Phân loại**: Business Logic Error  
**Mức độ**: 🟡 TRUNG - Sai dữ liệu

**Code gốc**:
```java
ps.setDouble(4, 0); // giả sử lấy giá sau
```

**Nguyên nhân**:
- Hardcode giá = 0
- Chỉ là tạm thời nhưng không bao giờ fix

**Hậu quả**:
- **DOANH THU BÁO CÁO SẼ SAI**
- Tất cả Order_Details có price = 0
- `GetRevenueByCategory()` trả về 0 (hoặc NULL)
- Báo cáo doanh thu vô dụng

**Ví dụ**:
```
Đặt mua: 10 sản phẩm × 100.000 VND = 1.000.000 VND
Kết quả DB: Order_Details.price = 0
Báo cáo doanh thu: 0 VND ❌
```

---

### **LỖI 5: Design Issue - OrderItem thiếu trường giá** 🟡

**Vị trí**: `OrderItem.java`  
**Phân loại**: Design Issue  
**Mức độ**: 🟡 TRUNG

**Code gốc**:
```java
public class OrderItem {
    public int productId;
    public int quantity;
    // ❌ Không có price
}
```

**Nguyên nhân**:
- Entity thiết kế không đầy đủ
- Không có cách lấy giá sản phẩm để insert vào Order_Details

**Hậu quả**:
- Phải hardcode `price = 0` (như lỗi 4)
- Không thể truy vết giá lịch sử

**Giải pháp cần thiết**:
```java
public class OrderItem {
    public int productId;
    public int quantity;
    public double price;  // ✅ Cần thêm
}
```

---

### **LỖI 6: Minor Leak - ResultSet trong OrderDAO** 🟡

**Vị trí**: `OrderDAO.java`, dòng 12  
**Phân loại**: Minor Resource Leak  
**Mức độ**: 🟡 THẤP - Tác động nhỏ

**Code gốc**:
```java
public int createOrder(Connection conn, int userId) throws Exception {
    String sql = "INSERT INTO Orders(user_id) VALUES (?)";
    PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    ps.setInt(1, userId);
    ps.executeUpdate();
    
    ResultSet rs = ps.getGeneratedKeys();  // ❌ Tạo RS
    if (rs.next()) return rs.getInt(1);
    // ❌ rs không close (nhưng PS sẽ đóng nó khi đóng)
    
    throw new Exception("Không tạo được order");
}
```

**Nguyên nhân**:
- ResultSet không explicit close
- Nhưng PreparedStatement sẽ đóng nó

**Hậu quả**:
- Minimal impact (connection vẫn close)
- Nhưng là best practice violation

---

### **LỖI 7: Connection Leak trong ProductDAO** 🔴

**Vị trí**: `ProductDAO.java`, dòng 15-16  
**Phân loại**: Resource Leak (Connection + PreparedStatement)  
**Mức độ**: 🔴 CỰC CAO - Rất nghiêm trọng

**Code gốc**:
```java
public void addProduct(String name, double price, int stock, String category) throws Exception {
    if (name == null || name.isEmpty()) throw new Exception("Tên không hợp lệ");
    if (price <= 0) throw new Exception("Giá phải > 0");
    if (stock < 0) throw new Exception("Stock không hợp lệ");
    
    String sql = "INSERT INTO Products(name, price, stock, category) VALUES (?, ?, ?, ?)";
    PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);  // ❌ Tạo connection mới
    ps.setString(1, name);
    ps.setDouble(2, price);
    ps.setInt(3, stock);
    ps.setString(4, category);
    ps.executeUpdate();
    // ❌ Không close ps
    // ❌ Không close connection!
}
```

**Nguyên nhân**:
- Mỗi gọi `DatabaseConnection.getConnection()` tạo **Connection mới**
- Không close PreparedStatement
- Không close Connection
- Gọi trong loop (initData) → leak nhiều connection

**Hậu quả**:
- **CỰC CAO** - Có thể crash ngay khi init data
- Trong `TestFlashSale.initData()`, loop 10 lần tạo 10 connection không close
- Nếu connection pool max = 10, sẽ hết connection từ lần thứ 11

**Ví dụ crash**:
```
For i = 1 to 10:
  addProduct() → DatabaseConnection.getConnection() → Connection mới
  ❌ Không close
Sau 10 lần:
  10 connections pending close
  connection pool hết → SQLException
```

---

## 🚨 TỔNG HỢP HẬU QUẢ

### **Khi chạy TestFlashSale:**

1. **Giai đoạn 1 - initData()**:
   - ❌ LỖI 7 phát sinh ngay
   - Tạo 10 Users → 10 PreparedStatements không close
   - Tạo 5 Products → 5 connection leak
   - **Kết quả**: Có thể SQLException sau 10-15 lần insert

2. **Giai đoạn 2 - 50 threads placeOrder()**:
   - ❌ LỖI 1 phát sinh (ResultSet leak)
   - ❌ LỖI 2 phát sinh (PreparedStatement leak)
   - ❌ LỖI 3 phát sinh (OrderDetailDAO leak)
   - Mỗi thread → 1+ ResultSet + 1+ PreparedStatement chưa close
   - 50 threads = 50+ resource leak
   - **Kết quả**: `SQLException: No more data sources available` sau 30-40 orders

3. **Giai đoạn 3 - Report**:
   - ❌ LỖI 4 phát hiện (giá = 0)
   - **Kết quả**: Doanh thu báo cáo = 0 (SAI DỮ LIỆU)

---

## 📊 MA TRẬN RỦI RO

```
Nguy hiểm    | Lỗi 1 | Lỗi 2 | Lỗi 3 | Lỗi 4 | Lỗi 5 | Lỗi 6 | Lỗi 7 |
-------------|-------|-------|-------|-------|-------|-------|-------|
Crash app    |  ✅   |  ✅   |  ✅   |       |       |       |  ✅✅ |
Sai dữ liệu  |       |       |       |  ✅   |  ✅   |       |       |
Memory leak  |  ✅   |  ✅   |  ✅   |       |       |  ✅   |  ✅   |
Conn pool    |  ✅   |  ✅   |  ✅   |       |       |  ⚠️   |  ✅✅ |
-------------|-------|-------|-------|-------|-------|-------|-------|
Phát sinh    | Lâu   | Lâu   | NGAY  | NGAY  | NGAY  | Lâu   | NGAY  |
```

---

## ✅ KHUYẾN CÁO KIỂM CHỨNG

### **Cách phát hiện lỗi 1-3 (Resource Leak)**:
```bash
# Monitor open connections
SELECT COUNT(*) FROM INFORMATION_SCHEMA.PROCESSLIST WHERE DB = 'FlashSale';
# Chạy test → số connections tăng không giảm → Resource Leak
```

### **Cách phát hiện lỗi 4 (Giá = 0)**:
```sql
SELECT SUM(price) FROM Order_Details;
-- Result: 0 hoặc NULL (phải > 0)
```

### **Cách phát hiện lỗi 7 (Connection Leak)**:
- Chạy `initData()` → Monitor connections trong MySQL
- Sẽ thấy số connections tăng từ 1 → 10+ và không giảm

---

## 🎯 KẾT LUẬN

- **7 lỗi phát hiện**, trong đó **4 lỗi CẦN ĐỀU YỲU** (1, 2, 3, 7)
- **Lỗi 7 (ProductDAO connection leak) ĐỘC HẠI NHẤT** - Có thể crash ngay trong `initData()`
- **Lỗi 3 (OrderDetailDAO) QUAN TRỌNG NHẤT** - Mỗi order → 1 PS leak, test 50 orders = 50 leaks
- **Lỗi 4 (Logic giá = 0) LÀM SAI DỮ LIỆU** - Báo cáo doanh thu vô dụng

**Khuyến cáo**: Ưu tiên sửa lỗi 7 → 3 → 1 → 2 → 4 để đảm bảo test chạy ổn định.


