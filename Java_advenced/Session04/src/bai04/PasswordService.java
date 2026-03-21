package bai04;

public class PasswordService {
    public String evaluatePasswordStrength(String password) {

        if (password == null || password.length() < 8) {
            return "Yếu";
        }

        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[^a-zA-Z0-9].*");

        if (hasUpper && hasLower && hasDigit && hasSpecial) {
            return "Mạnh";
        }

        if (hasUpper || hasLower || hasDigit || hasSpecial) {
            return "Trung bình";
        }

        return "Yếu";
    }
}
