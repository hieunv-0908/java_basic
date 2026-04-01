package service;

import dao.FoodAndDrinkDAO;
import dao.MachineDAO;
import dao.ServiceDAO;
import model.FoodAndDrink;
import model.Machine;
import model.Service;

import java.util.List;

public class ProductService {
    List<FoodAndDrink> foodAndDrinks;

    public ProductService() {
        this.foodAndDrinks = FoodAndDrinkDAO.queryDb();
    }

    // Display all food and drinks in table format
    public void displayAllProducts() {
        foodAndDrinks = FoodAndDrinkDAO.queryDb();
        if (foodAndDrinks.isEmpty()) {
            System.out.println("No products available!");
            return;
        }

        // Print table header
        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                        DANH MỤC ĐỒ ĂN / THỨC UỐNG                             ║");
        System.out.println("╠════╦═══════════════════╦═══════════════════════════╦═════════╦═══════════════╣");
        System.out.printf("║ ID ║      TÊN          ║       MÔ TẢ               ║  GIÁ    ║  TỒN KHO      ║%n");
        System.out.println("╠════╬═══════════════════╬═══════════════════════════╬═════════╬═══════════════╣");

        // Print data rows
        for (FoodAndDrink foodAndDrink : foodAndDrinks) {
            System.out.printf("║ %-2d ║ %-17s ║ %-25s ║ %7.2f ║ %13d ║%n",
                    foodAndDrink.getFoodAndDrinkId(),
                    truncateString(foodAndDrink.getName(), 17),
                    truncateString(foodAndDrink.getDescription(), 25),
                    foodAndDrink.getPrice(),
                    foodAndDrink.getStock());
        }

        System.out.println("╚════╩═══════════════════╩═══════════════════════════╩═════════╩═══════════════╝");
    }

    // Helper method to truncate string to specified length
    private String truncateString(String str, int length) {
        if (str == null) return "";
        if (str.length() <= length) {
            return String.format("%-" + length + "s", str);
        }
        return str.substring(0, length - 2) + "..";
    }

    public List<FoodAndDrink> getFoodAndDrinks() {
        return foodAndDrinks;
    }

    // Create new service with validation
    public boolean createNewService() {
        return ServiceDAO.createService();
    }

    // Update existing service
    public boolean updateExistingService() {
        return ServiceDAO.updateService();
    }

    // Delete service with confirmation
    public boolean deleteExistingService() {
        return ServiceDAO.deleteService();
    }

    // Display all machines in table format
    public void displayAllMachines() {
        List<Machine> machines = MachineDAO.queryDb();
        if (machines.isEmpty()) {
            System.out.println("No machines available!");
            return;
        }

        // Print table header
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                              DANH SÁCH MÁY TRẠM CYBER GAMING                                         ║");
        System.out.println("╠════╦══════════════╦════════════╦════════════════════════════════════════════════════╦═════════╦══════════════╣");
        System.out.printf("║ ID ║   MÃ MÁY     ║  KHU VỰC   ║                CẤU HÌNH                        ║ GIÁ/GIỜ ║  TRẠNG THÁI  ║%n");
        System.out.println("╠════╬══════════════╬════════════╬════════════════════════════════════════════════════╬═════════╬══════════════╣");

        // Print data rows
        for (Machine machine : machines) {
            System.out.printf("║ %-2d ║ %-12s ║ %-10s ║ %-50s ║ %7.2f ║ %-12s ║%n",
                    machine.getMachineId(),
                    truncateString(machine.getMachineCode(), 12),
                    machine.getArea().name(),
                    truncateString(machine.getConfig(), 50),
                    machine.getPricePerHour(),
                    machine.getStatusMachine().getStatusCode());
        }

        System.out.println("╚════╩══════════════╩════════════╩════════════════════════════════════════════════════╩═════════╩══════════════╝");
    }

    // Create new machine with validation
    public boolean createNewMachine() {
        return MachineDAO.createMachine();
    }

    // Update existing machine
    public boolean updateExistingMachine() {
        return MachineDAO.updateMachine();
    }

    // Delete machine with confirmation
    public boolean deleteExistingMachine() {
        return MachineDAO.deleteMachine();
    }

    // Display all services in table format
    public void displayAllServices() {
        List<Service> services = ServiceDAO.queryDb();
        if (services.isEmpty()) {
            System.out.println("No services available!");
            return;
        }

        // Print table header
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                              DANH SÁCH DỊCH VỤ CYBER GAMING                            ║");
        System.out.println("╠════╦═══════════════════╦════════════════════════════════════════════════╦═════════════╣");
        System.out.printf("║ ID ║      TÊN          ║                MÔ TẢ                         ║   GIÁ       ║%n");
        System.out.println("╠════╬═══════════════════╬════════════════════════════════════════════════╬═════════════╣");

        // Print data rows
        for (Service service : services) {
            System.out.printf("║ %-2d ║ %-17s ║ %-44s ║ %11.2f ║%n",
                    service.getServiceId(),
                    truncateString(service.getServiceName(), 17),
                    truncateString(service.getDescription(), 44),
                    service.getPrice());
        }

        System.out.println("╚════╩═══════════════════╩════════════════════════════════════════════════╩═════════════╝");
    }

    // Display deleted services in table format
    public void displayDeletedServices() {
        List<Service> deletedServices = ServiceDAO.getDeletedServices();
        if (deletedServices.isEmpty()) {
            System.out.println("Không có dịch vụ nào đã xóa!");
            return;
        }

        // Print table header
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                        DANH SÁCH DỊCH VỤ ĐÃ XÓA CYBER GAMING                            ║");
        System.out.println("╠════╦═══════════════════╦════════════════════════════════════════════════╦═════════════╣");
        System.out.printf("║ ID ║      TÊN          ║                MÔ TẢ                         ║   GIÁ       ║%n");
        System.out.println("╠════╬═══════════════════╬════════════════════════════════════════════════╬═════════════╣");

        // Print data rows
        for (Service service : deletedServices) {
            System.out.printf("║ %-2d ║ %-17s ║ %-44s ║ %11.2f ║%n",
                    service.getServiceId(),
                    truncateString(service.getServiceName(), 17),
                    truncateString(service.getDescription(), 44),
                    service.getPrice());
        }

        System.out.println("╚════╩═══════════════════╩════════════════════════════════════════════════╩═════════════╝");
    }

    public boolean restoreService() {
        return ServiceDAO.restoreService();
    }

    // Display deleted machines in table format
    public void displayDeletedMachines() {
        List<Machine> deletedMachines = MachineDAO.getDeletedMachines();
        if (deletedMachines.isEmpty()) {
            System.out.println("Không có máy trạm nào đã xóa!");
            return;
        }

        // Print table header
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                           DANH SÁCH MÁY TRẠM ĐÃ XÓA CYBER GAMING                                      ║");
        System.out.println("╠════╦══════════════╦════════════╦════════════════════════════════════════════════╦═════════╦══════════════╣");
        System.out.printf("║ ID ║   MÃ MÁY     ║  KHU VỰC   ║                CẤU HÌNH                        ║ GIÁ/GIỜ ║  TRẠNG THÁI  ║%n");
        System.out.println("╠════╬══════════════╬════════════╬════════════════════════════════════════════════╬═════════╬══════════════╣");

        // Print data rows
        for (Machine machine : deletedMachines) {
            System.out.printf("║ %-2d ║ %-12s ║ %-10s ║ %-50s ║ %7.2f ║ %-12s ║%n",
                    machine.getMachineId(),
                    truncateString(machine.getMachineCode(), 12),
                    machine.getArea().name(),
                    truncateString(machine.getConfig(), 50),
                    machine.getPricePerHour(),
                    machine.getStatusMachine().getStatusCode());
        }

        System.out.println("╚════╩══════════════╩════════════╩════════════════════════════════════════════════╩═════════╩══════════════╝");
    }

    public boolean restoreMachine() {
        return MachineDAO.restoreMachine();
    }
}
