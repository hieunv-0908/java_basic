package interfaceRole;

import dao.BookingDAO;
import dao.FoodAndDrinkDAO;
import dao.MachineDAO;
import dao.OrderDAO;
import dao.OrderFoodItemDAO;
import model.Area;
import model.Booking;
import model.FoodAndDrink;
import model.Machine;
import model.Order;
import model.OrderFoodItem;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Customer;

public class InterfaceCustomer {
    private static Scanner scanner = new Scanner(System.in);

    public static boolean handleCustomerMenu(Customer customer) {
        System.out.println("\n=== WELCOME " + customer.getFullName().toUpperCase() + " ===");
        System.out.println("Role: " + customer.getRole());
        System.out.println("1. View Profile");
        System.out.println("2. Book Machine and Order Food");
        System.out.println("3. View Products");
        System.out.println("4. View My Order Status");
        System.out.println("5. Logout");

        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                viewProfile(customer);
                return true;
            case 2:
                bookMachineAndOrderFood(customer);
                return true;
            case 3:
                viewProducts();
                return true;
            case 4:
                viewMyOrderStatus(customer);
                return true;
            case 5:
                System.out.println("Logged out successfully!");
                return false;
            default:
                System.out.println("Invalid choice!");
                return true;
        }
    }

    private static void viewProducts() {
        List<FoodAndDrink> menu = FoodAndDrinkDAO.queryDb();
        System.out.println("\n=== FOOD AND DRINK MENU ===");
        System.out.printf("%-5s %-20s %-30s %-10s %-10s%n", "ID", "Name", "Description", "Price", "Stock");
        for (FoodAndDrink f : menu) {
            System.out.printf("%-5d %-20s %-30s %-10.0f %-10d%n", f.getFoodAndDrinkId(), f.getName(), f.getDescription(), f.getPrice(), f.getStock());
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

    private static void bookMachineAndOrderFood(Customer customer) {
        System.out.println("\n=== BOOK MACHINE AND ORDER FOOD ===");

        // Step 1: Display available machines by area
        System.out.println("Available machines by area:");
        for (Area area : Area.values()) {
            List<Machine> machines = MachineDAO.getAvailableMachinesByArea(area);
            if (!machines.isEmpty()) {
                System.out.println("\n" + area + ":");
                System.out.printf("%-5s %-10s %-20s %-10s%n", "ID", "Code", "Config", "Price/Hour");
                for (Machine m : machines) {
                    System.out.printf("%-5d %-10s %-20s %-10.0f%n", m.getMachineId(), m.getMachineCode(), m.getConfig(), m.getPricePerHour());
                }
            }
        }

        // Step 2: Select area
        Area selectedArea = null;
        do {
            System.out.print("Select area (STANDARD/VIP/STREAM): ");
            String areaInput = scanner.nextLine().trim().toUpperCase();
            try {
                selectedArea = Area.fromArea(areaInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid area!");
            }
        } while (selectedArea == null);

        // Step 3: Select machine
        List<Machine> availableMachines = MachineDAO.getAvailableMachinesByArea(selectedArea);
        if (availableMachines.isEmpty()) {
            System.out.println("No available machines in this area.");
            return;
        }

        Machine selectedMachine = null;
        do {
            System.out.print("Enter machine ID: ");
            try {
                int machineId = Integer.parseInt(scanner.nextLine());
                for (Machine m : availableMachines) {
                    if (m.getMachineId() == machineId) {
                        selectedMachine = m;
                        break;
                    }
                }
                if (selectedMachine == null) {
                    System.out.println("Invalid machine ID!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
            }
        } while (selectedMachine == null);

        // Display booked time slots for selected machine
        System.out.println("\n=== BOOKED TIME SLOTS FOR MACHINE " + selectedMachine.getMachineCode() + " ===");
        List<Booking> bookedSlots = BookingDAO.getBookingsByMachineId(selectedMachine.getMachineId());
        if (bookedSlots.isEmpty()) {
            System.out.println("No bookings on this machine. All time is available!");
        } else {
            System.out.printf("%-15s %-15s %-10s%n", "Start Time", "End Time", "Status");
            for (Booking b : bookedSlots) {
                System.out.printf("%-15s %-15s %-10s%n",
                        b.getStartTime(),
                        b.getEndTime(),
                        b.getStatus());
            }
        }

        // Step 4: Enter start and end time
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        do {
            System.out.print("Enter start time (yyyy-MM-dd HH:mm): ");
            String startInput = scanner.nextLine();
            try {
                startInput = startInput.replace("/", "-");
                startTime = LocalDateTime.parse(startInput, formatter);
                if (startTime.isBefore(LocalDateTime.now())) {
                    System.out.println("Start time must be in the future!");
                    startTime = null;
                }
            } catch (Exception e) {
                System.out.println("Invalid format!");
            }
        } while (startTime == null);

        do {
            System.out.print("Enter end time (yyyy-MM-dd HH:mm): ");
            String endInput = scanner.nextLine();
            try {
                endInput = endInput.replace("/", "-");
                endTime = LocalDateTime.parse(endInput, formatter);
                if (endTime.isBefore(startTime) || endTime.isEqual(startTime)) {
                    System.out.println("End time must be after start time!");
                    endTime = null;
                }
            } catch (Exception e) {
                System.out.println("Invalid format!");
            }
        } while (endTime == null);

        // Step 5: Check availability
        if (!BookingDAO.isMachineAvailable(selectedMachine.getMachineId(), Timestamp.valueOf(startTime), Timestamp.valueOf(endTime))) {
            System.out.println("Machine is not available for the selected time!");
            return;
        }

        // Step 6: Display F&B menu
        List<FoodAndDrink> menu = FoodAndDrinkDAO.queryDb();
        System.out.println("\n=== FOOD AND DRINK MENU ===");
        System.out.printf("%-5s %-20s %-30s %-10s %-10s%n", "ID", "Name", "Description", "Price", "Stock");
        for (FoodAndDrink f : menu) {
            System.out.printf("%-5d %-20s %-30s %-10.0f %-10d%n", f.getFoodAndDrinkId(), f.getName(), f.getDescription(), f.getPrice(), f.getStock());
        }

        // Step 7: Order F&B
        double foodTotal = 0;
        List<OrderFoodItem> orderedItems = new ArrayList<>();
        while (true) {
            System.out.print("Enter food/drink ID to order (0 to finish): ");
            try {
                int foodId = Integer.parseInt(scanner.nextLine());
                if (foodId == 0) break;
                FoodAndDrink selectedFood = null;
                for (FoodAndDrink f : menu) {
                    if (f.getFoodAndDrinkId() == foodId) {
                        selectedFood = f;
                        break;
                    }
                }
                if (selectedFood == null) {
                    System.out.println("Invalid food ID!");
                    continue;
                }
                System.out.print("Enter quantity: ");
                int quantity = Integer.parseInt(scanner.nextLine());
                if (quantity <= 0 || quantity > selectedFood.getStock()) {
                    System.out.println("Invalid quantity!");
                    continue;
                }
                double itemTotal = selectedFood.getPrice() * quantity;
                foodTotal += itemTotal;
                orderedItems.add(new OrderFoodItem(0, 0, selectedFood.getFoodAndDrinkId(), quantity, selectedFood.getPrice()));
                System.out.println("Added " + quantity + " x " + selectedFood.getName() + " = " + itemTotal);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
            }
        }

        // Step 8: Calculate totals
        double hours = java.time.Duration.between(startTime, endTime).toMinutes() / 60.0;
        double machineTotal = selectedMachine.getPricePerHour() * Math.ceil(hours);
        double totalAmount = machineTotal + foodTotal;

        // Step 9: Create booking
        boolean bookingCreated = BookingDAO.createBooking(customer.getUserId(), selectedMachine.getMachineId(), Timestamp.valueOf(startTime), Timestamp.valueOf(endTime));
        if (!bookingCreated) {
            System.out.println("Failed to create booking!");
            return;
        }
        int bookingId = BookingDAO.getLastInsertedId();

        // Step 10: Create order
        boolean orderCreated = OrderDAO.createOrder(customer.getUserId(), bookingId, totalAmount);
        if (!orderCreated) {
            System.out.println("Failed to create order!");
            return;
        }
        int orderId = OrderDAO.getLastInsertedId();

        // Step 11: Add order food items
        for (OrderFoodItem item : orderedItems) {
            item.setOrderId(orderId);
            OrderFoodItemDAO.createOrderFoodItem(orderId, item.getFoodAndDrinkId(), item.getQuantity(), item.getPrice());
        }

        // Step 12: Print invoice
        System.out.println("\n=== BOOKING AND ORDER SUCCESSFUL ===");
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Order ID: " + orderId);
        System.out.println("Machine: " + selectedMachine.getMachineCode() + " (" + selectedArea + ")");
        System.out.println("Start Time: " + startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        System.out.println("End Time: " + endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        System.out.println("Duration: " + String.format("%.2f", hours) + " hours");
        System.out.println("Machine Cost: " + String.format("%.2f", machineTotal));
        System.out.println("Food Cost: " + String.format("%.2f", foodTotal));
        System.out.println("Total Amount: " + String.format("%.2f", totalAmount));
        System.out.println("Status: Pending Confirmation");
    }

    private static void viewMyOrderStatus(Customer customer) {
        System.out.println("\n=== ĐƠN HÀNG CỦA TÔI ===");
        List<Order> orders = OrderDAO.getOrdersByUserId(customer.getUserId());
        if (orders.isEmpty()) {
            System.out.println("Bạn chưa có đơn hàng nào.");
        } else {
            System.out.printf("%-5s | %-12s | %-15s | %-15s | %-20s%n", "ID", "Booking", "Tổng Tiền", "Trạng Thái", "Thời Gian Tạo");
            System.out.println("-".repeat(80));
            for (Order o : orders) {
                String bookingId = (o.getBookingId() != null) ? o.getBookingId().toString() : "N/A";
                System.out.printf("%-5d | %-12s | %-15.2f | %-15s | %-20s%n",
                        o.getOrderId(),
                        bookingId,
                        o.getTotalAmount(),
                        o.getStatus(),
                        o.getCreatedAt());
            }

            // Cho phép xem chi tiết từng đơn hàng
            System.out.print("\nNhập ID đơn hàng để xem chi tiết (0 để quay lại): ");
            int orderId = scanner.nextInt();
            scanner.nextLine();

            if (orderId > 0) {
                viewOrderDetails(customer.getUserId(), orderId);
            }
        }
    }

    private static void viewOrderDetails(int userId, int orderId) {
        Order order = OrderDAO.getOrderById(orderId);
        if (order == null || order.getUserId() != userId) {
            System.out.println("Đơn hàng không tìm thấy hoặc không phải của bạn!");
            return;
        }

        System.out.println("\n" + "=".repeat(80));
        System.out.println("CHI TIẾT ĐƠN HÀNG #" + orderId);
        System.out.println("=".repeat(80));
        System.out.println("Ngày tạo: " + order.getCreatedAt());
        System.out.println("Trạng thái: " + getStatusDisplay(order.getStatus().getStatusCode()));

        if (order.getBookingId() != null) {
            Booking booking = BookingDAO.getBookingById(order.getBookingId());
            if (booking != null) {
                Machine machine = MachineDAO.getMachineById(booking.getMachineId());
                String machineCode = (machine != null) ? machine.getMachineCode() : "N/A";
                System.out.println("\n### Thông tin Booking Máy ###");
                System.out.println("Booking ID: " + booking.getBookingId());
                System.out.println("Máy: " + machineCode);
                System.out.println("Thời gian bắt đầu: " + booking.getStartTime());
                System.out.println("Thời gian kết thúc: " + booking.getEndTime());
                System.out.println("Trạng thái booking: " + booking.getStatus());
            }
        }

        List<OrderFoodItem> foodItems = OrderFoodItemDAO.getOrderFoodItems(orderId);
        if (!foodItems.isEmpty()) {
            System.out.println("\n### Danh sách F&B đã đặt ###");
            System.out.printf("%-5s | %-25s | %-10s | %-10s | %-10s%n", "ID", "Tên F&B", "Số Lượng", "Giá", "Thành Tiền");
            System.out.println("-".repeat(70));
            for (OrderFoodItem item : foodItems) {
                FoodAndDrink food = FoodAndDrinkDAO.getFoodAndDrinkById(item.getFoodAndDrinkId());
                String foodName = (food != null) ? food.getName() : "Unknown";
                double subTotal = item.getPrice() * item.getQuantity();
                System.out.printf("%-5d | %-25s | %-10d | %-10.2f | %-10.2f%n",
                        item.getId(),
                        foodName.length() > 25 ? foodName.substring(0, 25) : foodName,
                        item.getQuantity(),
                        item.getPrice(),
                        subTotal);
            }
        }

        System.out.println("\n### Tổng cộng ###");
        System.out.println("Tổng tiền: " + String.format("%.2f", order.getTotalAmount()) + " VND");
    }

    private static String getStatusDisplay(String status) {
        switch (status) {
            case "PENDING":
                return "PENDING (Chờ xác nhận)";
            case "PREPARING":
                return "PREPARING (Đang phục vụ)";
            case "DONE":
                return "DONE (✓ Hoàn thành)";
            default:
                return status;
        }
    }
}
