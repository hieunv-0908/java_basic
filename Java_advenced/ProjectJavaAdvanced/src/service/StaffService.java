package service;

import dao.UserDAO;
import model.User;

import java.util.List;

public class StaffService {
    UserDAO userDAO = new UserDAO();
    List<User> customers = UserDAO.queryDb("CUSTOMER");

    public StaffService() {
    }

    public boolean findCustomer() {
        
        return true;
    }
}
