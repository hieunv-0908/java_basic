import java.util.Scanner;

public class b4 {
    public static void main(String[] args){
        int quantity;
        do{
            System.out.println(" \nMời nhập vào id của sách (Phải lớn hơn 0): ");
            Scanner sc = new Scanner(System.in);
            quantity = Integer.parseInt(sc.nextLine());
            if(quantity < 0){
                System.out.printf("Nhập vào không hợp lệ!Mời nhập lại");
            }else{
                System.out.printf("Xác nhận: Mã sách %d đã được ghi nhận.",quantity);
            }
        }while(quantity < 0);
    }
}
