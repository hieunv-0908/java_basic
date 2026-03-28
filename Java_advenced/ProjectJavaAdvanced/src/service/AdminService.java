package service;

import dao.UserDAO;

public class AdminService {
    UserDAO userDAO = new UserDAO();

    public AdminService() {
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
}
