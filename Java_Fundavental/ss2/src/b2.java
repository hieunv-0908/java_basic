import java.util.Scanner;

public class b2 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập mã khu vực (A,B,C,D): ");
        char choose = sc.next().charAt(0);
        switch (choose){
            case 'A':{
                System.out.println("Vị trí: Tầng 1 - Sach Van Hoc");
                break;
            }
            case 'B':{
                System.out.println("Vị trí: Tầng 2 - Sach Khoa Hoc");
                break;
            }
            case 'C':{
                break;
            }
            case 'D':{
                break;
            }
            default:
                System.out.println("Lỗi: Mã khu vực không hợp lệ");
                break;
        }
    }
}
