package ultil;

/**
 * Utility class để format giao diện Console
 * Tạo giao diện căn lề, gọn gàng
 */
public class ConsoleUI {

    // Kích thước Console
    public static final int WIDTH = 100;
    public static final String BORDER = "=".repeat(WIDTH);
    public static final String LINE = "-".repeat(WIDTH);

    /**
     * In tiêu đề lớn
     */
    public static void printHeader(String title) {
        System.out.println();
        System.out.println(BORDER);
        System.out.println(centerText(title));
        System.out.println(BORDER);
    }

    /**
     * In tiêu đề nhỏ
     */
    public static void printSubHeader(String title) {
        System.out.println();
        System.out.println("╔" + "═".repeat(WIDTH - 2) + "╗");
        System.out.println("║ " + title);
        System.out.println("╚" + "═".repeat(WIDTH - 2) + "╝");
    }

    /**
     * In section
     */
    public static void printSection(String title) {
        System.out.println();
        System.out.println("### " + title + " ###");
        System.out.println(LINE);
    }

    /**
     * In menu
     */
    public static void printMenu(String title, String[] options) {
        printSubHeader(title);
        for (String option : options) {
            System.out.println(option);
        }
        System.out.println();
    }

    /**
     * In thông báo success
     */
    public static void printSuccess(String message) {
        System.out.println(message);
    }

    /**
     * In thông báo error
     */
    public static void printError(String message) {
        System.out.println(message);
    }

    /**
     * In thông báo warning
     */
    public static void printWarning(String message) {
        System.out.println(message);
    }

    /**
     * In thông báo info
     */
    public static void printInfo(String message) {
        System.out.println(message);
    }

    /**
     * In bảng header
     */
    public static void printTableHeader(String... columns) {
        StringBuilder sb = new StringBuilder();
        for (String col : columns) {
            sb.append(String.format("%-" + (WIDTH / columns.length - 2) + "s | ", col));
        }
        System.out.println(sb.toString());
        System.out.println(LINE);
    }

    /**
     * In bảng row
     */
    public static void printTableRow(String... values) {
        StringBuilder sb = new StringBuilder();
        for (String val : values) {
            sb.append(String.format("%-" + (WIDTH / values.length - 2) + "s | ", val));
        }
        System.out.println(sb.toString());
    }

    /**
     * Căn giữa text
     */
    public static String centerText(String text) {
        if (text.length() >= WIDTH) {
            return text;
        }
        int padding = (WIDTH - text.length()) / 2;
        return " ".repeat(padding) + text;
    }

    /**
     * In blank line
     */
    public static void printBlank() {
        System.out.println();
    }

    /**
     * In info box
     */
    public static void printInfoBox(String... lines) {
        System.out.println("┌" + "─".repeat(WIDTH - 2) + "┐");
        for (String line : lines) {
            System.out.println("│ " + String.format("%-" + (WIDTH - 4) + "s", line) + " │");
        }
        System.out.println("└" + "─".repeat(WIDTH - 2) + "┘");
    }

    /**
     * Clear screen
     */
    public static void clearScreen() {
        // Không clear được trên tất cả terminal, nên skip
        // System.out.print("\033[H\033[2J");
        // System.out.flush();
    }

    /**
     * Print back to menu option
     */
    public static void printBackOption() {
        System.out.println();
        System.out.println("0. Quay lại menu chính");
    }

    /**
     * Print separator
     */
    public static void printSeparator() {
        System.out.println(LINE);
    }

    /**
     * Format tiền tệ
     */
    public static String formatCurrency(double amount) {
        return String.format("%,.0f VND", amount);
    }

    /**
     * Format số
     */
    public static String formatNumber(double number) {
        return String.format("%,.2f", number);
    }

    /**
     * Format ngày
     */
    public static String formatDate(java.sql.Timestamp timestamp) {
        if (timestamp == null) return "N/A";
        return new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(timestamp);
    }

    /**
     * In status badge
     */
    public static String getStatusBadge(String status) {
        switch (status.toUpperCase()) {
            case "PENDING":
                return "PENDING";
            case "PREPARING":
                return "⚙PREPARING";
            case "DONE":
                return "DONE";
            case "BOOKED":
                return "BOOKED";
            case "USING":
                return "USING";
            case "CANCELLED":
                return "CANCELLED";
            case "AVAILABLE":
                return "AVAILABLE";
            case "MAINTENANCE":
                return "MAINTENANCE";
            default:
                return status;
        }
    }
}

