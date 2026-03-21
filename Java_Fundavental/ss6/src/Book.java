public class Book {
    private String maSach;
    private String tenSach;
    private String tacGia;
    private double gia;

    public Book(String maSach, String tenSach, String tacGia, double gia) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.gia = gia;
    }

    public void showInfo() {
        System.out.println("Mã sách: " + maSach);
        System.out.println("Tên sách: " + tenSach);
        System.out.println("Tác giả: " + tacGia);
        System.out.println("Giá: " + gia);
    }
}
