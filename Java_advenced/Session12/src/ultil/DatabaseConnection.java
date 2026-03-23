package ultil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private final static String URL = "jdbc:mysql://localhost:3306/hospital";
    private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final static String user = "root";
    private final static String pass = "Hieu123456";

    public static Connection openConnection(){
        Connection conn = null;

        // Khai báo cho class biến Driver
        try {
            // khai báo cho class biết driver
            Class.forName(DRIVER);

            // mở kết nối
            conn = DriverManager.getConnection(URL, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static void main(String[] args) {
        openConnection();
    }
}
