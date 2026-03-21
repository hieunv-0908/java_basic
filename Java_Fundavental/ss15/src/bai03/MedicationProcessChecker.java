package bai03;
import java.util.Stack;

public class MedicationProcessChecker {

    private Stack<String> stack = new Stack<>();

    // Kiểm tra quy trình
    public boolean checkProcess(String[] actions) {

        for (int i = 0; i < actions.length; i++) {

            String action = actions[i];

            if (action.equals("PUSH")) {
                stack.push("Medicine");
            }

            else if (action.equals("POP")) {

                if (stack.isEmpty()) {
                    System.out.println("❌ Lỗi tại bước " + (i + 1)
                            + ": POP khi Stack đang rỗng.");
                    return false;
                }

                stack.pop();
            }

            else {
                System.out.println("❌ Hành động không hợp lệ tại bước "
                        + (i + 1));
                return false;
            }
        }

        if (!stack.isEmpty()) {
            System.out.println("❌ Kết thúc ca trực nhưng vẫn còn "
                    + stack.size() + " thuốc chưa hoàn tất.");
            return false;
        }

        System.out.println("✅ Quy trình hợp lệ.");
        return true;
    }

    // Reset stack
    public void reset() {
        stack.clear();
    }
}
