package interfaceRole;

import java.util.Scanner;
import model.Staff;

public class InterfaceStaff {
    private static Scanner scanner = new Scanner(System.in);

    public static void displayInterface() {
        System.out.println("3. View Products");
    }

    public static boolean handleStaffMenu(Staff staff) {
        System.out.println("\n=== WELCOME " + staff.getFullName().toUpperCase() + " ===");
        System.out.println("Role: " + staff.getRole());
        System.out.println("1. View Profile");
        System.out.println("2. Logout");
        displayInterface();

        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                viewProfile(staff);
                return true;
            case 2:
                System.out.println("Logged out successfully!");
                return false;
            case 3:
                System.out.println("Staff options here");
                return true;
            default:
                System.out.println("Invalid choice!");
                return true;
        }
    }

    private static void viewProfile(Staff staff) {
        System.out.println("\n=== PROFILE ===");
        System.out.println("Full Name: " + staff.getFullName());
        System.out.println("Age: " + staff.getAge());
        System.out.println("Email: " + staff.getEmail());
        System.out.println("Phone: " + staff.getPhone());
        System.out.println("Role: " + staff.getRole());
        System.out.println("Balance: $" + staff.getBalance());
        System.out.println("Created At: " + staff.getCreated_at());
    }
}
