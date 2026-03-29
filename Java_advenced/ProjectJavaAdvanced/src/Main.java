import interfaceRole.InterfaceAdmin;
import interfaceRole.InterfaceCustomer;
import interfaceRole.InterfaceStaff;
import interfaceRole.InterfaceUser;
import model.User;
import model.Admin;
import model.Staff;
import model.Customer;

public class Main {
    public static void main(String[] args) {
        User currentUser = null;

        while (true) {
            if (currentUser == null) {
                currentUser = InterfaceUser.handleMainMenu();
            } else {
                boolean isStayLoggedIn = false;
                
                if (currentUser instanceof Admin) {
                    isStayLoggedIn = InterfaceAdmin.handleAdminMenu((Admin) currentUser);
                } else if (currentUser instanceof Staff) {
                    isStayLoggedIn = InterfaceStaff.handleStaffMenu((Staff) currentUser);
                } else if (currentUser instanceof Customer) {
                    isStayLoggedIn = InterfaceCustomer.handleCustomerMenu((Customer) currentUser);
                }

                if (!isStayLoggedIn) {
                    currentUser = null;
                }
            }
        }
    }
}