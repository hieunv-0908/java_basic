package re.edu.score.bai1;

/*
VẤN ĐỀ:

Hệ thống nạp tiền đang bị giới hạn chỉ sử dụng một cổng thanh toán nội bộ.
Khi khách hàng muốn thêm Momo hoặc ZaloPay thì không thể mở rộng dễ dàng.


ĐOẠN CODE SAI:
*/

class RechargeService {

    private PaymentGateway gateway;

    public RechargeService() {
        // Sai ở đây: tự khởi tạo phụ thuộc (hard-code)
        this.gateway = new InternalPaymentGateway();
    }

    public void processRecharge(String username, double amount) {
        gateway.pay(amount);
        System.out.println("Nạp " + amount + " cho " + username);
    }
}

/*

NGUYÊN NHÂN SAI:

Dòng code:
    this.gateway = new InternalPaymentGateway();

=> Đây là "tight coupling" (liên kết chặt)

RechargeService đang phụ thuộc trực tiếp vào InternalPaymentGateway

Hệ quả:
- Không thể thay đổi sang Momo, ZaloPay mà không sửa code
- Vi phạm nguyên lý IoC (Inversion of Control)
- Khó mở rộng và bảo trì khi hệ thống lớn lên

GIẢI PHÁP:

Áp dụng Dependency Injection (DI) để Spring quản lý dependency.

Bước 1: Tạo interface chung
*/

interface PaymentGateway {
    void pay(double amount);
}

/*
Bước 2: Các implementation cụ thể
*/

class InternalPaymentGateway implements PaymentGateway {
    public void pay(double amount) {
        System.out.println("Thanh toán bằng cổng nội bộ: " + amount);
    }
}

class MomoPaymentGateway implements PaymentGateway {
    public void pay(double amount) {
        System.out.println("Thanh toán bằng Momo: " + amount);
    }
}

/*
Bước 3: Inject dependency thay vì tự tạo
*/

class RechargeServiceFixed {

    private PaymentGateway gateway;

    // Inject từ bên ngoài (Constructor Injection)
    public RechargeServiceFixed(PaymentGateway gateway) {
        this.gateway = gateway;
    }

    public void processRecharge(String username, double amount) {
        gateway.pay(amount);
        System.out.println("Nạp " + amount + " cho " + username);
    }
}

/*

GIẢI THÍCH GIẢI PHÁP:

- RechargeService không còn tự tạo PaymentGateway nữa
- Quyền tạo object được chuyển cho bên ngoài (IoC Container)

=> Đây chính là Inversion of Control:
   thay vì class tự điều khiển dependency,
   thì framework (Spring) sẽ quản lý và inject vào

Lợi ích:
- Dễ thay đổi cổng thanh toán (chỉ cần đổi implementation)
- Không cần sửa code RechargeService
- Giảm phụ thuộc, tăng tính linh hoạt (loose coupling)

KẾT LUẬN:

Không nên tự khởi tạo dependency bằng "new" trong class,
vì sẽ gây liên kết chặt và khó mở rộng.

Nên sử dụng Dependency Injection để tách rời các thành phần
và giúp hệ thống linh hoạt hơn.
*/
