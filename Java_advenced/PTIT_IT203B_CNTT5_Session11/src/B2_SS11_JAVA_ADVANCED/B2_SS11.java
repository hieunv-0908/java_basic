package B2_SS11_JAVA_ADVANCED;
/**
 * Phần 1:
 *  Vì if chỉ kiểm tra điều kiện 1 lần
 *
 *  Phần 2:
 *
 *  // Giả sử stmt và connection đã được khởi tạo đúng
 * String sql = "SELECT medicine_name, stock FROM Pharmacy";
 * ResultSet rs = null;
 *
 * try {
 *     rs = stmt.executeQuery(sql);
 *
 *     System.out.println("--- DANH MỤC THUỐC TRONG KHO ---");
 *
 *     // Sử dụng WHILE để lặp qua cho đến khi hết dữ liệu
 *     while (rs.next()) {
 *         // Lấy dữ liệu bằng tên cột
 *         String name = rs.getString("medicine_name");
 *         int stock = rs.getInt("stock");
 *
 *         // In ra màn hình
 *         System.out.println("Tên thuốc: " + name + " | Số lượng tồn: " + stock);
 *     }
 *
 * } catch (SQLException e) {
 *     System.err.println("Lỗi truy vấn dữ liệu: " + e.getMessage());
 * } finally {
 *     // Đừng quên dọn dẹp "thùng chứa" sau khi dùng xong
 *     if (rs != null) {
 *         try {
 *             rs.close();
 *         } catch (SQLException e) {
 *             e.printStackTrace();
 *         }
 *     }
 * }
 */