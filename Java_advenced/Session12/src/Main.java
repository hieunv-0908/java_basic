import ultil.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // tạo database bệnh viện
        // tạo bảng doctor(id, doctor, gerner)
        // mở kết nối
        try(Connection con = DatabaseConnection.openConnection()){
            // câu lệnh sql
            String sql = "insert into doctors(doctor_name, gender, age, department) values(?,?,?,?)";
            // khởi tạo đối tượng prepareStatement (đưa câu lệnh sql)
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            // Set parameter (tham số) vào
            System.out.println("Nhập tên bác si:");
            preparedStatement.setString(1, sc.nextLine());
            System.out.println("Nhập giới tính:");
            preparedStatement.setString(2, sc.nextLine());
            System.out.println("Nhập tuổi bác si:");
            preparedStatement.setInt(3, Integer.parseInt(sc.nextLine()));
            System.out.println("Nhập khoa:");
            preparedStatement.setString(4, sc.nextLine());
            preparedStatement.executeUpdate();
            System.out.println("Thêm thành công");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}