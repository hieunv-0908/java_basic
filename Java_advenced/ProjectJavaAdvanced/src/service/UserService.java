package service;

import dao.UserDAO;
import model.Admin;
import model.Customer;
import model.Staff;
import model.User;

import java.util.List;

public class UserService {
    UserDAO userDAO = new UserDAO();

    public UserService() {
    }

    public boolean register(String userCode, String username, String password, String fullName, int age, String email, String phone) {
        // Validate input
        if (userCode == null || userCode.trim().isEmpty() ||
                username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                fullName == null || fullName.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                phone == null || phone.trim().isEmpty()) {
            System.out.println("All fields are required!");
            return false;
        }

        // Validate age
        if (age <= 0 || age > 120) {
            System.out.println("Invalid age!");
            return false;
        }

        // Check if username already exists
        if (UserDAO.isUsernameExists(username)) {
            System.out.println("Username already exists!");
            return false;
        }

        // Register user as customer by default
        return UserDAO.registerUser(userCode, username, password, fullName, age, email, phone, "customer");
    }

    public boolean createStaffAccount(String userCode, String username, String password, String fullName, int age, String email, String phone) {
        // Validate input
        if (userCode == null || userCode.trim().isEmpty() ||
                username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                fullName == null || fullName.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                phone == null || phone.trim().isEmpty()) {
            System.out.println("All fields are required!");
            return false;
        }

        // Validate age
        if (age <= 0 || age > 120) {
            System.out.println("Invalid age!");
            return false;
        }

        // Check if username already exists
        if (UserDAO.isUsernameExists(username)) {
            System.out.println("Username already exists!");
            return false;
        }

        // Create staff account
        return UserDAO.registerUser(userCode, username, password, fullName, age, email, phone, "staff");
    }

    public User login(String username, String password) {
        // Validate input
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            System.out.println("Username and password are required!");
            return null;
        }

        // Attempt login
        User user = UserDAO.loginUser(username, password);
        if (user == null) {
            System.out.println("Invalid username or password!");
        }
        return user;
    }

//    public List<Admin> getAllAdmins() {
//        return;
//    }
}
