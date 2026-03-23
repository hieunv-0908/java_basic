package bai3;

import ultil.DatabaseConnection;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.openConnection();

            CallableStatement cstmt = null;
            try {
                cstmt = conn.prepareCall("{call GET_SURGERY_FEE(?, ?)}");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // tham số IN
            cstmt.setInt(1, 505);

            //THIẾU registerOutParameter

            cstmt.execute();

            //Lỗi khi lấy OUT
            double cost = cstmt.getDouble(2);

            System.out.println("Chi phí: " + cost);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Connection conn = DatabaseConnection.openConnection();

            CallableStatement cstmt = conn.prepareCall("{call GET_SURGERY_FEE(?, ?)}");

            // tham số IN
            cstmt.setInt(1, 505);

            //KHAI BÁO THAM SỐ OUT
            cstmt.registerOutParameter(2, Types.DECIMAL);

            // chạy
            cstmt.execute();

            // lấy kết quả
            double cost = cstmt.getDouble(2);

            System.out.println("Chi phí: " + cost);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
