import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import bai01.*;

public class UserValidatorTest {
    @Test
    void TC01_validUsername() {

        UserValidator validator = new UserValidator();
        boolean result = validator.isValidUsername("user123");

        assertTrue(result);
    }

    @Test
    void TC02_usernameTooShort() {

        UserValidator validator = new UserValidator();
        boolean result = validator.isValidUsername("abc");

        assertFalse(result);
    }

    @Test
    void TC03_usernameContainsSpace() {

        UserValidator validator = new UserValidator();
        boolean result = validator.isValidUsername("user name");

        assertFalse(result);
    }

}
