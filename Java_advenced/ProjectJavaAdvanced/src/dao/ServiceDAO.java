package dao;

import model.*;
import ultil.SqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServiceDAO {
    static Connection connection = SqlConnection.getconnection();

    // ...existing queryDb method...

    public static List<Service> queryDb() {
        List<Service> list = new ArrayList<>();
        String sql = "SELECT * FROM services WHERE is_deleted = 0";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            ResultSet resultSet = prsmt.executeQuery();
            while (resultSet.next()) {
                int serviceId = resultSet.getInt("service_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");

                Service service = new Service(serviceId, name, description, price);
                list.add(service);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn database: " + e.getMessage());
        }

        return list;
    }

    public static boolean createService() {
        Scanner scanner = new Scanner(System.in);
        String sql = "INSERT INTO services(name, description, price) VALUES (?,?,?)";

        // Validate tên dịch vụ
        String name;
        do {
            System.out.println("Mời nhập tên dịch vụ muốn tạo: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Tên dịch vụ không được để trống!");
            } else if (name.length() > 100) {
                System.out.println("Tên dịch vụ không được vượt quá 100 ký tự!");
            }
        } while (name.isEmpty() || name.length() > 100);

        // Validate mô tả
        String description;
        do {
            System.out.println("Mời nhập mô tả dịch vụ muốn tạo: ");
            description = scanner.nextLine().trim();
            if (description.isEmpty()) {
                System.out.println("Mô tả dịch vụ không được để trống!");
            }
        } while (description.isEmpty());

        // Validate giá
        double price = -1;
        do {
            System.out.println("Mời nhập giá dịch vụ muốn tạo: ");
            try {
                price = Double.parseDouble(scanner.nextLine());
                if (price < 0) {
                    System.out.println("Giá dịch vụ phải >= 0!");
                    price = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Giá dịch vụ phải là số!");
            }
        } while (price < 0);

        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setString(1, name);
            prsmt.setString(2, description);
            prsmt.setDouble(3, price);
            int rowsAffected = prsmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Dịch vụ đã được tạo thành công!");
                return true;
            } else {
                System.out.println("Tạo dịch vụ thất bại.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi database: " + e.getMessage());
            return false;
        }
    }

    public static boolean updateService() {
        Scanner scanner = new Scanner(System.in);

        // Validate ID tồn tại
        int serviceId = -1;
        do {
            System.out.println("Mời nhập ID dịch vụ muốn cập nhật: ");
            try {
                serviceId = Integer.parseInt(scanner.nextLine());
                if (serviceId <= 0) {
                    System.out.println("ID dịch vụ phải > 0!");
                    serviceId = -1;
                } else if (!isServiceExists(serviceId)) {
                    System.out.println("ID dịch vụ không tồn tại!");
                    serviceId = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("ID dịch vụ phải là số nguyên!");
            }
        } while (serviceId <= 0);

        // Hiển thị thông tin hiện tại
        Service currentService = getServiceById(serviceId);
        System.out.println("\n=== THÔNG TIN HIỆN TẠI ===");
        System.out.println("Tên: " + currentService.getServiceName());
        System.out.println("Mô tả: " + currentService.getDescription());
        System.out.println("Giá: " + currentService.getPrice());

        // Validate tên mới
        String newName;
        do {
            System.out.println("Mời nhập tên dịch vụ mới (nhấn Enter để giữ nguyên): ");
            newName = scanner.nextLine().trim();
            if (newName.isEmpty()) {
                newName = currentService.getServiceName();
            } else if (newName.length() > 100) {
                System.out.println("Tên dịch vụ không được vượt quá 100 ký tự!");
                newName = "";
            }
        } while (newName.isEmpty());

        // Validate mô tả mới
        String newDescription;
        do {
            System.out.println("Mời nhập mô tả dịch vụ mới (nhấn Enter để giữ nguyên): ");
            newDescription = scanner.nextLine().trim();
            if (newDescription.isEmpty()) {
                newDescription = currentService.getDescription();
            }
        } while (newDescription.isEmpty());

        // Validate giá mới
        double newPrice = -1;
        do {
            System.out.println("Mời nhập giá mới (nhấn Enter để giữ nguyên): ");
            String priceInput = scanner.nextLine().trim();
            if (priceInput.isEmpty()) {
                newPrice = currentService.getPrice();
            } else {
                try {
                    newPrice = Double.parseDouble(priceInput);
                    if (newPrice < 0) {
                        System.out.println("Giá dịch vụ phải >= 0!");
                        newPrice = -1;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Giá dịch vụ phải là số!");
                    newPrice = -1;
                }
            }
        } while (newPrice < 0);

        String sql = "UPDATE services SET name = ?, description = ?, price = ? WHERE service_id = ?";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setString(1, newName);
            prsmt.setString(2, newDescription);
            prsmt.setDouble(3, newPrice);
            prsmt.setInt(4, serviceId);
            int rowsAffected = prsmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Dịch vụ đã được cập nhật thành công!");
                return true;
            } else {
                System.out.println("Cập nhật dịch vụ thất bại.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi database: " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteService() {
        Scanner scanner = new Scanner(System.in);

        // Validate ID tồn tại
        int serviceId = -1;
        do {
            System.out.println("Mời nhập ID dịch vụ muốn xóa: ");
            try {
                serviceId = Integer.parseInt(scanner.nextLine());
                if (serviceId <= 0) {
                    System.out.println("ID dịch vụ phải > 0!");
                    serviceId = -1;
                } else if (!isServiceExists(serviceId)) {
                    System.out.println("ID dịch vụ không tồn tại!");
                    serviceId = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("ID dịch vụ phải là số nguyên!");
            }
        } while (serviceId <= 0);

        // Hiển thị thông tin và xác nhận
        Service service = getServiceById(serviceId);
        System.out.println("\n=== THÔNG TIN DỊCH VỤ ===");
        System.out.println("ID: " + service.getServiceId());
        System.out.println("Tên: " + service.getServiceName());
        System.out.println("Mô tả: " + service.getDescription());
        System.out.println("Giá: " + service.getPrice());

        // Xác nhận xóa
        String confirm;
        do {
            System.out.println("Bạn có chắc chắn muốn xóa dịch vụ này? (y/n): ");
            confirm = scanner.nextLine().trim().toLowerCase();
        } while (!confirm.equals("y") && !confirm.equals("n"));

        if (confirm.equals("n")) {
            System.out.println("Đã hủy xóa dịch vụ.");
            return false;
        }

        String sql = "UPDATE services SET is_deleted = 1 WHERE service_id = ?";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setInt(1, serviceId);
            int rowsAffected = prsmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Dịch vụ đã được xóa!");
                return true;
            } else {
                System.out.println("Xóa dịch vụ thất bại.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi database: " + e.getMessage());
            return false;
        }
    }

    public static List<Service> getDeletedServices() {
        List<Service> list = new ArrayList<>();
        String sql = "SELECT * FROM services WHERE is_deleted = 1";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            ResultSet resultSet = prsmt.executeQuery();
            while (resultSet.next()) {
                int serviceId = resultSet.getInt("service_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");

                Service service = new Service(serviceId, name, description, price);
                list.add(service);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn dịch vụ đã xóa: " + e.getMessage());
        }

        return list;
    }

    public static boolean restoreService() {
        Scanner scanner = new Scanner(System.in);

        // Validate ID tồn tại
        int serviceId = -1;
        do {
            System.out.println("Mời nhập ID dịch vụ muốn khôi phục: ");
            try {
                serviceId = Integer.parseInt(scanner.nextLine());
                if (serviceId <= 0) {
                    System.out.println("ID dịch vụ phải > 0!");
                    serviceId = -1;
                } else if (!isDeletedServiceExists(serviceId)) {
                    System.out.println("ID dịch vụ đã xóa không tồn tại!");
                    serviceId = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("ID dịch vụ phải là số nguyên!");
            }
        } while (serviceId <= 0);

        // Hiển thị thông tin và xác nhận
        Service service = getDeletedServiceById(serviceId);
        System.out.println("\n=== THÔNG TIN DỊCH VỤ ĐÃ XÓA ===");
        System.out.println("ID: " + service.getServiceId());
        System.out.println("Tên: " + service.getServiceName());
        System.out.println("Mô tả: " + service.getDescription());
        System.out.println("Giá: " + service.getPrice());

        // Xác nhận khôi phục
        String confirm;
        do {
            System.out.println("Bạn có chắc chắn muốn khôi phục dịch vụ này? (y/n): ");
            confirm = scanner.nextLine().trim().toLowerCase();
        } while (!confirm.equals("y") && !confirm.equals("n"));

        if (confirm.equals("n")) {
            System.out.println("Đã hủy khôi phục dịch vụ.");
            return false;
        }

        String sql = "UPDATE services SET is_deleted = 0 WHERE service_id = ?";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setInt(1, serviceId);
            int rowsAffected = prsmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Dịch vụ đã được khôi phục thành công!");
                return true;
            } else {
                System.out.println("Khôi phục dịch vụ thất bại.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi database: " + e.getMessage());
            return false;
        }
    }

    private static boolean isServiceExists(int serviceId) {
        String sql = "SELECT COUNT(*) FROM services WHERE service_id = ?";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setInt(1, serviceId);
            ResultSet rs = prsmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kiểm tra ID: " + e.getMessage());
        }
        return false;
    }

    private static Service getServiceById(int serviceId) {
        String sql = "SELECT * FROM services WHERE service_id = ?";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setInt(1, serviceId);
            ResultSet rs = prsmt.executeQuery();
            if (rs.next()) {
                return new Service(
                        rs.getInt("service_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price")
                );
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy thông tin dịch vụ: " + e.getMessage());
        }
        return null;
    }

    private static boolean isDeletedServiceExists(int serviceId) {
        String sql = "SELECT COUNT(*) FROM services WHERE service_id = ? AND is_deleted = 1";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setInt(1, serviceId);
            ResultSet rs = prsmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kiểm tra dịch vụ đã xóa: " + e.getMessage());
        }
        return false;
    }

    private static Service getDeletedServiceById(int serviceId) {
        String sql = "SELECT * FROM services WHERE service_id = ? AND is_deleted = 1";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setInt(1, serviceId);
            ResultSet rs = prsmt.executeQuery();
            if (rs.next()) {
                return new Service(
                        rs.getInt("service_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price")
                );
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy thông tin dịch vụ đã xóa: " + e.getMessage());
        }
        return null;
    }
}
