import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            // Hiển thị menu
            System.out.println("===== MENU =====");
            System.out.println("1. Two Sum");
            System.out.println("2. Move Zeroes");
            System.out.println("3. Valid Palindrome");
            System.out.println("4. Reverse Words");
            System.out.println("5. Happy Number");
            System.out.println("0. Exit");
            System.out.print("Nhập lựa chọn: ");
            choice = sc.nextInt();
            sc.nextLine(); // đọc newline còn thừa

            switch (choice) {
                case 1: // Two Sum
                    // Nhập mảng và target
                    System.out.print("Nhập kích thước mảng: ");
                    int n = sc.nextInt();
                    int[] arr = new int[n];
                    System.out.println("Nhập các phần tử:");
                    for (int i = 0; i < n; i++) arr[i] = sc.nextInt();
                    System.out.print("Nhập target: ");
                    int target = sc.nextInt();

                    // Tư duy: tìm 2 số sao cho tổng = target
                    boolean found = false;
                    for (int i = 0; i < n; i++) {
                        for (int j = i + 1; j < n; j++) {
                            if (arr[i] + arr[j] == target) {
                                System.out.println("Cặp chỉ số: [" + i + ", " + j + "]");
                                found = true; // tìm thấy, dừng vòng lặp
                                break;
                            }
                        }
                        if (found) break;
                    }
                    if (!found) System.out.println("Không tìm thấy cặp nào.");
                    break;

                case 2: // Move Zeroes
                    // Nhập mảng có số 0
                    System.out.print("Nhập kích thước mảng: ");
                    int m = sc.nextInt();
                    int[] nums = new int[m];
                    System.out.println("Nhập các phần tử:");
                    for (int i = 0; i < m; i++) nums[i] = sc.nextInt();

                    // Tư duy: giữ thứ tự các số khác 0, đưa 0 về cuối
                    int index = 0; // con trỏ để ghi số khác 0
                    for (int i = 0; i < m; i++) {
                        if (nums[i] != 0) {
                            nums[index++] = nums[i]; // ghi số khác 0
                        }
                    }
                    while (index < m) {
                        nums[index++] = 0; // gán 0 cho các phần còn lại
                    }

                    System.out.println("Mảng sau khi dồn số 0: " + Arrays.toString(nums));
                    break;

                case 3: // Valid Palindrome
                    System.out.print("Nhập chuỗi: ");
                    String s = sc.nextLine();

                    // Tư duy: chuỗi đầu vào có thể "bẩn", cần chuẩn hóa
                    String clean = s.replaceAll("[^a-zA-Z]", "").toLowerCase();
                    int left = 0, right = clean.length() - 1;
                    boolean isPalindrome = true;

                    while (left < right) {
                        if (clean.charAt(left) != clean.charAt(right)) {
                            isPalindrome = false; // gặp ký tự khác nhau
                            break;
                        }
                        left++;
                        right--;
                    }

                    System.out.println("Chuỗi đối xứng? " + isPalindrome);
                    break;

                case 4: // Reverse Words
                    System.out.print("Nhập chuỗi: ");
                    String sentence = sc.nextLine();

                    // Tư duy: đảo thứ tự từ, không đảo ký tự, chuẩn hóa khoảng trắng
                    String[] words = sentence.trim().split("\\s+"); // tách từ
                    StringBuilder reversed = new StringBuilder();
                    for (int i = words.length - 1; i >= 0; i--) {
                        reversed.append(words[i]);
                        if (i > 0) reversed.append(" ");
                    }

                    System.out.println("Chuỗi sau khi đảo từ: \"" + reversed.toString() + "\"");
                    break;

                case 5: // Happy Number
                    System.out.print("Nhập số nguyên dương n: ");
                    int num = sc.nextInt();

                    // Tư duy: tính tổng bình phương chữ số, kiểm tra lặp vô hạn
                    Set<Integer> seen = new HashSet<>();
                    int current = num;

                    while (current != 1 && !seen.contains(current)) {
                        seen.add(current); // đánh dấu số đã xuất hiện
                        int sum = 0;

                        // tính tổng bình phương các chữ số
                        while (current > 0) {
                            int digit = current % 10;
                            sum += digit * digit;
                            current /= 10;
                        }

                        current = sum;
                    }

                    if (current == 1) System.out.println(num + " là số hạnh phúc.");
                    else System.out.println(num + " không phải là số hạnh phúc.");
                    break;

                case 0:
                    System.out.println("Thoát chương trình.");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ, thử lại.");
            }
            System.out.println();
        } while (choice != 0);

        sc.close();
    }
}
