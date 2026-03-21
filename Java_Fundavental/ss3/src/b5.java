import java.util.Scanner;

public class b5 {

    public static int deleteBook(int[] arr, int n, int bookId) {
        int pos = -1;

        for (int i = 0; i < n; i++) {
            if (arr[i] == bookId) {
                pos = i;
                break;
            }
        }

        if (pos == -1) {
            System.out.println("Không tìm thấy mã sách cần xóa");
            return n;
        }

        for (int i = pos; i < n - 1; i++) {
            arr[i] = arr[i + 1];
        }

        System.out.println("Đã xóa mã sách " + bookId);
        return n - 1;
    }

    public static void displayBooks(int[] arr, int n) {
        if (n == 0) {
            System.out.println("Danh sách rỗng");
            return;
        }

        for (int i = 0; i < n; i++) {
            System.out.print(arr[i]);
            if (i < n - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int[] books = {10, 20, 30, 40, 50};
        int n = 5;

        while (n > 0) {
            System.out.println("Danh sách hiện tại:");
            displayBooks(books, n);

            System.out.print("Nhập mã sách cần xóa: ");
            int bookId = Integer.parseInt(sc.nextLine());

            n = deleteBook(books, n, bookId);

            System.out.print("Bạn có muốn tiếp tục xóa không? (y/n): ");
            String choice = sc.nextLine();
            if (!choice.equalsIgnoreCase("y")) {
                break;
            }
        }
    }
}
