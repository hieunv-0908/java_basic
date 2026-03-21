import service.InvalidOrderIdException;
import service.InsufficientStockException;
import model.*;
import service.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        MenuService menuService = new MenuService();
        OrderService orderService = new OrderService();

        // Tạo sẵn vài món ăn
        Food burger = new Food("F1", "Burger", 20, "FastFood");
        Drink cola = new Drink("D1", "Coca", 10, "L");
        Dessert cake = new Dessert("DS1", "Cake", 15, 5);

        menuService.addMenuItem(burger);
        menuService.addMenuItem(cola);
        menuService.addMenuItem(cake);

        Order currentOrder = null;

        int choice;

        do {

            System.out.println("\n===== FAST FOOD MANAGEMENT =====");
            System.out.println("1. Show Menu");
            System.out.println("2. Create Order");
            System.out.println("3. Add Item to Order");
            System.out.println("4. Calculate Total");
            System.out.println("5. Show Revenue");
            System.out.println("0. Exit");

            System.out.print("Choose: ");
            choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    System.out.println("\n--- MENU ---");

                    for (MenuItem item : menuService.getAllMenuItems()) {
                        System.out.println(
                                item.getId() + " | "
                                        + item.getName() + " | "
                                        + item.getPrice()
                        );
                    }
                    break;

                case 2:

                    currentOrder = orderService.createOrder();
                    System.out.println("Order created: " + currentOrder.getId());
                    break;

                case 3:

                    if (currentOrder == null) {
                        System.out.println("Create order first!");
                        break;
                    }

                    System.out.print("Enter item id: ");
                    String id = scanner.next();

                    System.out.print("Enter quantity: ");
                    int qty = scanner.nextInt();

                    MenuItem selected = null;

                    for (MenuItem item : menuService.getAllMenuItems()) {
                        if (item.getId().equals(id)) {
                            selected = item;
                        }
                    }

                    try {

                        orderService.addItemToOrder(currentOrder.getId(), selected, qty);

                        System.out.println("Item added!");

                    } catch (InvalidOrderIdException e) {

                        System.out.println("Order Error: " + e.getMessage());

                    } catch (InsufficientStockException e) {

                        System.out.println("Stock Error: " + e.getMessage());

                    } finally {

                        System.out.println("Add item operation finished.");

                    }

                    break;

                case 4:

                    if (currentOrder == null) {
                        System.out.println("No order yet.");
                        break;
                    }

                    double total = currentOrder.calculateTotal();

                    System.out.println("Total price: " + total);

                    break;

                case 5:

                    ReportService reportService =
                            new ReportService(orderService.getOrders());

                    double revenue = reportService.calculateTotalRevenue();

                    System.out.println("Total Revenue: " + revenue);

                    break;

                case 0:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice!");

            }

        } while (choice != 0);

    }
}