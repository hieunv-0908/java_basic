public class b3 {

    public static void maxQuantityOfBooks(String[] names, int[] quantities) {
        int max = quantities[0];

        for (int i = 1; i < quantities.length; i++) {
            if (quantities[i] > max) {
                max = quantities[i];
            }
        }

        System.out.println("Sách có số lượng nhiều nhất:");
        for (int i = 0; i < quantities.length; i++) {
            if (quantities[i] == max) {
                System.out.println("- " + names[i] + ": " + quantities[i]);
            }
        }
    }

    public static void minQuantityOfBooks(String[] names, int[] quantities) {
        int min = quantities[0];

        for (int i = 1; i < quantities.length; i++) {
            if (quantities[i] < min) {
                min = quantities[i];
            }
        }

        System.out.println("Sách có số lượng ít nhất:");
        for (int i = 0; i < quantities.length; i++) {
            if (quantities[i] == min) {
                System.out.println("- " + names[i] + ": " + quantities[i]);
            }
        }
    }

    public static void main(String[] args) {

        String[] names = {
                "Java Basic",
                "Python Intro",
                "C Programming",
                "Data Structures",
                "Software Engineering"
        };

        int[] quantities = {10, 5, 10, 3, 3};

        maxQuantityOfBooks(names, quantities);
        minQuantityOfBooks(names, quantities);
    }
}
