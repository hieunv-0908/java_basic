package B3_SS11_JAVA_ADVANCED;

/**
 * Phần 1: Phân tích
 * - Giá trị trả về của executeUpdate() là kiểu số nguyên (int), đại diện cho số lượng dòng (rows)
 * thực tế đã bị tác động (thêm, sửa, hoặc xóa) trong cơ sở dữ liệu.
 * - Để phản hồi chính xác cho y tá, ta cần kiểm tra giá trị này:
 * + Nếu trả về > 0: Có ít nhất một giường đã được cập nhật trạng thái thành công.
 * + Nếu trả về = 0: Không có dòng nào thỏa mãn điều kiện WHERE (Mã giường không tồn tại),
 * từ đó ta có thể in ra thông báo lỗi tương ứng.
 *
 * Phần 2: Thực thi
 */

/**
    // Giả sử stmt và inputId đã được khởi tạo đúng từ trước
    String sql = "UPDATE Beds SET bed_status = 'Occupied' WHERE bed_id = '" + inputId + "'";

    try {
        int rowsAffected = stmt.executeUpdate(sql);

        if (rowsAffected > 0) {
            System.out.println("Thành công: Đã cập nhật trạng thái cho giường " + inputId);
        } else {
            // Trường hợp executeUpdate trả về 0
            System.out.println("Lỗi: Mã giường '" + inputId + "' không tồn tại trong hệ thống!");
        }

    } catch (SQLException e) {
        System.err.println("Lỗi hệ thống khi cập nhật: " + e.getMessage());
    } finally {
        // Luôn đảm bảo đóng tài nguyên sau khi sử dụng (tùy thuộc vào cấu trúc dự án)
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
*/