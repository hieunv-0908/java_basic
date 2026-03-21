import java.util.Scanner;

public class b1 {

    public static int[] addBookToLibraries(int n) {
        Scanner sc = new Scanner(System.in);
        int[] isbnList = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Nhập mã sách thứ " + (i + 1) + ": ");
            isbnList[i] = Integer.parseInt(sc.nextLine());
        }

        return isbnList;
    }

    public static void displayLibraries(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhập số lượng sách: ");
        int n = Integer.parseInt(sc.nextLine());

        int[] libraries = addBookToLibraries(n);
        System.out.println("Danh sách mã sách:");
        displayLibraries(libraries);
    }
}
