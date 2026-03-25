package b2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentService {

    public void thanhToanVienPhi(int patientId, int invoiceId, double amount) {
        Connection conn = null;

        try {
            DriverManager DatabaseManager = null;
            conn = DatabaseManager.getConnection("jdbc:mysql://localhost ;3306/hospital", "root", "Hieu123456");

            // Tắt auto commit → bắt đầu transaction
            conn.setAutoCommit(false);

            // Bước 1: Trừ tiền ví
            String sqlDeductWallet = "UPDATE Patient_Wallet SET balance = balance - ? WHERE patient_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(sqlDeductWallet);
            ps1.setDouble(1, amount);
            ps1.setInt(2, patientId);
            ps1.executeUpdate();

            // Bước 2: Cập nhật hóa đơn (cố tình sai để gây lỗi)
            String sqlUpdateInvoice = "UPDATE Invoicesss SET status = 'PAID' WHERE invoice_id = ?";
            PreparedStatement ps2 = conn.prepareStatement(sqlUpdateInvoice);
            ps2.setInt(1, invoiceId);
            ps2.executeUpdate();

            // Nếu tất cả thành công → commit
            conn.commit();
            System.out.println("Thanh toán hoàn tất!");

        } catch (SQLException e) {
            System.out.println("Lỗi hệ thống: Không thể hoàn tất thanh toán. Chi tiết: " + e.getMessage());

            // QUAN TRỌNG: rollback khi có lỗi
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Đã rollback transaction.");
                } catch (SQLException rollbackEx) {
                    System.out.println("Lỗi khi rollback: " + rollbackEx.getMessage());
                }
            }

        } finally {
            // Đóng kết nối để tránh leak tài nguyên
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException closeEx) {
                    System.out.println("Lỗi khi đóng kết nối: " + closeEx.getMessage());
                }
            }
        }
    }
}
