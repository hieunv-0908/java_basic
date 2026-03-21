import java.util.Scanner;

public class b1 {
    public static void main(String[] args){
        int age;
        int number_of_book;
        Scanner sc = new Scanner(System.in);
        System.out.printf("Nhập Tuổi: ");
        age = Integer.parseInt(sc.nextLine());
        System.out.printf("Nhập số sách đang mượn: ");
        number_of_book = Integer.parseInt(sc.nextLine());
        if(age >= 18 && number_of_book < 3){
            System.out.printf("Kết quả: Bạn đủ điều kiện mượn sách quyas hiếm.");
        }else if(age < 18){
            System.out.printf("Kết quả: Bạn Không đủ điều kiện.");
            System.out.printf("Bạn KHông đủ tuổi.");
        }else if(number_of_book == 3){
            System.out.printf("Kết quả: Bạn Không đủ điều kiện.");
            System.out.printf("Bạn đã mượn tối đa số sách.");
        }
    }
}
