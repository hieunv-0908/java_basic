# 🔴 BÁO CÁO LỖI - KIẾN TRÚC DAO, RÀNG BUỘC VÀ TRANSACTION

**Ngày**: 25/03/2026  
**Dự án**: FlashSale Order Management System  
**Phạm vi kiểm tra**: DAO Architecture, Database Constraints, Transaction Management  

---

## 📌 TÓM TẮT LỖI VÀ VẤN ĐỀ

| # | Vấn đề | File | Loại | Mức độ | Trạng thái |
|---|--------|------|------|-------|-----------|
| 1 | Resource Management | ProductDAO.java, OrderDetailDAO.java, OrderService.java | Architecture | 🔴 CAO | ❌ Có lỗi |
| 2 | DAO Pattern Violation | ProductDAO.java | Architecture | 🟡 TRUNG | ⚠️ Chưa tối ưu |
| 3 | Database Constraints | Database Design | Constraint | 🟡 TRUNG | ❓ Chưa rõ |
| 4 | Foreign Key Management | - | Constraint | 🟡 TRUNG | ❓ Chưa rõ |
| 5 | Transaction Isolation | OrderService.java | Transaction | ✅ GOOD | ✅ Đã áp dụng |
| 6 | Error Handling | OrderService.java, DAO | Error handling | 🟡 TRUNG | ⚠️ Có thể tốt hơn |

---

## 🔍 CHI TIẾT TỪNG VẤN ĐỀ

### **VẤN ĐỀ 1: Resource Management trong DAO Pattern** 🔴

**Vị trí**: `ProductDAO.java`, `OrderDetailDAO.java`, `OrderService.java`  
**Phân loại**: Architecture Issue - Resource Leak  
**Mức độ**: 🔴 CAO - Vi phạm DAO Pattern

#### **1.1 - ProductDAO không close resources**

**Code gốc**:
```java
public void addProduct(String name, double price, int stock, String category) throws Exception {
    // ...validation...
    String sql = "INSERT INTO Products(name, price, stock, category) VALUES (?, ?, ?, ?)";
    PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);  // ❌
    ps.setString(1, name);
    ps.setDouble(2, price);
    ps.setInt(3, stock);
    ps.setString(4, category);
    ps.executeUpdate();
    // ❌ ps không close
    // ❌ connection không close
}
```

**DAO Pattern Violation**:
- ❌ **Không tuân theo try-with-resources**
- ❌ **Không close PreparedStatement**
- ❌ **Không close Connection**
- ❌ **Không handle exception**

**Correct DAO Pattern**:
```java
public void addProduct(String name, double price, int stock, String category) throws Exception {
    // ... validation ...
    String sql = "INSERT INTO Products(name, price, stock, category) VALUES (?, ?, ?, ?)";
    
    // ✅ Correct: Try-with-resources tự động close
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setString(1, name);
        ps.setDouble(2, price);
        ps.setInt(3, stock);
        ps.setString(4, category);
        ps.executeUpdate();
        
    } catch (SQLException e) {
        throw new Exception("Lỗi thêm sản phẩm: " + e.getMessage(), e);
    }
}
```

**Tác động lên DAO Architecture**:
- ❌ DAO không tuân thủ chuẩn Resource Management
- ❌ Tạo resource leak, gây sập hệ thống
- ❌ Khó bảo trì và mở rộng

---

#### **1.2 - OrderDetailDAO không close resources**

**Code gốc**:
```java
public void insertBatch(Connection conn, int orderId, List<OrderItem> items) throws Exception {
    String sql = "INSERT INTO Order_Details(order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
    
    PreparedStatement ps = conn.prepareStatement(sql);  // ❌ Không close
    
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

**DAO Pattern Violation**:
- ❌ **DAO nhận Connection từ Service** (✓ Đúng)
- ❌ **Nhưng không close PreparedStatement** (✗ Sai)
- ❌ **Không handle exception**

**Correct DAO Pattern** (Batch insert):
```java
public void insertBatch(Connection conn, int orderId, List<OrderItem> items) throws Exception {
    String sql = "INSERT INTO Order_Details(order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
    
    // ✅ Try-with-resources cho PreparedStatement
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        
        for (OrderItem item : items) {
            ps.setInt(1, orderId);
            ps.setInt(2, item.productId);
            ps.setInt(3, item.quantity);
            ps.setDouble(4, item.price);  // ✅ Lấy từ OrderItem
            ps.addBatch();
        }
        
        ps.executeBatch();
        
    } catch (SQLException e) {
        throw new Exception("Lỗi insert Order_Details: " + e.getMessage(), e);
    }
}
```

**Tác động lên DAO Architecture**:
- ❌ Batch processing không được resource management đúng
- ❌ PreparedStatement không đóng → leak
- ❌ Vi phạm DAO Pattern

---

### **VẤN ĐỀ 2: DAO Pattern - Resource Management trong OrderService** 🟡

**Vị trí**: `OrderService.java`, dòng 39-50, 63-71  
**Phân loại**: Architecture Issue  
**Mức độ**: 🟡 TRUNG

**Code gốc**:
```java
try {
    conn = DatabaseConnection.getConnection();
    conn.setAutoCommit(false);
    conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
    
    // ❌ Tạo PreparedStatement nhưng không close
    String checkSql = "SELECT stock FROM Products WHERE product_id = ? FOR UPDATE";
    PreparedStatement checkStmt = conn.prepareStatement(checkSql);  // ❌ Không close
    
    for (OrderItem item : items) {
        checkStmt.setInt(1, item.productId);
        ResultSet rs = checkStmt.executeQuery();  // ❌ ResultSet không close
        // ...
    }
    
    // ❌ Tạo PreparedStatement nhưng không close
    String updateSql = "UPDATE Products SET stock = stock - ? WHERE product_id = ?";
    PreparedStatement updateStmt = conn.prepareStatement(updateSql);  // ❌ Không close
    
    for (OrderItem item : items) {
        updateStmt.setInt(1, item.quantity);
        updateStmt.setInt(2, item.productId);
        updateStmt.addBatch();
    }
    
    updateStmt.executeBatch();
    // ❌ updateStmt không được close
}
```

**DAO Pattern Violation**:
- ❌ **Service tạo PreparedStatement trực tiếp** (nên để DAO)
- ❌ **Không close PreparedStatement**
- ❌ **Quá nhiều logic SQL trong Service** (nên để DAO)

**Correct DAO Pattern**:
```java
// OrderService.java
OrderDAO orderDAO = new OrderDAO();
OrderDetailDAO detailDAO = new OrderDetailDAO();
ProductDAO productDAO = new ProductDAO();

// ... check stock
productDAO.checkStockWithLock(conn, items);

// ... update stock  
productDAO.updateStockBatch(conn, items);

// ... create order
int orderId = orderDAO.createOrder(conn, userId);

// ... insert details
detailDAO.insertBatch(conn, orderId, items);
```

**Tác động lên DAO Architecture**:
- ❌ Business logic và Data Access không tách biệt
- ❌ Service chứa quá nhiều SQL
- ❌ Khó test, khó bảo trì

---

### **VẤN ĐỀ 3: Database Constraints** 🟡

**Vị trí**: Database Design  
**Phân loại**: Constraint Issue  
**Mức độ**: 🟡 TRUNG - Chưa rõ có constraints không

**Câu hỏi chưa trả lời**:
- ❓ Có Foreign Key constraints không?
- ❓ Có Check constraints để đảm bảo stock >= 0?
- ❓ Có Unique constraints trên các fields cần thiết?
- ❓ Có Primary Key, Index tối ưu không?

**Ví dụ - Thiếu Check Constraint**:
```sql
-- ❌ KHÔNG CÓ (Yêu cầu phải có)
ALTER TABLE Products ADD CONSTRAINT chk_stock_positive CHECK (stock >= 0);

-- ❌ KHÔNG CÓ (Yêu cầu phải có)
ALTER TABLE Products ADD CONSTRAINT chk_price_positive CHECK (price > 0);

-- ❌ KHÔNG CÓ Foreign Key
ALTER TABLE Orders ADD CONSTRAINT fk_order_user 
  FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE;
```

**Tác động**:
- ❌ Không thể ngăn chặn stock âm ở DB level
- ❌ Phụ thuộc vào application logic (kém an toàn)
- ❌ Không có referential integrity

---

### **VẤN ĐỀ 4: Foreign Key Management** 🟡

**Vị trí**: Database Design + OrderDAO  
**Phân loại**: Constraint Issue  
**Mức độ**: 🟡 TRUNG

**Code gốc - OrderDAO**:
```java
public int createOrder(Connection conn, int userId) throws Exception {
    String sql = "INSERT INTO Orders(user_id) VALUES (?)";
    PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    ps.setInt(1, userId);
    ps.executeUpdate();
    
    // ❓ Không kiểm tra userId có tồn tại không
    // ❓ Không có Foreign Key constraint
}
```

**Vấn đề**:
- ❌ Không validate userId tồn tại
- ❌ Nếu không có FK constraint, có thể insert order với invalid userId
- ❌ Referential integrity không đảm bảo

**Correct Pattern**:
```java
public int createOrder(Connection conn, int userId) throws Exception {
    // ✅ Validate userId trước
    if (!isUserExists(conn, userId)) {
        throw new Exception("User không tồn tại: " + userId);
    }
    
    String sql = "INSERT INTO Orders(user_id) VALUES (?)";
    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        ps.setInt(1, userId);
        ps.executeUpdate();
        
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) return rs.getInt(1);
        
        throw new Exception("Không tạo được order");
    }
}

private boolean isUserExists(Connection conn, int userId) throws SQLException {
    String sql = "SELECT 1 FROM Users WHERE user_id = ? LIMIT 1";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, userId);
        return ps.executeQuery().next();
    }
}
```

**Tác động**:
- ❌ Dữ liệu inconsistent nếu thiếu constraint
- ❌ Không thể maintain referential integrity

---

### **VẤN ĐỀ 5: Transaction Management - GOOD ✅**

**Vị trí**: `OrderService.java`  
**Phân loại**: Transaction Control  
**Mức độ**: ✅ TỐT

**Code gốc** (Điều tốt):
```java
try {
    conn = DatabaseConnection.getConnection();
    
    // ✅ Tắt autoCommit
    conn.setAutoCommit(false);
    
    // ✅ Đặt isolation level cao nhất
    conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
    
    // ... Thực thi các thao tác ...
    
    // ✅ Commit nếu thành công
    conn.commit();
    
} catch (Exception e) {
    // ✅ Rollback nếu có exception
    try {
        if (conn != null) {
            conn.rollback();
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    
} finally {
    // ✅ Reset autoCommit và close
    try {
        if (conn != null) {
            conn.setAutoCommit(true);
            conn.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
```

**Điều tốt**:
- ✅ **TRANSACTION_SERIALIZABLE** - Level cao nhất, chống Race Condition
- ✅ **FOR UPDATE** - Lock database row
- ✅ **commit() + rollback()** - Đúng cách
- ✅ **Try-catch-finally** - Proper error handling
- ✅ **reset autoCommit = true** - Best practice

**Khuyến cáo nâng cao**:
- ⚠️ TRANSACTION_SERIALIZABLE có thể chậm, có thể dùng TRANSACTION_REPEATABLE_READ
- ⚠️ Cần log transaction state để debug

---

### **VẤN ĐỀ 6: Error Handling trong DAO & Service** 🟡

**Vị trí**: Tất cả DAO classes, OrderService  
**Phân loại**: Error Handling  
**Mức độ**: 🟡 TRUNG

#### **6.1 - Generic Exception Handling**

**Code gốc - ProductDAO**:
```java
public void addProduct(String name, double price, int stock, String category) throws Exception {
    // ❌ Throw generic Exception, không phân biệt loại lỗi
    if (name == null || name.isEmpty()) throw new Exception("Tên không hợp lệ");
    if (price <= 0) throw new Exception("Giá phải > 0");
    // ...
}
```

**Code gốc - OrderService**:
```java
catch (Exception e) {
    // ❌ Catch generic Exception
    try {
        if (conn != null) {
            conn.rollback();
        }
        System.out.println("❌ User " + userId + " thất bại: " + e.getMessage());
    } catch (SQLException ex) {
        // ❌ Chỉ catch SQLException ở đây
        ex.printStackTrace();
    }
}
```

**Vấn đề**:
- ❌ Không phân biệt SQLException vs Validation Exception
- ❌ Khó debug vì không biết lỗi gì
- ❌ Không thể handle riêng từng loại lỗi

**Correct Error Handling Pattern**:
```java
// ✅ Custom Exception classes
public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}

public class DatabaseException extends Exception {
    public DatabaseException(String message, SQLException cause) {
        super(message, cause);
    }
}

// ✅ DAO with proper error handling
public void addProduct(String name, double price, int stock, String category) 
    throws ValidationException, DatabaseException {
    
    if (name == null || name.isEmpty()) {
        throw new ValidationException("Tên không hợp lệ");
    }
    if (price <= 0) {
        throw new ValidationException("Giá phải > 0");
    }
    
    String sql = "INSERT INTO Products(name, price, stock, category) VALUES (?, ?, ?, ?)";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setString(1, name);
        ps.setDouble(2, price);
        ps.setInt(3, stock);
        ps.setString(4, category);
        ps.executeUpdate();
        
    } catch (SQLException e) {
        throw new DatabaseException("Lỗi thêm sản phẩm vào DB: " + e.getMessage(), e);
    }
}

// ✅ Service with proper error handling
try {
    // ... placeOrder logic ...
    conn.commit();
    
} catch (ValidationException e) {
    // ✅ Validation error - không rollback (chưa có transaction)
    System.out.println("❌ Validation error: " + e.getMessage());
    
} catch (DatabaseException e) {
    // ✅ DB error - cần rollback
    try {
        if (conn != null) conn.rollback();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    System.out.println("❌ Database error: " + e.getMessage());
    
} catch (SQLException e) {
    // ✅ Transaction error
    try {
        if (conn != null) conn.rollback();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    System.out.println("❌ Transaction error: " + e.getMessage());
}
```

**Tác động**:
- ❌ Không phân biệt lỗi → khó debug
- ❌ Không thể handle riêng từng loại → logic bị chung chung

---

## 📊 MA TRẬN KIẾN TRÚC & CONSTRAINT

```
Vấn đề                    | Trạng thái | Mức độ | Khắc phục |
--------------------------|-----------|--------|-----------|
1. Resource Management    | ❌ Có lỗi  | 🔴 CAO | NGAY      |
2. DAO Pattern            | ⚠️ Vi phạm | 🟡 TB  | NGAY      |
3. DB Constraints         | ❓ Chưa rõ | 🟡 TB  | Cần chk   |
4. Foreign Key            | ❌ Yếu    | 🟡 TB  | NGAY      |
5. Transaction Mgmt       | ✅ TỐT    | ✅ GOOD| Giữ lại   |
6. Error Handling         | ⚠️ Generic | 🟡 TB  | Cải thiện |
```

---

## 🎯 KẾT LUẬN

### **Điểm mạnh**:
- ✅ **Transaction Management** - Đã áp dụng đúng TRANSACTION_SERIALIZABLE
- ✅ **Commit/Rollback** - Xử lý chính xác
- ✅ **FOR UPDATE** - Sử dụng lock phù hợp

### **Điểm yếu**:
- ❌ **Resource Management** - PreparedStatement & ResultSet không close đúng cách
- ❌ **DAO Pattern** - Không tuân thủ try-with-resources
- ❌ **Service logic** - Quá nhiều SQL trong Service, nên để DAO
- ❌ **Database Constraints** - Thiếu Check & Foreign Key constraints
- ❌ **Error Handling** - Generic Exception, khó phân biệt lỗi

### **Ưu tiên sửa**:
1. ✅ **Lỗi Resource Management** (Lỗi 1, 2, 3 ở báo cáo khác)
2. ✅ **Tách Business Logic ra DAO** - Move SQL từ Service → DAO
3. ✅ **Thêm Database Constraints** - Check constraints, FK constraints
4. ✅ **Thêm Custom Exception classes** - ValidationException, DatabaseException
5. ✅ **Cải thiện Error Handling** - Catch & handle riêng từng loại

**Giữ lại**:
- ✅ Transaction Management (TRANSACTION_SERIALIZABLE là tốt)
- ✅ Commit/Rollback logic

