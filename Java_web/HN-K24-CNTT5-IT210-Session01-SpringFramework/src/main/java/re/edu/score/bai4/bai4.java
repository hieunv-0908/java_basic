package re.edu.score.bai4;

public class bai4 {

}

/*
==================================================
1. MỤC TIÊU
==================================================

- Hiểu và so sánh các kỹ thuật Dependency Injection (DI)
- Đánh giá trade-off giữa Constructor Injection và Field Injection
- Biết lựa chọn giải pháp phù hợp với hệ thống thực tế

==================================================
2. BỐI CẢNH
==================================================

Hệ thống Cyber Center cần gửi thông báo sau khi trừ tiền thành công.

Có 2 hình thức:
- Email
- SMS

Service chính: NotificationService
Phụ thuộc vào:
- EmailSender
- SmsSender

==================================================
3. VẤN ĐỀ
==================================================

Có nhiều cách tiêm dependency:
1. Constructor Injection
2. Field Injection
3. Setter Injection

Cần chọn giải pháp tối ưu nhất.

==================================================
4. BẪY DỮ LIỆU
==================================================

- Dịch vụ SMS có thể bị lỗi mạng giữa chừng
- Hệ thống cần:
    + Không crash
    + Có thể fallback sang Email

==================================================
5. ĐỊNH NGHĨA INTERFACE
==================================================
*/

interface EmailSender {
    void send(String message);
}

interface SmsSender {
    void send(String message);
}

/*
==================================================
6. GIẢI PHÁP 1: CONSTRUCTOR INJECTION (KHUYẾN NGHỊ)
==================================================
*/

class NotificationServiceConstructor {

    private final EmailSender emailSender;
    private final SmsSender smsSender;

    /*
    Constructor Injection:

    - Dependency được truyền qua constructor
    - Đảm bảo object luôn đầy đủ dependency khi khởi tạo
    - Có thể dùng "final" -> tăng tính an toàn
    */
    public NotificationServiceConstructor(EmailSender emailSender, SmsSender smsSender) {
        this.emailSender = emailSender;
        this.smsSender = smsSender;
    }

    /*
    XỬ LÝ GỬI THÔNG BÁO

    Có xử lý bẫy dữ liệu:
    - Nếu SMS lỗi -> fallback sang Email
    */
    public void notifyUser(String message) {
        try {
            smsSender.send(message);
            System.out.println("Gửi SMS thành công");
        } catch (Exception e) {
            System.out.println("SMS lỗi, chuyển sang Email");
            emailSender.send(message);
        }
    }
}

/*
==================================================
7. GIẢI PHÁP 2: FIELD INJECTION
==================================================
*/

class NotificationServiceField {

    /*
    Field Injection:

    - Spring sẽ inject trực tiếp vào biến
    - Code ngắn hơn nhưng có nhiều hạn chế
    */

    // @Autowired (giả lập)
    private EmailSender emailSender;

    // @Autowired (giả lập)
    private SmsSender smsSender;

    public void notifyUser(String message) {
        try {
            smsSender.send(message);
            System.out.println("Gửi SMS thành công");
        } catch (Exception e) {
            System.out.println("SMS lỗi, chuyển sang Email");
            emailSender.send(message);
        }
    }
}

/*
==================================================
8. BẢNG SO SÁNH
==================================================

| Tiêu chí | Constructor Injection | Field Injection |
|----------|---------------------|-----------------|
| Rõ dependency | Rõ ràng qua constructor | Ẩn |
| Test | Dễ mock | Khó |
| An toàn | Không null | Có thể null |
| final | Có thể dùng | Không |
| Dễ bảo trì | Cao | Thấp |
| Xử lý lỗi (SMS fail) | Dễ test và kiểm soát | Khó |

==================================================
9. PHÂN TÍCH
==================================================

- Constructor Injection:
    + Rõ ràng dependency
    + Dễ test (có thể giả lập SmsSender lỗi)
    + Phù hợp xử lý tình huống mạng lỗi

- Field Injection:
    + Code ngắn
    + Nhưng khó kiểm soát và khó test
    + Không phù hợp hệ thống lớn

==================================================
10. KẾT LUẬN
==================================================

Chọn: Constructor Injection

Lý do:
- Đảm bảo đầy đủ dependency ngay từ đầu
- Dễ test các trường hợp lỗi (ví dụ SMS bị lỗi mạng)
- Code rõ ràng, dễ hiểu
- Phù hợp thiết kế chuẩn của Spring

==================================================
TÓM TẮT:

Không nên dùng Field Injection trong hệ thống lớn.
Constructor Injection là lựa chọn tốt nhất vì an toàn và dễ mở rộng.
==================================================
*/
