import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int choose;
        String[] MSSV = new String[100];
        int cur_el = 0;
        do{
            System.out.printf("1.Hiển thị \n 2.Thêm mới \n 3.Cập nhật \n 4.Xoá \n 5.Tìm kiếm \n 6.Thoát \n");
            System.out.println("Mời bạn nhập vào lựa chọn: ");
            Scanner sc = new Scanner(System.in);
            choose = Integer.parseInt(sc.nextLine());
            if(choose == 6){
                break;
            }
            switch (choose){
                case 1:{
                    for (int i = 0; i < cur_el; i++) {
                        System.out.println("Sinh viên " + (i + 1) + ":" + MSSV[i]);
                    }
                    break;
                }
                case 2:{
                    int breakWhile = 1;
                    if(cur_el == 100){
                        System.out.println("Mảng đầy.");
                        break;
                    }
                    do {
                        System.out.println("Nhập số bất kì để thoát khỏi chức năng (0 để dừng chức năng): ");
                        breakWhile = Integer.parseInt(sc.nextLine());
                        if(breakWhile == 0) continue;
                        System.out.println("Nhập vào id muốn thêm (B + 7 chữ số): ");
                        String insert_id = sc.nextLine();
                        String regex = "B\\d{7}";
                        if(insert_id.matches(regex)){
                            MSSV[cur_el] = insert_id;
                            cur_el++;
                            break;
                        }else{
                            System.out.println("Nhập sai định dạng yêu cầu nhập lại.");
                        }
                    }while (breakWhile != 0);
                    break;
                }
                case 4: {
                    if (cur_el == 0) {
                        System.out.println("Danh sách đang trống, không thể cập nhật.");
                        break;
                    }
                    System.out.println("Nhập vị trí (index) cần sửa (từ 0 đến " + (cur_el - 1) + "): ");
                    int index = Integer.parseInt(sc.nextLine());
                    if (index < 0 || index >= cur_el) {
                        System.out.println("Index không hợp lệ.");
                        break;
                    }
                    System.out.println("MSSV hiện tại: " + MSSV[index]);
                    System.out.println("Nhập MSSV mới (B + 7 chữ số): ");
                    String newMSSV = sc.nextLine();
                    String regex = "B\\d{7}";
                    if (newMSSV.matches(regex)) {
                        MSSV[index] = newMSSV;
                        System.out.println("Cập nhật thành công!");
                    } else {
                        System.out.println("MSSV không đúng định dạng.");
                    }
                    break;
                }

                case 5:{
                    System.out.println("Mời nhập vào id mới muốn tìm kiếm: ");
                    String search_id = sc.nextLine();
                    String regex = "(?i).*" + search_id + ".*";
                    boolean check = false;
                    for (int i = 0; i < cur_el; i++) {
                        if(MSSV[i].matches(regex)){
                            System.out.printf("Tìm thấy: %s. \n",MSSV[i]);
                            check = true;
                        }
                    }
                    if(!check){
                        System.out.println("Không tìm thấy kết quả ph hợp.");
                    }
                    break;
                }
                default:
                    break;
            }
        }while (true);
    }
}