package bai03;
import java.util.*;

public class Main {
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Mời nhập vào biểu thức toán học: ");
            String expression = sc.nextLine();
            // Kiểm tra nếu biểu thức rỗng hoặc chỉ chứa khoảng trắng
            if (expression.trim().isEmpty()) {
                System.out.println("Lỗi");
                return;
            }
            boolean isValid = checkBrackets(expression);
            System.out.println(isValid);

        }

        // Phương thức kiểm tra của biểu thức toán học hợp lệ
    private static boolean checkBrackets(String expression) {
        Stack<Character> stack = new Stack<>();
        // Duyệt qua từng ký tự trong biểu thức
        for (char ch : expression.toCharArray()) {
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
            } else if (ch == ')' || ch == ']' || ch == '}') {
                if (stack.isEmpty()) {
                    return false; // Không có dấu mở tương ứng
                }
                char top = stack.pop();
                if (!isMatchingPair(top, ch)) {
                    return false; // Dấu đóng không khớp với dấu mở
                }
            }
        }
        return stack.isEmpty(); // Nếu còn dấu mở chưa đóng thì không hợp lệ
    }

    private static boolean isMatchingPair(char top, char ch) {
        return (top == '(' && ch == ')') ||
               (top == '[' && ch == ']') ||
               (top == '{' && ch == '}');
    }
}
