import java.util.Scanner;

public class btth {
    public static void main(String[] args){
        int choose;
        int quantity_book =0;
        do{
            System.out.printf("Nhập vào lựa chọn từ 1-3: \n 1.Tính tiền phạt \n 2.Đăg ký hội viên \n 3.Thống kê sách nhập kho \n");
            Scanner sc = new Scanner(System.in);
            choose = Integer.parseInt(sc.nextLine());
            switch (choose){
                case 1: {
                    System.out.printf("Nhập vào số lượng sách: ");
                    int number_of_books = Integer.parseInt(sc.nextLine());
                    if (number_of_books < 0) break;
                    int total_late_date = 0;
                    int total_money_punish = 0;
                    while (number_of_books > 0) {
                        System.out.printf("Nhập vào số ngày ");
                        int late_date = Integer.parseInt(sc.nextLine());
                        if (late_date < 0) continue;
                        total_late_date += late_date;
                        if (total_late_date >= 1 && total_late_date <= 5) {
                            total_money_punish += late_date * 2000;
                        } else if (total_late_date > 5) {
                            total_money_punish += late_date * 2000 + (late_date - 5) * 5000;
                        } else {
                            continue;
                        }
                        number_of_books--;
                    }

                    System.out.printf("Tổng tiền phạt phạt: %d", total_money_punish);
                    break;
                }
                case 2: {
                    int age;
                    int number_of_books;
                    int prioritize;
                    System.out.printf("Nhập tuổi:");
                    age = Integer.parseInt(sc.nextLine());
                    System.out.printf("Nhập số sách đã mượn trong 1 tháng:");
                    number_of_books = Integer.parseInt(sc.nextLine());
                    System.out.printf("Ưu tiên sinh viên 1-Có 0-Không:");
                    prioritize = Integer.parseInt(sc.nextLine());
                    if(age < 0 || number_of_books < 0 || prioritize != 1 && prioritize != 0) {
                        System.out.printf("Một trong số các thông tin không hợp lệ");
                        break;
                    };
                    if(age >= 18 && number_of_books >= 10 || prioritize == 1){
                        System.out.printf("Đủ tiêu chuẩn nâng cấp VIP");
                    }else {
                        System.out.printf("Không đủ tiêu chuẩn nâng cấp VIP");
                    }
                    break;
                }
                case 3:{
                    int bookId;
                    int quantity_book_current = 0;
                    do{
                        System.out.printf("Nhập vào mã sách cần nhập kho (Không được âm - nhập 0 để dừng):");
                        bookId = Integer.parseInt(sc.nextLine());
                        if(bookId < 0) continue;
                        quantity_book_current++;
                        quantity_book++;
                    }while (bookId == 0);
                    System.out.printf("Số lượng sách hợp lệ đã thêm %d",quantity_book_current);
                    System.out.printf("Tổng Số lượng sách hợp lệ %d",quantity_book);

                    break;
                }
                default:
                    System.out.printf("Lựa chọn không hợp lệ!!!");
            }
        }while(choose == 4);
    }
}
