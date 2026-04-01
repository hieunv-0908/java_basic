package interfaceRole;

import java.util.Scanner;

import model.Admin;

public class InterfaceAdmin {
    private static Scanner scanner = new Scanner(System.in);

    public static void displayMainMenu() {
        System.out.println("\n╔═══════════════════════════════════════════════════╗");
        System.out.println("║   CHÀO MỪNG ĐẾN HỆ THỐNG QUẢN LÝ CYBER GAMING   ║");
        System.out.println("╠═══════════════════════════════════════════════════╣");
        System.out.println("║ 1. Xem hồ sơ cá nhân                              ║");
        System.out.println("║ 2. Quản lý dịch vụ                                ║");
        System.out.println("║ 3. Quản lý máy trạm                               ║");
        System.out.println("║ 4. Quản lý đồ ăn/thức uống                        ║");
        System.out.println("║ 5. Quản lý tài khoản                              ║");
        System.out.println("║ 6. Đăng xuất                                      ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
    }

    public static boolean handleAdminMenu(Admin admin) {
        while (true) {
            System.out.println("\n╔════════════════════════════════════════════════╗");
            System.out.println("║  ADMIN: " + String.format("%-37s", admin.getFullName()) + " ║");
            System.out.println("╚════════════════════════════════════════════════╝");

            displayMainMenu();
            System.out.print("Chọn chức năng: ");

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
                    viewProfile(admin);
                    break;
                case 2:
                    InterfaceServiceManagement.handleMenu();
                    break;
                case 3:
                    InterfaceMachineManagement.handleMenu();
                    break;
                case 4:
                    InterfaceProductManagement.handleMenu();
                    break;
                case 5:
                    InterfaceAccountManagement.handleMenu();
                    break;
                case 6:
                    System.out.println("Đã đăng xuất thành công!");
                    return false;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private static void viewProfile(Admin admin) {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║              HỒ SƠ CÁ NHÂN                      ║");
        System.out.println("╠════════════════════════════════════════════════╣");
        System.out.println("║ Tên: " + String.format("%-42s", admin.getFullName()) + " ║");
        System.out.println("║ Tuổi: " + String.format("%-41s", admin.getAge()) + " ║");
        System.out.println("║ Email: " + String.format("%-40s", admin.getEmail()) + " ║");
        System.out.println("║ Điện thoại: " + String.format("%-37s", admin.getPhone()) + " ║");
        System.out.println("║ Chức vụ: " + String.format("%-38s", admin.getRole()) + " ║");
        System.out.println("║ Số dư: " + String.format("%-40s", "$" + admin.getBalance()) + " ║");
        System.out.println("║ Ngày tạo: " + String.format("%-38s", admin.getCreated_at()) + " ║");
        System.out.println("╚════════════════════════════════════════════════╝");
    }
}
