package service;

public class InvalidOrderIdException extends Exception  {
    public InvalidOrderIdException(String message) {
        super(message);
    }
}
