package bai03;

public class Main {
    public static void main(String[] args) {
        UserRepository repo = new UserRepository();

        repo.findUserByUsername("alice")
                .ifPresentOrElse(
                        user -> System.out.println("Welcome " + user.username()),
                        () -> System.out.println("Guest login")
                );
    }
}
