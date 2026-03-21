package Bai04;

import bai01.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<User> users = List.of(
                new User("alice", "alice@gmail.com", "ACTIVE"),
                new User("bob", "bob@yahoo.com", "INACTIVE"),
                new User("alice", "alice2@gmail.com", "ACTIVE"),
                new User("charlie", "charlie@gmail.com", "ACTIVE")
        );

        Collection<User> uniqueUsers = users.stream()
                .collect(Collectors.toMap(
                        User::username,     // key
                        user -> user,       // value
                        (u1, u2) -> u1      // nếu trùng thì giữ u1
                ))
                .values();

        uniqueUsers.forEach(System.out::println);
    }
}
