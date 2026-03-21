package Bai02;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Map<String, Integer> map = new HashMap<>(); // Lưu trữ tên danh mục và số lượng sản phẩm

        System.out.println("Mời nhập vào chuỗi các danh mục sản phẩm (cách nhau bằng dấu phẩy): ");
        String categoryList = sc.nextLine().toLowerCase(); // Người dùng nhập vào chuỗi các danh mục sản phẩm, cách nhau bằng dấu phẩy
        String[] categories = categoryList.split(","); // Tách chuỗi thành mảng các
        for (String category : categories) {
            category = category.trim(); // Loại bỏ khoảng trắng thừa
            map.put(category, map.getOrDefault(category, 0) + 1); // Đếm số lượng sản phẩm trong mỗi danh mục
        }

        // Hiển thị danh sách các danh mục sản phẩm và số lượng sản phẩm trong mỗi danh mục không phân biệt chữ hoa chữ thường
        System.out.println("Danh mục sản phẩm và số lượng sản phẩm:");
        // Sử dụng entrySet để duyệt qua các cặp key-value trong map và hiển thị chúng
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Cho phép người dùng nhập tên danh mục để tra cứu nhanh số lượng
        System.out.println("Mời nhập vào tên danh mục để tra cứu số lượng sản phẩm: ");
        String searchCategory = sc.nextLine().trim(); // Người dùng nhập vào tên danh mục để tra cứu
        int quantity = map.getOrDefault(searchCategory, 0); // Tra cứu số lượng sản phẩm trong danh mục, nếu không tồn tại thì trả về 0
        System.out.println("Search " + searchCategory + "': " + quantity);
    }
}
