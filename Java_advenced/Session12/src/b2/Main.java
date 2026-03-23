package b2;

import ultil.DatabaseConnection;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.openConnection();
        double temp = 37.5;
        int patientId = new Scanner(System.in).nextInt();
        // sử dụng statement
        // sử dụng cộng chuỗi nên temp phải cộng chuỗi và chính nó cũng chuyển thành chuỗi dễ bị lỗi định dạng số khi chuyển ừ số sang chuỗi
        String sql = "UPDATE Vitals SET tempature = " + temp + " WHERE p_id = "+ patientId;
        Statement statement = null;
        try {
            statement = conn.prepareStatement(sql);
            int rs = statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        sql = "UPDATE Vitals SET tempature = ? WHERE p_id = ?";
        System.out.println("Nhập nhiệt độ mới:");
        temp = new Scanner(System.in).nextDouble();
        System.out.println("Nhập id bệnh nhân:");
        patientId = new Scanner(System.in).nextInt();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            // sử dụng setDouble và set... có thể sử dụng thẳng số mà không cần lo đến việc định dạng khác biệt gây lỗi
            preparedStatement.setDouble(1, temp);
            preparedStatement.setInt(2, patientId);
            ResultSet rs = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
