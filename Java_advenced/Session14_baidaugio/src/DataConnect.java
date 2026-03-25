import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataConnect {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/banking";
    private static final String USER = "root";
    private static final String PASSWORD = "Hieu123456";

    public static Connection opConn() {
        Connection con = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    }

}
