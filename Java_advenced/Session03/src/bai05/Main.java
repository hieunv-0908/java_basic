package bai05;

import java.util.List;
import java.util.Comparator;

record User(String username, String email, String status) {}

public class Main {
    public static void main(String[] args) {

        List<User> users = List.of(
                new User("alexander", "a@gmail.com", "ACTIVE"),
                new User("bob", "b@gmail.com", "ACTIVE"),
                new User("charlotte", "c@gmail.com", "ACTIVE"),
                new User("tom", "t@gmail.com", "ACTIVE"),
                new User("Benjamin", "be@gmail.com", "ACTIVE"),
                new User("anna", "an@gmail.com", "ACTIVE")
        );

        users.stream()
                .sorted(Comparator.comparingInt(user -> user.username().length()).reversed())
                .limit(3)
                .map(User::username)
                .forEach(System.out::println);
    }
}