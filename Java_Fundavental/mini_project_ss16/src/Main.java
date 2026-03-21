import java.util.*;

/*
Mô tả
Viết chương trình Java quản lý danh sách sản phẩm trong cửa hàng
bằng Java Collections Framework.
Hệ thống cần lưu trữ nhiều sản phẩm, thực hiện các thao tác thêm, xóa,
tìm kiếm, sắp xếp và thống kê.
YÊU CẦU KỸ THUẬT
1. Thiết kế Interface và Abstract Class (2.0 điểm)
a) Interface IRepository<T>
Chứa các phương thức:
● boolean add(T item) – Thêm phần tử vào danh sách
● boolean removeById(String id) – Xóa theo mã
● T findById(String id) – Tìm theo mã
● List<T> findAll() – Lấy toàn bộ danh sách
b) Abstract Class Product
Thuộc tính (private hoặc protected):
● id – Mã sản phẩm
● name – Tên sản phẩm
● price – Giá sản phẩm
Constructor:
● Khởi tạo đầy đủ 3 thuộc tính trên
Phương thức abstract:
● double calculateFinalPrice() – Tính giá thực tế của sản phẩm
Phương thức thường:
● void displayInfo() – In ra Mã, Tên, Giá gốc
2. Thiết kế các lớp con (4.0 điểm)
a) Class ElectronicProduct (Kế thừa Product)
Thuộc tính riêng:
● int warrantyMonths – Số tháng bảo hành
Override calculateFinalPrice():
● Nếu warrantyMonths > 12 → Giá = price + 1.000.000
● Ngược lại → Giá = price
Override displayInfo():
● In thêm thông tin số tháng bảo hành
b) Class FoodProduct (Kế thừa Product)
Thuộc tính riêng:
● int discountPercent – Phần trăm giảm giá
Override calculateFinalPrice():
● Giá sau giảm theo công thức:
price - (price * discountPercent / 100)
Override displayInfo():
● In thêm phần trăm giảm giá
3. Xây dựng lớp ProductRepository (2.0 điểm)
● Thực thi interface IRepository<Product>
● Sử dụng Collection:
o ArrayList<Product> để lưu danh sách
o HashMap<String, Product> để hỗ trợ tìm kiếm nhanh theo id
Cài đặt đầy đủ các phương thức của interface.
4. Chương trình chính – Main Class (2.0 điểm)
a) Tạo danh sách sản phẩm
● Khởi tạo đối tượng ProductRepository
● Thêm ít nhất 4 sản phẩm gồm:
o 2 ElectronicProduct
o 2 FoodProduct
b) Thực hiện các thao tác nghiệp vụ sau:
1. Hiển thị toàn bộ danh sách sản phẩm
o Gọi displayInfo()
o In thêm "Thành tiền" bằng calculateFinalPrice()
2. Tìm sản phẩm theo id và hiển thị kết quả
3. Sắp xếp danh sách theo giá tăng dần (dùng Collections.sort hoặc
Comparator)
4. Thống kê số lượng sản phẩm theo từng loại (Electronic, Food)
o Trả về kết quả dạng Map<String, Integer>
LƯU Ý
● Bắt buộc sử dụng Collection Framework (List, Map, Comparator
hoặc Collections)
● Phải có Interface và Abstract Class
● Kiểm tra null trước khi xử lý dữ liệu
● Chương trình chạy được, không lỗi
*/

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ProductRepository repository = new ProductRepository();
        repository.add(new ElectronicProduct("1", "Laptop", 15000000, 24));
        repository.add(new ElectronicProduct("2", "Smartphone", 8000000, 12));
        repository.add(new FoodProduct("3", "Chocolate", 50000, 10));
        repository.add(new FoodProduct("4", "Bread", 20000, 20));

        while(true){
            System.out.println("1. Hiển thị toàn bộ danh sách sản phẩm");
            System.out.println("2. Tìm sản phẩm theo id");
            System.out.println("3. Sắp xếp danh sách theo giá tăng dần");
            System.out.println("4. Thống kê số lượng sản phẩm theo từng loại");
            System.out.println("5. Thoát");
            System.out.println("Mời nhập vào lựa chọn: ");

            int choice = Integer.parseInt(sc.nextLine());
            switch (choice){
                case 1:
                    repository.displayAllProducts();
                    break;
                case 2:
                    repository.findById("1");
                    break;
                case 3:
                    repository.sortPriceAscending();
                    repository.displayAllProducts();
                    break;
                case 4:
                    System.out.println(repository.countProductsType());
                    break;
                case 5:
                    System.out.println("Thoát chương trình");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng thử lại");
            }
        }
    }
}