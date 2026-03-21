import java.util.Scanner;

public class b2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhập mô tả sách: ");
        String bookDescribe = sc.nextLine();
        bookDescribe = bookDescribe.trim().replaceAll("\\s+", " ");
//        System.out.println(bookDescribe.indexOf("Kệ"));
//        System.out.println(bookDescribe.substring(31));
        String[] tempBookDes = bookDescribe.split(" ");
        String indexBook = "";
        for (int i = 0; i < tempBookDes.length; i++) {
            if(tempBookDes[i].indexOf("Kệ") != -1){
                indexBook = tempBookDes[i].substring(tempBookDes[i].indexOf("Kệ") +3);
                bookDescribe = bookDescribe.replace("Kệ","Vị trí lưu trữ");
            }
        }
        System.out.println("Vị trí tìm thấy: " + indexBook == "" ? "Không tìm thấy" : indexBook);
        System.out.println("Mô tả mới: " + bookDescribe);
    }
}

//  Sách giáo khoa Toán lớp 12, Kệ:A1-102, tình trạng mới.