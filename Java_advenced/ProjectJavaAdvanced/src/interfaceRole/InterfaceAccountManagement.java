package interfaceRole;

import java.util.Scanner;

import service.AdminService;

public class InterfaceAccountManagement {
    private static Scanner scanner = new Scanner(System.in);
    private static AdminService adminService = new AdminService();

    public static void displayMenu() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        QUẢN LÝ TÀI KHOẢN CYBER GAMING          ║");
        System.out.println("╠════════════════════════════════════════════════╣");
        System.out.println("║ 1. Xem danh sách nhân viên                      ║");
        System.out.println("║ 2. Tạo tài khoản nhân viên                      ║");
        System.out.println("║ 3. Xem danh sách khách hàng                     ║");
        System.out.println("║ 4. Quay lại menu chính                          ║");
        System.out.println("╚════════════════════════════════════════════════╝");
    }

    public static boolean handleMenu() {
        while (true) {
            displayMenu();
            System.out.print("Chọn thao tác: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Nhập không hợp lệ!");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("Chức năng này sẽ được thêm sau!");
                    break;
                case 2:
                    createStaffAccount();
                    break;
                case 3:
                    System.out.println("Chức năng này sẽ được thêm sau!");
                    break;
                case 4:
                    return true;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private static void createStaffAccount() {
        System.out.println("\n=== TẠO TÀI KHOẢN NHÂN VIÊN ===");
        System.out.print("User Code: ");
        String userCode = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Full Name: ");
        String fullName = scanner.nextLine();
        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        boolean success = adminService.createStaffAccount(userCode, username, password, fullName, age, email, phone);
        if (success) {
            System.out.println("Tài khoản nhân viên đã được tạo thành công!");
        } else {
            System.out.println("Tạo tài khoản nhân viên thất bại!");
        }
    }
}

