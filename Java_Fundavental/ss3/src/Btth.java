import java.util.Scanner;

public class Btth {
    public static void main(String[] args){
        int[] ids = {1, 2, 3, 4, 5};
        String[] title = {
                "Java Basic",
                "Python Intro",
                "C Programming",
                "Data Structures",
                "Web Development"
        };
        int[] quantity = {10, 7, 5, 12, 3};
        Scanner sc = new Scanner(System.in);
        int current = 0;
        int choose;
        do{
            System.out.printf("1.Xem danh sách \n 2.Thêm sách mới \n 3.Cập nhật số lượng \n 4.Xoá sách \n 5.Tìm kiếm \n 6.Sắp xếp theo số lượng giảm dần \n 7.Thoát");
            System.out.println("\n Mời nhập vào lựa chọn: ");
            choose = Integer.parseInt(sc.nextLine());
            switch (choose){
                case 1:{
                    System.out.printf("%-10s %-30s %-10s\n", "Mã", "Tên sách", "Số lượng");
                    for (int i = 0; i < ids.length; i++) {
                        System.out.printf("%-10d %-30s %-10d\n",ids[i],title[i],quantity[i]);
                    };
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 2:{

                    break;
                }
                default:
                    break;
            }
        }while (true);
    }
}
