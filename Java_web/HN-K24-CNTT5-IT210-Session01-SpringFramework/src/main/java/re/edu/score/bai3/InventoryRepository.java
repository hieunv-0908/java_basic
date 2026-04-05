package re.edu.score.bai3;

/*

Hệ thống Cyber Center cho phép người chơi gọi đồ ăn tại chỗ.

Khi user gọi món, hệ thống cần:
1. Kiểm tra kho còn món không
2. Kiểm tra tài khoản đủ tiền không
3. Nếu hợp lệ -> trừ tiền và xác nhận đơn


INPUT:
- username: tên người dùng
- foodName: tên món ăn (ví dụ: "Mì xào bò")
- quantity: số lượng
- pricePerItem: giá mỗi món

OUTPUT:
- "Đặt món thành công"
- "Món ăn đã hết hàng"
- "Tài khoản không đủ tiền"

THIẾT KẾ KIẾN TRÚC

OrderFoodService

InventoryRepository (kiểm tra kho)
UserAccountRepository (quản lý tài khoản)

NGUYÊN TẮC:
- Service KHÔNG được dùng "new"
- Phải dùng Dependency Injection
- Phụ thuộc vào interface, không phụ thuộc class cụ thể

INTERFACE (GIẢM PHỤ THUỘC)
*/

/*
Interface kho:
Service chỉ biết "lấy số lượng"
không quan tâm dữ liệu đến từ đâu (DB, API,...)
*/
interface InventoryRepository {
    int getStock(String foodName);
}

/*
Interface tài khoản:
Service chỉ biết lấy số dư và trừ tiền
*/
interface UserAccountRepository {
    double getBalance(String username);
    void deductBalance(String username, double amount);
}

/*
SERVICE CHÍNH (ÁP DỤNG DI)
*/

class OrderFoodService {

    private InventoryRepository inventoryRepo;
    private UserAccountRepository userRepo;

    /*
    Constructor Injection:

    Sai:
        this.inventoryRepo = new InventoryRepository();

    Vì:
    - Gây tight coupling (phụ thuộc chặt)
    - Không thể thay đổi implementation
    - Vi phạm nguyên lý IoC

    Đúng:
    - Nhận dependency từ bên ngoài

    => Đây là Dependency Injection (DI)
    => Spring sẽ inject vào thông qua ApplicationContext
    */
    public OrderFoodService(InventoryRepository inventoryRepo,
                            UserAccountRepository userRepo) {
        this.inventoryRepo = inventoryRepo;
        this.userRepo = userRepo;
    }

    /*
    LUỒNG XỬ LÝ LOGIC

    Bước 1: Kiểm tra kho
    Bước 2: Nếu hết -> báo lỗi
    Bước 3: Tính tiền
    Bước 4: Kiểm tra số dư
    Bước 5: Nếu không đủ tiền -> báo lỗi
    Bước 6: Trừ tiền
    Bước 7: Trả kết quả thành công
    */

    public String orderFood(String username, String foodName, int quantity, double pricePerItem) {

        /*
        VALIDATE INPUT (có thể mở rộng)
        */
        if (quantity <= 0) {
            return "Số lượng không hợp lệ";
        }

        /*
        BƯỚC 1: KIỂM TRA KHO
        */
        int stock = inventoryRepo.getStock(foodName);

        /*
        BẪY DỮ LIỆU 1: HẾT HÀNG
        */
        if (stock <= 0 || stock < quantity) {
            return "Món ăn đã hết hàng";
        }

        /*
        BƯỚC 2: TÍNH TIỀN
        */
        double total = quantity * pricePerItem;

        /*
        BƯỚC 3: KIỂM TRA SỐ DƯ
        */
        double balance = userRepo.getBalance(username);

        /*
        BẪY DỮ LIỆU 2: KHÔNG ĐỦ TIỀN
        */
        if (balance < total) {
            return "Tài khoản không đủ tiền";
        }

        /*
        BƯỚC 4: TRỪ TIỀN

        Chỉ thực hiện khi tất cả điều kiện hợp lệ
        */
        userRepo.deductBalance(username, total);

        /*
        BƯỚC 5: (CÓ THỂ MỞ RỘNG)
        - Trừ kho
        - Lưu đơn hàng
        - Ghi log

        inventoryRepo.reduceStock(foodName, quantity);
        */

        return "Đặt món thành công cho " + username;
    }
}

/*
GIẢI THÍCH IoC + DI

IoC (Inversion of Control):
- Thay vì class tự tạo object
- Quyền tạo object được chuyển cho framework (Spring)

DI (Dependency Injection):
- Framework sẽ "tiêm" dependency vào class

Ví dụ:
OrderFoodService không tự tạo repository
mà nhận từ bên ngoài

=> Giảm phụ thuộc, tăng linh hoạt

LỢI ÍCH THIẾT KẾ NÀY

- Dễ thay đổi repository (DB -> API)
- Dễ test (mock dữ liệu)
- Dễ mở rộng
- Code rõ ràng, tách biệt trách nhiệm

Kết luận

- Không dùng new trong Service
- Luôn phụ thuộc vào interface
- Dùng DI để đảm bảo Loose Coupling
- Xử lý lỗi rõ ràng theo luồng logic
*/