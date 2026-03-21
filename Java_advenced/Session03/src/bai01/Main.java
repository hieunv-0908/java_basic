package bai01;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<User> users = List.of(
                new User("alice", "alice@gmail.com", "ACTIVE"),
                new User("bob", "bob@gmail.com", "INACTIVE"),
                new User("charlie", "charlie@gmail.com", "ACTIVE")
        );

        users.forEach(user -> System.out.println(user));
    }
}
