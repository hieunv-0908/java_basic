package model;

import java.util.Date;

public abstract class User {
    private int userId;
    private String userCode;
    private String userName;
    private String fullName;
    private String password;
    private int age;
    private String email;
    private String phone;
    private Role role;
    private Double balance;
    private Date created_at;

    public User(int userId, String userCode, String userName, String fullName, String password, int age, String email, String phone, Role role, Double balance, Date created_at) {
        this.userId = userId;
        this.userCode = userCode;
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.balance = balance;
        this.created_at = created_at;
    }

    public User() {
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
