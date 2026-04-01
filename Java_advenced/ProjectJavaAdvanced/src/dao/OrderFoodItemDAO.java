package dao;

import model.OrderFoodItem;
import ultil.SqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderFoodItemDAO {
    static Connection connection = SqlConnection.getconnection();

    public static List<OrderFoodItem> queryDb() {
        List<OrderFoodItem> list = new ArrayList<>();
        String sql = "SELECT * FROM order_food_items";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            ResultSet resultSet = prsmt.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int orderId = resultSet.getInt("order_id");
                int foodAndDrinkId = resultSet.getInt("foodAndDrink_id");
                int quantity = resultSet.getInt("quantity");
                double price = resultSet.getDouble("price");

                OrderFoodItem item = new OrderFoodItem(id, orderId, foodAndDrinkId, quantity, price);
                list.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn database: " + e.getMessage());
        }

        return list;
    }

    public static boolean createOrderFoodItem(int orderId, int foodAndDrinkId, int quantity, double price) {
        String sql = "INSERT INTO order_food_items (order_id, foodAndDrink_id, quantity, price) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setInt(1, orderId);
            prsmt.setInt(2, foodAndDrinkId);
            prsmt.setInt(3, quantity);
            prsmt.setDouble(4, price);
            int rowsAffected = prsmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi tạo order food item: " + e.getMessage());
        }
        return false;
    }

    public static List<OrderFoodItem> getOrderFoodItems(int orderId) {
        List<OrderFoodItem> list = new ArrayList<>();
        String sql = "SELECT * FROM order_food_items WHERE order_id = ?";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setInt(1, orderId);
            ResultSet resultSet = prsmt.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int foodAndDrinkId = resultSet.getInt("foodAndDrink_id");
                int quantity = resultSet.getInt("quantity");
                double price = resultSet.getDouble("price");

                OrderFoodItem item = new OrderFoodItem(id, orderId, foodAndDrinkId, quantity, price);
                list.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn order food items: " + e.getMessage());
        }

        return list;
    }
}
