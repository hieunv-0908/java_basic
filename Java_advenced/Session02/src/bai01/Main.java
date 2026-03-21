package bai01;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        // 1. Predicate: kiểm tra bai01.User có phải Admin hay không
        Predicate<User> isAdmin = user -> user.getRole().equals("ADMIN");

        // 2. Function: chuyển bai01.User thành chuỗi username
        Function<User, String> getUsername = user -> user.getUsername();

        // 3. Consumer: in thông tin bai01.User ra màn hình
        Consumer<User> printUser = user -> System.out.println(user);

        // 4. Supplier: tạo bai01.User mới với giá trị mặc định
        Supplier<User> createUser = () -> new User("guest", "USER");

        User user1 = new User("Hieu", "ADMIN");

        System.out.println("Is Admin: " + isAdmin.test(user1));
        System.out.println("Username: " + getUsername.apply(user1));
        printUser.accept(user1);

        User defaultUser = createUser.get();
        printUser.accept(defaultUser);
    }
}