package bai02;

@FunctionalInterface
interface PasswordValidator {
    boolean isValid(String password);
}
