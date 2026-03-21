import java.util.Scanner;

public class b5 {
    public static void main(String[] args) {
        int reputation = 100;
        int number_of_date_return_late = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập số lần trả sách.");
        int quantity_return_book = Integer.parseInt(sc.nextLine());
        while(quantity_return_book > 0){
            System.out.printf("Nhập số ngày trả muộn (0 nếu không muộn): ");
            number_of_date_return_late = Integer.parseInt(sc.nextLine());
            if(number_of_date_return_late == 999) break;
            if(number_of_date_return_late <= 0){
                reputation += 5;
            }else{
                reputation -= number_of_date_return_late * 2;
            }
            quantity_return_book -= 1;
        }
        if(reputation > 120){
            System.out.printf("Độc giả thân thiết");
        }else if (reputation > 80){
            System.out.printf("Độc giả tiu chuẩn");
        }else{
            System.out.printf("Độc giả cần lưu ý");
        }
    }
}
