package dao;

import model.*;
import ultil.SqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    static Connection connection = SqlConnection.getconnection();

    public static List<User> queryDb(String queryNeed) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = ?";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setString(1, queryNeed.toUpperCase());
            ResultSet resultSet = prsmt.executeQuery();
            while (resultSet.next()) {
                String role = resultSet.getString("role");
                int userId = resultSet.getInt("user_id");
                String userCode = resultSet.getString("user_code");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String fullName = resultSet.getString("full_name");
                int age = resultSet.getInt("age");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                Double balance = resultSet.getDouble("balance");
                Date createdAt = new Date(resultSet.getTimestamp("created_at").getTime());

                User user;

                if (role.equalsIgnoreCase("admin")) {
                    user = new Admin(
                            userId,
                            userCode,
                            username,
                            fullName,
                            password,
                            age,
                            email,
                            phone,
                            Role.ADMIN,
                            balance,
                            createdAt
                    );
                    list.add(user);
                } else if (role.equalsIgnoreCase("staff")) {
                    user = new Staff(
                            userId,
                            userCode,
                            username,
                            fullName,
                            password,
                            age,
                            email,
                            phone,
                            Role.STAFF,
                            balance,
                            createdAt
                    );
                    list.add(user);
                } else if (role.equalsIgnoreCase("customer")) {
                    user = new Customer(
                            userId,
                            userCode,
                            username,
                            fullName,
                            password,
                            age,
                            email,
                            phone,
                            Role.USER,
                            balance,
                            createdAt
                    );
                    list.add(user);
                } else {
                    continue;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static boolean registerUser(String userCode, String username, String password, String fullName, int age, String email, String phone, String role) {
        String sql = "INSERT INTO users (user_code, username, password, full_name, age, email, phone, role, balance) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 0)";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setString(1, userCode);
            prsmt.setString(2, username);
            prsmt.setString(3, password);
            prsmt.setString(4, fullName);
            prsmt.setInt(5, age);
            prsmt.setString(6, email);
            prsmt.setString(7, phone);
            prsmt.setString(8, role.toLowerCase());

            int rowsAffected = prsmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static User loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setString(1, username);
            prsmt.setString(2, password);
            ResultSet resultSet = prsmt.executeQuery();

            if (resultSet.next()) {
                String role = resultSet.getString("role");
                int userId = resultSet.getInt("user_id");
                String userCode = resultSet.getString("user_code");
                String fullName = resultSet.getString("full_name");
                int age = resultSet.getInt("age");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                Double balance = resultSet.getDouble("balance");
                Date createdAt = new Date(resultSet.getTimestamp("created_at").getTime());

                User user;
                if (role.equalsIgnoreCase("admin")) {
                    user = new Admin(userId, userCode, username, fullName, password, age, email, phone, Role.ADMIN, balance, createdAt);
                } else if (role.equalsIgnoreCase("staff")) {
                    user = new Staff(userId, userCode, username, fullName, password, age, email, phone, Role.STAFF, balance, createdAt);
                } else {
                    user = new Customer(userId, userCode, username, fullName, password, age, email, phone, Role.USER, balance, createdAt);
                }
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setString(1, username);
            ResultSet resultSet = prsmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        queryDb("admin").forEach(user -> System.out.println(user.getFullName()));
    }

}
