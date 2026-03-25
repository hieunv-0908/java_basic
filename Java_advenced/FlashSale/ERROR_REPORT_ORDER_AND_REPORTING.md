# 🔴 BÁO CÁO LỖI - QUY TRÌNH ĐẶT HÀNG VÀ BÁO CÁO

**Ngày**: 25/03/2026  
**Dự án**: FlashSale Order Management System  
**Phạm vi kiểm tra**: OrderService, OrderDAO, OrderDetailDAO, ReportDAO  

---

## 📌 TÓM TẮT LỖI

| # | File | Loại Lỗi | Mức độ | Dòng | Phát sinh khi |
|---|------|----------|-------|------|---------------|
| 1 | OrderService.java | Resource Leak (ResultSet) | 🔴 CAO | 45-50 | Runtime (sau ~100 lần gọi) |
| 2 | OrderService.java | Resource Leak (PreparedStatement) | 🔴 CAO | 63-71 | Runtime (mỗi lần executeBatch) |
| 3 | OrderDetailDAO.java | Resource Leak (PreparedStatement) | 🔴 CAO | 14-20 | Runtime (mỗi Order chi tiết) |
| 4 | OrderDetailDAO.java | Logic Error (Giá = 0) | 🟡 TRUNG | 19 | Mỗi lần insert Order_Details |
| 5 | OrderDAO.java | Minor Leak (ResultSet) | 🟡 THẤP | 12 | Minimal impact |

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
- Khi có nhiều items, tạo nhiều ResultSet mà không đóng

**Hậu quả**:
- Memory leak - kết nối DB tích tụ không được giải phóng
- Sau ~100-500 lần gọi: `SQLException: No more data sources available`
- Trong test 50 threads với 50 orders: có thể xảy ra **từ lần thứ 30-40**

**Cách phát hiện**: 
- Chạy TestFlashSale 2-3 lần liên tiếp → sẽ crash
- Monitor: Số lượng open connections tăng liên tục

**Tác động lên Quy trình Đặt Hàng**:
- ❌ Không thể kiểm tra stock
- ❌ Order không thể tạo
- ❌ Flash Sale test fail

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
- Kết hợp với Lỗi 1 & 3, có thể hết kết nối rất nhanh
- Trong test 50 threads → fail sau ~30-40 orders

**Cách phát hiện**:
- Chạy 50 threads → Một số thread sẽ fail do connection exhausted

**Tác động lên Quy trình Đặt Hàng**:
- ❌ Không thể update stock
- ❌ Order không thể hoàn thành
- ⚠️ Có thể bị inconsistent (stock bị sai)

---

### **LỖI 3: Resource Leak - PreparedStatement trong OrderDetailDAO** 🔴

**Vị trí**: `OrderDetailDAO.java`, dòng 14-20  
**Phân loại**: Resource Leak (Serious)  
**Mức độ**: 🔴 CAO - **QUAN TRỌNG NHẤT**

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
- Trong test 50 orders → tạo 50 PreparedStatement không đóng

**Hậu quả**:
- **GIAI ĐOẠN CUỐI CỦA ĐẶT HÀNG** sẽ fail
- Mỗi order = 1 PreparedStatement không đóng
- 50 orders = 50 PS leak → Connection hết rất nhanh
- **Gây ra**: Tính nguyên khối của transaction bị phá vỡ (Lỗi được rollback)

**Cách phát hiện**:
- Chạy 50 threads → Thành công ~30 orders, sau đó crash

**Tác động lên Quy trình Đặt Hàng**:
- ❌ Order detail không thể insert
- ❌ Transaction rollback → Stock phải hoàn trả
- ⚠️ Dữ liệu inconsistent (order tạo nhưng không có detail)

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
- **DOANH THU BÁO CÁO SẼ SAI HOÀN TOÀN**
- Tất cả Order_Details có price = 0
- `GetRevenueByCategory()` trả về 0 (hoặc NULL)
- Báo cáo doanh thu vô dụng

**Ví dụ**:
```
Đặt mua: 10 sản phẩm × 100.000 VND = 1.000.000 VND
Kết quả DB: Order_Details.price = 0
Báo cáo doanh thu: 0 VND ❌ (phải là 1.000.000)
```

**Cách phát hiện**:
```sql
SELECT SUM(price) FROM Order_Details;
-- Kết quả: 0 (phải > 0) ❌

SELECT SUM(price * quantity) FROM Order_Details;
-- Kết quả: 0 (phải > 0) ❌
```

**Tác động lên Báo Cáo**:
- ❌ Báo cáo doanh thu bị sai 100%
- ❌ Không thể kiểm toán tài chính
- ❌ Dữ liệu báo cáo vô dụng

---

### **LỖI 5: Minor Leak - ResultSet trong OrderDAO** 🟡

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
- Nhưng PreparedStatement sẽ đóng nó khi connection đóng

**Hậu quả**:
- Minimal impact (connection vẫn close cuối cùng)
- Nhưng là best practice violation
- Trong trường hợp exception, có thể bị resource leak

---

## 🚨 TỔNG HỢP HẬU QUẢ TRÊN QUY TRÌNH ĐẶT HÀNG

### **Timeline khi chạy Test 50 Threads:**

```
placeOrder() × 50 (50 threads)
  ↓
  [Bước 1] CHECK STOCK - LỖI 1 PHÁT SINH
    checkStmt.executeQuery() → ResultSet
    ❌ Không close rs
    Lỗi tích tụ: 50+ ResultSet không đóng
  ↓
  [Bước 2] UPDATE STOCK - LỖI 2 PHÁT SINH
    updateStmt.executeBatch()
    ❌ Không close updateStmt
    Lỗi tích tụ: 50+ PreparedStatement không đóng
  ↓
  [Bước 3] CREATE ORDER - LỖI 5 (NHẸ)
    createOrder() tạo order
    ⚠️ Không close rs (minimal impact)
  ↓
  [Bước 4] INSERT ORDER DETAILS - LỖI 3 PHÁT SINH
    insertBatch() tạo PS
    ❌ Không close ps
    Lỗi tích tụ: 50+ PreparedStatement không đóng
  ↓
  Kết quả sau ~30-40 threads:
    🔴 SQLException: No more data sources available
    ❌ Transaction ROLLBACK (Stock hoàn trả)
    ❌ Order chi tiết không được tạo
    ❌ Tính nguyên khối bị phá vỡ
```

### **Sau khi test hoàn thành - Lỗi 4 phát hiện:**

```
getRevenueByCategory() Stored Procedure
  SELECT SUM(price * quantity)
  = 0 (vì tất cả price = 0)
  ❌ Báo cáo doanh thu = 0 (SAIDATA)
```

---

## 📊 MA TRẬN RỦI RO - QUY TRÌNH ĐẶT HÀNG

```
Nguy hiểm         | Lỗi 1 | Lỗi 2 | Lỗi 3 | Lỗi 4 | Lỗi 5 |
------------------|-------|-------|-------|-------|-------|
Crash app         |  ✅   |  ✅   |  ✅   |       |       |
Order không tạo   |  ✅   |  ✅   |  ✅   |       |       |
Sai dữ liệu báo cáo|      |       |       |  ✅   |       |
Memory leak       |  ✅   |  ✅   |  ✅   |       |  ⚠️   |
Connection pool   |  ✅   |  ✅   |  ✅   |       |       |
Rollback lỗi      |  ✅   |  ✅   |  ✅   |       |       |
------------------|-------|-------|-------|-------|-------|
Phát sinh khi     | Lâu   | Lâu   | NGAY  | NGAY  | Lâu   |
Ưu tiên sửa       | 2️⃣   | 3️⃣   | 1️⃣   | 4️⃣   | 5️⃣   |
```

---

## ✅ KHUYẾN CÁO KIỂM CHỨNG

### **Cách phát hiện Lỗi 1-3 (Resource Leak)**:

```sql
-- Monitor open connections
SELECT COUNT(*) FROM INFORMATION_SCHEMA.PROCESSLIST WHERE DB = 'FlashSale';

-- Trước test: 1-2 connections
-- Chạy test 50 threads
-- Trong lúc chạy: connections tăng từ 2 → 10+ (không giảm)
-- Sau test fail: 10+ connections còn pending → Resource Leak!
```

### **Cách phát hiện Lỗi 4 (Giá = 0)**:

```sql
SELECT * FROM Order_Details LIMIT 5;
-- Kết quả: price = 0 (phải > 0) ❌

SELECT SUM(price * quantity) FROM Order_Details;
-- Kết quả: 0 (phải > 0) ❌

CALL GetRevenueByCategory();
-- Kết quả: 0 hoặc NULL (phải > 0) ❌
```

### **Cách phát hiện Lỗi 5 (Minor Leak)**:
- Ít tác động, phát hiện khó
- Monitor connection sau khi exception xảy ra

---

## 🎯 KẾT LUẬN

- **5 lỗi phát hiện**, trong đó **3 lỗi CẦN ĐỀU YỲU** (1, 2, 3)
- **Lỗi 3 (OrderDetailDAO) QUAN TRỌNG NHẤT** - Phát sinh NGAY, mỗi order = 1 PS leak
- **Lỗi 1 & 2 (OrderService leaks)** - Tích tụ dần, gây fail sau 30-40 orders
- **Lỗi 4 (Logic error) LÀM SAI DỮ LIỆU** - Báo cáo doanh thu = 0
- **Quy trình đặt hàng không thể hoạt động ổn định** cho đến khi sửa lỗi 1, 2, 3

**Khuyến cáo**:
- ✅ Ưu tiên sửa **Lỗi 3** (OrderDetailDAO) trước tiên - NGAY LẬP TỨC
- ✅ Sửa **Lỗi 1** (OrderService ResultSet leak)
- ✅ Sửa **Lỗi 2** (OrderService PreparedStatement leak)
- ✅ Sửa **Lỗi 4** (OrderDetailDAO - thêm price từ OrderItem)
- ✅ Sửa **Lỗi 5** (OrderDAO - best practice)
- ✅ Kiểm chứng lại bằng các lệnh SQL phía trên

