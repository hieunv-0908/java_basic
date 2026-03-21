import java.io.IOException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
            System.out.printf("1.Tính tuổi bằng năm sinh \n 2.Tính số người trên 1 nhóm \n 3.Lưu thông tin người dùng \n");
            System.out.println("Mời nhập vào lựa chọn của bạn: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice){
                case 1:
                    b1(sc);
                    break;
                case 2:
                    b2(sc);
                    break;
                case 3:
                    try {
                        processUserData();
                    } catch (IOException e) {
                        System.out.println("Lỗi khi lưu file: " + e.getMessage());
                    }
                    break;
                default:
                    break;
            }
    }

    public static void b1(Scanner sc){
        System.out.println("Mời nhập vào năm sinh của bạn.");
        String yearString = sc.nextLine();
        int yearInt;
        try{
            yearInt = Integer.parseInt(yearString);
            System.out.println("Tuổi của bạn là: " + ( LocalDate.now().getYear() - yearInt));
        }catch(NumberFormatException nfe){
            System.out.println("Dữ liệu không thể ép được thành chuỗi");
        }finally {
            sc.close();
        }
    }
    public static void b2(Scanner sc){
        System.out.println("Mời nhập vào số người và số nhóm muốn chia: ");
        int numberPerson = Integer.parseInt(sc.nextLine());
        int numberGroup = Integer.parseInt(sc.nextLine());
        try{
            int numberPerson_numberGroup = Math.round(numberPerson/numberGroup);

            System.out.println("Số người có thể có trong 1 nhóm: " + numberPerson_numberGroup);
        }catch(ArithmeticException ae){
            System.out.println("Không thể chia cho 0!!");
        }
    }

    public static void processUserData() throws IOException {
        System.out.println("Đang xử lý dữ liệu người dùng...");
        saveToFile();
    }

    public static void saveToFile() throws IOException {
        System.out.println("Đang lưu dữ liệu vào file...");

        throw new IOException("Không thể ghi file do lỗi môi trường");
    }
}