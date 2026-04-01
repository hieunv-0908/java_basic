package interfaceRole;

import java.util.Scanner;

import service.ProductService;

public class InterfaceMachineManagement {
    private static Scanner scanner = new Scanner(System.in);
    private static ProductService productService = new ProductService();

    public static void displayMenu() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        QUẢN LÝ MÁY TRẠM CYBER GAMING           ║");
        System.out.println("╠════════════════════════════════════════════════╣");
        System.out.println("║ 1. Xem danh sách máy trạm                       ║");
        System.out.println("║ 2. Thêm máy trạm                                ║");
        System.out.println("║ 3. Sửa thông tin máy trạm                       ║");
        System.out.println("║ 4. Xóa máy trạm                                 ║");
        System.out.println("║ 5. Xem máy trạm đã xóa                          ║");
        System.out.println("║ 6. Khôi phục máy trạm                           ║");
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
                    productService.displayAllMachines();
                    break;
                case 2:
                    productService.createNewMachine();
                    break;
                case 3:
                    productService.updateExistingMachine();
                    break;
                case 4:
                    productService.deleteExistingMachine();
                    break;
                case 5:
                    productService.displayDeletedMachines();
                    break;
                case 6:
                    productService.restoreMachine();
                    break;
                case 7:
                    return true;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ!");
            }
        }
    }
}

