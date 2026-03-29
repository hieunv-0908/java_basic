package interfaceRole;

import java.util.Scanner;
import model.Customer;

public class InterfaceCustomer {
    private static Scanner scanner = new Scanner(System.in);

    public static void displayInterface() {
        System.out.println("3. View Products");
    }

    public static boolean handleCustomerMenu(Customer customer) {
        System.out.println("\n=== WELCOME " + customer.getFullName().toUpperCase() + " ===");
        System.out.println("Role: " + customer.getRole());
        System.out.println("1. View Profile");
        System.out.println("2. Logout");
        displayInterface();

        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                viewProfile(customer);
                return true;
            case 2:
                System.out.println("Logged out successfully!");
                return false;
            case 3:
                System.out.println("Customer options here");
                return true;
            default:
                System.out.println("Invalid choice!");
                return true;
        }
    }

    private static void viewProfile(Customer customer) {
        System.out.println("\n=== PROFILE ===");
        System.out.println("Full Name: " + customer.getFullName());
        System.out.println("Age: " + customer.getAge());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Phone: " + customer.getPhone());
        System.out.println("Role: " + customer.getRole());
        System.out.println("Balance: $" + customer.getBalance());
        System.out.println("Created At: " + customer.getCreated_at());
    }
}
