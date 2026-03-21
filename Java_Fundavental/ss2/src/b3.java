import java.util.Scanner;

public class b3 {
    public static void main(String[] args){
        int total = 0;
        System.out.println("Nhập vào Số lượng sách: ");
        Scanner sc = new Scanner(System.in);
        int number_of_book = Integer.parseInt(sc.nextLine());
        if(number_of_book < 0){
            return;
        }
        for (int i = 1; i <= number_of_book; i++){
            System.out.println("Nhập vào số ngày muộn của sách thứ: " + i);
            int late_date = Integer.parseInt(sc.nextLine());
            total += late_date * 5000;
        }
        System.out.println("Tổng số tiền phải trả là: " + total);
    }
}
