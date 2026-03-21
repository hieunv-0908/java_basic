import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class b4 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhập mã thẻ thư viện: ");
        String cardCode = sc.nextLine().trim();

        String regexFull = "^[A-Z]{2}\\d{9}$";

        Pattern fullPattern = Pattern.compile(regexFull);
        Matcher fullMatcher = fullPattern.matcher(cardCode);

        if (fullMatcher.matches()) {
            System.out.println("Mã thẻ hợp lệ ✔");
        } else {
            if (!Pattern.matches("^[A-Z]{2}.*", cardCode)) {
                System.out.println("Lỗi: Thiếu tiền tố 2 chữ cái viết hoa");
            }
            else if (!Pattern.matches("^[A-Z]{2}\\d{4}.*", cardCode)) {
                System.out.println("Lỗi: Năm vào học không hợp lệ");
            }
            else if (!Pattern.matches("^[A-Z]{2}\\d{4}\\d{5}$", cardCode)) {
                System.out.println("Lỗi: Thiếu hoặc sai 5 chữ số cuối");
            }
            else {
                System.out.println("Lỗi: Định dạng mã thẻ không hợp lệ");
            }
        }
    }
}
