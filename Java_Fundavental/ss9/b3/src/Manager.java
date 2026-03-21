public class Manager extends Employee {
    private String phongBan;

    public Manager(String ten, double luongCoBan, String phongBan) {
        super(ten, luongCoBan); // khởi tạo thuộc tính lớp cha
        this.phongBan = phongBan;
    }

    @Override
    public void hienThiThongTin() {
        super.hienThiThongTin(); // gọi phương thức lớp cha
        System.out.println("Phòng ban: " + phongBan);
    }
}
