public class StudentManager {
    private static Student[] students;
    private static int count;
    private static final int MAX_STUDENT = 100;

    public StudentManager(int size) {
        students = new Student[size];
        count = 0;
    }

    public boolean them(Student s) {
        if (count >= MAX_STUDENT || timTheoMa(s.getMaSV()) != null)
            return false;
        students[count++] = s;
        return true;
    }

    public void hienThiTatCa() {
        System.out.printf("%-8s %-20s %-5s %-8s %-6s %-10s%n",
                "MaSV", "HoTen", "Tuoi", "GT", "DTB", "XepLoai");
        for (int i = 0; i < count; i++) {
            students[i].hienThi();
        }
    }

    public Student timTheoMa(String ma) {
        for (int i = 0; i < count; i++) {
            if (students[i].getMaSV().equalsIgnoreCase(ma))
                return students[i];
        }
        return null;
    }

    public void timTheoTen(String key) {
        key = key.toLowerCase();
        for (int i = 0; i < count; i++) {
            if (students[i].getHoTen().toLowerCase().contains(key))
                students[i].hienThi();
        }
    }

    public boolean xoa(String ma) {
        for (int i = 0; i < count; i++) {
            if (students[i].getMaSV().equalsIgnoreCase(ma)) {
                for (int j = i; j < count - 1; j++)
                    students[j] = students[j + 1];
                students[--count] = null;
                return true;
            }
        }
        return false;
    }

    public void sapXepTheoDTB() {
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                if (students[j].getDiemTB() < students[j + 1].getDiemTB()) {
                    Student tmp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = tmp;
                }
            }
        }
    }

    public void thongKe() {
        int gioi = 0, kha = 0, tb = 0, yeu = 0;
        for (int i = 0; i < count; i++) {
            switch (students[i].getXepLoai()) {
                case "Giỏi": gioi++; break;
                case "Khá": kha++; break;
                case "Trung bình": tb++; break;
                default: yeu++;
            }
        }
        System.out.println("Giỏi: " + gioi);
        System.out.println("Khá: " + kha);
        System.out.println("Trung bình: " + tb);
        System.out.println("Yếu: " + yeu);
    }
}
