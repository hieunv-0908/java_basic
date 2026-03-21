package bai03;

public interface Authenticatable {
    // 1. Phương thức trừu tượng
    String getPassword();

    // 2. Default method kiểm tra mật khẩu có rỗng hay không
    default boolean isAuthenticated() {
        return getPassword() != null && !getPassword().isEmpty();
    }

    // 3. Static method mô phỏng mã hóa mật khẩu
    static String encrypt(String rawPassword) {
        return "ENC_" + rawPassword;
    }
}
