package dao;

import model.Service;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class StaffDAO {
    List<User> list = UserDAO.queryDb("STAFF");
    
}
