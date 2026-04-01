package interfaceRole;

import dao.BookingDAO;
import dao.UserDAO;
import dao.FoodAndDrinkDAO;
import dao.MachineDAO;
import dao.OrderDAO;
import dao.OrderFoodItemDAO;
import model.Booking;
import model.FoodAndDrink;
import model.Machine;
import model.Order;
import model.OrderFoodItem;
import model.OrderStatus;
import model.Staff;
import model.User;
import ultil.ConsoleUI;
import ultil.InputValidator;

import java.util.List;

public class InterfaceStaff {

    public static boolean handleStaffMenu(Staff staff) {
        while (true) {
            try {
                ConsoleUI.printMenu("CHÀO MỪNG " + staff.getFullName().toUpperCase(),
                    new String[]{
                        "Role: " + staff.getRole(),
                        "",
                        "1. Xem thông tin cá nhân",
                        "2. Xem danh sách đặt máy & đơn hàng chờ",
                        "3. Cập nhật trạng thái đơn hàng",
                        "4. Xem chi tiết đơn hàng",
                        "0. Đăng xuất"
                    }
                );

                int choice = InputValidator.readChoice("Chọn thao tác: ", 0, 4);

                switch (choice) {
                    case 1:
                        viewProfile(staff);
                        break;
                    case 2:
                        viewPendingBookingsAndOrders();
                        break;
                    case 3:
                        updateOrderStatusMenu();
                        break;
                    case 4:
                        viewOrderDetails();
                        break;
                    case 0:
                        ConsoleUI.printSuccess("Đăng xuất thành công!");
                        return false;
                }
            } catch (Exception e) {
                ConsoleUI.printError("Lỗi: " + e.getMessage());
                InputValidator.pause();
            }
        }
    }

    private static void viewProfile(Staff staff) {
        try {
            ConsoleUI.printSubHeader("THÔNG TIN CÁ NHÂN");
            System.out.println("Họ tên:      " + staff.getFullName());
            System.out.println("Tuổi:        " + staff.getAge());
            System.out.println("Email:       " + staff.getEmail());
            System.out.println("Phone:       " + staff.getPhone());
            System.out.println("Vai trò:     " + staff.getRole());
            System.out.println("Số dư:       " + ConsoleUI.formatCurrency(staff.getBalance()));
            System.out.println("Tạo lúc:     " + new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(staff.getCreated_at()));
            InputValidator.pause();
        } catch (Exception e) {
            ConsoleUI.printError("Lỗi khi xem thông tin: " + e.getMessage());
            InputValidator.pause();
        }
    }

    private static void viewPendingBookingsAndOrders() {
        try {
            ConsoleUI.printHeader("DANH SÁCH ĐẶT MÁY VÀ ĐƠN HÀNG ĐANG CHỜ XỬ LÝ");
            
            ConsoleUI.printSection("DANH SÁCH YÊU CẦU ĐẶT MÁY ĐANG CHỜ");
            List<Booking> bookings = BookingDAO.getPendingBookings();
            if (bookings.isEmpty()) {
                ConsoleUI.printInfo("Không có yêu cầu đặt máy nào.");
            } else {
                System.out.printf("%-5s | %-20s | %-15s | %-19s | %-19s | %-10s%n", 
                    "ID", "Khách Hàng", "Mã Máy", "Bắt Đầu", "Kết Thúc", "Trạng Thái");
                ConsoleUI.printSeparator();
                for (Booking b : bookings) {
                    User user = UserDAO.getUserById(b.getUserId());
                    String userName = (user != null && user.getFullName() != null) ? user.getFullName() : "Unknown";
                    
                    Machine machine = MachineDAO.getMachineById(b.getMachineId());
                    String machineCode = (machine != null) ? machine.getMachineCode() : "N/A";
                    
                    System.out.printf("%-5d | %-20s | %-15s | %-19s | %-19s | %-10s%n",
                            b.getBookingId(),
                            userName.length() > 20 ? userName.substring(0, 20) : userName,
                            machineCode,
                            b.getStartTime(),
                            b.getEndTime(),
                            b.getStatus());
                }
            }

            ConsoleUI.printSection("DANH SÁCH ĐƠN HÀNG ĐANG CHỜ XỬ LÝ");
            List<Order> orders = OrderDAO.getPendingOrders();
            if (orders.isEmpty()) {
                ConsoleUI.printInfo("Không có đơn hàng nào đang chờ.");
            } else {
                System.out.printf("%-5s | %-20s | %-12s | %-15s | %-12s | %-20s%n", 
                    "ID", "Khách Hàng", "Booking", "Tổng Tiền", "Trạng Thái", "Thời Gian");
                ConsoleUI.printSeparator();
                for (Order o : orders) {
                    User user = UserDAO.getUserById(o.getUserId());
                    String userName = (user != null && user.getFullName() != null) ? user.getFullName() : "Unknown";
                    String bookingId = (o.getBookingId() != null) ? o.getBookingId().toString() : "N/A";
                    System.out.printf("%-5d | %-20s | %-12s | %-15.2f | %-12s | %-20s%n",
                            o.getOrderId(),
                            userName.length() > 20 ? userName.substring(0, 20) : userName,
                            bookingId,
                            o.getTotalAmount(),
                            o.getStatus(),
                            o.getCreatedAt());
                }
            }
            InputValidator.pause();
        } catch (Exception e) {
            ConsoleUI.printError("Lỗi khi lấy danh sách: " + e.getMessage());
            InputValidator.pause();
        }
    }

    private static void viewOrderDetails() {
        try {
            ConsoleUI.printSubHeader("XEM CHI TIẾT ĐƠN HÀNG");
            
            int orderId = InputValidator.readPositiveInt("Nhập ID đơn hàng: ");

            Order order = OrderDAO.getOrderById(orderId);
            if (order == null) {
                ConsoleUI.printError("Đơn hàng không tìm thấy!");
                InputValidator.pause();
                return;
            }

            User user = UserDAO.getUserById(order.getUserId());
            String userName = (user != null && user.getFullName() != null) ? user.getFullName() : "Unknown";

            ConsoleUI.printHeader("CHI TIẾT ĐƠN HÀNG #" + orderId);
            System.out.println("Khách hàng: " + userName);
            System.out.println("Phone: " + (user != null ? user.getPhone() : "N/A"));
            System.out.println("Ngày tạo: " + order.getCreatedAt());
            System.out.println("Trạng thái: " + order.getStatus());

            if (order.getBookingId() != null) {
                try {
                    Booking booking = BookingDAO.getBookingById(order.getBookingId());
                    if (booking != null) {
                        Machine machine = MachineDAO.getMachineById(booking.getMachineId());
                        String machineCode = (machine != null) ? machine.getMachineCode() : "N/A";
                        ConsoleUI.printSection("THÔNG TIN BOOKING");
                        System.out.println("Booking ID: " + booking.getBookingId());
                        System.out.println("Máy: " + machineCode);
                        System.out.println("Thời gian bắt đầu: " + booking.getStartTime());
                        System.out.println("Thời gian kết thúc: " + booking.getEndTime());
                        System.out.println("Trạng thái: " + booking.getStatus());
                    }
                } catch (Exception e) {
                    ConsoleUI.printWarning("Lỗi lấy thông tin booking: " + e.getMessage());
                }
            }

            try {
                List<OrderFoodItem> foodItems = OrderFoodItemDAO.getOrderFoodItems(orderId);
                if (!foodItems.isEmpty()) {
                    ConsoleUI.printSection("DANH SÁCH F&B ĐÃ ĐẶT");
                    System.out.printf("%-5s | %-25s | %-10s | %-10s | %-10s%n", "ID", "Tên F&B", "Số Lượng", "Giá", "Thành Tiền");
                    ConsoleUI.printSeparator();
                    for (OrderFoodItem item : foodItems) {
                        try {
                            FoodAndDrink food = FoodAndDrinkDAO.getFoodAndDrinkById(item.getFoodAndDrinkId());
                            String foodName = (food != null) ? food.getName() : "Unknown";
                            double subTotal = item.getPrice() * item.getQuantity();
                            System.out.printf("%-5d | %-25s | %-10d | %-10.2f | %-10.2f%n",
                                    item.getId(),
                                    foodName.length() > 25 ? foodName.substring(0, 25) : foodName,
                                    item.getQuantity(),
                                    item.getPrice(),
                                    subTotal);
                        } catch (Exception e) {
                            ConsoleUI.printWarning("Lỗi hiển thị F&B: " + e.getMessage());
                        }
                    }
                }
            } catch (Exception e) {
                ConsoleUI.printWarning("Lỗi lấy danh sách F&B: " + e.getMessage());
            }

            System.out.println();
            System.out.println("Tổng tiền: " + ConsoleUI.formatCurrency(order.getTotalAmount()));
            InputValidator.pause();
        } catch (Exception e) {
            ConsoleUI.printError("Lỗi: " + e.getMessage());
            InputValidator.pause();
        }
    }

    private static void updateOrderStatusMenu() {
        try {
            ConsoleUI.printSubHeader("CẬP NHẬT TRẠNG THÁI ĐƠN HÀNG");
            
            int orderId = InputValidator.readPositiveInt("Nhập ID đơn hàng: ");

            Order order = OrderDAO.getOrderById(orderId);
            if (order == null) {
                ConsoleUI.printError("Đơn hàng không tìm thấy!");
                InputValidator.pause();
                return;
            }

            System.out.println();
            System.out.println("Trạng thái hiện tại: " + ConsoleUI.getStatusBadge(order.getStatus().getStatusCode()));
            System.out.println();
            
            ConsoleUI.printMenu("CHỌN TRẠNG THÁI MỚI",
                new String[]{
                    "1. " + ConsoleUI.getStatusBadge("PENDING"),
                    "2. " + ConsoleUI.getStatusBadge("PREPARING"),
                    "3. " + ConsoleUI.getStatusBadge("DONE"),
                    "0. Quay lại"
                }
            );
            
            int statusChoice = InputValidator.readChoice("Chọn trạng thái: ", 0, 3);

            if (statusChoice == 0) {
                return;
            }

            String newStatus = "";
            String statusDisplay = "";
            switch (statusChoice) {
                case 1:
                    newStatus = "PENDING";
                    statusDisplay = "Chờ xác nhận";
                    break;
                case 2:
                    newStatus = "PREPARING";
                    statusDisplay = "Đang phục vụ";
                    break;
                case 3:
                    newStatus = "DONE";
                    statusDisplay = "Hoàn thành";
                    break;
            }

            if (OrderDAO.updateOrderStatus(orderId, newStatus)) {
                ConsoleUI.printSuccess("Cập nhật trạng thái đơn hàng #" + orderId + " thành: " + statusDisplay);
            } else {
                ConsoleUI.printError("Cập nhật thất bại!");
            }
            InputValidator.pause();
        } catch (Exception e) {
            ConsoleUI.printError("Lỗi: " + e.getMessage());
            InputValidator.pause();
        }
    }
}
