import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import bai02.*;


public class UserServiceTest {

    @Test
    void TC01_age18_valid() {

        UserService service = new UserService();
        int age = 18;

        boolean result = service.checkRegistrationAge(age);

        assertEquals(true, result);
    }

    @Test
    void TC02_age17_invalid() {

        UserService service = new UserService();
        int age = 17;

        boolean result = service.checkRegistrationAge(age);

        assertEquals(false, result);
    }

    @Test
    void TC03_negativeAge_exception() {

        UserService service = new UserService();
        int age = -1;

        assertThrows(IllegalArgumentException.class, () -> {
            service.checkRegistrationAge(age);
        });
    }
}
