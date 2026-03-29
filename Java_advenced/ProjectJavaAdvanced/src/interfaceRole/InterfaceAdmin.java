package interfaceRole;

import java.util.Scanner;
import service.AdminService;
import service.ProductService;
import model.Admin;

public class InterfaceAdmin {
    private static Scanner scanner = new Scanner(System.in);
    private static AdminService adminService = new AdminService();
    private static ProductService productService = new ProductService();

    public static void displayInterface() {
        System.out.println("3. Create Staff Account");
        System.out.println("4. View Products");
    }

    public static boolean handleAdminMenu(Admin admin) {
        System.out.println("\n=== WELCOME " + admin.getFullName().toUpperCase() + " ===");
        System.out.println("Role: " + admin.getRole());
        System.out.println("1. View Profile");
        System.out.println("2. Logout");
        displayInterface();

        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                viewProfile(admin);
                return true;
            case 2:
                System.out.println("Logged out successfully!");
                return false;
            case 3:
                createStaffAccount();
                return true;
            case 4:
                productService.displayAllProducts();
                return true;
            default:
                System.out.println("Invalid choice!");
                return true;
        }
    }

    private static void viewProfile(Admin admin) {
        System.out.println("\n=== PROFILE ===");
        System.out.println("Full Name: " + admin.getFullName());
        System.out.println("Age: " + admin.getAge());
        System.out.println("Email: " + admin.getEmail());
        System.out.println("Phone: " + admin.getPhone());
        System.out.println("Role: " + admin.getRole());
        System.out.println("Balance: $" + admin.getBalance());
        System.out.println("Created At: " + admin.getCreated_at());
    }

    private static void createStaffAccount() {
        System.out.println("\n=== CREATE STAFF ACCOUNT ===");
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

        boolean success = adminService.createStaffAccount(userCode, username, password, fullName, age, email, phone);
        if (success) {
            System.out.println("Staff account created successfully!");
        } else {
            System.out.println("Failed to create staff account!");
        }
    }
}
