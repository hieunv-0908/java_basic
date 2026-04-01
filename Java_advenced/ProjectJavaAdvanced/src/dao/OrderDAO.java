package dao;

import dao.OrderFoodItemDAO;
import model.Order;
import model.OrderFoodItem;
import model.OrderStatus;
import model.FoodAndDrink;
import ultil.SqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    static Connection connection = SqlConnection.getconnection();

    public static List<Order> queryDb() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            ResultSet resultSet = prsmt.executeQuery();
            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                int userId = resultSet.getInt("user_id");
                Integer bookingId = resultSet.getInt("booking_id");
                if (resultSet.wasNull()) bookingId = null;
                double totalAmount = resultSet.getDouble("total_amount");
                OrderStatus status = OrderStatus.fromStatus(resultSet.getString("status"));
                Timestamp createdAt = resultSet.getTimestamp("created_at");

                Order order = new Order(orderId, userId, bookingId, totalAmount, status, createdAt);
                list.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn database: " + e.getMessage());
        }

        return list;
    }

    public static boolean createOrder(int userId, Integer bookingId, double totalAmount) {
        String sql = "INSERT INTO orders (user_id, booking_id, total_amount, status) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prsmt.setInt(1, userId);
            if (bookingId != null) {
                prsmt.setInt(2, bookingId);
            } else {
                prsmt.setNull(2, Types.INTEGER);
            }
            prsmt.setDouble(3, totalAmount);
            prsmt.setString(4, OrderStatus.PENDING.getStatusCode());
            int rowsAffected = prsmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = prsmt.getGeneratedKeys();
                if (rs.next()) {
                    int orderId = rs.getInt(1);
                    System.out.println("Order created with ID: " + orderId);
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tạo order: " + e.getMessage());
        }
        return false;
    }

    public static int getLastInsertedId() {
        String sql = "SELECT LAST_INSERT_ID()";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            ResultSet rs = prsmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy ID: " + e.getMessage());
        }
        return -1;
    }

    public static List<Order> getPendingOrders() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE status = 'PENDING' ORDER BY created_at DESC";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            ResultSet resultSet = prsmt.executeQuery();
            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                int userId = resultSet.getInt("user_id");
                Integer bookingId = resultSet.getInt("booking_id");
                if (resultSet.wasNull()) bookingId = null;
                double totalAmount = resultSet.getDouble("total_amount");
                OrderStatus status = OrderStatus.fromStatus(resultSet.getString("status"));
                Timestamp createdAt = resultSet.getTimestamp("created_at");

                Order order = new Order(orderId, userId, bookingId, totalAmount, status, createdAt);
                list.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn order đang chờ: " + e.getMessage());
        }

        return list;
    }

    public static List<Order> getOrdersByUserId(int userId) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setInt(1, userId);
            ResultSet resultSet = prsmt.executeQuery();
            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                Integer bookingId = resultSet.getInt("booking_id");
                if (resultSet.wasNull()) bookingId = null;
                double totalAmount = resultSet.getDouble("total_amount");
                OrderStatus status = OrderStatus.fromStatus(resultSet.getString("status"));
                Timestamp createdAt = resultSet.getTimestamp("created_at");

                Order order = new Order(orderId, userId, bookingId, totalAmount, status, createdAt);
                list.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn order của user: " + e.getMessage());
        }

        return list;
    }

    public static Order getOrderById(int orderId) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setInt(1, orderId);
            ResultSet resultSet = prsmt.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                Integer bookingId = resultSet.getInt("booking_id");
                if (resultSet.wasNull()) bookingId = null;
                double totalAmount = resultSet.getDouble("total_amount");
                OrderStatus status = OrderStatus.fromStatus(resultSet.getString("status"));
                Timestamp createdAt = resultSet.getTimestamp("created_at");

                return new Order(orderId, userId, bookingId, totalAmount, status, createdAt);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn order: " + e.getMessage());
        }
        return null;
    }

    public static boolean updateOrderStatus(int orderId, String newStatus) {
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setString(1, newStatus);
            prsmt.setInt(2, orderId);
            
            System.out.println("DEBUG: Updating order " + orderId + " to status: " + newStatus);
            
            int rowsAffected = prsmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cập nhật trạng thái order thành công!");
                
                // Auto trừ tồn kho khi chuyển sang "Đang phục vụ"
                if ("PREPARING".equals(newStatus)) {
                    deductStockForOrder(orderId);
                }
                
                return true;
            } else {
                System.out.println("DEBUG: Không có dòng nào được cập nhật. Order ID có tồn tại không?");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi cập nhật order: " + e.getMessage());
            System.out.println("DEBUG: SQL State: " + e.getSQLState());
            System.out.println("DEBUG: Error Code: " + e.getErrorCode());
        }
        return false;
    }

    private static void deductStockForOrder(int orderId) {
        try {
            // Lấy danh sách món ăn trong đơn hàng
            List<OrderFoodItem> items = OrderFoodItemDAO.getOrderFoodItems(orderId);
            
            for (OrderFoodItem item : items) {
                // Kiểm tra tồn kho trước khi trừ
                FoodAndDrink food = FoodAndDrinkDAO.getFoodAndDrinkById(item.getFoodAndDrinkId());
                if (food != null) {
                    if (food.getStock() >= item.getQuantity()) {
                        // Trừ tồn kho
                        boolean success = FoodAndDrinkDAO.updateStock(item.getFoodAndDrinkId(), -item.getQuantity());
                        if (success) {
                            System.out.println("Đã trừ " + item.getQuantity() + " " + food.getName() + " khỏi kho");
                        }
                    } else {
                        System.out.println("⚠️ Cảnh báo: " + food.getName() + " không đủ tồn kho (còn " + food.getStock() + ", cần " + item.getQuantity() + ")");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi trừ tồn kho: " + e.getMessage());
        }
    }
}
