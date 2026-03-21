package bai03;

public class Main {
    public static void main(String[] args) {

        User user = new User("123456");

        // Gọi default method
        System.out.println("Authenticated: " + user.isAuthenticated());

        // Gọi static method từ interface
        String encrypted = Authenticatable.encrypt("123456");
        System.out.println("Encrypted password: " + encrypted);
    }
}
