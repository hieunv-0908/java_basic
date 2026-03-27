package model;

public enum StatusMachine {
    STANDARD("AVAILABLE"),
    VIP("IN_USE"),
    STREAM("MAINTENANCE");

    private String statusCode;

    StatusMachine(String statusCode) {
        this.statusCode = statusCode;
    }

    public static StatusMachine fromStatus(String statusCode) {
        for (StatusMachine s : StatusMachine.values()) {
            if (s.statusCode.equals(statusCode)) return s;
        }
        throw new IllegalArgumentException("Invalid role: " + statusCode);
    }
}
