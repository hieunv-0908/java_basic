package b1;

import ultil.DatabaseConnection;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.openConnection();
        String code = new Scanner(System.in).nextLine();
        String pass = new Scanner(System.in).nextLine();

        // Statement
        String sql = "select * from doctors where code = '" + code + "' and pass = '" + pass + "'";
        try {
            // Vì Statement thông tờng chỉ là cộng chuỗi và cả chuỗi là câu lệnh nên có thể dẫn ến người dùng khi nhập chuỗi có thể nập cả
            // câu lệnh làm cho trương trình có nguy cơ mất an toàn
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // PreparedStatement
        // câu lệnh đã được mặc định không thể thay đổi chỉ có thể truyền được các giá tị vào vùng dấu ? nên sẽ không có tình trạng thay đô cả câu lệnh
        // tránh được dữ liệu mất án toàn
        sql = "select * from doctors where code = ? and pass = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, code);
            preparedStatement.setString(2, pass);
            ResultSet rs = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
