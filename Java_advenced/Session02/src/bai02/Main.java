package bai02;

public class Main {
    public static void main(String[] args) {
        // Lambda rút gọn
        PasswordValidator validator = password -> password.length() >= 8;

        System.out.println(validator.isValid("12345678"));
        System.out.println(validator.isValid("1234"));
    }
}
