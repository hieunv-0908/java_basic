//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        b1();
        b2();
        b3();
        b4();
        b5();
    }
    public static void b1(){
        Student std01 = new Student(1,"nguyen van hieu",2006,7);
        Student std02 = new Student(2,"nguyen van an",2005,5);
        std02.display_student();
        std01.display_student();
    }

    public static void b2(){
        Account acc = new Account(
                "hieu123",
                "123456",
                "hieu@gmail.com"
        );
        acc.showInfo();
        System.out.println("-----");
        acc.changePassword("abcdef");
        acc.showInfo();
    }

    public static void b3(){
        Product p = new Product("SP01", "Bàn phím cơ", 500000);
        p.showInfo();
        System.out.println("-----");
        p.setGiaBan(-200000);
        System.out.println("-----");
        p.showInfo();
    }

    public static void b4(){
        Employee e1 = new Employee();

        Employee e2 = new Employee("NV01", "Nguyễn Văn A");

        Employee e3 = new Employee("NV02", "Trần Thị B", 12000000);

        System.out.println("=== Nhân viên 1 ===");
        e1.showInfo();

        System.out.println("=== Nhân viên 2 ===");
        e2.showInfo();

        System.out.println("=== Nhân viên 3 ===");
        e3.showInfo();
    }

    public static void b5(){
        Book b = new Book(
                "B01",
                "Lập trình Java",
                "Nguyễn Văn A",
                85000
        );
        b.showInfo();
    }

    public static void b6(){
        User u1 = new User(1, "hieu", "123456", "hieu@gmail.com");
        System.out.println("=== User 1 ===");
        u1.showInfo();

        System.out.println("-----");

        User u2 = new User(2, "an", "", "an@gmail.com");
        System.out.println("=== User 2 ===");
        u2.showInfo();

        System.out.println("-----");

        User u3 = new User(3, "binh", "abcdef", "binhgmail.com");
        System.out.println("=== User 3 ===");
        u3.showInfo();

        System.out.println("-----");

        User u4 = new User(4, "linh", "", "saiemail");
        System.out.println("=== User 4 ===");
        u4.showInfo();
    }
}