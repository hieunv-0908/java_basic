# 🔴 BÁO CÁO LỖI - THIẾT KẾ DATABASE VÀ QUẢN LÍ KHO HÀNG

**Ngày**: 25/03/2026  
**Dự án**: FlashSale Order Management System  
**Phạm vi kiểm tra**: Database Design, Inventory Management, ProductDAO  

---

## 📌 TÓM TẮT LỖI

| # | File | Loại Lỗi | Mức độ | Dòng | Phát sinh khi |
|---|------|----------|-------|------|---------------|
| 1 | ProductDAO.java | Connection Leak | 🔴 CỰC CAO | 15-16 | Mỗi lần gọi addProduct() |
| 2 | ProductDAO.java | Resource Leak (PreparedStatement) | 🔴 CAO | 15-23 | Mỗi lần add/update sản phẩm |
| 3 | OrderItem.java | Design Issue (Thiếu price field) | 🟡 TRUNG | - | Mỗi lần sử dụng |

---

## 🔍 CHI TIẾT TỪNG LỖI

### **LỖI 1: Connection Leak trong ProductDAO** 🔴

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
- **Gây ra**: `SQLException: No more data sources available`

**Ví dụ crash**:
```
Khi chạy initData():
  For i = 1 to 10:
    addProduct() → DatabaseConnection.getConnection() → Connection mới
    ❌ Không close Connection
  
  Sau 10 lần:
    10 connections pending close
    Connection pool hết → SQLException
```

**Cách phát hiện**:
```sql
-- Chạy TestFlashSale.initData()
-- Trong MySQL, monitor connections:
SELECT COUNT(*) FROM INFORMATION_SCHEMA.PROCESSLIST WHERE DB = 'FlashSale';

-- Kết quả:
-- Lần đầu: 1 connection
-- Sau initData: 10+ connections (không giảm) ← BỊ LEAK!
```

**Tác động trên Inventory Management**:
- Inventory không thể tạo, không thể update
- Test Flash Sale không thể chạy được
- **Lỗi này PHẢI sửa trước tiên**

---

### **LỖI 2: Resource Leak trong các phương thức khác của ProductDAO** 🔴

**Vị trí**: `ProductDAO.java`, dòng 23, 31  
**Phân loại**: Resource Leak (PreparedStatement)  
**Mức độ**: 🔴 CAO

**Code gốc**:
```java
public void updateStock(int productId, int quantity) throws Exception {
    String sql = "UPDATE Products SET stock = stock + ? WHERE product_id = ?";
    PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);  // ❌ Tạo connection
    ps.setInt(1, quantity);
    ps.setInt(2, productId);
    ps.executeUpdate();
    // ❌ Không close ps và connection
}

public int getStock(int productId) throws Exception {
    String sql = "SELECT stock FROM Products WHERE product_id = ?";
    PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);  // ❌ Tạo connection
    ps.setInt(1, productId);
    ResultSet rs = ps.executeQuery();
    
    if (rs.next()) return rs.getInt("stock");
    throw new Exception("Không tìm thấy sản phẩm");
    // ❌ rs và ps không được close
}
```

**Nguyên nhân**:
- Cùng mô hình như lỗi 1: getConnection() mà không close
- Mỗi lần check stock hoặc update stock → leak 1 connection + 1 PreparedStatement

**Hậu quả**:
- Inventory management không thể hoạt động ổn định
- Quá trình đặt hàng sẽ bị block hoặc fail vì hết connection
- **Gây ra** crash hệ thống trong test Flash Sale (50 threads cùng gọi)

**Cách phát hiện**:
- Chạy 50 threads test → Nhiều threads fail với `SQLException: No more data sources available`

---

### **LỖI 3: Design Issue - OrderItem thiếu trường price** 🟡

**Vị trí**: `OrderItem.java`  
**Phân loại**: Design Issue - Entity không đầy đủ  
**Mức độ**: 🟡 TRUNG

**Code gốc**:
```java
public class OrderItem {
    public int productId;
    public int quantity;
    // ❌ Không có price field
}
```

**Nguyên nhân**:
- Entity thiết kế không đầy đủ
- Không có cách lấy/lưu trữ giá sản phẩm tại thời điểm đặt hàng

**Hậu quả**:
- Không thể lưu giá sản phẩm khi tạo Order_Details
- Phải hardcode `price = 0` trong OrderDetailDAO
- **Kết quả**: Báo cáo doanh thu sai (doanh thu = 0)
- Không thể kiểm toán lịch sử giá

**Ví dụ vấn đề**:
```
Sản phẩm A: 100.000 VND
User mua: OrderItem(productId=1, quantity=1)
  ❌ Không có price trong OrderItem

Khi insert vào Order_Details:
  price = 0 (hardcode)
  ❌ Mất thông tin giá sản phẩm
  
Báo cáo doanh thu:
  SELECT SUM(price * quantity) FROM Order_Details
  = 0 × 1 = 0 ❌ (phải là 100.000)
```

**Giải pháp cần thiết**:
```java
public class OrderItem {
    public int productId;
    public int quantity;
    public double price;  // ✅ Cần thêm để lưu giá tại thời điểm đặt hàng
}
```

**Tác động lên Inventory**:
- Kho hàng có thể quản lý tốt, nhưng báo cáo giá trị kho sẽ sai
- Không thể trace giá lịch sử sản phẩm

---

## 🚨 TỔNG HỢP HẬU QUẢ TRÊN HỆ THỐNG

### **Tác động trực tiếp lên Inventory Management:**

1. **Lỗi 1 & 2 (Connection/PS Leak)**:
   - ❌ Không thể thêm sản phẩm vào kho
   - ❌ Không thể kiểm tra stock
   - ❌ Không thể update stock
   - ⚠️ **Hệ thống sập ngay từ bước init data**

2. **Lỗi 3 (Thiếu price)**:
   - ✅ Kho hàng hoạt động (quantity đúng)
   - ❌ Nhưng giá trị kho sai (tất cả giá = 0)
   - ❌ Báo cáo tài chính không chính xác

### **Timeline khi chạy TestFlashSale:**

```
initData() → addProduct() × 5
  ↓
  Lỗi 1 phát sinh: Connection leak
  Kết nối 1 → 2 → 3 → 4 → 5
  ↓
  Lỗi 7 phát sinh (lỗi khác): Connection pool exhausted
  ❌ CRASH - không thể tiếp tục
```

---

## 📊 MA TRẬN RỦI RO - DATABASE & INVENTORY

```
Nguy hiểm           | Lỗi 1 | Lỗi 2 | Lỗi 3 |
---------------------|-------|-------|-------|
Crash app           |  ✅✅ |  ✅   |       |
Inventory không hoạt|  ✅✅ |  ✅   |       |
Sai dữ liệu giá     |       |       |  ✅   |
Không trace lịch sử |       |       |  ✅   |
Connection leak     |  ✅✅ |  ✅   |       |
Memory leak         |  ✅   |  ✅   |       |
---------------------|-------|-------|-------|
Phát sinh           | NGAY  | NGAY  | NGAY  |
Ưu tiên sửa         | 1️⃣   | 2️⃣   | 3️⃣   |
```

---

## ✅ KHUYẾN CÁO KIỂM CHỨNG

### **Cách phát hiện Lỗi 1 & 2 (Connection Leak)**:

```sql
-- Trước chạy test
SELECT COUNT(*) FROM INFORMATION_SCHEMA.PROCESSLIST WHERE DB = 'FlashSale';
-- Kết quả: 1

-- Chạy TestFlashSale.initData()
-- Trong khi chạy, monitor:
SELECT COUNT(*) FROM INFORMATION_SCHEMA.PROCESSLIST WHERE DB = 'FlashSale';

-- Kết quả sau: 10+ (không giảm) → Resource Leak!
```

### **Cách phát hiện Lỗi 3 (Thiếu price)**:

```sql
-- Sau khi insert Order_Details
SELECT * FROM Order_Details;
-- Kết quả: price = 0 (phải > 0) ❌

SELECT SUM(price * quantity) FROM Order_Details;
-- Kết quả: 0 (phải > 0) ❌
```

---

## 🎯 KẾT LUẬN

- **3 lỗi phát hiện**, trong đó **2 lỗi CỰC CAO** (1, 2)
- **Lỗi 1 & 2 (Connection Leak) ĐỘC HẠI NHẤT** - Có thể crash ngay trong `initData()`
- **Lỗi 3 (Thiếu price) ẢNH HƯỞNG TRỰC TIẾP** - Báo cáo doanh thu bị sai
- **Database & Inventory không thể hoạt động bình thường** cho đến khi sửa lỗi 1 & 2

**Khuyến cáo**:
- ✅ Ưu tiên sửa **Lỗi 1** trước tiên (Connection leak trong addProduct)
- ✅ Sửa **Lỗi 2** (Resource leak trong updateStock, getStock)
- ✅ Sửa **Lỗi 3** (Thêm price field vào OrderItem)
- ✅ Kiểm chứng lại bằng các lệnh SQL phía trên

