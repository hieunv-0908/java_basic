public class b4 {

    public static void sortBooks(int[] arr) {
        int temp;

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    // đổi chỗ
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static void displayBooks(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {

        int[] books = {45, 12, 89, 23, 5, 67};

        System.out.println("Mảng trước khi sắp xếp:");
        displayBooks(books);

        sortBooks(books);

        System.out.println("Mảng sau khi sắp xếp tăng dần:");
        displayBooks(books);
    }
}
