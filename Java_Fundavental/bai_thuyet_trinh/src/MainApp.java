import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentManager sm = new StudentManager(100);

        while (true) {
            System.out.println("\n===== QUẢN LÝ SINH VIÊN =====");
            System.out.println("1. Thêm sinh viên");
            System.out.println("2. Hiển thị danh sách");
            System.out.println("3. Tìm kiếm");
            System.out.println("4. Xóa sinh viên");
            System.out.println("5. Sắp xếp theo điểm");
            System.out.println("6. Thống kê");
            System.out.println("7. Thoát");
            System.out.print("Chọn: ");
            int chon = Integer.parseInt(sc.nextLine());

            switch (chon) {
                case 1:
                    System.out.print("MaSV: ");
                    String ma = sc.nextLine();
                    System.out.print("Ho ten: ");
                    String ten = sc.nextLine();
                    System.out.print("Tuoi: ");
                    int tuoi = Integer.parseInt(sc.nextLine());
                    System.out.print("Gioi tinh: ");
                    String gt = sc.nextLine();
                    System.out.print("Diem Toan: ");
                    double toan = Double.parseDouble(sc.nextLine());
                    System.out.print("Diem Ly: ");
                    double ly = Double.parseDouble(sc.nextLine());
                    System.out.print("Diem Hoa: ");
                    double hoa = Double.parseDouble(sc.nextLine());

                    if (sm.them(new Student(ma, ten, tuoi, gt, toan, ly, hoa)))
                        System.out.println("Them thanh cong!");
                    else
                        System.out.println("Them that bai!");
                    break;

                case 2:
                    sm.hienThiTatCa();
                    break;

                case 3:
                    System.out.print("Nhap ma hoac ten: ");
                    String key = sc.nextLine();
                    Student s = sm.timTheoMa(key);
                    if (s != null) s.hienThi();
                    else sm.timTheoTen(key);
                    break;

                case 4:
                    System.out.print("Nhap ma can xoa: ");
                    if (sm.xoa(sc.nextLine()))
                        System.out.println("Da xoa!");
                    else
                        System.out.println("Khong tim thay!");
                    break;

                case 5:
                    sm.sapXepTheoDTB();
                    System.out.println("Da sap xep!");
                    break;

                case 6:
                    sm.thongKe();
                    break;

                case 7:
                    System.out.println("Thoat chuong trinh!");
                    return;
            }
        }
    }
}
