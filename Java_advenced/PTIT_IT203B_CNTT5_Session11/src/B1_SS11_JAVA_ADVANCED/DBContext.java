package B1_SS11_JAVA_ADVANCED;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {
    /**  phần 1: phân tích
     *  Việc khởi tạo kết nối liên tục mà không đóng hoặc không quản lý tập trung gấy ra ậu quả nghiêm trọng
     * cạn kiệt ta nguyên
     * gián đọn dịch vụ khẩn cấp
     * lãng phí chi phí vận hành
     */

    private static final String URL = "jdbc:mysql://192.168.1.10:3306/Hospital_DB";
    private static final String USER = "admin";
    private static final String PASS = "med123";

    /**
     * Phương thức lấy kết nối
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy Driver: " + e.getMessage());
            return null;
        }
    }

    public void testQuery() {
        Connection conn = null;
        try {
            conn = getConnection();
            if (conn != null) {
                System.out.println("Kết nối thành công tới Hospital_DB!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                    System.out.println("Đã đóng kết nối an toàn.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
