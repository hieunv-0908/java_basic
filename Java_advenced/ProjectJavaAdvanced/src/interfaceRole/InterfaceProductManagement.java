package interfaceRole;

import java.util.Scanner;

import service.ProductService;

public class InterfaceProductManagement {
    private static Scanner scanner = new Scanner(System.in);
    private static ProductService productService = new ProductService();

    public static void displayMenu() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║     QUẢN LÝ ĐỒ ĂN/THỨC UỐNG CYBER GAMING       ║");
        System.out.println("╠════════════════════════════════════════════════╣");
        System.out.println("║ 1. Xem danh sách sản phẩm                       ║");
        System.out.println("║ 2. Thêm sản phẩm                                ║");
        System.out.println("║ 3. Sửa thông tin sản phẩm                       ║");
        System.out.println("║ 4. Xóa sản phẩm                                 ║");
        System.out.println("║ 5. Quay lại menu chính                          ║");
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
                    productService.displayAllProducts();
                    break;
                case 2:
                    System.out.println("Chức năng này sẽ được thêm sau!");
                    break;
                case 3:
                    System.out.println("Chức năng này sẽ được thêm sau!");
                    break;
                case 4:
                    System.out.println("Chức năng này sẽ được thêm sau!");
                    break;
                case 5:
                    return true;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}

