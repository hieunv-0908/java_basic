

public class b3 {
    public static void  main(String args){
        String[] transactions = {
                "BK101 - 2026-01-29",
                "BK102 - 2026-01-29",
                "BK103 - 2026-01-29",
                "BK104 - 2026-01-29",
                "BK105 - 2026-01-29",
                "BK106 - 2026-01-29",
                "BK107 - 2026-01-29",
                "BK108 - 2026-01-29"
        };
        long startSB = System.currentTimeMillis();

        StringBuilder reportSB = new StringBuilder();
        reportSB.append("Thời gian hệ thống: ").append(java.time.LocalDateTime.now()).append("\n");

        for (String t : transactions) {
            reportSB.append(t).append("\n");
        }
        long endSB = System.currentTimeMillis();

        System.out.println("Báo cáo dùng StringBuilder:");
        System.out.println(reportSB.toString());
        System.out.println("Thời gian thực thi (StringBuilder): "
                + (endSB - startSB) + " ms");
    }
}
