import service.UserService;
import model.User;
import model.Admin;
import model.Staff;
import model.Customer;

import java.util.Scanner;

public class Main {
    private static UserService userService = new UserService();
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;

    public static void main(String[] args) {
        while (true) {
            if (currentUser == null) {
                showMainMenu();
            } else {
                showUserMenu();
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\n=== CYBER GAMING SYSTEM ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private static void showUserMenu() {
        System.out.println("\n=== WELCOME " + currentUser.getFullName().toUpperCase() + " ===");
        System.out.println("Role: " + currentUser.getRole());
        System.out.println("1. View Profile");
        System.out.println("2. Logout");

        // Only show admin options for admin users
        if (currentUser instanceof Admin) {
            System.out.println("3. Create Staff Account");
        }

        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                viewProfile();
                break;
            case 2:
                logout();
                break;
            case 3:
                if (currentUser instanceof Admin) {
                    createStaffAccount();
                } else {
                    System.out.println("Invalid choice!");
                }
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private static void login() {
        System.out.println("\n=== LOGIN ===");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        currentUser = userService.login(username, password);
        if (currentUser != null) {
            System.out.println("Login successful! Welcome " + currentUser.getFullName());
        }
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

    private static void viewProfile() {
        System.out.println("\n=== PROFILE ===");
        System.out.println("User ID: " + currentUser.getUserId());
        System.out.println("User Code: " + currentUser.getUserCode());
        System.out.println("Username: " + currentUser.getUserName());
        System.out.println("Full Name: " + currentUser.getFullName());
        System.out.println("Age: " + currentUser.getAge());
        System.out.println("Email: " + currentUser.getEmail());
        System.out.println("Phone: " + currentUser.getPhone());
        System.out.println("Role: " + currentUser.getRole());
        System.out.println("Balance: $" + currentUser.getBalance());
        System.out.println("Created At: " + currentUser.getCreated_at());
    }

    private static void logout() {
        System.out.println("Logged out successfully!");
        currentUser = null;
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

        boolean success = userService.createStaffAccount(userCode, username, password, fullName, age, email, phone);
        if (success) {
            System.out.println("Staff account created successfully!");
        } else {
            System.out.println("Failed to create staff account!");
        }
    }
}