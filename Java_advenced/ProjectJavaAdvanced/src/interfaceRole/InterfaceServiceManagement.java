package interfaceRole;

import java.util.Scanner;

import service.ProductService;

public class InterfaceServiceManagement {
    private static Scanner scanner = new Scanner(System.in);
    private static ProductService productService = new ProductService();

    public static void displayMenu() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        QUẢN LÝ DỊCH VỤ CYBER GAMING            ║");
        System.out.println("╠════════════════════════════════════════════════╣");
        System.out.println("║ 1. Xem danh sách dịch vụ                        ║");
        System.out.println("║ 2. Thêm dịch vụ                                 ║");
        System.out.println("║ 3. Sửa thông tin dịch vụ                        ║");
        System.out.println("║ 4. Xóa dịch vụ                                  ║");
        System.out.println("║ 5. Xem dịch vụ đã xóa                           ║");
        System.out.println("║ 6. Khôi phục dịch vụ                            ║");
        System.out.println("║ 7. Quay lại menu chính                          ║");
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
                    productService.displayAllServices();
                    break;
                case 2:
                    productService.createNewService();
                    break;
                case 3:
                    productService.updateExistingService();
                    break;
                case 4:
                    productService.deleteExistingService();
                    break;
                case 5:
                    productService.displayDeletedServices();
                    break;
                case 6:
                    productService.restoreService();
                    break;
                case 7:
                    return true;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}

