import java.time.LocalDate;
import java.util.Scanner;

// class chính
public class Main {
    public static void main(String[] args) {
        // bai 1
        /*System.out.printf("Mời nhập theo thứ tự sau: Id,Name,Publish Year,Price,Is Available \n");
        Scanner sc = new Scanner(System.in);
        String bookId = sc.nextLine();
        String bookName = sc.nextLine();
        int publishYear = Integer.parseInt(sc.nextLine());
        double price = Integer.parseInt(sc.nextLine());
        Boolean isAvailable = Boolean.parseBoolean(sc.nextLine());
        int ageBook = 2026 - publishYear;
        System.out.printf("Tên sách: %s \n Giá: %.2f \n Trạng thái: %s \n",bookName,price,isAvailable ? "Còn sách" : "Hết sách");
        */
        // bai 2
        /*
        System.out.printf("Nhập số ngày muộn và số lượng sách");
        Scanner sc = new Scanner(System.in);
        int lateDate = Integer.parseInt(sc.nextLine());
        int numberOfBook = Integer.parseInt(sc.nextLine());
        int total = lateDate * numberOfBook * 5000;
        if (lateDate > 7 && numberOfBook >=3) {
            total *= 1.2;
        }
        if(total > 50000){
            System.out.printf("true \n");
            System.out.printf("yêu cầu khoá thẻ");
        }else{
            System.out.printf("false");
        }
        */
        // b3
        /*
        String book1 = "Java Basic";
        String book2 = "Python Intro";
        String temp;
        temp = book1;
        book1 = book2;
        book2 = temp;
        System.out.println("book1 = " + book1);
        System.out.println("book2 = " + book2);
         {

        /*
         * Giải thích cơ chế hoán đổi String trong Java (Stack & Heap):
         *
         * - book1, book2, temp là các biến tham chiếu, được lưu trên STACK.
         * - Các chuỗi "Java Basic" và "Python Intro" là các đối tượng String,
         *   được lưu trên HEAP (cụ thể là String Pool).
         *
         * Ban đầu:
         *   book1 trỏ tới đối tượng String "Java Basic" trên Heap
         *   book2 trỏ tới đối tượng String "Python Intro" trên Heap
         *
         * Khi thực hiện: temp = book1;
         *   temp trỏ tới cùng vùng nhớ Heap mà book1 đang trỏ ("Java Basic")
         *   Không có đối tượng String mới nào được tạo ra.
         *
         * Khi thực hiện: book1 = book2;
         *   book1 thay đổi tham chiếu, chuyển sang trỏ tới "Python Intro"
         *   Nội dung String trên Heap vẫn giữ nguyên vì String là immutable.
         *
         * Khi thực hiện: book2 = temp;
         *   book2 trỏ lại vùng nhớ mà temp đang giữ ("Java Basic")
         *
         * Kết luận:
         * - Việc hoán đổi chỉ làm thay đổi các tham chiếu trên STACK.
         * - Các đối tượng String trên HEAP không bị thay đổi nội dung.
         * - Không có chuỗi mới được tạo trong quá trình hoán đổi.
         */
        // b4
        /* Scanner sc = new Scanner(System.in);
        System.out.print("Nhập giá sách (USD): ");
        double priceUSD = sc.nextDouble();
        System.out.print("Nhập tỷ giá: ");
        float exchangeRate = sc.nextFloat();
        double totalVND = priceUSD * exchangeRate;
        long totalVNDInt = (long) totalVND;
        System.out.println("Tổng tiền VNĐ (làm tròn xuống): " + totalVNDInt + " VND");
         */
        // b5
        /*
        Scanner sc = new Scanner(System.in);
        System.out.printf("Nhập mã sách (4 chữ số) \n");
        int bookId = Integer.parseInt(sc.nextLine());
        int sumOfFirstThreeNumber;
        int units = bookId % 10;
        bookId /= 10;
        int dozens = bookId % 10;
        bookId /= 10;
        int hundreds = bookId % 10;
        bookId /= 10;
        int thousands = bookId % 10;
        bookId /= 10;
        sumOfFirstThreeNumber = dozens + hundreds + thousands;
        System.out.printf("Chữ số kểm tra kì vọng:%d \n",units);
        if(sumOfFirstThreeNumber == units){
            System.out.printf("Hợp lệ");
        }else{
            System.out.printf("Sai mã");
        }
         */
        // b6
        /*
        Scanner sc = new Scanner(System.in);
        int stt = Integer.parseInt(sc.nextLine());
        int ke;
        System.out.printf("Sách số 1");
        ke = (stt < 25) ?1 : (stt < 50 ? 2 : 3);
        System.out.printf("Kệ %d - vị trí %d",ke,stt);
        if(stt < 25){
            System.out.printf("Kệ %d - vị trí %d",ke,stt);
        }else if (stt < 50){
            ke = 2;
            System.out.printf("Kệ %d - vị trí %d",ke,stt);
        };
        if(ke <= 10){
            System.out.printf("Phân khu: Khu cận (Gần cửa)");
        }else {
            System.out.printf("Phân khu: Khu viễn");
        }
         */
    }
}