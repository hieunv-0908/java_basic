package model;

import java.sql.Timestamp;
import java.util.Date;

public class Customer extends User {
    public Customer(int userId, String userCode, String userName, String fullName, String password, int age, String email, String phone, Role role, Double balance, Date created_at) {
        super(userId, userCode, userName, fullName, password, age, email, phone, role, balance, created_at);
    }

    public Customer() {
    }

    public Customer(int userId, String userCode, String username, String password, String fullName, int age, String email, String phone, Double balance, Timestamp createdAt) {
        super(userId, userCode, username, fullName, password, age, email, phone, Role.USER, balance, new Date(createdAt.getTime()));
    }
}
