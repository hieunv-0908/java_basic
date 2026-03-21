package bai02;

public class UserService {
    public boolean checkRegistrationAge(int age) {

        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }

        return age >= 18;
    }
}
