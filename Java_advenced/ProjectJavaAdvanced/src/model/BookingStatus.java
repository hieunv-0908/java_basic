package model;

public enum BookingStatus {
    BOOKED("BOOKED"),
    USING("USING"),
    DONE("DONE"),
    CANCELLED("CANCELLED");

    private String statusCode;

    BookingStatus(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public static BookingStatus fromStatus(String statusCode) {
        for (BookingStatus s : BookingStatus.values()) {
            if (s.statusCode.equals(statusCode)) return s;
        }
        throw new IllegalArgumentException("Invalid status: " + statusCode);
    }
}
