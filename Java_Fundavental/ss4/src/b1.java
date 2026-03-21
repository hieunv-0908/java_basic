import java.util.Scanner;

public class b1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhập tên sách: ");
        String bookName = sc.nextLine();

        System.out.print("Nhập tên tác giả: ");
        String author = sc.nextLine();

        System.out.print("Nhập thể loại: ");
        String category = sc.nextLine();

        bookName = bookName.trim().replaceAll("\\s+", " ").toUpperCase();
        author = author.trim().replaceAll("\\s+", " ");
        category = category.trim().replaceAll("\\s+", " ");
        String[] tempAuthor = author.split(" ");
        String result = "";
        for (int i = 0; i < tempAuthor.length; i++) {
            result +=  Character.toUpperCase(tempAuthor[i].charAt(0)) + tempAuthor[i].substring(1) + " ";
        }
        author = result;
        System.out.println("Tên sách: " + bookName);
        System.out.println("Thể loại: " + category);
        System.out.println("Tên tác giả: " + author);
    }
}
