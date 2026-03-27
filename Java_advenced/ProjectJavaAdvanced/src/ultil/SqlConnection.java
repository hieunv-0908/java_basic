package ultil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {
    // các thông tin để kết nối với db
    private static final String URL = "jdbc:mysql://localhost:3306/cyberGaming";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Hieu123456";

    // phương thức để lấy kết nối
    public static Connection getconnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (connection != null) {
                System.out.println("Kết nối thành công!");
                return connection;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
