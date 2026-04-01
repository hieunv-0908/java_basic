package ultil;

import java.util.Scanner;

/**
 * Utility class để validate input từ user
 * Tránh crash khi nhập sai kiểu dữ liệu
 */
public class InputValidator {
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Nhập chuỗi text an toàn
     *
     * @param prompt Thông báo nhập
     * @return Chuỗi text đã trim
     */
    public static String readString(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("⚠️  Không được để trống! Vui lòng nhập lại.");
                    continue;
                }
                return input;
            } catch (Exception e) {
                System.out.println("❌ Lỗi nhập dữ liệu: " + e.getMessage());
                scanner.nextLine(); // Clear buffer
            }
        }
    }

    /**
     * Nhập chuỗi text cho phép rỗng
     */
    public static String readStringOptional(String prompt) {
        try {
            System.out.print(prompt);
            return scanner.nextLine().trim();
        } catch (Exception e) {
            System.out.println("❌ Lỗi nhập dữ liệu: " + e.getMessage());
            scanner.nextLine();
            return "";
        }
    }

    /**
     * Nhập số nguyên an toàn
     *
     * @param prompt Thông báo nhập
     * @return Số nguyên đã validate
     */
    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("⚠️  Không được để trống! Vui lòng nhập số.");
                    continue;
                }
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Lỗi: Vui lòng nhập một số nguyên hợp lệ!");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("❌ Lỗi nhập dữ liệu: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }

    /**
     * Nhập số nguyên dương
     */
    public static int readPositiveInt(String prompt) {
        while (true) {
            int num = readInt(prompt);
            if (num > 0) {
                return num;
            }
            System.out.println("⚠️  Số phải lớn hơn 0! Vui lòng nhập lại.");
        }
    }

    /**
     * Nhập số thực (double) an toàn
     */
    public static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("⚠️  Không được để trống! Vui lòng nhập số.");
                    continue;
                }
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Lỗi: Vui lòng nhập một số hợp lệ!");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("❌ Lỗi nhập dữ liệu: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }

    /**
     * Nhập số thực dương
     */
    public static double readPositiveDouble(String prompt) {
        while (true) {
            double num = readDouble(prompt);
            if (num >= 0) {
                return num;
            }
            System.out.println("⚠️  Số phải >= 0! Vui lòng nhập lại.");
        }
    }

    /**
     * Nhập lựa chọn (1-n)
     */
    public static int readChoice(String prompt, int min, int max) {
        while (true) {
            int choice = readInt(prompt);
            if (choice >= min && choice <= max) {
                return choice;
            }
            System.out.println("⚠️  Vui lòng chọn từ " + min + " đến " + max);
        }
    }

    /**
     * Xác nhận Yes/No
     */
    public static boolean readConfirm(String prompt) {
        while (true) {
            System.out.print(prompt + " (Y/N): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("Y") || input.equals("YES")) {
                return true;
            } else if (input.equals("N") || input.equals("NO")) {
                return false;
            }
            System.out.println("⚠️  Vui lòng nhập Y hoặc N!");
        }
    }

    /**
     * Validate chuỗi không trống
     */
    public static String validateNotEmpty(String input, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " không được để trống!");
        }
        return input.trim();
    }

    /**
     * Validate độ dài chuỗi
     */
    public static String validateLength(String input, int maxLength, String fieldName) {
        validateNotEmpty(input, fieldName);
        if (input.length() > maxLength) {
            throw new IllegalArgumentException(fieldName + " không được vượt quá " + maxLength + " ký tự!");
        }
        return input;
    }

    /**
     * Validate số >= 0
     */
    public static double validateNonNegative(double value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " phải >= 0!");
        }
        return value;
    }

    /**
     * Validate số > 0
     */
    public static int validatePositive(int value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " phải > 0!");
        }
        return value;
    }

    /**
     * Clear input buffer
     */
    public static void clearBuffer() {
        try {
            scanner.nextLine();
        } catch (Exception e) {
            // Ignore
        }
    }

    /**
     * Pause console
     */
    public static void pause(String message) {
        System.out.print(message + " Nhấn Enter để tiếp tục...");
        try {
            scanner.nextLine();
        } catch (Exception e) {
            // Ignore
        }
    }

    public static void pause() {
        pause("✓ Hoàn thành!");
    }
}

