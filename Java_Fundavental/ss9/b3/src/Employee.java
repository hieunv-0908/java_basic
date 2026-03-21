public class Employee {
    protected String ten;
    protected double luongCoBan;

    public Employee(String ten, double luongCoBan) {
        this.ten = ten;
        this.luongCoBan = luongCoBan;
    }

    public void hienThiThongTin() {
        System.out.println("Tên: " + ten);
        System.out.println("Lương cơ bản: " + luongCoBan);
    }
}
