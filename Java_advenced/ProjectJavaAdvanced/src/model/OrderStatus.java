package model;

public enum OrderStatus {
    PENDING("PENDING"),
    PREPARING("PREPARING"),
    DONE("DONE");

    private String statusCode;

    OrderStatus(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public static OrderStatus fromStatus(String statusCode) {
        for (OrderStatus s : OrderStatus.values()) {
            if (s.statusCode.equals(statusCode)) return s;
        }
        throw new IllegalArgumentException("Invalid status: " + statusCode);
    }
}
