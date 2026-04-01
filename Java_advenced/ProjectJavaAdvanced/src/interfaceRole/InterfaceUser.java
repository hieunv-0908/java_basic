package interfaceRole;

import java.util.Scanner;

import service.UserService;
import model.User;

public class InterfaceUser {
    private static Scanner scanner = new Scanner(System.in);
    private static UserService userService = new UserService();

    public static void displayInterface() {
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
    }

    public static User handleMainMenu() {
        while (true) {
            System.out.println("\n╔═══════════════════════════════════════════════════╗");
            System.out.println("║          HỆ THỐNG QUẢN LÝ CYBER GAMING           ║");
            System.out.println("╠═══════════════════════════════════════════════════╣");
            System.out.println("║ 1. Đăng nhập                                      ║");
            System.out.println("║ 2. Đăng ký                                        ║");
            System.out.println("║ 3. Thoát                                          ║");
            System.out.println("╚═══════════════════════════════════════════════════╝");
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
                    return login();
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("Tạm biệt!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private static User login() {
        System.out.println("\n=== LOGIN ===");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User currentUser = userService.login(username, password);
        if (currentUser != null) {
            System.out.println("Login successful! Welcome " + currentUser.getFullName());
        } else {
            System.out.println("Login failed!");
        }
        return currentUser;
    }

    private static void register() {
        System.out.println("\n=== REGISTER ===");
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
        scanner.nextLine(); // consume newline
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        boolean success = userService.register(userCode, username, password, fullName, age, email, phone);
        if (success) {
            System.out.println("Registration successful! You can now login.");
        } else {
            System.out.println("Registration failed!");
        }
    }
}
