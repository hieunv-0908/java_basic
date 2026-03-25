import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /**
         * Bài toán:
         Kiểm tra tài khoản: Trước khi chuyển, hãy truy vấn xem AccountId người gửi có tồn
         tại và đủ số dư không
         ◦ Quản lý Giao dịch:
         ▪ Toàn bộ quá trình trừ tiền người gửi và cộng tiền người nhận phải nằm trong cùng một
         Transaction
         ▪ Nếu có bất kỳ lỗi nào (Lỗi SQL, lỗi kết nối, hoặc số dư không đủ), phải rollback()
         toàn bộ
         ◦ Thực thi nghiệp vụ: Sử dụng đối tượng CallableStatement để gọi Stored Procedure
         sp_UpdateBalance hai lần:
         ▪ Lần 1: Truyền tham số để trừ tiền tài khoản A (amount âm)
         ▪ Lần 2: Truyền tham số để cộng tiền tài khoản B (amount dương)
         ◦ Xác nhận và Hiển thị : Sau khi chuyển thành công, gọi commit()
         ▪ Sử dụng PreparedStement (truy vấn) để lấy ra danh sách kết quả của cả tài khoản
         chuyển tiền và nhận tiền và in bảng kết quả cuối cùng ra màn hình Console để đối soát
         * */
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập AccountId người gửi: ");
        String senderId = scanner.nextLine();
        System.out.print("Nhập AccountId người nhận: ");
        String receiverId = scanner.nextLine();
        System.out.print("Nhập số tiền chuyển: ");
        double amount = scanner.nextDouble();

        Connection con = null;
        try {
            con = DataConnect.opConn();
            con.setAutoCommit(false); // Start transaction

            // Check sender account
            String checkSql = "SELECT Balance FROM Accounts WHERE AccountId = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkSql);
            checkStmt.setString(1, senderId);
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                throw new SQLException("Tài khoản người gửi không tồn tại.");
            }
            double senderBalance = rs.getDouble("Balance");
            if (senderBalance < amount) {
                throw new SQLException("Số dư không đủ.");
            }
            rs.close();
            checkStmt.close();

            // Call stored procedure for sender (subtract)
            CallableStatement cs1 = con.prepareCall("{call sp_UpdateBalance(?, ?)}");
            cs1.setString(1, senderId);
            cs1.setDouble(2, -amount);
            cs1.execute();
            cs1.close();

            // Call stored procedure for receiver (add)
            CallableStatement cs2 = con.prepareCall("{call sp_UpdateBalance(?, ?)}");
            cs2.setString(1, receiverId);
            cs2.setDouble(2, amount);
            cs2.execute();
            cs2.close();

            con.commit(); // Commit transaction

            // Query and display final balances
            String querySql = "SELECT AccountId, FullName, Balance FROM Accounts WHERE AccountId IN (?, ?)";
            PreparedStatement queryStmt = con.prepareStatement(querySql);
            queryStmt.setString(1, senderId);
            queryStmt.setString(2, receiverId);
            ResultSet result = queryStmt.executeQuery();
            System.out.println("Kết quả sau chuyển khoản:");
            System.out.printf("%-10s %-20s %-10s%n", "AccountId", "FullName", "Balance");
            while (result.next()) {
                System.out.printf("%-10s %-20s %-10.2f%n", result.getString("AccountId"), result.getString("FullName"), result.getDouble("Balance"));
            }
            result.close();
            queryStmt.close();

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Rollback on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            System.err.println("Lỗi: " + e.getMessage());
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true); // Reset auto commit
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}