package model;

import java.util.Date;

public class Customer extends User {
    public Customer(int userId, String userCode, String userName, String fullName, String password, int age, String email, String phone, Role role, Double balance, Date created_at) {
        super(userId, userCode, userName, fullName, password, age, email, phone, role, balance, created_at);
    }

    public Customer() {
    }
}
