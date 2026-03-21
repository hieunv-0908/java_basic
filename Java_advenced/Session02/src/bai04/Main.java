package bai04;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) {

        List<User> users = Arrays.asList(
                new User("Hieu"),
                new User("Nam"),
                new User("Linh")
        );

        // 1. Lambda: (user) -> user.getUsername()
        users.stream()
                .map(User::getUsername)
                .forEach(System.out::println);

        // 2. Lambda: (s) -> System.out.println(s)
        users.stream()
                .map(User::getUsername)
                .forEach(System.out::println);

        // 3. Lambda: () -> new User()
        Supplier<User> createUser = User::new;

        User newUser = createUser.get();
        System.out.println(newUser.getUsername());
    }
}
