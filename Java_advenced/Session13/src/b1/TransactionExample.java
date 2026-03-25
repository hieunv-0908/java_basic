package b1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionExample {

    /*
     PHÂN TÍCH

     Trong JDBC, Auto-Commit mặc định là true, mỗi câu lệnh SQL sẽ được commit ngay sau khi thực thi.

     Khi thực hiện:
     1. Trừ thuốc trong kho
     2. Lưu lịch sử cấp phát

     Nếu không tắt Auto-Commit:
     - Câu lệnh trừ thuốc sẽ commit ngay
     - Nếu câu lệnh lưu lịch sử bị lỗi thì không thể rollback lại

     Kết quả:
     - Thuốc bị trừ nhưng không có lịch sử
     - Dữ liệu không nhất quán
    */


    // Code bị lỗi
    public static void wrongCode(Connection conn, int medicineId, int patientId) throws SQLException {

        String updateStock = "UPDATE Medicine_Inventory SET quantity = quantity - 1 WHERE id = ?";
        PreparedStatement ps1 = conn.prepareStatement(updateStock);
        ps1.setInt(1, medicineId);
        ps1.executeUpdate();

        String insertHistory = "INSERT INTO Prescription_History (patient_id, medicine_id, date) VALUES (?, ?, NOW())";
        PreparedStatement ps2 = conn.prepareStatement(insertHistory);
        ps2.setInt(1, patientId);
        ps2.setInt(2, medicineId);
        ps2.executeUpdate();
    }


    // Code đã sửa
    public static void fixedCode(Connection conn, int medicineId, int patientId) {

        try {
            conn.setAutoCommit(false);

            String updateStock = "UPDATE Medicine_Inventory SET quantity = quantity - 1 WHERE id = ?";
            PreparedStatement ps1 = conn.prepareStatement(updateStock);
            ps1.setInt(1, medicineId);
            ps1.executeUpdate();

            String insertHistory = "INSERT INTO Prescription_History (patient_id, medicine_id, date) VALUES (?, ?, NOW())";
            PreparedStatement ps2 = conn.prepareStatement(insertHistory);
            ps2.setInt(1, patientId);
            ps2.setInt(2, medicineId);
            ps2.executeUpdate();

            conn.commit();

        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}