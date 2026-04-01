package dao;

import model.*;
import ultil.SqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MachineDAO {
    static Connection connection = SqlConnection.getconnection();

    public static List<Machine> queryDb() {
        List<Machine> list = new ArrayList<>();
        String sql = "SELECT * FROM machines WHERE is_deleted = 0";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            ResultSet resultSet = prsmt.executeQuery();
            while (resultSet.next()) {
                int machineId = resultSet.getInt("machine_id");
                String machineCode = resultSet.getString("machine_code");
                Area area = Area.fromArea(resultSet.getString("area"));
                String config = resultSet.getString("config");
                double pricePerHour = resultSet.getDouble("price_per_hour");
                StatusMachine status = StatusMachine.fromStatus(resultSet.getString("status"));

                Machine machine = new Machine(machineId, machineCode, area, config, pricePerHour, status);
                list.add(machine);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn database: " + e.getMessage());
        }

        return list;
    }

    public static boolean createMachine() {
        Scanner scanner = new Scanner(System.in);
        String sql = "INSERT INTO machines(machine_code, area, config, price_per_hour, status) VALUES (?,?,?,?,?)";

        // Validate machine code
        String machineCode;
        do {
            System.out.println("Mời nhập mã máy trạm: ");
            machineCode = scanner.nextLine().trim();
            if (machineCode.isEmpty()) {
                System.out.println("Mã máy trạm không được để trống!");
            } else if (machineCode.length() > 20) {
                System.out.println("Mã máy trạm không được vượt quá 20 ký tự!");
            } else if (isMachineCodeExists(machineCode)) {
                System.out.println("Mã máy trạm đã tồn tại!");
                machineCode = "";
            }
        } while (machineCode.isEmpty() || machineCode.length() > 20);

        // Validate area
        Area area = null;
        do {
            System.out.println("Chọn khu vực (STANDARD/VIP/STREAM): ");
            String areaInput = scanner.nextLine().trim().toUpperCase();
            try {
                area = Area.fromArea(areaInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Khu vực không hợp lệ! Chọn STANDARD, VIP hoặc STREAM.");
            }
        } while (area == null);

        // Validate config
        String config;
        do {
            System.out.println("Mời nhập cấu hình máy: ");
            config = scanner.nextLine().trim();
            if (config.isEmpty()) {
                System.out.println("Cấu hình máy không được để trống!");
            }
        } while (config.isEmpty());

        // Validate price
        double pricePerHour = -1;
        do {
            System.out.println("Mời nhập giá/giờ: ");
            try {
                pricePerHour = Double.parseDouble(scanner.nextLine());
                if (pricePerHour < 0) {
                    System.out.println("Giá phải >= 0!");
                    pricePerHour = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Giá phải là số!");
            }
        } while (pricePerHour < 0);

        // Validate status
        StatusMachine status = null;
        do {
            System.out.println("Chọn trạng thái (AVAILABLE/IN_USE/MAINTENANCE): ");
            String statusInput = scanner.nextLine().trim().toUpperCase();
            try {
                status = StatusMachine.fromStatus(statusInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Trạng thái không hợp lệ! Chọn AVAILABLE, IN_USE hoặc MAINTENANCE.");
            }
        } while (status == null);

        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setString(1, machineCode);
            prsmt.setString(2, area.name());
            prsmt.setString(3, config);
            prsmt.setDouble(4, pricePerHour);
            prsmt.setString(5, status.getStatusCode());

            int rowsAffected = prsmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Máy trạm đã được tạo thành công!");
                return true;
            } else {
                System.out.println("Tạo máy trạm thất bại.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi database: " + e.getMessage());
            return false;
        }
    }

    public static boolean updateMachine() {
        Scanner scanner = new Scanner(System.in);

        // Validate machine ID exists
        int machineId = -1;
        do {
            System.out.println("Mời nhập ID máy trạm muốn cập nhật: ");
            try {
                machineId = Integer.parseInt(scanner.nextLine());
                if (machineId <= 0) {
                    System.out.println("ID máy trạm phải > 0!");
                    machineId = -1;
                } else if (!isMachineExists(machineId)) {
                    System.out.println("ID máy trạm không tồn tại!");
                    machineId = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("ID máy trạm phải là số nguyên!");
            }
        } while (machineId <= 0);

        // Display current info
        Machine currentMachine = getPrivateMachineById(machineId);
        if (currentMachine == null) {
            System.out.println("Không thể lấy thông tin máy trạm!");
            return false;
        }
        System.out.println("\n=== THÔNG TIN HIỆN TẠI ===");
        System.out.println("Mã máy: " + currentMachine.getMachineCode());
        System.out.println("Khu vực: " + currentMachine.getArea());
        System.out.println("Cấu hình: " + currentMachine.getConfig());
        System.out.println("Giá/giờ: " + currentMachine.getPricePerHour());
        System.out.println("Trạng thái: " + currentMachine.getStatusMachine());

        // Update machine code
        String newMachineCode;
        do {
            System.out.println("Mời nhập mã máy mới (nhấn Enter để giữ nguyên): ");
            newMachineCode = scanner.nextLine().trim();
            if (newMachineCode.isEmpty()) {
                newMachineCode = currentMachine.getMachineCode();
            } else if (newMachineCode.length() > 20) {
                System.out.println("Mã máy trạm không được vượt quá 20 ký tự!");
                newMachineCode = "";
            } else if (!newMachineCode.equals(currentMachine.getMachineCode()) && isMachineCodeExists(newMachineCode)) {
                System.out.println("Mã máy trạm đã tồn tại!");
                newMachineCode = "";
            }
        } while (newMachineCode.isEmpty());

        // Update area
        Area newArea = currentMachine.getArea();
        System.out.println("Chọn khu vực mới (nhấn Enter để giữ nguyên): ");
        String areaInput = scanner.nextLine().trim().toUpperCase();
        if (!areaInput.isEmpty()) {
            try {
                newArea = Area.fromArea(areaInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Khu vực không hợp lệ! Giữ nguyên khu vực cũ.");
            }
        }

        // Update config
        String newConfig;
        do {
            System.out.println("Mời nhập cấu hình mới (nhấn Enter để giữ nguyên): ");
            newConfig = scanner.nextLine().trim();
            if (newConfig.isEmpty()) {
                newConfig = currentMachine.getConfig();
            }
        } while (newConfig.isEmpty());

        // Update price
        double newPrice = currentMachine.getPricePerHour();
        System.out.println("Mời nhập giá mới (nhấn Enter để giữ nguyên): ");
        String priceInput = scanner.nextLine().trim();
        if (!priceInput.isEmpty()) {
            try {
                newPrice = Double.parseDouble(priceInput);
                if (newPrice < 0) {
                    System.out.println("Giá phải >= 0! Giữ nguyên giá cũ.");
                    newPrice = currentMachine.getPricePerHour();
                }
            } catch (NumberFormatException e) {
                System.out.println("Giá phải là số! Giữ nguyên giá cũ.");
            }
        }

        // Update status
        StatusMachine newStatus = currentMachine.getStatusMachine();
        System.out.println("Chọn trạng thái mới (nhấn Enter để giữ nguyên): ");
        String statusInput = scanner.nextLine().trim().toUpperCase();
        if (!statusInput.isEmpty()) {
            try {
                newStatus = StatusMachine.fromStatus(statusInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Trạng thái không hợp lệ! Giữ nguyên trạng thái cũ.");
            }
        }

        String sql = "UPDATE machines SET machine_code = ?, area = ?, config = ?, price_per_hour = ?, status = ? WHERE machine_id = ?";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setString(1, newMachineCode);
            prsmt.setString(2, newArea.name());
            prsmt.setString(3, newConfig);
            prsmt.setDouble(4, newPrice);
            prsmt.setString(5, newStatus.getStatusCode());
            prsmt.setInt(6, machineId);

            int rowsAffected = prsmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Máy trạm đã được cập nhật thành công!");
                return true;
            } else {
                System.out.println("Cập nhật máy trạm thất bại.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi database: " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteMachine() {
        Scanner scanner = new Scanner(System.in);

        // Validate machine ID exists
        int machineId = -1;
        do {
            System.out.println("Mời nhập ID máy trạm muốn xóa: ");
            try {
                machineId = Integer.parseInt(scanner.nextLine());
                if (machineId <= 0) {
                    System.out.println("ID máy trạm phải > 0!");
                    machineId = -1;
                } else if (!isMachineExists(machineId)) {
                    System.out.println("ID máy trạm không tồn tại!");
                    machineId = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("ID máy trạm phải là số nguyên!");
            }
        } while (machineId <= 0);

        // Display info and confirm
        Machine machine = getPrivateMachineById(machineId);
        if (machine == null) {
            System.out.println("Không thể lấy thông tin máy trạm!");
            return false;
        }
        System.out.println("\n=== THÔNG TIN MÁY TRẠM ===");
        System.out.println("ID: " + machine.getMachineId());
        System.out.println("Mã máy: " + machine.getMachineCode());
        System.out.println("Khu vực: " + machine.getArea());
        System.out.println("Cấu hình: " + machine.getConfig());
        System.out.println("Giá/giờ: " + machine.getPricePerHour());
        System.out.println("Trạng thái: " + machine.getStatusMachine());

        // Confirm deletion
        String confirm;
        do {
            System.out.println("Bạn có chắc chắn muốn xóa máy trạm này? (y/n): ");
            confirm = scanner.nextLine().trim().toLowerCase();
        } while (!confirm.equals("y") && !confirm.equals("n"));

        if (confirm.equals("n")) {
            System.out.println("Đã hủy xóa máy trạm.");
            return false;
        }

        String sql = "UPDATE machines SET is_deleted = 1 WHERE machine_id = ?";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setInt(1, machineId);
            int rowsAffected = prsmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Máy trạm đã được xóa!");
                return true;
            } else {
                System.out.println("Xóa máy trạm thất bại.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi database: " + e.getMessage());
            return false;
        }
    }

    public static List<Machine> getDeletedMachines() {
        List<Machine> list = new ArrayList<>();
        String sql = "SELECT * FROM machines WHERE is_deleted = 1";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            ResultSet resultSet = prsmt.executeQuery();
            while (resultSet.next()) {
                int machineId = resultSet.getInt("machine_id");
                String machineCode = resultSet.getString("machine_code");
                Area area = Area.fromArea(resultSet.getString("area"));
                String config = resultSet.getString("config");
                double pricePerHour = resultSet.getDouble("price_per_hour");
                StatusMachine status = StatusMachine.fromStatus(resultSet.getString("status"));

                Machine machine = new Machine(machineId, machineCode, area, config, pricePerHour, status);
                list.add(machine);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn máy trạm đã xóa: " + e.getMessage());
        }

        return list;
    }

    public static boolean restoreMachine() {
        Scanner scanner = new Scanner(System.in);

        // Validate ID tồn tại
        int machineId = -1;
        do {
            System.out.println("Mời nhập ID máy trạm muốn khôi phục: ");
            try {
                machineId = Integer.parseInt(scanner.nextLine());
                if (machineId <= 0) {
                    System.out.println("ID máy trạm phải > 0!");
                    machineId = -1;
                } else if (!isDeletedMachineExists(machineId)) {
                    System.out.println("ID máy trạm đã xóa không tồn tại!");
                    machineId = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("ID máy trạm phải là số nguyên!");
            }
        } while (machineId <= 0);

        // Hiển thị thông tin và xác nhận
        Machine machine = getDeletedMachineById(machineId);
        if (machine == null) {
            System.out.println("Không thể lấy thông tin máy trạm đã xóa!");
            return false;
        }
        System.out.println("\n=== THÔNG TIN MÁY TRẠM ĐÃ XÓA ===");
        System.out.println("ID: " + machine.getMachineId());
        System.out.println("Mã máy: " + machine.getMachineCode());
        System.out.println("Khu vực: " + machine.getArea());
        System.out.println("Cấu hình: " + machine.getConfig());
        System.out.println("Giá/giờ: " + machine.getPricePerHour());
        System.out.println("Trạng thái: " + machine.getStatusMachine());

        // Xác nhận khôi phục
        String confirm;
        do {
            System.out.println("Bạn có chắc chắn muốn khôi phục máy trạm này? (y/n): ");
            confirm = scanner.nextLine().trim().toLowerCase();
        } while (!confirm.equals("y") && !confirm.equals("n"));

        if (confirm.equals("n")) {
            System.out.println("Đã hủy khôi phục máy trạm.");
            return false;
        }

        String sql = "UPDATE machines SET is_deleted = 0 WHERE machine_id = ?";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setInt(1, machineId);
            int rowsAffected = prsmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Máy trạm đã được khôi phục thành công!");
                return true;
            } else {
                System.out.println("Khôi phục máy trạm thất bại.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi database: " + e.getMessage());
            return false;
        }
    }

    public static List<Machine> getAvailableMachinesByArea(Area area) {
        List<Machine> list = new ArrayList<>();
        String sql = "SELECT * FROM machines WHERE area = ? AND status = 'AVAILABLE' AND is_deleted = 0";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setString(1, area.getAreaCode());
            ResultSet resultSet = prsmt.executeQuery();
            while (resultSet.next()) {
                int machineId = resultSet.getInt("machine_id");
                String machineCode = resultSet.getString("machine_code");
                Area machineArea = Area.fromArea(resultSet.getString("area"));
                String config = resultSet.getString("config");
                double pricePerHour = resultSet.getDouble("price_per_hour");
                StatusMachine status = StatusMachine.fromStatus(resultSet.getString("status"));

                Machine machine = new Machine(machineId, machineCode, machineArea, config, pricePerHour, status);
                list.add(machine);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn database: " + e.getMessage());
        }

        return list;
    }

    private static boolean isMachineExists(int machineId) {
        String sql = "SELECT COUNT(*) FROM machines WHERE machine_id = ?";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setInt(1, machineId);
            ResultSet rs = prsmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kiểm tra ID: " + e.getMessage());
        }
        return false;
    }

    private static boolean isMachineCodeExists(String machineCode) {
        String sql = "SELECT COUNT(*) FROM machines WHERE machine_code = ?";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setString(1, machineCode);
            ResultSet rs = prsmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kiểm tra mã máy: " + e.getMessage());
        }
        return false;
    }

    private static boolean isDeletedMachineExists(int machineId) {
        String sql = "SELECT COUNT(*) FROM machines WHERE machine_id = ? AND is_deleted = 1";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setInt(1, machineId);
            ResultSet rs = prsmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kiểm tra máy trạm đã xóa: " + e.getMessage());
        }
        return false;
    }

    private static Machine getPrivateMachineById(int machineId) {
        String sql = "SELECT * FROM machines WHERE machine_id = ?";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setInt(1, machineId);
            ResultSet rs = prsmt.executeQuery();
            if (rs.next()) {
                return new Machine(
                        rs.getInt("machine_id"),
                        rs.getString("machine_code"),
                        Area.fromArea(rs.getString("area")),
                        rs.getString("config"),
                        rs.getDouble("price_per_hour"),
                        StatusMachine.fromStatus(rs.getString("status"))
                );
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy thông tin máy trạm: " + e.getMessage());
        }
        return null;
    }

    // Public method to get machine by ID (for use in other classes)
    public static Machine getMachineById(int machineId) {
        String sql = "SELECT * FROM machines WHERE machine_id = ? AND is_deleted = 0";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setInt(1, machineId);
            ResultSet rs = prsmt.executeQuery();
            if (rs.next()) {
                return new Machine(
                        rs.getInt("machine_id"),
                        rs.getString("machine_code"),
                        Area.fromArea(rs.getString("area")),
                        rs.getString("config"),
                        rs.getDouble("price_per_hour"),
                        StatusMachine.fromStatus(rs.getString("status"))
                );
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy thông tin máy trạm: " + e.getMessage());
        }
        return null;
    }

    private static Machine getDeletedMachineById(int machineId) {
        String sql = "SELECT * FROM machines WHERE machine_id = ? AND is_deleted = 1";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setInt(1, machineId);
            ResultSet rs = prsmt.executeQuery();
            if (rs.next()) {
                return new Machine(
                        rs.getInt("machine_id"),
                        rs.getString("machine_code"),
                        Area.fromArea(rs.getString("area")),
                        rs.getString("config"),
                        rs.getDouble("price_per_hour"),
                        StatusMachine.fromStatus(rs.getString("status"))
                );
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy thông tin máy trạm đã xóa: " + e.getMessage());
        }
        return null;
    }
}
