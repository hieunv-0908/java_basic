package bai02;

import java.util.List;

record User(String username, String email, String status) {}

public class Main {
    public static void main(String[] args) {

        List<User> users = List.of(
                new User("alice", "alice@gmail.com", "ACTIVE"),
                new User("bob", "bob@yahoo.com", "INACTIVE"),
                new User("charlie", "charlie@gmail.com", "ACTIVE")
        );

        users.stream()
                .filter(user -> user.email().endsWith("@gmail.com"))
                .forEach(user -> System.out.println(user.username()));
    }
}