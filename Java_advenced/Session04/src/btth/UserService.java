package btth;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    List<User> users = new ArrayList<>();

    // thêm user
    public void addUser(User user){
        if(user.getName().isBlank()){
            throw new IllegalArgumentException("Username cannot be null");
        }
        users.add(user);
    }

    // tìm kiếm theo id
    public User findUserById(int id){
        return users.stream().filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean isValidEmail(String email){
        String regexEmail = "[a-zA-Z0-9]+@[a-zA-Z]+\\.[a-zA-Z]{2,4}";
        return email.matches(regexEmail);
    }
}
