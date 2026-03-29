package dao;

import model.FoodAndDrink;
import ultil.SqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodAndDrinkDAO {
    static Connection connection = SqlConnection.getconnection();

    public static List<FoodAndDrink> queryDb() {
        List<FoodAndDrink> list = new ArrayList<>();
        String sql = "SELECT * FROM foodAndDrinks";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            ResultSet resultSet = prsmt.executeQuery();
            while (resultSet.next()) {
                int foodAndDrinkId = resultSet.getInt("foodAndDrink_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int stock = resultSet.getInt("stock");

                FoodAndDrink foodAndDrink = new FoodAndDrink(
                        foodAndDrinkId,
                        name,
                        description,
                        price,
                        stock
                );
                list.add(foodAndDrink);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static boolean createFoodAndDrink() {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String sql = "INSERT INTO foodAndDrinks(name, description, price, stock) value (?,?,?,?)";
        System.out.println("Mời nhập tên đồ ăn/thức uống muốn tạo: ");
        String name = scanner.nextLine();
        System.out.println("Mời nhập mô tả đồ ăn/thức uống muốn tạo: ");
        String description = scanner.nextLine();
        System.out.println("Mời nhập giá đồ ăn/thức uống muốn tạo: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.println("Mời nhập số lượng đồ ăn/thức uống muốn tạo: ");
        int stock = Integer.parseInt(scanner.nextLine());

        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setString(1, name);
            prsmt.setString(2, description);
            prsmt.setDouble(3, price);
            prsmt.setInt(4, stock);
            int rowsAffected = prsmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Đồ ăn/thức uống đã được tạo thành công!");
            } else {
                System.out.println("Tạo đồ ăn/thức uống thất bại.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
