package model;

public enum Role {
    ADMIN("Admin"),
    USER("User"),
    STAFF("Staff");

    private String roleCode;

    Role(String roleCode) {
        this.roleCode = roleCode;
    }

    public static Role fromRole(String role) {
        for (Role r : Role.values()) {
            if (r.roleCode.equals(role)) return r;
        }
        throw new IllegalArgumentException("Invalid role: " + role);
    }
};
