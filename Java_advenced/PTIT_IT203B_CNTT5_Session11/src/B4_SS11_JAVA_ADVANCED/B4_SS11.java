package B4_SS11_JAVA_ADVANCED;

/**
 * Phần 1: Phân tích
 * - Sau khi thực hiện nối chuỗi, câu lệnh SQL thực tế gửi đến Database sẽ là:
 * SELECT * FROM Patients WHERE full_name = '' OR '1'='1';
 * - Tại sao mệnh đề WHERE luôn đúng:
 * + Toán tử OR sẽ trả về true nếu một trong hai vế đúng.
 * + Trong khi vế đầu (full_name = '') có thể sai, thì vế sau ('1'='1') luôn luôn là một biểu thức logic ĐÚNG.
 * + Kết quả là điều kiện lọc bị vô hiệu hóa, Database coi như không có điều kiện lọc và
 * trả về toàn bộ danh sách bệnh nhân cho Hacker, gây lộ thông tin nghiêm trọng.
 *
 * Phần 2: Giải pháp xử lý (Loại bỏ ký tự đặc biệt)
 */

/**
    // Giả sử đầu vào từ ô tìm kiếm là patientName
    String patientName = "'' OR '1'='1'"; // Chuỗi giả lập tấn công

    // Bước 1: Kiểm tra và làm sạch dữ liệu (Sanitization)
    // Loại bỏ các ký tự nguy hiểm thường dùng trong SQL Injection
    String safeName = patientName.replace("'", "")   // Loại bỏ dấu nháy đơn
                                 .replace("--", "")  // Loại bỏ dấu comment
                                 .replace(";", "");  // Loại bỏ dấu kết thúc lệnh

    // Bước 2: Tạo câu lệnh SQL với chuỗi đã được làm sạch
    String sql = "SELECT * FROM Patients WHERE full_name = '" + safeName + "'";

    try {
        // Thực thi truy vấn an toàn hơn với Statement
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            System.out.println("Bệnh nhân: " + rs.getString("full_name"));
        }

        if (!rs.isBeforeFirst()) {
             System.out.println("Không tìm thấy bệnh nhân phù hợp.");
        }

    } catch (SQLException e) {
        System.err.println("Lỗi truy vấn: " + e.getMessage());
    }
*/
