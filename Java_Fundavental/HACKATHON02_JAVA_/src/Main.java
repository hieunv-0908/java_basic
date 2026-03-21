import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void countCharacterFrequency() {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextLine()) {
            System.out.println("Lỗi: không nhập nội dung");
            return;
        }
        String s = sc.nextLine();
        if (s == null || s.length() == 0) {
            System.out.println("Lỗi: không nhập nội dung");
            return;
        }

        // Chuyển sang mảng ký tự và dùng mảng này cùng vòng lặp để đếm tần suất
        char[] arr = s.toCharArray();
        int n = arr.length;

        for (int i = 0; i < n; i++) {
            char current = arr[i];
            // Kiểm tra xem ký tự này đã xuất hiện trước i chưa
            boolean seenBefore = false;
            for (int k = 0; k < i; k++) {
                if (arr[k] == current) { seenBefore = true; break; }
            }
            if (seenBefore) continue; // đã in ra ở lần xuất hiện trước

            // Nếu chưa xuất hiện trước, đếm tổng số lần xuất hiện
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (arr[j] == current) count++;
            }

            if (current == ' ') {
                System.out.println("  : " + count + "  (Khoảng trắng)");
            } else {
                System.out.println(current + " : " + count);
            }
        }
    }

    public static void countIntegerFrequency() {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        if (n <= 0) return;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            if (!sc.hasNextInt()) return;
            arr[i] = sc.nextInt();
        }

        for (int i = 0; i < n; i++) {
            int current = arr[i];
            // kiểm tra xem current đã xuất hiện trước i hay chưa
            boolean seenBefore = false;
            for (int k = 0; k < i; k++) {
                if (arr[k] == current) { seenBefore = true; break; }
            }
            if (seenBefore) continue;

            // đếm số lần xuất hiện của current trong arr
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (arr[j] == current) count++;
            }

            System.out.println("Số " + current + " xuất hiện: " + count + " lần");
        }
    }

    public static void Child_Arr(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập số phần tử của mảng A:");
        int n = Integer.parseInt(sc.nextLine()) ;

        System.out.println("Nhập số phần tử của mảng B:");
        int M = Integer.parseInt(sc.nextLine());
        if (n < 0 || M < 0) return;

        int[] A = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Nhập phần tử thứ " + (i + 1) + " của mảng A:");
            A[i] = Integer.parseInt(sc.nextLine());
        }

        int[] B = new int[M];
        for (int i = 0; i < M; i++) {
            System.out.println("Nhập phần tử thứ " + (i + 1) + " của mảng B:");
            B[i] = Integer.parseInt(sc.nextLine());
        }

        int j = 0;
        for (int i = 0; i < n && j < M; i++) {
            if (A[i] == B[j]) {
                j++;
            }
        }

        if (j == M) {
            System.out.println("Mảng B là mảng con của mảng A");
        } else {
            System.out.println("Mảng B không là mảng con của mảng A");
        }

    }

    public static void sumNumbersInString() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập một chuỗi:");
        String s = sc.nextLine();
        if (s == null || s.trim().length() == 0) {
            System.out.println("Lỗi: không nhập nội dung");
            return;
        }

        s = s.trim();

        if (s.length() >= 2) {
            char c0 = s.charAt(0);
            char c1 = s.charAt(s.length() - 1);
            if ((c0 == '"' && c1 == '"') || (c0 == '\'' && c1 == '\'')) {
                s = s.substring(1, s.length() - 1);
            }
        }

        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(s);
        long sum = 0L;
        boolean found = false;
        while (m.find()) {
            String numStr = m.group();
            long val = 0L;
            for (int i = 0; i < numStr.length(); i++) {
                char ch = numStr.charAt(i);
                if (ch >= '0' && ch <= '9') {
                    val = val * 10 + (ch - '0');
                }
            }
            sum += val;
            found = true;
        }

        System.out.println(sum);
    }

    public static void main(String[] args) {
        // Câu 1:
        // Phần 1:
//        sumNumbersInString();
        // Phần 2:
//        countCharacterFrequency();
        // Phần 1:
//        countIntegerFrequency();
        // Phần 2:

        // Câu 3:
//        Child_Arr();
    }
}
